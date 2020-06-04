package client.model;

import client.model.Chatroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ClientModel {


    public static String name, token;
    public static ObservableList<Chatroom> joinedRooms = FXCollections.observableArrayList();
    public static ObservableList<Chatroom> joinedPrivateChats = FXCollections.observableArrayList();

    public ClientModel(String name, String token){
        this.name = name;
        this.token = token;

    }

    public void setName(String name) {
        this.name = name;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getName() {
        return name;
    }
    public String getToken() {
        return token;
    }
    public boolean isAccessed(String room){
        boolean proof = false;
        for (Chatroom chatroom: joinedRooms){
            if (chatroom.getChatRoomName().equals(room)) {
                proof = true;
            }
        }
        return proof;
    }
    public Chatroom findRoomByName(String room){
        for (Chatroom chatroom : joinedRooms){
            if (chatroom.getChatRoomName().equals(room)){
                return chatroom;
            }
        }
        return null;
    }
  public void deleteRoombyName(String roomName){
        Chatroom chatroom = findRoomByName(roomName);
        if (chatroom!=null){
            joinedRooms.remove(chatroom);
        }
  }

}
