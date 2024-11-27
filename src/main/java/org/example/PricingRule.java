package org.example;

public class PricingRule {

    private int unitPrice;
    private int specialPrice;
    private int specialQuantity;

    public PricingRule(int unitPrice, int specialPrice, int specialQuantity) {
        this.unitPrice = unitPrice;
        this.specialPrice = specialPrice;
        this.specialQuantity = specialQuantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getSpecialPrice() {
        return specialPrice;
    }

    public int getSpecialQuantity() {
        return specialQuantity;
    }
}
