package client.view;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InfoWindow extends BorderPane {


    private Label info;
    private Button confirmBtn;

    private Scene scene;
    private Stage infoStage;

    private HBox hb;

    public InfoWindow(){

        this.confirmBtn = new Button("OK");
        this.confirmBtn.setAlignment(Pos.CENTER);
        this.info = new Label();
        this.hb = new HBox();

        this.setCenter(info);
        this.hb.getChildren().add(confirmBtn);
        hb.setId("dialogButton");
        this.setBottom(hb);
        this.getStyleClass().add("root");

        this.infoStage = new Stage();
        this.scene = new Scene(this, 400,150);

    }

    public void startDialod(String title, String infoText){
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        this.info.setText(infoText);
        infoStage.setTitle(title);
        infoStage.setScene(scene);
        infoStage.show();
        closeDialog(infoStage);

    }
    public void closeDialog(Stage stage){
        this.confirmBtn.setOnAction(e->{
            stage.close();
        });
    }
    public Button getConfirmBtn() {
        return confirmBtn;
    }

}
