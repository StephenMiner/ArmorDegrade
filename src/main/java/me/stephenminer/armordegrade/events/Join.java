package me.stephenminer.armordegrade.events;

import me.stephenminer.armordegrade.ArmorDegrade;
import me.stephenminer.armordegrade.cosmetic.ArmorManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Join implements Listener {
    private final ArmorDegrade plugin;
    public Join(ArmorDegrade plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        ArmorManager manager = new ArmorManager(plugin, event.getPlayer());
        new BukkitRunnable(){
            @Override
            public void run(){
                if (!plugin.on) return;
                if (!event.getPlayer().isOnline()){
                    this.cancel();
                    return;
                }
                manager.sendPackets();
            }
        }.runTaskTimer(plugin, 10, 15);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        ArmorManager manager = plugin.getArmorManager(event.getPlayer());
        if (manager != null)
            plugin.managers.remove(manager);
    }

}
