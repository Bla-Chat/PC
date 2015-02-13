package jdw.xjcp.client;

import java.util.HashMap;
import java.util.Scanner;

import jdw.xjcp.client.command.ListConversations;
import jdw.xjcp.client.command.SetConversation;
import net.michaelfuerst.xjcp.Handler;
import net.michaelfuerst.xjcp.Message;
import net.michaelfuerst.xjcp.XJCP;
import net.michaelfuerst.xjcp.connection.HttpConnection;
import net.michaelfuerst.xjcp.device.DesktopDevice;
import net.michaelfuerst.xjcp.helper.Event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A simple XJCP based client.
 * 
 * @author TheDwoon
 * @date 21.09.2014
 * @version 1.0.0
 *
 */
public final class Client {
	private static final Logger LOG = LogManager.getLogger();
	private static final HashMap<String, CommandExecutor> execturos = new HashMap<String, CommandExecutor>();
	
	public static String conversation = null;
	
	private Client() {
		
	}
	
	/**
	 * Runs the program. <br />
	 * <br />
	 * args[0] host <br />
	 * args[1] id <br />
	 * args[2] password <br />
	 * 
	 * @param args the arguments provided for running the client.
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		
		final XJCP xjcp = XJCP.createXJCP(args[0], true, false, new HttpConnection(args[0], new DesktopDevice(true, true)));
		
		initCmds(xjcp);
		
		xjcp.setEventHandler(new Handler() {			
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == XJCP.RPL_EVENT) {
					Event m = (Event) msg.obj;
					LOG.info(m.getNick() + ": " + m.getText());
				}
			}
		});
		
		xjcp.setLoginData(args[1], args[2], null);
		
		Thread.sleep(2000);
		
		xjcp.createConversation(new String[] {"testertom", "thedwoon"}, null);
		
		while (scanner.hasNext()) {
			final String line = scanner.nextLine();			

			if (line == null) {
				break;
			}
			
			if (line.startsWith("/")) {
				// execute the command
				String label;
				String[] cmdArgs;
				
				int i = line.indexOf(" ");
				if (i < 0) {			
					label = line.substring(1);
					cmdArgs = new String[] {};
				} else {
					label = line.substring(1, i);
					cmdArgs = line.substring(i + 1).split(" ");
				}
				
				CommandExecutor executor = execturos.get(label);
				if (executor != null) {
					executor.executeCommand(label, cmdArgs);
				} else {
					LOG.info("No executor found for " + label);
				}				
			} else {
				// send our message
				if (conversation != null) {
					xjcp.sendMessage(conversation, line, null);
				} else {
					LOG.info("No conversation selected");
				}
			}
			
			if (line.equalsIgnoreCase("/quit")) {
				break;
			}
		}
				
		scanner.close();
		
		LOG.info("Client terminated");
	}
	
	private static void initCmds(XJCP xjcp) {
		execturos.put("list", new ListConversations(xjcp));
		execturos.put("conversation", new SetConversation());
	}
}
