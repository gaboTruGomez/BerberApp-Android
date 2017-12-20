package com.berber.berberapp.Models;

import java.io.Serializable;

/**
 * Created by gabotrugomez on 12/6/17.
 */

public class Coupon implements Serializable {
    private String id;
    private String type;
    private Double amount;

    public Coupon() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
