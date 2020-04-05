package com.chickenstyle.confirmdrop;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	static Main main = Main.getPlugin(Main.class);
	static File file = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
	static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	//Getting info from the config
	static public YamlConfiguration getConfig() {
		return config;
	}
	
	static public String getString(String path) {
		return config.getString(path);
	}
	
	static public Integer getInt(String path) {
		return config.getInt(path);
	}
	
	// Setting info in the config
	
	static public void setString(String path,String str) {
		config.set(path, str);
		try {
			config.save(file);
			main.reloadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static public void setBoolean(String path, boolean bool) {
		config.set(path, bool);
		try {
			config.save(file);
			main.reloadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	static public void setInt(String path,Integer in) {
		config.set(path, in);
		try {
			config.save(file);
			main.reloadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
    
    static public void Reload() {
      	try {
    		config.save(file);
    		main.reloadConfig();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
