package com.rohit.clinic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindMatchesRequest {

    private Long incomingLeadId;
    private Integer limit;

    public Long getIncomingLeadId() {
        return incomingLeadId;
    }

    public void setIncomingLeadId(Long incomingLeadId) {
        this.incomingLeadId = incomingLeadId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
