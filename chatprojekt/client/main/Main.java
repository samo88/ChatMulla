package client.main;

import client.controller.ClientHandler;
import client.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        SignupView sign = new SignupView(primaryStage);
        LoginView login = new LoginView(primaryStage);
        LobbyView lobby = new LobbyView(primaryStage);

        ClientHandler handler = new ClientHandler(login,sign,lobby);


        Scene scene = new Scene(sign, 450, 500);
        primaryStage.setTitle("SignUp - Erstelle einen neuen Benutzer");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
