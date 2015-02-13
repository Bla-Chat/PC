package jdw.xjcp.client.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdw.xjcp.client.Client;
import jdw.xjcp.client.CommandExecutor;

public final class SetConversation implements CommandExecutor {
	private static final Logger LOG = LogManager.getLogger();
	
	@Override
	public void executeCommand(String label, String[] args) {
		if (args.length < 1) {
			LOG.info("Specify a conversation");
			return;
		}
		
		Client.conversation = args[0];
	}

}
