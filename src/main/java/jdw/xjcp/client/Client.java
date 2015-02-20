package jdw.xjcp.client;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public final class Client {
	public static void main(final String[] args) throws InterruptedException, ExecutionException, TimeoutException, IOException {
		final String host = args[0];
		final String user = args[1];
		final String password = args[2];
		
//		System.out.println(user + "@" + host + " using " + password);
//		
//		XJCP xjcp = new DesktopXJCP(host);
//		
//		System.out.println("login: " + xjcp.login(user, password).get(2, TimeUnit.SECONDS));
//		System.out.println("contacts: " + xjcp.requestContacts().get(2, TimeUnit.SECONDS));
//		System.out.println("chats: " + xjcp.requestChats().get(2, TimeUnit.SECONDS));
//		System.out.println("messages: " + xjcp.requestHistory("penguinmenac3,thedwoon", 2).get(2, TimeUnit.SECONDS));
//
//		xjcp.shutdown();
		
//		new FxGUI();
		FxGUI.startGUI();
	}
}
