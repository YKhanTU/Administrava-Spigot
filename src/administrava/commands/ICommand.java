package administrava.commands;

import org.bukkit.entity.Player;

public interface ICommand {

	String getName();
	String getPermission();
	int getArgLength();
	String getUsage();
	boolean onExecute(Player executor, String[] args);
}
