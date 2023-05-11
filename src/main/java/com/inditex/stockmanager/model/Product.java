package com.inditex.stockmanager.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * Product Model
 */
@Entity
@Table(name = "Product")
@Setter
@Getter
public class Product implements Serializable {

    @Id
    private int id;
    @Column(nullable = false)
    private int sequence;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product_id")
    private Set<Size> sizes;


}
