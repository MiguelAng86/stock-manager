package com.inditex.stockmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Size Model
 */

@Entity
@Table(name = "Stock")
@IdClass(SizeId.class)
@Setter
@Getter
public class Stock implements Serializable {

    @Id
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    @JsonIgnore
    private Size size_id;
    @Column(nullable = false)
    private int quantity;
}
