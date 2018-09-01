package me.onenrico.commanddelay.utils;

import java.util.Random;

public class RandomUT {
	public static boolean chance(int percent) {

		Random rand = new Random();
		int number = rand.nextInt(100) + 1;
		if (number >= percent) {
			return true;
		}
		return false;
	}
}
