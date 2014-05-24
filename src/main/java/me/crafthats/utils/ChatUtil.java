package me.crafthats.utils;

public class ChatUtil {

	public static String convertToChatColors(String oldString) {
		String newString = oldString;
		newString = newString.replace("&0", "§0");
		newString = newString.replace("&1", "§1");
		newString = newString.replace("&2", "§2");
		newString = newString.replace("&3", "§3");
		newString = newString.replace("&4", "§4");
		newString = newString.replace("&5", "§5");
		newString = newString.replace("&6", "§6");
		newString = newString.replace("&7", "§7");
		newString = newString.replace("&8", "§8");
		newString = newString.replace("&9", "§9");
		newString = newString.replace("&a", "§a");
		newString = newString.replace("&b", "§b");
		newString = newString.replace("&c", "§c");
		newString = newString.replace("&d", "§d");
		newString = newString.replace("&e", "§e");
		newString = newString.replace("&f", "§f");

		newString = newString.replace("&l", "§l");
		newString = newString.replace("&m", "§m");
		newString = newString.replace("&r", "§r");
		newString = newString.replace("&k", "§k");
		newString = newString.replace("&o", "§o");
		newString = newString.replace("&n", "§n");
		return newString;
	}

}
