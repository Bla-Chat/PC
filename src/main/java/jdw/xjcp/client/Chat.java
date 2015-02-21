package jdw.xjcp.client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import jdw.xjcp.client.Message.MessageType;
import net.michaelfuerst.xjcp.XJCP;
import net.michaelfuerst.xjcp.protocol.Conversation;

public final class Chat extends Tab {
	private static final URL FXML_SOURCE = Chat.class.getResource("/fxml/chat.fxml");

	@FXML
	private VBox messages;
	@FXML
	private ScrollPane messagesScroller;
	@FXML
	private TextArea textbox;
	
	private final XJCP xjcp;
	private final Conversation conversation;
	private final List<Message> messageList;


	public Chat(final XJCP xjcp, final Conversation conversation) throws IOException {
		super(conversation.getName());
		this.xjcp = xjcp;
		this.conversation = conversation;
		
		messageList = new ArrayList<>();

		FXMLLoader loader = new FXMLLoader(FXML_SOURCE);
		loader.setController(this);
		setContent((Node) loader.load());

		// setOnSelectionChanged((e) -> {
		// messagesScroller.setVvalue(messagesScroller.getVmax());
		// });
	}

	public void addMessages(final Message... messages) {
		for (Message message : messages) {
			messageList.add(message);
		}

		messageList.sort((o1, o2) -> {
			return o1.getTimestamp().compareTo(o2.getTimestamp());
		});

		List<Node> uiMessages = this.messages.getChildren();
		uiMessages.clear();
		messageList.forEach(m -> {
			uiMessages.add(m.getRootPane());
		});
	}
	
	@FXML
	private void sendMessage() {
		String text = textbox.getText();
		if (text == null || text.isEmpty()) {
			return;
		}
		
		textbox.clear();
		xjcp.sendMessage(conversation.getConversation(), text);
		try {
			//FIXME execute as JavaFX Thread or else it will die.
			messages.getChildren().add(new Message(MessageType.ME, text, LocalDateTime.now()).getRootPane());
		} catch (IOException e) {
			// TODO handle in a way
			throw new RuntimeException(e);
		}
	}
}
