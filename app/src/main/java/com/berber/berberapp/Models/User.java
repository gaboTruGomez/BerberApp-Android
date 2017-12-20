package com.berber.berberapp.Models;

import java.util.ArrayList;

/**
 * Created by gabotrugomez on 11/21/17.
 */

public class User {
    private int ordersMade;
    //private String username;
    private ArrayList<Order> orders;

    public User() {
    }

    public int getOrdersMade() {
        return ordersMade;
    }

    public void setOrdersMade(int ordersMade) {
        this.ordersMade = ordersMade;
    }
/*
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
*/
    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
