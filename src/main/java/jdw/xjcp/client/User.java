package jdw.xjcp.client;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public final class User {
	private static final URL FXML_SOURCE = User.class.getResource("/fxml/user.fxml");
	
	private Pane rootPane;
	
	@FXML
	private Label info;
	@FXML
	private Label name;
	
	public User(final String name, final String info) throws IOException {
		Objects.requireNonNull(name);
		Objects.requireNonNull(info);
		
		FXMLLoader loader = new FXMLLoader(FXML_SOURCE);
		loader.setController(this);
		rootPane = loader.load();
		
		this.info.setText(info);
		this.name.setText(name);
	}
	
	public Pane getRootPane() {
		return rootPane;
	}
}
