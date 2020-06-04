package client.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView extends GridPane {


    private Label loginLabel, nameLabel, passwordLabel;
    private TextField nameField;
    private PasswordField password;
    private Button confirmBtn;

    private Stage stage;
    private Scene scene;

    public LoginView(Stage primaryStage){

        this.stage = primaryStage;
        this.scene = scene;

        this.loginLabel = new Label("Melde dich an");
        loginLabel.setId("headerLogin");

        this.nameLabel = new Label("Nickname:");

        this.nameField = new TextField("samosa");

        this.passwordLabel = new Label("Passwort:");
        this.password = new PasswordField();

        this.confirmBtn = new Button("ENTER CHAT");


        this.add(loginLabel, 1,1,3,1);
        this.add(nameLabel,1,2);
        this.add(nameField,2,2);

        this.add(passwordLabel,1,3);
        this.add(password,2,3);

        this.add(confirmBtn, 2, 4, 2,1);

        this.getStyleClass().add("sign");

    }
    public TextField getNameField() {
        return nameField;
    }
    public PasswordField getPassword() {
        return password;
    }
    public Button getConfirmBtn() {
        return confirmBtn;
    }

    public void startLogin(){
        scene = new Scene(this, 450 ,500);
        stage.setScene(scene);
        stage.setTitle("LogIn - Melde dich an");
        stage.show();
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }
}
