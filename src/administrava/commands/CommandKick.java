package administrava.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import administrava.Administrava;
import administrava.util.ChatHelper;

public class CommandKick extends Command {

	public CommandKick() {
		super("Kick", "kick", 1, "/kick [player] (reason)");
	}

	@Override
	public boolean onExecute(Player player, String[] args) {

		if(args.length > 0) {
			
			String playerArg = args[0];
			String reason = "";
			
			if(args.length > 1) {
				for(int i = 1; i < args.length; i++) {
					if(i == args.length - 1) {
						reason += args[i];
					}else{
						reason += (args[i] + " ");
					}
				}
			}else{
				reason = "unspecified";
			}
			
			Player target = null;
			
			target = Administrava.getInstance().getServer().getPlayerExact(playerArg);
			
			if(target == null) {
				ChatHelper.sendMessage(player, ChatColor.RED + "Error: Target is invalid.");
				// Invalid player
				return false;
			}else{
				if(target.isOnline()) {
					kick(target, ChatColor.RED + "You have been kicked by " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.RED +" for reason: " + reason);
					
					ChatHelper.broadcast(ChatColor.GREEN + player.getDisplayName() + " has kicked " + target.getDisplayName() +
																" for reason: " + ChatColor.RED + reason);
					return true;
				}else{
					// Player is offline
					ChatHelper.sendMessage(player, ChatColor.RED + "Error: Target is not online.");
					return false;
				}
			}
		}else{
			
			return false;
		}
	}

	private void kick(Player player, String reason) {
		player.kickPlayer(reason);
	}
}
