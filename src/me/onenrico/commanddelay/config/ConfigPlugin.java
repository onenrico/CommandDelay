package me.onenrico.commanddelay.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import me.onenrico.commanddelay.locale.Locales;
import me.onenrico.commanddelay.main.Core;
import me.onenrico.commanddelay.object.Category;
import me.onenrico.commanddelay.utils.DelayUT;
import me.onenrico.commanddelay.utils.MessageUT;

public class ConfigPlugin {
	public static Core instance;
	public static FileConfiguration config;
	public static Boolean changed = false;

	public ConfigPlugin() {
		instance = Core.getThis();
		config = instance.getConfig();
	}

	public static Object getSetting(String configPath) {
		config = instance.getConfig();
		return config.get(configPath);
	}

	public static void setSetting(String configPath, Object configObject) {
		config = instance.getConfig();
		config.set(configPath, configObject);
		changed = true;
	}

	public static FileConfiguration getConfig() {
		return config;
	}

	public static void reloadSetting() {
		instance.saveDefaultConfig();
		instance.reloadConfig();
		setupSetting();
	}

	public static int getDelay() {
		return config.getInt("teleport_default_delay", 5);
	}

	public static String getStr(String path) {
		return MessageUT.t(config.getString(path, "<" + path + " not set>"));
	}

	public static String getStr(String path, String def) {
		if (config.getString(path) == null) {
			config.set(path, def);
			changed = true;
			Core.getThis().saveConfig();
		}
		return MessageUT.t(config.getString(path, def));
	}

	public static List<Category> getCategory() {
		Set<String> categories = config.getConfigurationSection("category").getKeys(false);
		List<Category> result = new ArrayList<>();
		for (String c : categories) {
			result.add(new Category(c));
		}
		return result;
	}

	public static int getInt(String path) {
		return config.getInt(path, 0);
	}

	public static List<String> getDesc(String path) {
		return config.getStringList(path);
	}

	public static void setupSetting() {
		config = instance.getConfig();
		Locales.setup();
		DelayUT.setup();
		upgrade();
	}

	public static Boolean getChat(String a) {
		return config.getBoolean("message_" + a + "_enabled", true);
	}

	public static Boolean getCancel(String a) {
		return config.getBoolean("delay_cancel_" + a, true);
	}

	public static HashMap<String, Object> upgradeMap = new HashMap<>();

	public static void upgrade() {
	}
}
