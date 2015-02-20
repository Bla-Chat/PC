package jdw.xjcp.client;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

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


public final class FxGUI extends Application {
	public static final URL MAIN_WINDOW_LOC = FxGUI.class.getResource("/fxml/mainWindow.fxml");

	private Pane page;
	
	@FXML
	private TabPane chatPane;
	
	@FXML
	private VBox userPane;
	
	public FxGUI() throws IOException {
		
	}
	
	public static void startGUI() {
		launch();
	}
	
	@Override
	public void init() throws Exception {
		FXMLLoader loader = new FXMLLoader(MAIN_WINDOW_LOC);
		loader.setController(this);
		page = (Pane) loader.load();	
	}
	
	@Override
	public void start(final Stage primaryStage) throws Exception {
		Random random = new Random();
		// FIXME fix me
		for (int i = 0; i < 3; i++) {
			Chat chat = new Chat("Test" + i);
			for (int j = 0; j < 100; j++) {
				chat.addMessages(new Message(random.nextBoolean() ? MessageType.ME : MessageType.OTHER, Long.toHexString(random.nextLong())));
			}
			
			chatPane.getTabs().add(chat);
		}		
		
		userPane.getChildren().add(new User("1", uselessDesc()).getRootPane());
		userPane.getChildren().add(new User("2", uselessDesc()).getRootPane());
		userPane.getChildren().add(new User("3", uselessDesc()).getRootPane());
		userPane.getChildren().add(new User("4", uselessDesc()).getRootPane());
		userPane.getChildren().add(new User("5", uselessDesc()).getRootPane());
		userPane.getChildren().add(new User("6", uselessDesc()).getRootPane());
		
		Scene scene = new Scene(page);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Bla Chat");		
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/icon.png")));
		primaryStage.show();
	}

	private String uselessDesc() {
		return Long.toHexString(new Random().nextLong());
	}
}
