package client.view;

import client.main.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupView extends GridPane {


    private Label signInLabel, nameLabel, emailLabel,skipLabel;
    private TextField nameField,password;
    private Button confirmBtn,skipBtn,testConnection;

    private Stage stage;


    public SignupView(Stage primaryStage){

        this.stage = primaryStage;

        this.signInLabel = new Label("REGISTRIERE DICH");
        signInLabel.setId("header");
        signInLabel.setAlignment(Pos.CENTER);
        this.nameLabel = new Label("Nickname:");
        this.nameField = new TextField("samosa");


        this.emailLabel = new Label("Passwort:");
        this.password = new TextField("samosa");
        this.nameField.setId("fields");
        this.password.setId("fields");

        this.confirmBtn = new Button("SIGNUP");
        this.skipLabel = new Label("Schon registriert?");
        this.skipBtn = new Button("zum Login");

        this.testConnection = new Button("Verbindung testen");
        this.add(signInLabel, 1,1,4,1);
        this.add(nameLabel,1,2);
        this.add(nameField,2,2);

        this.add(emailLabel,1,3);
        this.add(password,2,3);

        this.add(confirmBtn, 2, 4, 4,1);

        this.add(skipLabel,1,6);
        this.add(skipBtn, 2,6);
        this.add(testConnection, 2,7);


        this.getStyleClass().add("sign");
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

    }
    public void startSignUpView() throws Exception {
        Main main = new Main();
        main.start(stage);

    }
    public TextField getNameField() {
        return nameField;
    }
    public TextField getPassword() {
        return password;
    }
    public Button getConfirmBtn() {
        return confirmBtn;
    }

    public Button getTestConnection() {
        return testConnection;
    }

    public Button getSkipBtn() {
        return skipBtn;
    }

}
