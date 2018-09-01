package me.onenrico.commanddelay.locale;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.onenrico.commanddelay.config.ConfigPlugin;
import me.onenrico.commanddelay.main.Core;
import me.onenrico.commanddelay.utils.MessageUT;

public class Locales {
	public static void setup() {
		message_command_nomoney = get("message_command_nomoney", message_command_nomoney);
		message_command_nopermission = get("message_command_nopermission", message_command_nopermission);
		message_command_reload = get("message_command_reload", message_command_reload);
		sound_error = ConfigPlugin.getStr("sound_error", "ENTITY_BLAZE_DEATH");
		sound_tick = ConfigPlugin.getStr("sound_tick", "UI_BUTTON_CLICK");
		sound_success = ConfigPlugin.getStr("sound_success", "ENTITY_ENDERMEN_TELEPORT");
		particle_main = get("particle_main","SNOW_SHOVEL");
		particle_second = get("particle_second","FIREWORKS_SPARK");
		if (ConfigPlugin.changed) {
			Core.getThis().saveDefaultConfig();
		}
		config = ConfigPlugin.getConfig();
	}

	public static String sound_error;
	public static String sound_tick;
	public static String particle_main;
	public static String particle_second;
	public static String sound_success;
	private static FileConfiguration config;

	public static String get(String a, String b) {
		return ConfigPlugin.getStr(a, b);
	}

	public static List<String> getStrList(String path) {
		return config.getStringList(path);
	}

	public static List<String> getStrList(String path, List<String> def) {
		if (config.getStringList(path) == null) {
			config.set(path, def);
			Core.getThis().saveConfig();
			return def;
		}
		return config.getStringList(path);
	}

	public static int getInt(String path, int def) {
		if (config.getString(path) == null) {
			config.set(path, def);
			Core.getThis().saveConfig();
			return def;
		}
		return config.getInt(path, def);
	}

	public static String getStr(String path) {
		return MessageUT.t(config.getString(path));
	}

	public static int getInt(String path) {
		return config.getInt(path);
	}

	public static Boolean getBool(String path) {
		return config.getBoolean(path);
	}

	public static Boolean getBool(String path, Boolean def) {
		if (config.get(path) == null) {
			config.set(path, def);
			Core.getThis().saveConfig();
			return def;
		}
		return config.getBoolean(path, def);
	}

	public static String message_command_nomoney = "You Don't Have Enough Money";
	public static String message_command_nopermission = "You Don't Have &8[&f{perm}&8] &cPermission !";
	public static String message_command_reload = "&6Config Reloaded";

}
