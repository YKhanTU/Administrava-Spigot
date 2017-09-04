package administrava.util;

import org.bukkit.entity.Player;

import administrava.Administrava;

public class ChatHelper {

	public static void broadcast(String msg) {
		Administrava.getInstance().getServer().broadcastMessage(msg);
	}
	
	public static void sendMessage(Player player, String msg) {
		player.sendMessage(msg);
	}
}
