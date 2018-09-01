package me.onenrico.commanddelay.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.onenrico.commanddelay.locale.Locales;
import me.onenrico.commanddelay.main.Core;
import me.onenrico.commanddelay.nms.particle.ParticleManager;

public class ParticleUT {
	public static Location newloc;

	public static void send(Player player, String effect, Location loc, float xOffset, float yOffset, float zOffset,
			float speed, int amount, Boolean all) {
		boolean oldmethod = false;
		if(ReflectionUT.VERSION.startsWith("v1_8")) {
			oldmethod = true;
		}
		if (all) {
			if (oldmethod) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					ParticleManager.sendParticles(p, effect, loc, xOffset, yOffset, zOffset, speed,
							amount);
				}
			} else {
				loc.getWorld().spawnParticle(Particle.valueOf(effect.toUpperCase()), 
						loc, amount, xOffset, yOffset,
						zOffset, speed);
			}
		} else {
			if(oldmethod) {
				ParticleManager.sendParticles(player, effect, loc, xOffset, yOffset, zOffset, speed, amount);
			}else {
				player.spawnParticle(Particle.valueOf(effect.toUpperCase()), 
						loc, amount, xOffset, yOffset,
						zOffset, speed);
			}
		}
	}

	public static void send(Player player, String effect, Location loc, float speed, int amount, Boolean all) {
		send(player, effect, loc, 0, 0, 0, speed, amount, all);
	}

	public static void send(Player player, String effect, Location loc, float speed, Boolean all) {
		send(player, effect, loc, speed, 1, all);
	}

	public static void send(Player player, String effect, Location loc, int amount, Boolean all) {
		send(player, effect, loc, 0, amount, all);
	}

	public static void send(Player player, String effect, Location loc, Boolean all) {
		send(player, effect, loc, 0, 0, 0, 0, 1, all);
	}

	public static void send(Player player, String effect, Boolean all) {
		send(player, effect, player.getLocation(), all);
	}

	public static Vector rotateAroundAxisX(Vector v, double cos, double sin) {
		double y = v.getY() * cos - v.getZ() * sin;
		double z = v.getY() * sin + v.getZ() * cos;
		return v.setY(y).setZ(z);
	}

	public static Vector rotateAroundAxisY(Vector v, double cos, double sin) {
		double x = v.getX() * cos + v.getZ() * sin;
		double z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static Vector rotateAroundAxisZ(Vector v, double cos, double sin) {
		double x = v.getX() * cos - v.getY() * sin;
		double y = v.getX() * sin + v.getY() * cos;
		return v.setX(x).setY(y);
	}

	public static Vector rotateAroundAxisZ(Vector v, double angle) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		double x = v.getX() * cos - v.getY() * sin;
		double y = v.getX() * sin + v.getY() * cos;
		return v.setX(x).setY(y);
	}
	public static List<Location> circle(Location loc, double r, double h, boolean hollow, boolean sphere,
			double plus_y) {
		List<Location> circleblocks = new ArrayList<>();
		double cx = loc.getX();
		double cy = loc.getY();
		double cz = loc.getZ();
		for (double x = cx - r; x <= cx + r; x += .3) {
			for (double z = cz - r; z <= cz + r; z += .3) {
				for (double y = sphere ? cy - r : cy; y < (sphere ? cy + r : cy + h); y += .3) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if ((dist < r * r) && ((!hollow) || (dist >= (r - 1) * (r - 1)))) {
						Location l = new Location(loc.getWorld(), x, y + plus_y, z);
						circleblocks.add(l);
					}
				}
			}
		}
		return circleblocks;
	}

	public static BukkitTask helixParticle(Player player, float height, float radius) {
		return new BukkitRunnable() {
			int cool = 0;
			@SuppressWarnings("unused")
			double angle, AxisCos, AxisSin, AxisCos2, AxisSin2, x, z;
			Vector vec;
			int count = 0;
			int times = 0;

			Boolean useP = Locales.getBool("use_particle", true);

			@Override
			public void run() {
				if (!useP) {
					cancel();
					return;
				}
				Location loc = player.getLocation().add(0, 3.2, 0);
				angle = Math.toRadians(cool += 9); // note that here we do have
				// to convert to radians.
				AxisCos2 = Math.cos(-angle); // getting the cos value for the
				// pitch.
				AxisSin2 = Math.sin(-angle); // getting the sin value for the
				// pitch.
				AxisCos = Math.cos(angle); // getting the cos value for the
				// pitch.
				AxisSin = Math.sin(angle); // getting the sin value for the
				// pitch.]
				for (double a = 0; a < Math.PI * 2; a += Math.PI / (20 * radius)) {
					x = Math.cos(a) * (radius);
					z = Math.sin(a) * (radius);
					vec = new Vector(x, 0, z);
					rotateAroundAxisZ(vec, Math.toRadians(90));
					rotateAroundAxisY(vec, AxisCos2, AxisSin2);
					loc.add(vec);
					if (times == 15) {
						if (++count >= Math.ceil((20 * radius) / 5)) {
							count = 0;
							try {
								send(player, Locales.particle_second.toUpperCase(), loc, 0f, 0f, 0f, 0f, 0, true);
							}catch(Exception ex) {
								send(player, "FIREWORKS_SPARK".toUpperCase(), loc, 0f, 0f, 0f, 0f, 0, true);
							}
						}
					}
					try {
						send(player, Locales.particle_main.toUpperCase(), loc, 0f, 0f, 0f, 0f, 0, true);
					}catch(Exception ex) {
						send(player, "REDSTONE".toUpperCase(), loc, 0f, 0f, 0f, 0f, 0, true);
					}
					loc.subtract(vec);
				}
				if (times == 15) {
					times = 0;
				}
				times++;
			}
		}.runTaskTimer(Core.getThis(), 0, 1);
	}
}
