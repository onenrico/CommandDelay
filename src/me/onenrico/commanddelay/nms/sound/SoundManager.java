package me.onenrico.commanddelay.nms.sound;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.onenrico.commanddelay.config.ConfigPlugin;

public class SoundManager {
	public static void playSound(Player player, String sound, Location loc, float volume, float pitch) {
		sound = sound.toUpperCase();
		Sound theSound = null;
		try {
			theSound = Sound.valueOf(sound);
		} catch (IllegalArgumentException ex) {
			String[] splitsound = sound.split("_");
			String newSound = "";
			for (int i = 1; i < splitsound.length; i++) {
				if (splitsound.length - i <= 1) {
					newSound += splitsound[i];
				} else if (splitsound.length - i > 1) {
					newSound += splitsound[i] + "_";
				}
			}
			newSound = newSound.replace("ENDERMEN", "ENDERMAN");

			try {
				theSound = Sound.valueOf(newSound);
			} catch (IllegalArgumentException exb) {
				try {

					theSound = Sound.valueOf("CLICK");
				} catch (IllegalArgumentException exxb) {
					theSound = Sound.valueOf("UI_BUTTON_CLICK");
				}
			}

		}
		player.playSound(loc, theSound, volume, pitch);
	}

	public static void playSound(Player player, String sound) {
		playSound(player, sound, player.getLocation(), 5f, 1f);
	}

	public static void playSound(Player player, String sound, Location loc) {
		playSound(player, sound, loc, 5f, 1f);
	}

	public static void playSound(Player player, String sound, float volume, float pitch) {
		playSound(player, sound, player.getLocation(), volume, pitch);
	}

	public static void playSound(String sound, Location loc, float volume, float pitch) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			playSound(player, sound, loc, volume, pitch);
		}
	}
}
