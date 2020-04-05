package com.chickenstyle.confirmdrop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConfirmDrop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 1) {
			switch (args[0].toLowerCase()) {
			case "edit":
				if (player.hasPermission("dropconfirm.admin") || player.hasPermission("dropconfirm.edit")) {
					Inventory open = Bukkit.createInventory(null, 54, ChatColor.GRAY + "" +ChatColor.BOLD + "DropConfirm Filter");
					if (Items.getContent() != null) {
				    	ItemStack[] content = Items.getContent().toArray(new ItemStack[0]);
				        open.setContents(content);
				        player.openInventory(open);
					} else {
						player.openInventory(open);
					}
				}
			break;
			
			case "setdelay":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					if (isInteger(args[1])) {
						Config.setInt("delay", Integer.valueOf(args[1]));
						player.sendMessage(ChatColor.GREEN + "Delay was set successfully!");
					} else {
						player.sendMessage(ChatColor.RED + "Invalid Number");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
			break;
			
			case "setmessage":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					StringBuilder builder = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
					    builder.append(args[i]).append(" ");
					}
					String msg = builder.toString();
					Config.setString("message", msg);
					player.sendMessage(ChatColor.GREEN + "Message was set successfully!");
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
			break;
			
			case "settitle":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					StringBuilder builder = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
					    builder.append(args[i]).append(" ");
					}
					String msg = builder.toString();
					Config.setString("titlemessage", msg);
					player.sendMessage(ChatColor.GREEN + "Title was set successfully!");
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
			break;
			
			case "setsubtitle":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					StringBuilder builder = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
					    builder.append(args[i]).append(" ");
					}
					String msg = builder.toString();
					Config.setString("subtitlemessage", msg);
					player.sendMessage(ChatColor.GREEN + "Subtitle was set successfully!");
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
			break;
			
			case "setduration":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					if (isInteger(args[1])) {
						Config.setInt("duration", Integer.valueOf(args[1]));
						player.sendMessage(ChatColor.GREEN + "Delay was set successfully!");
					} else {
						player.sendMessage(ChatColor.RED + "Invalid Number");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
			break;
			
			case "settype":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					if (args[1].toLowerCase().equals("both") || args[1].toLowerCase().equals("text") || args[1].toLowerCase().equals("title")) {
						Config.setString("type", args[1].toLowerCase());
						player.sendMessage(ChatColor.GREEN + "Type was set successfully!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
			break;
			
			case "setsound":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					if (isSound(args[1].toUpperCase())) {
						Config.setString("sound", args[1].toUpperCase());
						player.sendMessage(ChatColor.GREEN + "Sound was set successfully!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
			break;
			
			case "reload":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					Config.Reload();
					player.sendMessage(ChatColor.GREEN + "Config has been reloaded");
				}
			break;
			
			case "help":
				if (sender.hasPermission("confirmdrop." + args[0].toLowerCase()) || sender.hasPermission("confirmdrop.admin")) {
					sender.sendMessage(ChatColor.GRAY + "<---------------------------------->");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd reload // reloads you config");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd settype (text/title/both) // sets the type of the message");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd setsound [sound's name] // sets the sound for the message");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd setduration [seconds] // sets the sound for the message");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd setsubtitle [message] // sets the subtitle for the message");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd settitlemessage [message] // sets the title's message");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd setmessage [message] // sets the message");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd edit // edit the items");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd confirmall [true/false] // Set confirm type");
					sender.sendMessage(ChatColor.GRAY + "");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/cd setconfirmedtime [seconds] // Set confirmed time!");
					sender.sendMessage(ChatColor.GRAY + "<---------------------------------->");
				}
			break;
			
			case "confirmall":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					if (args[1].toLowerCase().equals("true") || args[1].toLowerCase().equals("false")) {
						Config.setBoolean("confirmall", Boolean.valueOf(args[1].toLowerCase()));
						player.sendMessage(ChatColor.GREEN + "Type was set successfully!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
				
			break;
			
			case "setconfirmedtime":
				if (player.hasPermission("confirmdrop." + args[0].toLowerCase()) || player.hasPermission("confirmdrop.admin")) {
					if (isInteger(args[1])) {
						Config.setInt("confirmedtime", Integer.valueOf(args[1]));
						player.sendMessage(ChatColor.GREEN + "Confirmed Time was set successfully!");
					} else {
						player.sendMessage(ChatColor.RED + "Invalid Number");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
				}
				
			break;
			
			default:
				player.sendMessage(ChatColor.GRAY + "/cd help");
			break;
			}
		  } else {
			  player.sendMessage(ChatColor.GRAY + "/cd help");
		  }
		}
		return false;
	}

	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isSound(String s) {
	    try { 
	       Sound.valueOf(s); 
	    } catch(Exception e) { 
	        return false; 
	    }
	    return true;
	}
	
	public static boolean isDouble(String s) {
	    try { 
	       Double.valueOf(s); 
	    } catch(Exception e) { 
	        return false; 
	    }
		return true;
	}
}
