package com.moonlight.signrewards;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerClass implements Listener {
    private final SignRewards plugin;
    private final RewardManager rewardManager;

    public ListenerClass(SignRewards plugin, RewardManager rewardManager) {
        this.plugin = plugin;
        this.rewardManager = rewardManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            for (RewardSign rewardSign : rewardManager.getRewardSigns()) {
                // Check if the sign is a reward sign (either by title or updated content)
                if (sign.getLine(0).equalsIgnoreCase(rewardSign.getTitle()) ||
                        (sign.getLine(0).equals(rewardSign.getSignContent()[0]) &&
                                sign.getLine(1).equals(rewardSign.getSignContent()[1]) &&
                                sign.getLine(2).equals(rewardSign.getSignContent()[2]) &&
                                sign.getLine(3).equals(rewardSign.getSignContent()[3]))) {

                    if (itemInHand.getType() == Material.STICK) { // Assuming the stick is the reward maker
                        // Apply glowing effect and lock the sign
                        sign.setGlowingText(true);
                        sign.setWaxed(true);

                        sign.setLine(0, rewardSign.getSignContent()[0]);
                        sign.setLine(1, rewardSign.getSignContent()[1]);
                        sign.setLine(2, rewardSign.getSignContent()[2]);
                        sign.setLine(3, rewardSign.getSignContent()[3]);
                        sign.update();

                        player.sendMessage("Reward sign created and locked with glowing effect!");
                        return;
                    } else if (itemInHand.getType() == Material.matchMaterial(rewardSign.getCostItem()) && itemInHand.getAmount() >= rewardSign.getCostQuantity()) {
                        // Handle the trade
                        itemInHand.setAmount(itemInHand.getAmount() - rewardSign.getCostQuantity());
                        RewardSign.Reward reward = rewardSign.chooseReward();
                        ItemStack rewardItem = new ItemStack(Material.matchMaterial(reward.getItem()), reward.getQuantity());
                        player.getInventory().addItem(rewardItem);

                        // Get localized item names using the utility method
                        String costItemName = LocalizationUtil.getItemLocalizedName(player, Material.matchMaterial(rewardSign.getCostItem()));
                        String rewardItemName = LocalizationUtil.getItemLocalizedName(player, Material.matchMaterial(reward.getItem()));

                        player.sendMessage("You traded " + rewardSign.getCostQuantity() + " " + costItemName + " for " + reward.getQuantity() + " " + rewardItemName);
                        return;
                    } else {
                        player.sendMessage("You don't have enough items to trade.");
                        return;
                    }
                }
            }
        }
    }
}
