package client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class ChatHistoryScreen extends ScrollPane {

    private VBox container;

    public ChatHistoryScreen() {

        this.container = new VBox();
        this.container.setId("container");
        this.setContent(container);

        this.setId("scroll");
        this.getStyleClass().add("root");
    }

    public void addMessage(String sender, String content) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = dateTime.format(myFormatObj);
        Label space = new Label("");
        Label time = new Label(formattedDate);
        space.setId("space");
        time.setId("time");
        HBox hb = new HBox();
        HBox hb2 = new HBox();
        hb.setId("hbox");
        hb2.setId("hbox");
            prepareMessage(sender, content, time, hb, hb2);
    }

    private void prepareMessage(String sender, String content, Label time, HBox hb, HBox hb2) {
        String message = null;
        if (content.length() > 60) {
            message = formatMessage(content);
        } else {
            message = content;
        }
        Label label2 = new Label(sender + "\n" + message);
        label2.setId("message");
        if (sender.equals("Du")) {
            label2.getStyleClass().add("me");
            hb.setAlignment(Pos.BASELINE_LEFT);
            hb2.setAlignment(Pos.BASELINE_LEFT);
            hb.getChildren().add(label2);
            hb2.getChildren().add(time);
        } else {
                label2.getStyleClass().add("other");
                hb.setAlignment(Pos.BASELINE_RIGHT);
                hb2.setAlignment(Pos.BASELINE_RIGHT);
                hb.getChildren().add(label2);
                hb2.getChildren().add(time);

        }
        container.getChildren().addAll(hb, hb2);
        this.vvalueProperty().bind(container.heightProperty());
    }

    public String[] breakMessageAPart(String msg) {
        String[] msgContent = msg.split("\\ ");
        return msgContent;
    }


    public String formatMessage(String content) {
        int counter = 1;
        String message = "";
        int length = content.length()/60;
        String[] msgContent = breakMessageAPart(content);
       for (String s: msgContent){
       message += s +" ";
       if (message.length()> counter*60){
           message += "\n";
           counter++;
       }

       }

        return message;
    }
}