package org.akaarts.AGE;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Console {
	
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
	 * Executes AGE console commands
	 * @param commandString - the command string
	 * Each command line must end with a semicolon (;)
	 */
	public static void execute(String commandString){
		//Drop all chars except a-z, A-Z, 0-9 and _SPACE-+;
		commandString = commandString.replaceAll("[^a-zA-Z0-9_ \\-\\+;]|(\\A )|( \\z)", "");
		//Drop multiple spaces
		commandString = commandString.replaceAll("[ ]{2,}", " ");
		//split command string at ;
		String[] commands = commandString.split("[;]");
		
		info(commandString);
		
		for(String command:commands){
			switch(command){
			
			}
		}
		
	}
}
