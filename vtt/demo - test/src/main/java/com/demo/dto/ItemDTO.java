package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {
    private Long id;

    private String name;

    private Boolean refrigerationRequired;

    private String createdBy;

    private Integer unitQuantity;

    private Long createdAt;

    private Long updatedAt;

    private TypeDTO type;

    private PackagingDTO packaging;

    private StateDTO state;

    private UnitDTO unit;
}
