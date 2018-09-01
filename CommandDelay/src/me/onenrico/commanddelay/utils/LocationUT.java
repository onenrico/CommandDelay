package me.onenrico.commanddelay.utils;

import org.bukkit.Location;

public class LocationUT {

	public static String getdebugXYZ(Location loc) {
		String Locstr = (String.format("&bDebug Loc&f&l:\n" + "&bX: &f%s\n" + "&aY: &f%s\n" + "&cZ: &f%s\n", loc.getX(),
				loc.getY(), loc.getZ()));
		return Locstr;
	}
}
