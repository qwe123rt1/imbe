package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.ParseWhatsAppMessageRequest;
import com.rohit.clinic.dto.response.ParsedLeadResponse;
import com.rohit.clinic.entity.InterestedSide;
import com.rohit.clinic.entity.ParseStatus;
import com.rohit.clinic.entity.Product;
import com.rohit.clinic.repository.ProductRepository;
import com.rohit.clinic.service.LeadParsingService;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class LeadParsingServiceImpl implements LeadParsingService {

    private final ProductRepository productRepository;

    public LeadParsingServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ParsedLeadResponse parseMessage(ParseWhatsAppMessageRequest request) {
        String raw = request != null ? request.getMessageText() : null;
        String text = normalize(raw);

        ParsedLeadResponse response = new ParsedLeadResponse();
        response.setInterestedSide(detectSide(text));
        response.setQuantity(extractQuantity(text));
        response.setUnitText(extractUnit(text));
        response.setProductText(extractProduct(text));
        response.setLocationFrom(extractLocationFrom(text));
        response.setLocationTo(extractLocationTo(text));

        int confidence = calculateConfidence(response);
        response.setParseConfidence(confidence);
        response.setParseStatus(determineParseStatus(confidence));
        response.setRemarks(buildRemarks(response));

        return response;
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }

        return value.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private InterestedSide detectSide(String text) {
        boolean buyHint = containsAny(text, "need", "required", "want", "looking for", "purchase", "buy");
        boolean sellHint = containsAny(text, "have", "available", "for sale", "sale", "supply", "can supply", "selling");

        if (buyHint && sellHint) {
            return InterestedSide.BOTH;
        }
        if (buyHint) {
            return InterestedSide.BUYER;
        }
        if (sellHint) {
            return InterestedSide.SELLER;
        }
        return InterestedSide.UNKNOWN;
    }

    private BigDecimal extractQuantity(String text) {
        Matcher matcher = Pattern.compile("\\b(\\d+(?:\\.\\d+)?)(?:\\s*(?:mt|tonnes?|tons?|kg|quintals?|trucks?|bags?))?\\b")
                .matcher(text);
        if (matcher.find()) {
            try {
                return new BigDecimal(matcher.group(1));
            } catch (NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    private String extractUnit(String text) {
        String compactUnit = extractUnitFromQuantityPhrase(text);
        if (compactUnit != null) {
            return compactUnit;
        }

        if (containsWord(text, "mt")) {
            return "MT";
        }
        if (containsWord(text, "ton")) {
            return "TON";
        }
        if (containsWord(text, "tonne")) {
            return "TONNE";
        }
        if (containsWord(text, "kg")) {
            return "KG";
        }
        if (containsWord(text, "quintal")) {
            return "QUINTAL";
        }
        if (containsWord(text, "truck")) {
            return "TRUCK";
        }
        if (containsWord(text, "bags")) {
            return "BAGS";
        }
        if (containsWord(text, "bag")) {
            return "BAG";
        }
        return null;
    }

    private String extractUnitFromQuantityPhrase(String text) {
        Matcher matcher = Pattern.compile("\\b\\d+(?:\\.\\d+)?\\s*(mt|tonnes?|tons?|kg|quintals?|trucks?|bags?)\\b")
                .matcher(text);
        if (!matcher.find()) {
            return null;
        }

        String unit = matcher.group(1);
        return switch (unit) {
            case "mt" -> "MT";
            case "ton", "tons" -> "TON";
            case "tonne", "tonnes" -> "TONNE";
            case "kg" -> "KG";
            case "quintal", "quintals" -> "QUINTAL";
            case "truck", "trucks" -> "TRUCK";
            case "bag" -> "BAG";
            case "bags" -> "BAGS";
            default -> null;
        };
    }

    private String extractProduct(String text) {
        List<Product> products = productRepository.findByIsActiveTrue();

        return products.stream()
                .filter(product -> product.getProductName() != null && !product.getProductName().isBlank())
                .sorted(Comparator.comparingInt((Product p) -> p.getProductName().length()).reversed())
                .map(Product::getProductName)
                .filter(productName -> {
                    String normalizedProductName = normalize(productName);
                    return !normalizedProductName.isBlank() && containsPhrase(text, normalizedProductName);
                })
                .findFirst()
                .orElse(null);
    }

    private String extractLocationFrom(String text) {
        String fromLocation = extractWordAfter(text, "from");
        if (fromLocation != null) {
            return fromLocation;
        }

        if (containsWord(text, "pickup")) {
            return extractWordAfter(text, "pickup");
        }

        return null;
    }

    private String extractLocationTo(String text) {
        String toLocation = extractWordAfter(text, "to");
        if (toLocation != null) {
            return toLocation;
        }

        String inLocation = extractWordAfter(text, "in");
        if (inLocation != null) {
            return inLocation;
        }

        return null;
    }

    private String extractWordAfter(String text, String keyword) {
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b\\s+([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private boolean containsAny(String text, String... values) {
        for (String value : values) {
            if (containsPhrase(text, value)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsWord(String text, String word) {
        return Pattern.compile("\\b" + Pattern.quote(word) + "\\b").matcher(text).find();
    }

    private boolean containsPhrase(String text, String phrase) {
        String normalizedPhrase = normalize(phrase);
        return !normalizedPhrase.isBlank() && text.contains(normalizedPhrase);
    }

    private int calculateConfidence(ParsedLeadResponse response) {
        int score = 0;

        if (response.getInterestedSide() != null && response.getInterestedSide() != InterestedSide.UNKNOWN) {
            score += 25;
        }
        if (response.getProductText() != null && !response.getProductText().isBlank()) {
            score += 25;
        }
        if (response.getQuantity() != null) {
            score += 20;
        }
        if (response.getUnitText() != null && !response.getUnitText().isBlank()) {
            score += 10;
        }
        if ((response.getLocationFrom() != null && !response.getLocationFrom().isBlank())
                || (response.getLocationTo() != null && !response.getLocationTo().isBlank())) {
            score += 20;
        }

        return score;
    }

    private ParseStatus determineParseStatus(int confidence) {
        if (confidence >= 80) {
            return ParseStatus.PARSED_HIGH_CONFIDENCE;
        }
        if (confidence >= 50) {
            return ParseStatus.PARSED_LOW_CONFIDENCE;
        }
        return ParseStatus.NEED_MANUAL_REVIEW;
    }

    private String buildRemarks(ParsedLeadResponse response) {
        if (response.getParseStatus() == ParseStatus.NEED_MANUAL_REVIEW) {
            return "Low confidence parse. Manual review needed.";
        }
        return "Parsed from WhatsApp text.";
    }
}
