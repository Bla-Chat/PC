package jdw.xjcp.client.notification;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class Notification {
	private static final URL FXML_SOURCE = Notification.class.getResource("/fxml/notification.fxml"); 
	
	private Pane rootPane;
	
	@FXML
	private Label text;
	@FXML
	private ImageView image;
	
	private Notification() throws IOException {
		this(NotificationType.INFO, "Some Information");
	}
	
	private Notification(final NotificationType type, final String text) throws IOException {
		FXMLLoader loader = new FXMLLoader(FXML_SOURCE);
		loader.setController(this);
		rootPane = loader.load();		
		
		this.text.setText(text);
		image.setImage(new Image(Notification.class.getResourceAsStream(type.getImagePath())));
	}
	
	public static Stage info(final String text) throws IOException {
		Stage stage = new Stage(StageStyle.TRANSPARENT);		
		stage.setX(Screen.getPrimary().getVisualBounds().getMaxX() - 260);
		stage.setY(Screen.getPrimary().getVisualBounds().getMinY() + 10);				
		stage.setAlwaysOnTop(true);
		stage.setOpacity(0.9);
		stage.initModality(Modality.NONE);
		
		Scene scene = new Scene(new Notification(NotificationType.INFO, text).rootPane);		
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);		
			
		return stage;
	}
	
	public static enum NotificationType {
		INFO("/icon/info.png"),
		WARNING("/icon/warning.png"),
		ERROR("/icon/error.png");
		
		private String path;
		
		private NotificationType(final String path) {
			this.path = path;
		}
		
		private String getImagePath() {
			return path;
		}
	}
}
