package com.inditex.stockmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Size Model
 */
@Entity
@Table(name = "Size")
@Setter
@Getter
public class Size implements Serializable {

    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product_id;
    @Column(nullable = false)
    private boolean backsoon;
    @Column(nullable = false)
    private boolean special;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "size_id")
    private Stock stock;
}
