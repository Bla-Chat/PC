package jdw.xjcp.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public final class Chat extends Tab {
	private static final URL FXML_SOURCE = Chat.class.getResource("/fxml/chat.fxml");

	@FXML
	private VBox messages;

	@FXML
	private ScrollPane messagesScroller;

	private final List<Message> messageList;

	public Chat(final String title) throws IOException {
		super(title);

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
}
