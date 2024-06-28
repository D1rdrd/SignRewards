package com.moonlight.signrewards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RewardSign {
    private String title;
    private String[] signContent;
    private String costItem;
    private int costQuantity;
    private List<Reward> rewards;

    public RewardSign(Map<String, Object> config) {
        this.title = (String) config.get("title");
        Map<String, Object> signContentMap = (Map<String, Object>) config.get("sign_content");
        this.signContent = new String[] {
                (String) signContentMap.get("line1"),
                (String) signContentMap.get("line2"),
                (String) signContentMap.get("line3"),
                (String) signContentMap.get("line4")
        };
        Map<String, Object> costConfig = (Map<String, Object>) config.get("cost");
        this.costItem = (String) costConfig.get("item");
        this.costQuantity = Integer.parseInt((String) costConfig.get("quantity"));
        this.rewards = loadRewards((List<Map<String, Object>>) config.get("rewards"));
    }

    private List<Reward> loadRewards(List<Map<String, Object>> rewardsConfig) {
        List<Reward> rewards = new ArrayList<>();
        for (Map<String, Object> rewardConfig : rewardsConfig) {
            String item = (String) rewardConfig.get("item");
            int quantity = Integer.parseInt((String) rewardConfig.get("quantity"));
            int weight = Integer.parseInt((String) rewardConfig.get("weight"));
            rewards.add(new Reward(item, quantity, weight));
        }
        return rewards;
    }

    public String getTitle() {
        return title;
    }

    public String[] getSignContent() {
        return signContent;
    }

    public String getCostItem() {
        return costItem;
    }

    public int getCostQuantity() {
        return costQuantity;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public Reward chooseReward() {
        int totalWeight = rewards.stream().mapToInt(Reward::getWeight).sum();
        int randomIndex = new Random().nextInt(totalWeight);

        int currentWeight = 0;
        for (Reward reward : rewards) {
            currentWeight += reward.getWeight();
            if (currentWeight > randomIndex) {
                return reward;
            }
        }
        return null; // This should never happen
    }

    class Reward {
        private String item;
        private int quantity;
        private int weight;

        public Reward(String item, int quantity, int weight) {
            this.item = item;
            this.quantity = quantity;
            this.weight = weight;
        }

        public String getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getWeight() {
            return weight;
        }
    }
}
