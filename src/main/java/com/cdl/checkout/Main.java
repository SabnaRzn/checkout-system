package com.cdl.checkout;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Define pricing rules
        Map<String, PricingRule> rules = new HashMap<>();
        rules.put("APPLE", new PricingRule(50, 130, 3));
        rules.put("BANANA", new PricingRule(30, 45, 2));
        rules.put("CARROT", new PricingRule(20, 0, 0));
        rules.put("DONUT", new PricingRule(15, 0, 0));

        CheckoutSystem checkout = new CheckoutSystem(rules);
        Scanner scanner = null;

        try {
            scanner = new Scanner(System.in);
            System.out.println("Enter items to scan (type 'done' to finish):");

            while (true) {
                String item = scanner.nextLine().toUpperCase();
                if (item.equals("DONE")) {
                    break;
                }
                if (!rules.containsKey(item)) {
                    System.out.println("Invalid item! Try again.");
                } else {
                    System.out.println("Enter quantity for " + item + ": ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    checkout.scan(item, quantity);
                }
            }
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
                System.out.println("Scanner resource closed.");
            }
        }
        System.out.println("Final Total: " + checkout.calculateTotal() + " pence");
    }
}
