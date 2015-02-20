package jdw.xjcp.client;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public final class Message {
	private static final URL FXML_SOURCE = Message.class.getResource("/fxml/message.fxml");
	
	private Pane rootPane;
	
	@FXML
	private Label message;
	
	public Message(final MessageType sender, final String message) throws IOException {
		assert sender != null;
		assert message != null;
		
		FXMLLoader loader = new FXMLLoader(FXML_SOURCE);
		loader.setController(this);
		rootPane = loader.load();
		
		rootPane.getStyleClass().add(sender.getStyleClass());
		this.message.setText(message);
	}
	
	
	public static enum MessageType {
		ME("message-me"),
		OTHER("message-other"),
		DATE("message-date");
		
		private final String styleClass;
		
		private MessageType(final String styleClass) {
			this.styleClass = styleClass;
		}

		public String getStyleClass() {
			return styleClass;
		}
	}
	
	public Pane getRootPane() {
		return rootPane;
	}
}
