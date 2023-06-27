package me.stephenminer.armordegrade.cosmetic;

import com.mojang.datafixers.util.Pair;
import me.stephenminer.armordegrade.ArmorDegrade;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class ArmorManager {
    private final ArmorDegrade plugin;
    private Player player;

    public ArmorManager(ArmorDegrade plugin, Player player){
        this.player = player;
        this.plugin = plugin;
    }



    private ServerPlayer serverPlayer(Player player){
        return ((CraftPlayer) player).getHandle();
    }

    public void sendPackets(){
        World world = player.getWorld();
        List<Player> players = world.getPlayers();
        List<Pair<EquipmentSlot, ItemStack>> equipment = getEquipment();
        if (equipment.size() < 1) return;
        for (Player inWorld : players) {
            if (inWorld.equals(player)) continue;
            Connection connection = serverPlayer(inWorld).connection.getConnection();
            connection.send(new ClientboundSetEquipmentPacket(serverPlayer(player).getId(), getEquipment()));
        }
    }

    private List<Pair<EquipmentSlot, ItemStack>> getEquipment(){
        List<Pair<EquipmentSlot, ItemStack>> equipment = new ArrayList<>();
        PlayerInventory inventory = player.getInventory();
        if (inventory.getHelmet() != null) {
            Pair<EquipmentSlot, ItemStack> helmet = new Pair<>(EquipmentSlot.HEAD, fromItem(inventory.getHelmet()));
            equipment.add(helmet);
        }
        if (inventory.getChestplate() != null){
            Pair<EquipmentSlot, ItemStack> chestplate = new Pair<>(EquipmentSlot.CHEST, fromItem(inventory.getChestplate()));
            equipment.add(chestplate);
        }
        if (inventory.getLeggings() != null){
            Pair<EquipmentSlot, ItemStack> leggings = new Pair<>(EquipmentSlot.LEGS, fromItem(inventory.getLeggings()));
            equipment.add(leggings);
        }
        if (inventory.getBoots() != null){
            Pair<EquipmentSlot, ItemStack> boots = new Pair<>(EquipmentSlot.FEET, fromItem(inventory.getBoots()));
            equipment.add(boots);
        }
        return equipment;
    }


    public ItemStack fromItem(org.bukkit.inventory.ItemStack item){
        org.bukkit.inventory.ItemStack copy = item.clone();
        ArmorPath path = new ArmorPath(plugin, item.getType());
        Material next = path.getNext(item);
        copy.setType(next);
        return CraftItemStack.asNMSCopy(copy);
    }

    public Player getPlayer(){
        return player;
    }
}
