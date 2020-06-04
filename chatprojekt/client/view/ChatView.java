package client.view;

import client.model.ClientModel;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChatView extends GridPane {

    private MessageInputField input;
    private ChatHistoryScreen screen;
    private Stage stage;
    private Scene scene;

    private String chatRoomName;

    public ChatView(){

        this.input = new MessageInputField();
        this.add(input,1,2);
        this.screen = new ChatHistoryScreen();
        this.add(screen,1,1);


        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        this.getStyleClass().add("root");
        this.setId("chatView");
    }
    public void startChatView(String name){
        this.chatRoomName = name;
        this.stage = new Stage();
        this.scene = new Scene(this,1000,700);
        stage.setScene(scene);
        stage.setTitle(name);
        stage.show();
        stage.setResizable(false);
    }
    public void closeChatView(){
        this.stage.close();
    }
    public MessageInputField getInput() {
        return input;
    }
    public ChatHistoryScreen getScreen() {
        return screen;
    }
    public String getChatRoomName() {
        return chatRoomName;
    }
    public Stage getStage() {
        return stage;
    }
}
