package com.chickenstyle.confirmdrop;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	
	@Override
	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "ConfirmDrop was Enabled");
		Bukkit.getPluginManager().registerEvents(this, this);
		this.getConfig().options().copyDefaults();
	    saveDefaultConfig();
	    getCommand("confirmdrop").setExecutor(new ConfirmDrop());
	    new Items(this);
	}
	HashMap<Player,Player> confirmDrop = new HashMap<Player,Player>();
	HashMap<Player,Player> confirmed = new HashMap<Player,Player>();
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		String message = getConfig().getString("message");
		String titlemessage = getConfig().getString("titlemessage");
		String subtitlemessage = getConfig().getString("subtitlemessage");
		String type = getConfig().getString("type");
		Sound sound = Sound.valueOf(getConfig().getString("sound"));
		int duration = getConfig().getInt("duration") * 20;
		boolean confirmall = getConfig().getBoolean("confirmall");
		ItemStack drop = e.getItemDrop().getItemStack();
		Player player = e.getPlayer();
		int confirmTime = getConfig().getInt("confirmedtime") * 20;
	 if (confirmall == false) {
		for (ItemStack item: Items.getContent()) {
			if (item != null && drop != null && item.getItemMeta().equals(drop.getItemMeta()) && item.getType().equals(drop.getType())) {
				if (confirmDrop.containsKey(player) == false && !confirmed.containsKey(player)) {
					confirmDrop.put(player,player);
					if (type.equals("text")) {
						e.setCancelled(true);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
						player.playSound(player.getLocation(), sound, 1f, 1f);
    					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    					    @Override
    					    public void run() {
    					        confirmDrop.remove(player);
    					   }
    					},duration);
					} else if (type.equals("title")) {
						e.setCancelled(true);
						try {
							Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
							Object subTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
							Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + titlemessage.replace("&", "§") + "\"}");
							Object subline = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitlemessage.replace('&', '§') + "\"}");
						
							Constructor<?> titleConstuctor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
							Object packet = titleConstuctor.newInstance(enumTitle,chat, 10, duration, 10);
							Object subpacket = titleConstuctor.newInstance(subTitle,subline, 10, duration, 10);
							sendPacket(player,packet);
							sendPacket(player,subpacket);
							player.playSound(player.getLocation(), sound, 1f, 1f);
	    					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	    					    @Override
	    					    public void run() {
	    					        confirmDrop.remove(player);
	    					   }
	    					},duration);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else if (type.equals("both")) {
						e.setCancelled(true);
						try {
							Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
							Object subTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
							Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + titlemessage.replace("&", "§") + "\"}");
							Object subline = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitlemessage.replace('&', '§') + "\"}");
						
							Constructor<?> titleConstuctor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
							Object packet = titleConstuctor.newInstance(enumTitle,chat, 10, duration, 10);
							Object subpacket = titleConstuctor.newInstance(subTitle,subline, 10, duration, 10);
							sendPacket(player,packet);
							sendPacket(player,subpacket);
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
							player.playSound(player.getLocation(), sound, 1f, 1f);
	    					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	    					    @Override
	    					    public void run() {
	    					        confirmDrop.remove(player);
	    					   }
	    					},duration);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						getServer().getConsoleSender().sendMessage(ChatColor.RED + "Confirm-Drop Invalid Type!");
					}
				} else {
					e.setCancelled(false);
					confirmDrop.remove(player);
					if (!confirmed.containsKey(player)) {
						confirmed.put(player, player);
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						    @Override
						    public void run() {
						        confirmed.remove(player);
						   }
						},confirmTime);
					}
				}
		   }
		}
	  } else {
			if (confirmDrop.containsKey(player) == false && !confirmed.containsKey(player)) {
				confirmDrop.put(player,player);
				if (type.equals("text")) {
					e.setCancelled(true);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					player.playSound(player.getLocation(), sound, 1f, 1f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					    @Override
					    public void run() {
					        confirmDrop.remove(player);
					   }
					},duration);
				} else if (type.equals("title")) {
					e.setCancelled(true);
					try {
						Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
						Object subTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
						Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + titlemessage.replace("&", "§") + "\"}");
						Object subline = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitlemessage.replace('&', '§') + "\"}");
					
						Constructor<?> titleConstuctor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
						Object packet = titleConstuctor.newInstance(enumTitle,chat, 10, duration, 10);
						Object subpacket = titleConstuctor.newInstance(subTitle,subline, 10, duration, 10);
						sendPacket(player,packet);
						sendPacket(player,subpacket);
						player.playSound(player.getLocation(), sound, 1f, 1f);
    					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    					    @Override
    					    public void run() {
    					        confirmDrop.remove(player);
    					   }
    					},duration);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (type.equals("both")) {
					e.setCancelled(true);
					try {
						Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
						Object subTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
						Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + titlemessage.replace("&", "§") + "\"}");
						Object subline = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitlemessage.replace('&', '§') + "\"}");
					
						Constructor<?> titleConstuctor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
						Object packet = titleConstuctor.newInstance(enumTitle,chat, 10, duration, 10);
						Object subpacket = titleConstuctor.newInstance(subTitle,subline, 10, duration, 10);
						sendPacket(player,packet);
						sendPacket(player,subpacket);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
						player.playSound(player.getLocation(), sound, 1f, 1f);
    					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    					    @Override
    					    public void run() {
    					        confirmDrop.remove(player);
    					   }
    					},duration);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					getServer().getConsoleSender().sendMessage(ChatColor.RED + "Confirm-Drop Invalid Type!");
				}
			} else {
				e.setCancelled(false);
				confirmDrop.remove(player);
				if (!confirmed.containsKey(player)) {
					confirmed.put(player, player);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					    @Override
					    public void run() {
					        confirmed.remove(player);
					   }
					},confirmTime);
				}
			}
	  }
	}
	
	@EventHandler
	public void inventoryClose(InventoryCloseEvent e) {
		if (e.getView().getTitle().equals(ChatColor.GRAY + "" +ChatColor.BOLD + "DropConfirm Filter")) {
			Player player = (Player) e.getPlayer();
			Items.setContent(e.getInventory().getContents());
			player.sendMessage(ChatColor.GREEN + "Filter was edited!");
		}
	}
	
	
	public void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket",getNMSClass("Packet")).invoke(playerConnection, packet);
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	
	
	
	
	}
}
