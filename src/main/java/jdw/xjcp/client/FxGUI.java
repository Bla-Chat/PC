package jdw.xjcp.client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdw.xjcp.client.Message.MessageType;
import jdw.xjcp.client.User.UserState;
import jdw.xjcp.client.notification.Notification;
import net.michaelfuerst.xjcp.DesktopXJCP;
import net.michaelfuerst.xjcp.EventHandler;
import net.michaelfuerst.xjcp.XJCP;
import net.michaelfuerst.xjcp.protocol.ChatHistory;
import net.michaelfuerst.xjcp.protocol.ChatMessage;
import net.michaelfuerst.xjcp.protocol.Contact;
import net.michaelfuerst.xjcp.protocol.Conversation;
import net.michaelfuerst.xjcp.protocol.Event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FxGUI extends Application {
	private static final Logger LOG = LogManager.getLogger();
	private static final URL MAIN_WINDOW_LOC = FxGUI.class.getResource("/fxml/mainWindow.fxml");
	private static final DateTimeFormatter MESSAGE_TIME_FORMAT = DateTimeFormatter
		.ofPattern("yyyy-MM-dd HH:mm:ss");

	private final HashMap<String, Chat> chats = new HashMap<>();

	private String host;
	private String user;

	private XJCP xjcp;
	private Pane page;
	
	@FXML
	private TabPane chatPane;
	@FXML
	private VBox userPane;

	
	public FxGUI() throws IOException {

	}

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		host = getParameters().getRaw().get(0);
		user = getParameters().getRaw().get(1);

		xjcp = new DesktopXJCP(host);
		xjcp.setEventHandler(new XJCPEventHandler());		
		xjcp.login(user, getParameters().getRaw().get(2)).get(5, TimeUnit.SECONDS);

		FXMLLoader loader = new FXMLLoader(MAIN_WINDOW_LOC);
		loader.setController(this);
		page = (Pane) loader.load();
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		Notification.info("Hello World!").show();
		
		List<Conversation> conversations = xjcp.requestChats().get();
		for (Conversation c : conversations) {
			Chat chat = new Chat(xjcp, c);
			chats.put(c.getConversation(), chat);
			
			ChatHistory history = xjcp.requestHistory(c.getConversation(), 100).get();
			for (ChatMessage m : history.getMessages()) {
				MessageType type = MessageType.ME;
				if (!m.getNick().equalsIgnoreCase(user)) {
					type = MessageType.OTHER;
				}

				chat.addMessages(new Message(type, m.getText(), LocalDateTime.parse(m.getTime(),
					MESSAGE_TIME_FORMAT)));
			}

			chatPane.getTabs().add(chat);
		}

		List<Contact> contacts = xjcp.requestContacts().get();
		for (Contact c : contacts) {
			User user = new User(c.getNick(), this.user + "@" + host);
			if (c.getStatus() != 0) {
				user.setOnlineState(UserState.ONLINE);
			} else {
				user.setOnlineState(UserState.OFFLINE);
			}

			userPane.getChildren().add(user.getRootPane());
		}

		userPane.getChildren().setAll(userPane.getChildren().sorted());

		Scene scene = new Scene(page);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Bla Chat");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/icon.png")));
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		xjcp.shutdown();
	}
	
	private final class XJCPEventHandler implements EventHandler {
		@Override
		public void handleEvent(final Event event) {
			if (event.getType().equalsIgnoreCase("onMessage")) {
				Chat chat = chats.get(event.getMessage());
				if (chat == null) {
					LOG.warn("Dropped message for: " + event.getMessage());
					return;
				}
				
				try {
					chat.addMessages(new Message(MessageType.OTHER, event.getText(), LocalDateTime.now()));
				} catch (IOException e) {
					LOG.error("Failed to add message: " + e.getMessage());
				}
			}
		}
	}
}
