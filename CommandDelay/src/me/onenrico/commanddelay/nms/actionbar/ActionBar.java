package me.onenrico.commanddelay.nms.actionbar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.onenrico.commanddelay.main.Core;

public class ActionBar {

	public static boolean works = true;

	private static boolean useOldMethods = false;
	static Core plugin;
	private static Class<?> c1;
	private static Class<?> c2;
	private static Class<?> c3;
	private static Class<?> c4;
	private static Class<?> c5;
	private static Class<?> c6;
	private static Method m1;

	public static void setup() {
		plugin = Core.getThis();

		if (Core.nmsver.equalsIgnoreCase("v1_8_R1") || Core.nmsver.equalsIgnoreCase("v1_7_")) {
			useOldMethods = true;
		}
		try {
			c1 = Class.forName("org.bukkit.craftbukkit." + Core.nmsver + ".entity.CraftPlayer");
			c5 = Class.forName("net.minecraft.server." + Core.nmsver + ".Packet");
			if (useOldMethods) {
				c2 = Class.forName("net.minecraft.server." + Core.nmsver + ".ChatSerializer");
				c3 = Class.forName("net.minecraft.server." + Core.nmsver + ".IChatBaseComponent");
			} else {
				c2 = Class.forName("net.minecraft.server." + Core.nmsver + ".ChatComponentText");
				c3 = Class.forName("net.minecraft.server." + Core.nmsver + ".IChatBaseComponent");
			}
			if (Core.nmsver.startsWith("v1_12_")) {
				c6 = Class.forName("net.minecraft.server." + Core.nmsver + ".ChatMessageType");
			}
			m1 = c1.getDeclaredMethod("getHandle");
			c4 = Class.forName("net.minecraft.server." + Core.nmsver + ".PacketPlayOutChat");
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public static void sendActionBar(Player player, String message) {
		if (!player.isOnline()) {
			return; // Player may have logged out
		}
		// Call the event, if cancelled don't send Action Bar
		ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
		Bukkit.getPluginManager().callEvent(actionBarMessageEvent);

		if (actionBarMessageEvent.isCancelled()) {
			return;
		}
		message = actionBarMessageEvent.getMessage();
		try {
			Object p = c1.cast(player);
			Object ppoc;
			if (useOldMethods) {
				Method m3 = c2.getDeclaredMethod("a", String.class);
				Object cbc = c3.cast(m3.invoke(c2, "{\"text\": \"" + message + "\"}"));
				ppoc = c4.getConstructor(new Class<?>[] { c3, byte.class }).newInstance(cbc, (byte) 2);
			} else {
				Object o = c2.getConstructor(new Class<?>[] { String.class }).newInstance(message);
				if (Core.nmsver.startsWith("v1_12_")) {
					Object[] chatMessageTypes = c6.getEnumConstants();
					Object chatMessageType = chatMessageTypes[2];
					// for (Object obj : chatMessageTypes) {
					// if (obj.toString().equals("GAME_INFO")) {
					// chatMessageType = obj;
					// }
					// }
					ppoc = c4.getConstructor(new Class<?>[] { c3, c6 }).newInstance(o, chatMessageType);
				} else {
					ppoc = c4.getConstructor(new Class<?>[] { c3, byte.class }).newInstance(o, (byte) 2);
				}
			}
			Object h = m1.invoke(p);
			Field f1 = h.getClass().getDeclaredField("playerConnection");
			Object pc = f1.get(h);
			Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
			m5.invoke(pc, ppoc);
		} catch (Exception ex) {
			ex.printStackTrace();
			works = false;
		}
	}

	public static void sendActionBar(final Player player, final String message, int duration) {
		sendActionBar(player, message);

		if (duration >= 0) {
			// Sends empty message at the end of the duration. Allows messages
			// shorter than 3 seconds, ensures precision.
			new BukkitRunnable() {
				@Override
				public void run() {
					sendActionBar(player, "");
				}
			}.runTaskLater(plugin, duration + 1);
		}

		// Re-sends the messages every 3 seconds so it doesn't go away from the
		// player's screen.
		while (duration > 60) {
			duration -= 60;
			int sched = duration % 60;
			new BukkitRunnable() {
				@Override
				public void run() {
					sendActionBar(player, message);
				}
			}.runTaskLater(plugin, sched);
		}
	}

	public static void sendActionBarToAllPlayers(String message) {
		sendActionBarToAllPlayers(message, -1);
	}

	public static void sendActionBarToAllPlayers(String message, int duration) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			sendActionBar(p, message, duration);
		}
	}
	// TODO Auto-generated constructor stub

}
