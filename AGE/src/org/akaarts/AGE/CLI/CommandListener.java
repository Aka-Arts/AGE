package org.akaarts.AGE.CLI;

public interface CommandListener {
	
	/**
	 * Gets called from the console, if a command is entered.
	 * @param func - the function of the command
	 * @param args - the arguments as array
	 * @return - Array - (index 0) true to prevent propagation (to prevent calls of following listeners)<br>
	 * (index 1) set true if you handled this command!
	 */
	public boolean[] run(String func, String[] args);
	
	public String[] getCommands();
	
}
