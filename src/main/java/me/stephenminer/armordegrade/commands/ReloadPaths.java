package me.stephenminer.armordegrade.commands;

import me.stephenminer.armordegrade.ArmorDegrade;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadPaths implements CommandExecutor {
    private final ArmorDegrade plugin;
    public ReloadPaths(ArmorDegrade plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender instanceof Player player){
            if (!player.hasPermission("ad.commands.reload")){
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return false;
            }
        }
        plugin.reloadConfig();
        return true;
    }
}
