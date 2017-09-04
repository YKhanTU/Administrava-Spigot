package administrava;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import administrava.commands.CommandHandler;
import administrava.listeners.ConnectionListener;
import administrava.listeners.EntityListener;
import administrava.listeners.ItemListener;
import administrava.listeners.MovementListener;

public class Administrava extends JavaPlugin {

	private static Administrava instance;
	private static final String CHAT_DISPLAY = ChatColor.GRAY + "[" + ChatColor.YELLOW + "Administrava" + ChatColor.GRAY + "]"
												+ ChatColor.WHITE + ": ";
	
	private CommandHandler commandHandler;
	
	public Administrava() {
		super();
		
		instance = this;
	}
	
	public static Administrava getInstance() {
		return instance;
	}
	
	public static String getChatDisplay() {
		return CHAT_DISPLAY;
	}
	
	@Override
	public void onEnable() {
		commandHandler = new CommandHandler();
		
		getServer().getPluginManager().registerEvents(new ItemListener(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new MovementListener(), this);
		getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
		
		this.getServer().broadcastMessage(CHAT_DISPLAY + "Plugin has successfully loaded.");
	}
	
	@Override
	public void onDisable() {
		this.getServer().broadcastMessage(CHAT_DISPLAY + "Plugin has successfully unloaded.");
	}
	
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String cmd = command.getName();
		Player player = null;
		
		
		if(sender instanceof Player) {
			player = (Player) sender;
			
			if(commandHandler.onCommandInvoke(player, cmd, args)) {
				return true;
			}
		}else{
			
			return false;
			// OP has attempted to invoke a command.
		}
		
		return false;
	}
	
}
