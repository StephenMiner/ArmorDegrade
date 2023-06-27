package me.stephenminer.armordegrade.cosmetic;

import me.stephenminer.armordegrade.ArmorDegrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ArmorPath {
    private final ArmorDegrade plugin;
    private List<Material> path;
    private HashMap<Material, Double> durabilityGates;
    private Material base;

    public ArmorPath(ArmorDegrade plugin, Material top){
        this.plugin = plugin;
        path = new ArrayList<>();
        durabilityGates = new HashMap<>();
        path.add(top);
        this.base = top;
        loadPath();

    }


    private void loadPath(){
        if (plugin.armorFile.getConfig().contains("paths." + base.name())){
            Set<String> equipment = plugin.armorFile.getConfig().getConfigurationSection("paths." + base.name()).getKeys(false);
            for (String key : equipment){
                double gate = plugin.armorFile.getConfig().getDouble("paths." + base.name() + "." + key);
                durabilityGates.put(Material.matchMaterial(key), gate);
            }

        }
        if (plugin.armorFile.getConfig().contains("paths." + path.get(0).name())){
            plugin.armorFile.getConfig().getStringList("paths." + path.get(0).name());
            List<Material> mats = new ArrayList<>();
            List<String> asStrings = plugin.armorFile.getConfig().getStringList("paths." + path.get(0).name());
            for (String entry : asStrings){
                mats.add(Material.matchMaterial(entry));
            }
            path.addAll(mats);
        }
    }


    private float getChangeRatio(int tier){
        return (float) (path.size() - tier) / path.size();
    }

    public Material getNext(ItemStack item){
        Damageable damageable = (Damageable) item.getItemMeta();
        float ratio = (float) (item.getType().getMaxDurability() - damageable.getDamage()) / (float) item.getType().getMaxDurability();
        ratio = (int) (ratio*100) / 100f;
        return switchMat(item.getType(), ratio);
    }

    public Material switchMat(Material mat, float ratio){
        switch (mat){
            case NETHERITE_HELMET -> {
                if (ratio >= 0.75f) return Material.NETHERITE_HELMET;
                else if (ratio >= 0.50f) return Material.DIAMOND_HELMET;
                else if (ratio >= 0.25f) return Material.IRON_HELMET;
                else return Material.CHAINMAIL_HELMET;
            }
            case NETHERITE_CHESTPLATE -> {
                if (ratio >= 0.75f) return Material.NETHERITE_CHESTPLATE;
                else if (ratio >= 0.50f) return Material.DIAMOND_CHESTPLATE;
                else if (ratio >= 0.25f) return Material.IRON_CHESTPLATE;
                else return Material.CHAINMAIL_CHESTPLATE;
            }
            case NETHERITE_LEGGINGS -> {
                if (ratio >= 0.75f) return Material.NETHERITE_LEGGINGS;
                else if (ratio >= 0.50f) return Material.DIAMOND_LEGGINGS;
                else if (ratio >= 0.25f) return Material.IRON_LEGGINGS;
                else return Material.CHAINMAIL_LEGGINGS;
            }
            case NETHERITE_BOOTS -> {
                if (ratio >= 0.75f) return Material.NETHERITE_BOOTS;
                else if (ratio >= 0.50f) return Material.DIAMOND_BOOTS;
                else if (ratio >= 0.25f) return Material.IRON_BOOTS;
                else return Material.CHAINMAIL_BOOTS;
            }
        }
        return mat;
    }

}
