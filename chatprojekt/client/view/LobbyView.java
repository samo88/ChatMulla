package client.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class LobbyView extends BorderPane {

    private Stage stage;
    private ScrollPane roomScrollPane;
    private HBox roomRow,topBar;
    private VBox roomBox;
    private Button addRoomBtn, logoutBtn,removeBtn, refreshBtn;
    private Label searchLabel;
    private TextField searchField;


    protected static HashMap<String, Label> roomRows = new HashMap<String, Label>();
    protected static HashMap<String, HBox> roomBoxes = new HashMap<String, HBox>();

    public LobbyView(Stage primaryStage) {


        this.stage = primaryStage;

        this.addRoomBtn = new Button("+Chatraum");
        this.logoutBtn = new Button("Abmelden");
        this.refreshBtn = new Button("Aktualisieren");
        this.removeBtn = new Button("Konto löschen");

        this.searchLabel = new Label("User suchen:");
        this.searchLabel.setId("searchLabel");
        this.searchField = new TextField();
        searchField.setId("searchField");

        this.topBar = new HBox();
        topBar.getChildren().addAll(addRoomBtn,refreshBtn,logoutBtn,removeBtn, searchLabel,searchField);
        this.setTop(topBar);
        topBar.setId("topBar");

        this.roomRow = new HBox();
        this.roomBox = new VBox();

        this.roomScrollPane = new ScrollPane();
        roomScrollPane.setContent(roomBox);

        this.setCenter(roomScrollPane);

        this.getStyleClass().add("lobby");

        topBar.setId("topBar");
        roomRow.setId("row");
        roomBox.setId("roomBox");
        roomScrollPane.setId("scroll");

    }

    public void startLobby(String info){
        Scene scene = new Scene(this, 1200, 800);
        stage.setTitle("Chatroom - Lobby | Herzlich Willkommen " + info + " zum Lobbyraum..." );
        stage.setScene(scene);
        stage.show();
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }
    public void exitLobby(){
        stage.close();
    }

    public HashMap<Label,Button> addRoomRow(String roomTitle){
        HashMap<Label, Button> row = new HashMap<Label, Button>();
        HBox box = new HBox();
        Label roomLabel = new Label(roomTitle);
        Button removeBtn = new Button("LÖSCHEN");
        box.getChildren().addAll(roomLabel,removeBtn);
        box.setStyle("-fx-spacing: 10px");
        this.roomBox.getChildren().add(box);
        row.put(roomLabel,removeBtn);

        return row;
    }


    public Button getAddRoomBtn() {
        return addRoomBtn;
    }

    public Button getRefreshBtn() {
        return refreshBtn;
    }

    public Button getLogoutBtn() {
        return logoutBtn;
    }

    public VBox getRoomBox() {
        return roomBox;
    }

    public Button getRemoveBtn() {
        return removeBtn;
    }

    public Stage getStage() {
        return stage;
    }

    public TextField getSearchField() {
        return searchField;
    }
}
