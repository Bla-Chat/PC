package jdw.xjcp.client;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public final class User implements Comparable<User> {
	private static final URL FXML_SOURCE = User.class.getResource("/fxml/user.fxml");
	
	private Pane rootPane;
	
	@FXML
	private Label info;
	@FXML
	private Label name;
	
	private UserState state;
	

	public User(final String name, final String info) throws IOException {
		this(name, info, UserState.OFFLINE);
	}
	
	public User(final String name, final String info, final UserState state) throws IOException {
		Objects.requireNonNull(name);
		Objects.requireNonNull(info);
		
		FXMLLoader loader = new FXMLLoader(FXML_SOURCE);
		loader.setController(this);
		rootPane = loader.load();
		
		this.info.setText(info);
		this.name.setText(name);
		
		setOnlineState(state);
	}
	
	public void setOnlineState(final UserState state) {
		rootPane.getStyleClass().clear();
		rootPane.getStyleClass().add(state.getNodeStyleClass());
		this.state = state;
	}
	
	public Pane getRootPane() {
		return rootPane;
	}
	
	public static enum UserState {
		ONLINE("user-online"), OFFLINE("user-offline");

        private final String nodeStyleClass;

        private UserState(final String nodeStyleClass) {
            this.nodeStyleClass = nodeStyleClass;
        }

        public String getNodeStyleClass() {
            return nodeStyleClass;
        }
	}
	
	@Override
	public int compareTo(final User o) {
		int difference = o.state.ordinal() - state.ordinal();
		
		if (difference == 0) {
			difference = name.getText().compareTo(o.name.getText());
		}
		
		return difference;
	}
}
