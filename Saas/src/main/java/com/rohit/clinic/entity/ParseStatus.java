package com.rohit.clinic.entity;

public enum ParseStatus {
    NOT_PROCESSED,
    PARSED_HIGH_CONFIDENCE,
    PARSED_LOW_CONFIDENCE,
    NEED_MANUAL_REVIEW,
    FAILED
}
