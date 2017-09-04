package administrava.commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import administrava.Administrava;

public abstract class Command implements ICommand {

	private String name;
	private String permission = "administrava.";
	private int argLength;
	private String usage;
	
	protected JavaPlugin administrava = Administrava.getInstance();
	
	public Command(String name, String permission, int argLength, String usage) {
		this.name = name;
		this.permission += permission;
		this.argLength = argLength;
		this.usage = usage;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getPermission() {
		return permission;
	}

	@Override
	public int getArgLength() {
		return argLength;
	}
	
	@Override
	public String getUsage() {
		return usage;
	}

	public abstract boolean onExecute(Player player, String[] args);
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Command) {
			Command cmd = (Command) o;
			
			return cmd.getName().equalsIgnoreCase(this.name) && cmd.getPermission().equalsIgnoreCase(this.permission);
		}
		return false;
	}
	
}
