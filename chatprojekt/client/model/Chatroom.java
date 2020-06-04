package client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Chatroom {


    private String chatRoomName,isPublic;
    public static ArrayList<String> chatRoomNames = new ArrayList<String>();
    public static ObservableList<Chatroom> chatrooms = FXCollections.observableArrayList();


    public Chatroom(String chatRoomName, String isPublic){

        this.chatRoomName = chatRoomName;
        this.isPublic = isPublic;
    }
    public String getChatRoomName() {
        return chatRoomName;
    }


}
