package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Boolean refrigerationRequired;

    @Column
    private String createdBy;

    @Column
    private Integer unitQuantity;

    @Column
    private Long createdAt;

    @Column
    private Long updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type", referencedColumnName = "id")
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "packaging", referencedColumnName = "id")
    private Packaging packaging;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state", referencedColumnName = "id")
    private State state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit", referencedColumnName = "id")
    private Unit unit;

}
