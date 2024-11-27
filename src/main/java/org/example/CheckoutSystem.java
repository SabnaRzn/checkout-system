package org.example;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSystem {

    // Map to hold the pricing rules
    private Map<String, PricingRule> pricingRules;
    private Map<String, Integer> cart;

    // Constructor
    public CheckoutSystem(Map<String, PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
        this.cart = new HashMap<>();
    }

    // Method to scan an item
    public void scan(String item) {
        cart.put(item, cart.getOrDefault(item, 0) + 1);
        System.out.println("Scanned: " + item);
        System.out.println("Running Total: " + calculateTotal() + " pence");
    }

    // Method to calculate total price
    public int calculateTotal() {
        int total = 0;
        for (String item : cart.keySet()) {
            int quantity = cart.get(item);
            PricingRule rule = pricingRules.get(item);

            if (rule == null) {
                // If the rule does not exist, skip this item
                System.out.println("Warning: No pricing rule found for item '" + item + "'. Skipping.");
                continue;
            }

            if (rule != null && rule.getSpecialPrice() > 0 && rule.getSpecialQuantity() > 0) {
                int specialSets = quantity / rule.getSpecialQuantity();
                int remainder = quantity % rule.getSpecialQuantity();
                total += specialSets * rule.getSpecialPrice() + remainder * rule.getUnitPrice();
            } else {
                total += quantity * rule.getUnitPrice();
            }
        }
        return total;
    }
}

