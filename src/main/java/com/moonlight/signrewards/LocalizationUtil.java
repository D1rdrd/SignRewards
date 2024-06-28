package com.moonlight.signrewards;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LocalizationUtil {

    public static String getItemLocalizedName(Player player, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();

        // Attempt to use the display name if set
        if (meta != null && meta.hasDisplayName()) {
            return meta.getDisplayName();
        }

        // Fallback if display name is not set
        return formatMaterialName(material);
    }

    private static String formatMaterialName(Material material) {
        String name = material.name().toLowerCase().replace('_', ' ');
        return ChatColor.RESET + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
