package jdw.xjcp.client;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;


public final class Chat extends Tab {
	private static final URL FXML_SOURCE = Chat.class.getResource("/fxml/chat.fxml");
	
	@FXML
	private VBox messages;
	
	public Chat(final String title) throws IOException {
		super(title);
		
		FXMLLoader loader = new FXMLLoader(FXML_SOURCE);
		loader.setController(this);
		setContent((Node) loader.load());
	}
	
	public void addMessages(final Message... messages) {
		for (Message message : messages) {
			this.messages.getChildren().add(message.getRootPane());
		}
	}
}
