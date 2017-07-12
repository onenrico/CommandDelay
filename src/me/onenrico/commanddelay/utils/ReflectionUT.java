package me.onenrico.commanddelay.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import me.onenrico.commanddelay.main.Core;

public class ReflectionUT {

	public static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
		String version = Core.nmsver;
		String name = "net.minecraft.server." + version + "." + nmsClassString;
		Class<?> nmsClass = Class.forName(name);
		return nmsClass;
	}

	public static Object getConnection(Player player) throws SecurityException, NoSuchMethodException,
			NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method getHandle = player.getClass().getMethod("getHandle");
		Object nmsPlayer = getHandle.invoke(player);
		Field conField = nmsPlayer.getClass().getField("playerConnection");
		Object con = conField.get(nmsPlayer);
		return con;
	}
}
