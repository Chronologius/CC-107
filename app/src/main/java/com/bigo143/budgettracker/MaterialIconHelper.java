package com.bigo143.budgettracker;

import java.util.HashMap;

/**
 * Maps readable icon names ("ic_food", "ic_salary") to Material Symbols glyphs.
 * Uses the OUTLINED font already added in your project.
 */
public class MaterialIconHelper {

    private static final HashMap<String, String> iconMap = new HashMap<>();

    static {
        // EXPENSE ICONS
        iconMap.put("ic_food", "\uE561");        // restaurant
        iconMap.put("ic_transport", "\uE530");   // directions_bus
        iconMap.put("ic_shopping", "\uE8CC");    // shopping_cart

        // INCOME ICONS
        iconMap.put("ic_salary", "\uE263");      // payments
        iconMap.put("ic_bonus", "\uE8B0");       // card_giftcard

        // TRANSFER ICONS
        iconMap.put("ic_transfer", "\uE8D4");    // sync_alt

        // FALLBACK
        iconMap.put("default", "\uE14C");        // help
    }

    public static String getIcon(String name) {
        if (name == null) return iconMap.get("default");
        return iconMap.getOrDefault(name, iconMap.get("default"));
    }
}
