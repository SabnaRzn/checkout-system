package com.cdl.checkout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutSystemTest {


    private CheckoutSystem checkout;
    private Map<String, PricingRule> pricingRules;

    @BeforeEach
    void setUp() {
        // Define pricing rules
        pricingRules = new HashMap<>();
        pricingRules.put("APPLE", new PricingRule(50, 130, 3)); // 3 for 130
        pricingRules.put("BANANA", new PricingRule(30, 45, 2)); // 2 for 45
        pricingRules.put("CARROT", new PricingRule(20, 0, 0));  // No special price
        pricingRules.put("DONUT", new PricingRule(15, 0, 0));   // No special price

        checkout = new CheckoutSystem(pricingRules);
    }

    @Test
    void testSingleItemWithoutSpecialPrice() {
        checkout.scan("CARROT", 1);
        assertEquals(20, checkout.calculateTotal(), "Total for 1 CARROT should be 20 pence.");
    }

    @Test
    void testMultipleItemsWithoutSpecialPrice() {
        checkout.scan("CARROT", 3);
        assertEquals(60, checkout.calculateTotal(), "Total for 3 CARROTs should be 60 pence.");
    }

    @Test
    void testSpecialPriceApplied() {
        checkout.scan("APPLE", 3);
        assertEquals(130, checkout.calculateTotal(), "Total for 3 APPLEs (special price) should be 130 pence.");
    }
    @Test
    void testSpecialPriceAndRemainder() {
        checkout.scan("APPLE", 4);
        assertEquals(180, checkout.calculateTotal(), "Total for 4 APPLEs (3 for 130 + 1 for 50) should be 180 pence.");
    }

    @Test
    void testMultipleSpecialPrices() {
        checkout.scan("BANANA", 4);
        assertEquals(90, checkout.calculateTotal(), "Total for 4 BANANAs (2x special price) should be 90 pence.");
    }

    @Test
    void testInvalidItemDoesNotCrash() {
        checkout.scan("INVALID_ITEM", 3);
        assertEquals(0, checkout.calculateTotal(), "Total should be 0 for unrecognized items.");
    }

    @Test
    void testMixedItemsWithSpecialAndRegularPrices() {
        checkout.scan("APPLE", 4);
        checkout.scan("BANANA", 3);
        checkout.scan("CARROT", 2);
        assertEquals(295, checkout.calculateTotal(),
                "Total should include 4 APPLEs (180), 3 BANANAs (75), and 2 CARROTs (40) = 295 pence.");
    }

    @Test
    void testZeroQuantityScan() {
        checkout.scan("APPLE", 0);
        assertEquals(0, checkout.calculateTotal(), "Total should be 0 when 0 quantity is scanned.");
    }

    @Test
    void testEmptyCart() {
        assertEquals(0, checkout.calculateTotal(), "Total should be 0 for an empty cart.");
    }

    @Test
    void testLargeQuantities() {
        checkout.scan("APPLE", 300);
        assertEquals(13000, checkout.calculateTotal(),
                "Total for 300 APPLEs (100x special price of 130) should be 13000 pence.");
    }

    @Test
    void testMultipleScansSameItem() {
        checkout.scan("CARROT", 2);
        checkout.scan("CARROT", 3);
        assertEquals(100, checkout.calculateTotal(), "Total for 5 CARROTs should be 100 pence.");
    }

    @Test
    void testNoPricingRule() {
        pricingRules.clear();
        checkout.scan("APPLE", 3);
        assertEquals(0, checkout.calculateTotal(), "Total should be 0 if no pricing rules are defined.");
    }
}