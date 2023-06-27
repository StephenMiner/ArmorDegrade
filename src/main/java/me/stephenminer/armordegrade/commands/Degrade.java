package me.stephenminer.armordegrade.commands;

import me.stephenminer.armordegrade.ArmorDegrade;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class Degrade implements CommandExecutor {
    private final ArmorDegrade plugin;
    public Degrade(ArmorDegrade plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("ad.commands.enable")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
            if (args.length < 1) return false;
            boolean on = Boolean.parseBoolean(args[0]);
            plugin.on = on;
            return true;
        }
        return false;
    }
}
