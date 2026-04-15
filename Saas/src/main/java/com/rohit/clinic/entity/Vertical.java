package com.rohit.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "verticals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vertical extends BaseEntity {

    @Column(name = "vertical_name")
    private String verticalName;

    @Column(name = "vertical_code", length = 100)
    private String verticalCode;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_active")
    private Boolean isActive;
}
