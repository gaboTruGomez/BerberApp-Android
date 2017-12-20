package com.berber.berberapp.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gabotrugomez on 11/6/17.
 */

public class Beer implements Serializable {
    private String name;
    private Double price;
    private String description;
    private String imgUrl;
    private boolean singleBeer;
    private boolean active = true;
    private int singleBeerAmount;
    private Double alcoholic_grade;
    private int bitterness_units = -1;
    private String malta;
    private String style;
    private String fermentation;
    private String pairing = "";
    private List<String> presentations;

    public Beer() {
    }

    public Beer(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isSingleBeer() {
        return singleBeer;
    }

    public void setSingleBeer(boolean singleBeer) {
        this.singleBeer = singleBeer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSingleBeerAmount() {
        return singleBeerAmount;
    }

    public void setSingleBeerAmount(int singleBeerAmount) {
        this.singleBeerAmount = singleBeerAmount;
    }

    public Double getAlcoholic_grade() {
        return alcoholic_grade;
    }

    public void setAlcoholic_grade(Double alcoholic_grade) {
        this.alcoholic_grade = alcoholic_grade;
    }

    public int getBitterness_units() {
        return bitterness_units;
    }

    public void setBitterness_units(int bitterness_units) {
        this.bitterness_units = bitterness_units;
    }

    public String getMalta() {
        return malta;
    }

    public void setMalta(String malta) {
        this.malta = malta;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getFermentation() {
        return fermentation;
    }

    public void setFermentation(String fermentation) {
        this.fermentation = fermentation;
    }

    public String getPairing() {
        return pairing;
    }

    public void setPairing(String pairing) {
        this.pairing = pairing;
    }

    public List<String> getPresentations() {
        return presentations;
    }

    public void setPresentations(List<String> presentations) {
        this.presentations = presentations;
    }
}
