package client.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RoomDialogWindow extends HBox {

    private Stage createStage;
    private Button create;
    private Label roomNameLabel;
    private TextField roomNameField;

    public RoomDialogWindow(){

        this.createStage = new Stage();
        this.create = new Button("CREATE");
        this.roomNameLabel = new Label("ChatRoomName");
        this.roomNameField = new TextField();

        this.getChildren().addAll(roomNameLabel, roomNameField, create);
        this.getStyleClass().add("root");

    }

    public Button getCreate() {
        return create;
    }
    public TextField getRoomNameField() {
        return roomNameField;
    }
    public void startDialog() {
        Scene scene = new Scene(this, 650, 70);
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        createStage.setScene(scene);
        createStage.setTitle("Chatroom erstellen");
        createStage.show();
    }

    public void closeWindow(){
        createStage.close();
    }
}
