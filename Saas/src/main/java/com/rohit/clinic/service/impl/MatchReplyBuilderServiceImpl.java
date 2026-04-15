package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.response.MatchResultResponse;
import com.rohit.clinic.service.MatchReplyBuilderService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MatchReplyBuilderServiceImpl implements MatchReplyBuilderService {

    @Override
    public String buildReplyMessage(List<MatchResultResponse> matches) {
        if (matches == null || matches.isEmpty()) {
            return "No matching buyer/seller found right now. Lead saved for manual review.";
        }

        MatchResultResponse first = matches.get(0);
        String productName = first.getProductName() != null ? first.getProductName() : "product";
        String sideText = resolveSideText(first);

        StringBuilder sb = new StringBuilder();
        sb.append("Found ")
                .append(matches.size())
                .append(" matching ")
                .append(sideText)
                .append(" for ")
                .append(productName)
                .append(":\n\n");

        for (int i = 0; i < matches.size(); i++) {
            MatchResultResponse match = matches.get(i);

            sb.append(i + 1).append(". ");

            if (match.getCompanyName() != null) {
                sb.append(match.getCompanyName());
            } else {
                sb.append("Unknown Company");
            }

            if (match.getCity() != null && !match.getCity().isBlank()) {
                sb.append(" - ").append(match.getCity());
            }

            if (match.getState() != null && !match.getState().isBlank()) {
                sb.append(", ").append(match.getState());
            }

            if (match.getPhone() != null && !match.getPhone().isBlank()) {
                sb.append(" - ").append(match.getPhone());
            }

            if (match.getRateHint() != null) {
                sb.append(" - Price: ").append(formatAmount(match.getRateHint()));
            }

            if (match.getMinQuantity() != null || match.getMaxQuantity() != null) {
                sb.append(" - Qty: ").append(buildQuantityText(match));
            }

            if (match.getUnitText() != null && !match.getUnitText().isBlank()) {
                sb.append(" ").append(match.getUnitText());
            }

            sb.append("\n");
        }

        return sb.toString().trim();
    }

    private String resolveSideText(MatchResultResponse first) {
        if (first.getMatchSide() == null) {
            return "parties";
        }

        return switch (first.getMatchSide()) {
            case BUYER -> "buyers";
            case SELLER -> "sellers";
        };
    }

    private String buildQuantityText(MatchResultResponse match) {
        if (match.getMinQuantity() != null && match.getMaxQuantity() != null) {
            return formatAmount(match.getMinQuantity()) + " to " + formatAmount(match.getMaxQuantity());
        }
        if (match.getMinQuantity() != null) {
            return "from " + formatAmount(match.getMinQuantity());
        }
        if (match.getMaxQuantity() != null) {
            return "up to " + formatAmount(match.getMaxQuantity());
        }
        return "-";
    }

    private String formatAmount(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }
}
