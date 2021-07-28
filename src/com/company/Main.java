package com.company;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        System.out.println("we in");
    }

    public void onDisable() {
        System.out.println("we out");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("poseidon")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Players only BOSS");
                return true;
            }
            Player player = (Player) sender;
            if (player.getInventory().firstEmpty() == -1) {
                Location loc = player.getLocation();
                World world = player.getWorld();
                world.dropItemNaturally(loc, getPoseiden());
                player.sendMessage(ChatColor.GOLD + "da floor");
                return true;
            }
            player.getInventory().addItem(getPoseiden());
            player.sendMessage(ChatColor.GOLD + "Blessed is he!");
        }
        if (label.equalsIgnoreCase("sugma")) {
            Player player = (Player) sender;
            player.sendMessage("ligma");
            player.getInventory().addItem(pissPants());
            return true;
        }
        return false;
    }
    public ItemStack getPoseiden() {
        ItemStack poseiden = new ItemStack(Material.TRIDENT);
        ItemMeta meta = poseiden.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "God Poke");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.ITALIC + "" + ChatColor.GREEN + "Trident O' Poseiden");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        poseiden.setItemMeta(meta);

        return poseiden;
    }

    public ItemStack pissPants() {
        ItemStack pant = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemMeta meta = pant.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "PissPants");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.ITALIC + "" + ChatColor.GREEN + "These pants have been PISSED");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        pant.setItemMeta(meta);

        return pant;
    }

    @EventHandler()
    public void onClick(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIDENT))
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta()
                    .getDisplayName().contains("God Poke")) {
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                    Player player = (Player) event.getPlayer();
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        Block b = player.getTargetBlock(null, 5);
                        Location loc = b.getLocation();
                        loc.setY(loc.getY() + 1);
                        loc.getWorld().spawnEntity(loc, EntityType.DROWNED);
                        loc.setX(loc.getX() + 1);
                        loc.getWorld().spawnEntity(loc, EntityType.DROWNED);
                        loc.setX(loc.getX() - 2);
                        loc.getWorld().spawnEntity(loc, EntityType.DROWNED);
                    }
                    if (event.getAction() == Action.LEFT_CLICK_AIR) {
                        player.launchProjectile(Fireball.class);
                    }
                }
            }
    }
}
