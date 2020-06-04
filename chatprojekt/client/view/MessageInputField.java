package client.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class MessageInputField extends HBox {

    private TextField inputField;
    private Button sendBtn;

    public MessageInputField() {


        this.inputField = new TextField();
        this.inputField.setId("inputField");
        this.sendBtn = new Button("senden");

        this.getChildren().addAll(inputField, sendBtn);
        setGlobalEventHandler();
        this.getStyleClass().add("root");
        this.setId("input");
    }

    public TextField getInputField() {
        return inputField;
    }

    public Button getSendBtn() {
        return sendBtn;
    }

    private void setGlobalEventHandler() {
        this.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                sendBtn.fire();
                e.consume();
            }
        });

    }
}

