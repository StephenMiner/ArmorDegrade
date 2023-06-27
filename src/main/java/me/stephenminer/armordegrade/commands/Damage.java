package me.stephenminer.armordegrade.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class Damage implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender instanceof Player player){
            if (!player.hasPermission("ad.commands.damage")){
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null) return false;
            if (args.length < 1) return false;
            int damage = Integer.parseInt(args[0]);
            Damageable meta = (Damageable) item.getItemMeta();
            meta.setDamage(damage);
            item.setItemMeta(meta);
            return true;
        }
        return false;
    }
}
