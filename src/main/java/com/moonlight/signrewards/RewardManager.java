package com.moonlight.signrewards;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardManager {
    private final SignRewards plugin;
    private List<RewardSign> rewardSigns;

    public RewardManager(SignRewards plugin) {
        this.plugin = plugin;
    }

    public void loadRewards() {
        File rewardsFile = new File(plugin.getDataFolder(), "rewards.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(rewardsFile);
        rewardSigns = new ArrayList<>();

        // Load reward signs from config
        List<Map<?, ?>> signsConfig = config.getMapList("reward_sign");
        for (Map<?, ?> signConfig : signsConfig) {
            rewardSigns.add(new RewardSign((Map<String, Object>) signConfig));
        }
    }

    public List<RewardSign> getRewardSigns() {
        return rewardSigns;
    }
}
