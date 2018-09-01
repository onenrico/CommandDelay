package me.onenrico.commanddelay.nms.particle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.onenrico.commanddelay.utils.MessageUT;
import me.onenrico.commanddelay.utils.ReflectionUT;

public class ParticleManager {
	private static Class<?> packetClass;
	private static Class<?> newEnum;
	private static Class<?> playerConnection;
	private static Class<?> Packet;
	private static Method getEnum;
	private static Method sendPacket;
	private static Constructor<?> packetConstructor;

	public static boolean first = true;
	public static void setup() {
		first = false;
		try {
			packetClass = ReflectionUT.getNMSClass("PacketPlayOutWorldParticles");
			newEnum = ReflectionUT.getNMSClass("EnumParticle");
			playerConnection = ReflectionUT.getNMSClass("PlayerConnection");
			Packet = ReflectionUT.getNMSClass("Packet");
			getEnum = newEnum.getMethod("valueOf", String.class);
			sendPacket = playerConnection.getMethod("sendPacket", Packet);
			packetConstructor = packetClass.getConstructor(newEnum, boolean.class, float.class, float.class,
					float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			MessageUT.cmessage("Using new Particle Protocol");
		}

	}

	public static void sendParticles(Player player, String particle, Location loc, float xOffset, float yOffset,
			float zOffset, float data, int amount) {
		if(first)setup();
		float x = (float) loc.getX();
		float y = (float) loc.getY();
		float z = (float) loc.getZ();
		try {
			Object enumParticle = getEnum.invoke(newEnum, particle);
			Object packet = packetConstructor.newInstance(enumParticle, true, x, y, z, xOffset, yOffset, zOffset, data,
					amount, null);
			sendPacket.invoke(ReflectionUT.getConnection(player), packet);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			e.printStackTrace();
		}

	}
}
