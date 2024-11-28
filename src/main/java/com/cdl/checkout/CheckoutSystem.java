package com.cdl.checkout;

import java.sql.Array;
import java.util.HashMap;
import java.util.Map;

public class CheckoutSystem {


    private Map<String, PricingRule> pricingRules;
    private Map<String, Integer> cart;

    public CheckoutSystem(Map<String, PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
        this.cart = new HashMap<>();
    }

    public void scan(String item, int quantity) {

        cart.put(item, cart.getOrDefault(item, 0) + quantity);
        System.out.println("Scanned: " + quantity + " x " + item);
        System.out.println("Running Total: " + calculateTotal() + " pence");
    }
    public int calculateTotal() {
        int total = 0;
        int itemsTotal = 0;
        for (String item : cart.keySet()) {
            int quantity = cart.get(item);
            PricingRule rule = pricingRules.get(item);

            if (rule == null) {
                // If the rule does not exist, skip this item
                System.out.println("Warning: No pricing rule found for item '" + item + "'. Skipping.");
                continue;
            }

            if (rule.specialPrice() > 0 && rule.specialQuantity() > 0) {
                int specialSets = quantity / rule.specialQuantity();
                int remainder = quantity % rule.specialQuantity();
                itemsTotal = specialSets * rule.specialPrice() + remainder * rule.unitPrice();
                total += itemsTotal;
            } else {
                itemsTotal = quantity * rule.unitPrice();
                total += itemsTotal;
            }
        }

        return total;
    }
}

