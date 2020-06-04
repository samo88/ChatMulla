package client.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

    public class TestConnectionWindow extends HBox {

        private Stage createStage;
        private Button test, save;
        private Label ipLabel,portLabel;
        private TextField ipInputField, portInputField;

        public TestConnectionWindow(){
            this.createStage = new Stage();

            this.test = new Button("Verbindung testen");
            this.save = new Button("Konfiguration speichern");

            this.ipLabel = new Label("IP: ");
            this.ipInputField = new TextField();

            this.portLabel = new Label("Port: ");
            this.portInputField = new TextField();

            this.getChildren().addAll(ipLabel, ipInputField,portLabel,portInputField, test,save);
            this.getStyleClass().add("root");
        }
        public Button getTest() {
            return test;
        }
        public TextField getIpInputField() {
            return ipInputField;
        }
        public TextField getPortInputField() {
            return portInputField;
        }
        public Button getSave() {
            return save;
        }

        public void startDialog() {
            Scene scene = new Scene(this, 1000, 70);
            this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            createStage.setScene(scene);
            createStage.setTitle("Serverkonfiguration");
            createStage.show();
        }
        public void closeWindow(){
            createStage.close();
        }

}
