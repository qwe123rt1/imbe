package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.FindMatchesRequest;
import com.rohit.clinic.dto.response.MatchResultResponse;
import com.rohit.clinic.entity.IncomingLead;
import com.rohit.clinic.entity.InterestedSide;
import com.rohit.clinic.entity.MarketListing;
import com.rohit.clinic.entity.MatchSide;
import com.rohit.clinic.entity.Product;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.IncomingLeadRepository;
import com.rohit.clinic.repository.MarketListingRepository;
import com.rohit.clinic.service.MatchFindingService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class MatchFindingServiceImpl implements MatchFindingService {

    private static final int DEFAULT_LIMIT = 5;
    private static final int MIN_LIMIT = 3;
    private static final int MAX_LIMIT = 10;

    private final IncomingLeadRepository incomingLeadRepository;
    private final MarketListingRepository marketListingRepository;

    public MatchFindingServiceImpl(
            IncomingLeadRepository incomingLeadRepository,
            MarketListingRepository marketListingRepository
    ) {
        this.incomingLeadRepository = incomingLeadRepository;
        this.marketListingRepository = marketListingRepository;
    }

    @Override
    public List<MatchResultResponse> findTopMatches(FindMatchesRequest request) {
        IncomingLead lead = incomingLeadRepository.findById(request.getIncomingLeadId())
                .orElseThrow(() -> new NotFoundException("Incoming lead not found"));

        MatchSide oppositeSide = getOppositeSide(lead.getInterestedSide());
        if (oppositeSide == null) {
            return List.of();
        }

        List<MarketListing> listings = fetchCandidateListings(lead, oppositeSide);
        if (listings.isEmpty()) {
            return List.of();
        }

        List<MarketListing> filtered = listings.stream()
                .filter(this::isValidListing)
                .filter(listing -> quantityMatches(lead.getQuantity(), listing.getMinQuantity(), listing.getMaxQuantity()))
                .toList();

        if (filtered.isEmpty()) {
            return List.of();
        }

        Comparator<MarketListing> comparator = buildPriceComparator(lead.getInterestedSide());

        List<MarketListing> sorted = filtered.stream()
                .sorted(comparator)
                .limit(normalizeLimit(request.getLimit()))
                .toList();

        List<MatchResultResponse> responses = new ArrayList<>();
        int rank = 1;
        for (MarketListing listing : sorted) {
            responses.add(mapToResponse(listing, rank++));
        }

        return responses;
    }

    private MatchSide getOppositeSide(InterestedSide side) {
        if (side == null) {
            return null;
        }

        return switch (side) {
            case BUYER -> MatchSide.SELLER;
            case SELLER -> MatchSide.BUYER;
            default -> null;
        };
    }

    private List<MarketListing> fetchCandidateListings(IncomingLead lead, MatchSide oppositeSide) {
        Product product = lead.getProduct();

        if (product != null) {
            List<MarketListing> exactProductMatches =
                    marketListingRepository.findByProduct_IdAndSideAndIsActiveTrue(product.getId(), oppositeSide);

            if (!exactProductMatches.isEmpty()) {
                return exactProductMatches;
            }
        }

        return marketListingRepository.findBySideAndIsActiveTrue(oppositeSide);
    }

    private boolean isValidListing(MarketListing listing) {
        return listing != null
                && Boolean.TRUE.equals(listing.getIsActive())
                && listing.getCompany() != null
                && Boolean.TRUE.equals(listing.getCompany().getIsActive());
    }

    private boolean quantityMatches(BigDecimal leadQty, BigDecimal minQty, BigDecimal maxQty) {
        if (leadQty == null) {
            return true;
        }

        boolean minOk = minQty == null || leadQty.compareTo(minQty) >= 0;
        boolean maxOk = maxQty == null || leadQty.compareTo(maxQty) <= 0;

        return minOk && maxOk;
    }

    private Comparator<MarketListing> buildPriceComparator(InterestedSide leadSide) {
        Comparator<MarketListing> nullSafePriceAsc =
                Comparator.comparing(
                        MarketListing::getRateHint,
                        Comparator.nullsLast(BigDecimal::compareTo)
                );

        Comparator<MarketListing> nullSafePriceDesc =
                Comparator.comparing(
                        MarketListing::getRateHint,
                        Comparator.nullsLast(Comparator.reverseOrder())
                );

        Comparator<MarketListing> locationComparator =
                Comparator.comparing((MarketListing listing) -> normalize(listing.getCity()))
                        .thenComparing(listing -> normalize(listing.getState()))
                        .thenComparing(this::safeCompanyName);

        if (leadSide == InterestedSide.BUYER) {
            return nullSafePriceAsc.thenComparing(locationComparator);
        }

        if (leadSide == InterestedSide.SELLER) {
            return nullSafePriceDesc.thenComparing(locationComparator);
        }

        return locationComparator;
    }

    private MatchResultResponse mapToResponse(MarketListing listing, int rank) {
        MatchResultResponse response = new MatchResultResponse();

        response.setMarketListingId(listing.getId());

        if (listing.getCompany() != null) {
            response.setCompanyId(listing.getCompany().getId());
            response.setCompanyName(listing.getCompany().getCompanyName());
            response.setContactPerson(listing.getCompany().getContactPerson());
            response.setPhone(listing.getCompany().getPhone());
            response.setCity(listing.getCompany().getCity());
            response.setState(listing.getCompany().getState());
        }

        if (listing.getProduct() != null) {
            response.setProductId(listing.getProduct().getId());
            response.setProductName(listing.getProduct().getProductName());
        }

        response.setMatchSide(listing.getSide());
        response.setMinQuantity(listing.getMinQuantity());
        response.setMaxQuantity(listing.getMaxQuantity());
        response.setUnitText(listing.getUnitText());
        response.setRateHint(listing.getRateHint());
        response.setPriorityRank(rank);

        return response;
    }

    private int normalizeLimit(Integer requestedLimit) {
        if (requestedLimit == null) {
            return DEFAULT_LIMIT;
        }
        if (requestedLimit < MIN_LIMIT) {
            return MIN_LIMIT;
        }
        return Math.min(requestedLimit, MAX_LIMIT);
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }

    private String safeCompanyName(MarketListing listing) {
        if (listing.getCompany() == null || listing.getCompany().getCompanyName() == null) {
            return "";
        }
        return listing.getCompany().getCompanyName().toLowerCase(Locale.ROOT);
    }
}
