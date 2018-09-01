package me.onenrico.commanddelay.utils;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUT {
	public static void random(Player player) {
		random(player.getLocation());
	}

	public static void random(Location loc) {
		Random r = new Random();
		int rani = r.nextInt(5) + 8;
		for (int i = 0; i <= rani; i++) {
			float x = r.nextInt(3);
			float z = r.nextInt(3);
			boolean randx = r.nextBoolean();
			boolean randz = r.nextBoolean();
			if (randx) {
				x = x * -1;
			}
			if (randz) {
				z = z * -1;
			}
			Location ran = loc.add(x, 0, z);
			Firework kembang = (Firework) loc.getWorld().spawnEntity(ran, EntityType.FIREWORK);
			FireworkMeta fm = kembang.getFireworkMeta();
			int rt = r.nextInt(4) + 1;
			Type type = Type.BALL;
			if (rt == 1) {
				type = Type.BALL;
			}
			if (rt == 2) {
				type = Type.BALL_LARGE;
			}
			if (rt == 3) {
				type = Type.BURST;
			}
			if (rt == 4) {
				type = Type.CREEPER;
			}
			if (rt == 5) {
				type = Type.STAR;
			}
			int red = r.nextInt(255);
			int green = r.nextInt(255);
			int blue = r.nextInt(255);
			Color c1 = Color.fromBGR(blue, green, red);
			red = r.nextInt(255);
			green = r.nextInt(255);
			blue = r.nextInt(255);
			Color c2 = Color.fromBGR(blue, green, red);
			FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2)
					.with(type).trail(r.nextBoolean()).build();

			fm.addEffect(effect);
			int rp = r.nextInt(2) + 1;
			fm.setPower(rp);
			kembang.setFireworkMeta(fm);
			kembang.setCustomNameVisible(true);
		}
	}
}
