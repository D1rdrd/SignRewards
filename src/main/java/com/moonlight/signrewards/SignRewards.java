package com.moonlight.signrewards;

import org.bukkit.plugin.java.JavaPlugin;

public class SignRewards extends JavaPlugin {

    private ConfigManager configManager;
    private RewardManager rewardManager;

    @Override
    public void onEnable() {
        // Initialize the ConfigManager and set up the config
        configManager = new ConfigManager(this);
        configManager.setupConfig();

        // Initialize the RewardManager and load rewards
        rewardManager = new RewardManager(this);
        rewardManager.loadRewards();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new ListenerClass(this, rewardManager), this);

        // Other initialization code...
    }

    @Override
    public void onDisable() {
        // Cleanup code...
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }
}
