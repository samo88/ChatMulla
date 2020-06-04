package client.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GenericDialogPane extends BorderPane {

    private Label info;
    private Button confirmBtn, rejectBtn;
    private HBox buttonBox;
    private Stage stage;
    private Scene scene;

    public GenericDialogPane(){


        this.info = new Label();

        this.confirmBtn = new Button("JA");
        this.rejectBtn = new Button("NEIN");
        this.confirmBtn.setId("dialogButton");
        this.rejectBtn.setId("dialogButton");
        this.buttonBox = new HBox();
        buttonBox.getChildren().addAll(confirmBtn,rejectBtn);
        this.setCenter(info);
        this.setBottom(buttonBox);

        this.setId("dialog");
        buttonBox.setId("dialogButton");
        this.scene = new Scene(this, 400, 150);


    }
    public void startDialog(String title, String infoText){
        this.info.setText(infoText);
        this.stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }
    public void closeDialog(){
        this.stage.close();
    }

    public Button getConfirmBtn() {
        return confirmBtn;
    }

    public Button getRejectBtn() {
        return rejectBtn;
    }
}
