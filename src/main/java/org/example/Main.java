package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Define pricing rules
        Map<String, PricingRule> rules = new HashMap<>();
        rules.put("A", new PricingRule(50, 130, 3));
        rules.put("B", new PricingRule(30, 45, 2));
        rules.put("C", new PricingRule(20, 0, 0));
        rules.put("D", new PricingRule(15, 0, 0));

        // Create Checkout System
        CheckoutSystem checkout = new CheckoutSystem(rules);

        // Scanner for command-line input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter items to scan (type 'done' to finish):");

        while (true) {
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("DONE")) {
                break;
            }
            if (!rules.containsKey(input)) {
                System.out.println("Invalid item! Try again.");
            } else {
                checkout.scan(input);
            }
        }

        System.out.println("Final Total: " + checkout.calculateTotal() + " pence");
    }
}
