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
        try (Scanner scanner = new Scanner(System.in) ){
            System.out.println("Enter items to scan (type 'done' to finish): (type total to see total amount)");

            while (true) {
                try {
                    String item = scanner.nextLine().toUpperCase();
                    if (item.equals("DONE")) {
                        System.out.println("Thank you for shopping with us!");
                        break;
                    } else if (item.equals("TOTAL")) {
                        System.out.println("Final Total: " + checkout.calculateTotal() + " pence");
                    } else if (!rules.containsKey(item)) {
                        System.out.println("Invalid item! Try again.");
                    } else {
                        System.out.println("Enter quantity for " + item + ": ");
                        String quantityInput = scanner.nextLine().trim();
                        int quantity = Integer.parseInt(quantityInput);// Consume the newline character
                        checkout.scan(item, quantity);
                    }
                } catch (NumberFormatException en) {
                    System.out.println("Invalid quantity: Please enter a valid number");
                }
            }
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
//        finally {
//            if (scanner != null) {
//                scanner.close();
//                System.out.println("Scanner resource closed.");
//            }
//        }

        System.out.println("\n -- Final bill");
        checkout.displayCart();
        System.out.println("Final Total: " + checkout.calculateTotal()+ " pence ");

    }
}



