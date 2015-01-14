package org.akaarts.earlyalpha.AGE.CLI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Console {
	
	private static ArrayList<CommandListener> listeners = new ArrayList<CommandListener>(4);
	private static ArrayList<Command> commandQueue = new ArrayList<Command>(4);
	
	/**
	 * Logs a message in to the console
	 * @param msg - The message
	 * @param lvl - The log level (0 = info, 1 = warning, 2 = error)
	 */
	public static void log(String msg,int lvl){
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
    	String time = sdf.format(cal.getTime());
		switch(lvl){
		case 0:
			System.out.println(time+"[INFO] "+msg);
			break;
		case 1:
			System.out.println(time+"[WARNING] "+msg);
			break;
		case 2:
			System.out.println(time+"[ERROR] "+msg);
			break;
		default:
			System.out.println(time+"[INVALID LOG LEVEL] "+msg);
			break;
		}
	}
	
	/**
	 * Logs a message as info
	 * @param msg - The message
	 */
	public static void info(String msg){
		Console.log(msg,0);
	}
	
	/**
	 * Logs a message as warning
	 * @param msg - The message
	 */
	public static void warning(String msg){
		Console.log(msg, 1);
	}
	
	/**
	 * Logs a message as error
	 * @param msg - The message
	 */
	public static void error(String msg){
		Console.log(msg, 2);
	}
	
	/**
	 * Executes AGE console commands queue
	 * Each command line must end with a semicolon (;)
	 */
	public static void executeQueue(){
		for(Command command:commandQueue){
			info(">"+command);
			boolean handled = false;
			for(CommandListener listener:listeners){
				boolean[] ret = listener.run(command.func, command.args);
				if(ret[1]){
					handled = true;
				}
				if(ret[0]){
					break;
				}
			}
			if(!handled){
				Console.warning("Command not found: "+command.func);
			}
		}
		commandQueue.clear();
	}
	
	/**
	 * Adds a new listener object to the broadcast list
	 * @param listener - the new listener
	 */
	
	public static void addListener(CommandListener listener){	
		listeners.add(listener);
		Console.info("Console - Status: "+listeners.size()+" listeners");
	}
	
	public static void clearListeners(){
		listeners.clear();
		Console.info("Console - Status: "+listeners.size()+" listeners");
	}

	public static void removeListener(CommandListener listener) {
		listeners.remove(listener);
		Console.info("Console - Status: "+listeners.size()+" listeners");
	}
	
	public static void queueCommands(String commandString){
		
		//Drop all chars except a-z, A-Z, 0-9 and _SPACE-+;
		commandString = commandString.replaceAll("[^a-zA-Z0-9_ \\-\\+;]|(\\A )|( \\z)", "");
		//Drop multiple spaces
		commandString = commandString.replaceAll("[ ]{2,}", " ");
		//split command string at ;
		String[] commands = commandString.split("[;]");
				
		for(String command:commands){
			commandQueue.add(new Command(command));
		}
		
		
	}
}
