package org.akaarts.AGE;

import org.akaarts.AGE.CLI.CommandListener;

public class LauncherCommands implements CommandListener {

	public static final LauncherCommands SELF = new LauncherCommands();
	
	private LauncherCommands() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean[] run(String func, String[] args) {
		boolean[] ret = new boolean[2];
		
		switch(func){
		case "exit":
			Engine.requestExit();
			break;
		default:
			ret[0] = false;
			ret[1] = false;
			return ret;
		}
		ret[1]=true;
		return ret;
	}

	@Override
	public String[] getCommands() {
		return new String[]{"exit"};
	}

}
