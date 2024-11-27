package org.example;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutSystemTest {

    // Helper method to set up pricing rules
    private Map<String, PricingRule> createPricingRules() {
        Map<String, PricingRule> rules = new HashMap<>();
        rules.put("A", new PricingRule(50, 130, 3)); // 3 for 130
        rules.put("B", new PricingRule(30, 45, 2)); // 2 for 45
        rules.put("C", new PricingRule(20, 0, 0));  // No special price
        rules.put("D", new PricingRule(15, 0, 0));  // No special price
        return rules;
    }

    @Test
    void testScanSingleItem() {
        Map<String, PricingRule> rules = createPricingRules();
        CheckoutSystem checkout = new CheckoutSystem(rules);

        checkout.scan("A");
        assertEquals(50, checkout.calculateTotal());
    }

    @Test
    void testScanMultipleItemsWithoutSpecialPrice() {
        Map<String, PricingRule> rules = createPricingRules();
        CheckoutSystem checkout = new CheckoutSystem(rules);

        checkout.scan("C");
        checkout.scan("D");
        assertEquals(35, checkout.calculateTotal()); // 20 + 15 = 35
    }

    @Test
    void testSpecialPriceForSingleItem() {
        Map<String, PricingRule> rules = createPricingRules();
        CheckoutSystem checkout = new CheckoutSystem(rules);

        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("A");
        assertEquals(130, checkout.calculateTotal()); // 3 for 130
    }

    @Test
    void testSpecialPriceAndExtraUnits() {
        Map<String, PricingRule> rules = createPricingRules();
        CheckoutSystem checkout = new CheckoutSystem(rules);

        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("A");
        assertEquals(180, checkout.calculateTotal()); // 3 for 130 + 1*50 = 180
    }

    @Test
    void testSpecialPriceForDifferentItem() {
        Map<String, PricingRule> rules = createPricingRules();
        CheckoutSystem checkout = new CheckoutSystem(rules);

        checkout.scan("B");
        checkout.scan("B");
        assertEquals(45, checkout.calculateTotal()); // 2 for 45
    }

    @Test
    void testSpecialPriceMixedWithOtherItems() {
        Map<String, PricingRule> rules = createPricingRules();
        CheckoutSystem checkout = new CheckoutSystem(rules);

        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("B");
        checkout.scan("B");
        checkout.scan("C");
        assertEquals(195, checkout.calculateTotal()); // 3A = 130, 2B = 45, 1C = 20
    }

    @Test
    void testInvalidItemDoesNotCrash() {
        Map<String, PricingRule> rules = createPricingRules();
        CheckoutSystem checkout = new CheckoutSystem(rules);

        checkout.scan("X"); // Invalid item
        assertEquals(0, checkout.calculateTotal()); // Should handle gracefully with 0 total
    }

    @Test
    void testEmptyCart() {
        Map<String, PricingRule> rules = createPricingRules();
        CheckoutSystem checkout = new CheckoutSystem(rules);

        assertEquals(0, checkout.calculateTotal()); // Empty cart, total should be 0
    }

}