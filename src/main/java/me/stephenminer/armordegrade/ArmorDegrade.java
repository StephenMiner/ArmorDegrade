package me.stephenminer.armordegrade;

import me.stephenminer.armordegrade.commands.Damage;
import me.stephenminer.armordegrade.commands.Degrade;
import me.stephenminer.armordegrade.commands.ReloadPaths;
import me.stephenminer.armordegrade.cosmetic.ArmorManager;
import me.stephenminer.armordegrade.events.Join;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ArmorDegrade extends JavaPlugin {
    public List<ArmorManager> managers;
    public boolean on;

    public ConfigFile armorFile;
    @Override
    public void onEnable() {
        on = true;
        managers = new ArrayList<>();
        armorFile = new ConfigFile(this, "armorpath");
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Join(this), this);
        getCommand("damage").setExecutor(new Damage());
        getCommand("reloadPaths").setExecutor(new ReloadPaths(this));
        getCommand("degrade").setExecutor(new Degrade(this));
    }

    public ArmorManager getArmorManager(Player player){
        for (int i = managers.size() - 1; i >= 0; i--){
            ArmorManager manager = managers.get(i);
            if (manager.getPlayer().equals(player)) return manager;
        }
        return null;
    }
}
