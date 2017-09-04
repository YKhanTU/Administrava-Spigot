package administrava.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import administrava.Administrava;

public class CommandHandler {

	// TODO Logging commands 
	
	private ArrayList<Command> commands = new ArrayList<Command>();
	
	public CommandHandler() {
		commands = new ArrayList<Command>();
		
		commands.add(new CommandKick());
		commands.add(new CommandSlap());
		commands.add(new CommandFun());
		commands.add(new CommandRocket());
	}
	
	public ArrayList<Command> getCommands() {
		return commands;
	}
	
	public Command getCommand(Class<?> clazz) {
		for(Command command : commands) {
			if(command.getClass() == clazz) {
				
				return command;
			}
		}
		
		return null;
	}

	public boolean onCommandInvoke(Player player, String cmd, String[] args) {
		
		for(Command command : commands) {
			if(command.getName().equalsIgnoreCase(cmd)) {
				
				if(player.hasPermission(command.getPermission())) {
					
					return command.onExecute(player, args);
				}else{
					// Notify player of permission negligence
					player.sendMessage(Administrava.getChatDisplay() + ChatColor.RED + "You do not have permission to use this command.");
					// Notify console that player attempted this command.
					Bukkit.getConsoleSender().sendMessage(Administrava.getChatDisplay() + "Player " + player.getDisplayName() + " has attempted to use command " + command.getName() + ".");
					
					return false;
				}
				
			}
		}
		
		return false;
	}

}
