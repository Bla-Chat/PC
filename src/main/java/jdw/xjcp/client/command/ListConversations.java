package jdw.xjcp.client.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.michaelfuerst.xjcp.Handler;
import net.michaelfuerst.xjcp.Message;
import net.michaelfuerst.xjcp.XJCP;
import net.michaelfuerst.xjcp.helper.ChatObject;
import jdw.xjcp.client.CommandExecutor;

/**
 * Executes a list command.
 * 
 * @author TheDwoon
 * @date 22.09.2014
 * @version 1.0.0
 *
 */
public final class ListConversations implements CommandExecutor {
	private static final Logger LOG = LogManager.getLogger();
	
	private final XJCP xjcp;
	
	public ListConversations(XJCP xjcp) {
		this.xjcp = xjcp;
	}
	
	@Override
	public void executeCommand(String label, String[] args) {
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == XJCP.RPL_CHATOBJECT) {
					ChatObject chat = (ChatObject) msg.obj;
					LOG.info(chat.getName() + " (" + chat.getConversation() + ")");
				}
			}			
		};
		
		xjcp.requestChats(handler);
	}

}
