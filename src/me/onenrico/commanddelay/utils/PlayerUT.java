package me.onenrico.commanddelay.utils;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUT {

	public static Collection<? extends Player> getOnlinePlayers() {
		return Bukkit.getServer().getOnlinePlayers();
	}

	public static Player getPlayer(String name) {
		return getPlayer(name, false);
	}

	public static Player getPlayer(String name, Boolean exact) {
		if (exact) {
			return Bukkit.getPlayerExact(name);
		} else {
			return Bukkit.getPlayer(name);
		}
	}

	public static Player getPlayer(UUID uuid) {
		return Bukkit.getPlayer(uuid);
	}

	public static Player getPlayer(Object object) {
		Player player = (Player) object;
		return player;
	}

	public static Boolean isOnline(Player player) {
		return isOnline(player.getName());
	}

	public static Boolean isOnline(String name) {
		if (Bukkit.getPlayer(name) == null) {
			return false;
		}
		return true;
	}
}
