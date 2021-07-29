package com.company;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin implements Listener {

    Map<String, Long> cooldowns = new HashMap<String, Long>();

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        System.out.println("we in");
    }

    public void onDisable() {
        System.out.println("we out");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("doom")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Players only BOSS");
                return true;
            }
            Player player = (Player) sender;
            if (player.getInventory().firstEmpty() == -1) {
                Location loc = player.getLocation();
                World world = player.getWorld();
                world.dropItemNaturally(loc, getDoom());
                player.sendMessage(ChatColor.GOLD + "da floor");
                return true;
            }
            player.getInventory().addItem(getDoom());
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
    public ItemStack getDoom() {
        ItemStack doom = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = doom.getItemMeta();
        meta.setDisplayName(ChatColor.BLACK + "Sword O' Doom!");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.ITALIC + "" + ChatColor.DARK_RED + "+10 dmg dealt against pregnant women");
        lore.add("");
        lore.add(ChatColor.DARK_PURPLE + "recharging skelly spawns take 25 seconds");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        doom.setItemMeta(meta);

        return doom;
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
        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_SWORD))
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta()
                    .getDisplayName().contains("Sword O' Doom!")) {
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                    Player player = (Player) event.getPlayer();
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (cooldowns.containsKey(player.getName())) {
                            if (cooldowns.get(player.getName()) > System.currentTimeMillis()) {
                                return;
                            }
                        }
                            cooldowns.put(player.getName(), System.currentTimeMillis() + 25 * 1000);

                        Block b = player.getTargetBlock(null, 5);
                        Location loc = b.getLocation();
                        loc.setY(loc.getY() + 1);
                        int duck = 0;
                        while (3 != duck) {
                            loc.setX(loc.getX() + 1);
                            Entity sdrown = loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                            sdrown.setCustomName("Skelly O' Doom!");
                            sdrown.setCustomNameVisible(true);
                            sdrown.setGlowing(true);
                            duck += 1;
                        }

                    }
                    if (event.getAction() == Action.LEFT_CLICK_AIR) {
                        player.launchProjectile(Fireball.class);
                    }
                }
            }
    }
}
