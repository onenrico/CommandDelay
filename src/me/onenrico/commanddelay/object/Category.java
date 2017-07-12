package me.onenrico.commanddelay.object;

import java.util.ArrayList;
import java.util.List;

import me.onenrico.commanddelay.config.ConfigPlugin;

public class Category {
	private double cost = 0;
	private int delay = 5;
	private String blacklist = "disabled";
	private List<String> worlds = new ArrayList<>();
	private List<String> commands = new ArrayList<>();

	public Category(String path) {
		String pref = "category." + path + ".";
		cost = ConfigPlugin.getConfig().getDouble(pref + "cost");
		delay = ConfigPlugin.getConfig().getInt(pref + "delay");
		blacklist = ConfigPlugin.getConfig().getString(pref + "blacklist");
		worlds = ConfigPlugin.getConfig().getStringList(pref + "worlds");
		commands = ConfigPlugin.getConfig().getStringList(pref + "commands");
	}

	public double getCost() {
		return cost;
	}

	public int getDelay() {
		return delay;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public List<String> getWorlds() {
		return worlds;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public void setWorlds(List<String> worlds) {
		this.worlds = worlds;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
}
