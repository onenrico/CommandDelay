package me.onenrico.commanddelay.utils;

import java.util.HashMap;

public class MathUT {

	public static float getPersentase(float value, float realvalue) {
		float persentase = 100 / realvalue * value;
		return persentase;
	}

	public static float getRealvalue(float value, float persentase) {
		float realvalue = value * persentase / 100;
		return realvalue;
	}

	public static int strInt(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		str = str.trim();
		double result = 0;
		int flag = 0;
		int i = 0;
		if (str.charAt(0) == '-') {
			flag = 1;
			i++;
		} else if (str.charAt(0) == '+') {
			i++;
		}
		while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
			result = result * 10 + (str.charAt(i) - '0');
			i++;
		}
		if (flag == 1) {
			result = -result;
		}
		result = clamp((int) result, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return (int) result;
	}

	public static int clamp(int value, int min, int max) {
		if (value > max) {
			return max;
		} else if (value < min) {
			return min;
		} else {
			return value;
		}
	}

	public static int clamp(int value, int min) {
		return clamp(value, min, Integer.MAX_VALUE);
	}

	public static int clamp(int value, float max) {
		int maxx = (int) max;
		return clamp(value, Integer.MIN_VALUE, maxx);
	}

	private static int c2n(char c) {
		switch (c) {
		case 'I':
			return 1;
		case 'V':
			return 5;
		case 'X':
			return 10;
		case 'L':
			return 50;
		case 'C':
			return 100;
		case 'D':
			return 500;
		case 'M':
			return 1000;
		default:
			return 0;
		}
	}

	public static int romanToInt(String a) {
		int result = 0;
		for (int i = 0; i < a.length(); i++) {
			if (i > 0 && c2n(a.charAt(i)) > c2n(a.charAt(i - 1))) {
				result += c2n(a.charAt(i)) - 2 * c2n(a.charAt(i - 1));
			} else {
				result += c2n(a.charAt(i));
			}
		}
		return result;
	}

	private static int[] bases = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
	private static HashMap<Integer, String> map = new HashMap<Integer, String>();
	private static Boolean setup = false;

	public static String intToRoman(int a) {
		if (!setup) {
			setup();
			setup = true;
		}
		String result = "";
		for (int i : bases) {
			while (a >= i) {
				result += map.get(i);
				a -= i;
			}
		}
		return result;

	}

	private static void setup() {
		map.put(1, "I");
		map.put(4, "IV");
		map.put(5, "V");
		map.put(9, "IX");
		map.put(10, "X");
		map.put(40, "XL");
		map.put(50, "L");
		map.put(90, "XC");
		map.put(100, "C");
		map.put(400, "CD");
		map.put(500, "D");
		map.put(900, "CM");
		map.put(1000, "M");
	}
}
