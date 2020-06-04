package client.controller;
import client.model.Chatroom;
import client.model.ClientModel;
import client.view.*;
import client.view.GenericDialogPane;
import client.view.InfoWindow;
import client.view.RoomDialogWindow;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import server.Client;
import server.message.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Logger;

public class ClientHandler {


    private SignupView sign;
    private LoginView login;
    private LobbyView lobbyView;

    private InfoWindow infoPane;
    private GenericDialogPane dialogPane;

    private  String currentJoined = "";

    private Socket socket = null;
    private OutputStreamWriter out;
    private BufferedReader in;

    private Logger logger = Logger.getLogger("");

    public static  HashMap<Chatroom, ChatView> viewRepository = new HashMap<>();

    private ClientModel client;

    private static String IP = "147.86.8.31";
    private static int PORT = 50001;

    private boolean receiveLoop = true;

    public ClientHandler(LoginView login, SignupView sign, LobbyView lobby) {
        this.lobbyView = lobby;
        this.login = login;
        this.sign = sign;

        this.dialogPane = new GenericDialogPane();
        this.infoPane = new InfoWindow();
        globalHandler();
    }
    public void startClient() {
        try {
            logger.info("Client: Verbinde Socket an"+ IP+ " PORT:"+ PORT);
            socket = new Socket(IP, PORT);
            logger.info("Client: verbunden");

            out = new OutputStreamWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (receiveLoop) {
                        try {
                            String msg = in.readLine();
                            String[] msgContent = breakMessageAPart(msg);
                            logger.info("Client: Message erhalten "+ msgContent[1]);
                            if (msgContent[1].equals("CreateLogin")){
                                Platform.runLater(()->{
                                    startLogin(msgContent[2]);
                                });
                            }
                            if (msgContent[1].equals("Login")){
                                Platform.runLater(()->{
                                    startLobby(msgContent);
                                });
                            }
                            if (msgContent[1].equals("ListChatrooms")){
                                Platform.runLater(()-> {
                                    resultListChatroom(msgContent);
                                });
                            }
                            if (msgContent[1].equals("JoinChatroom")){
                                Platform.runLater(()->{
                                    resultJoinChatroom(msgContent);
                                });
                            }
                            if (msgContent[1].equals("LeaveChatroom")){
                                Platform.runLater(()->{
                                   resultLeaveChatroom(msgContent);
                                });
                            }
                            if (msgContent[1].equals("SendMessage")){
                                Platform.runLater(()->{
                                    resultSendMessage(msgContent[2]);
                                });
                            }
                            if (msgContent[1].equals("DeleteChatroom")){
                                Platform.runLater(()->{
                                    resultDeleteChatroom(msgContent);
                                });
                            }
                            if (msgContent[1].equals("Ping")){
                                Platform.runLater(()->{
                                infoPane.startDialod("Verbindung", "Rückmeldung wurde erhalten... Server ist aktiv!");
                                logger.info("Port: "+socket.getPort()+" IP: "+ socket.getInetAddress());
                                });
                            }
                            if (msgContent[1].equals("DeleteLogin")){
                                Platform.runLater(()->{
                                    try {
                                        resultDeleteLogin(msgContent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                            if (msgContent[1].equals("ListChatroomUsers")){
                                Platform.runLater(()->{
                                    System.out.println(msg);
                                });
                            }
                            if (msgContent[1].equals("Logout")){
                                Platform.runLater(()->{
                                    resultLogout(msgContent);
                                });
                            }
                            if (msgContent[1].equals("UserOnline")){
                                Platform.runLater(()->{
                                    resultUserOnline(msgContent[2]);
                                });
                            }
                            if (msgContent[0].equals("MessageText")){
                                Platform.runLater(()->{
                                    displayMessage(msgContent);

                                });
                            }
                            if (msgContent[0].equals("MessageError")){
                                Platform.runLater(()->{
                                    handleErrorMessages("MessageError");
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        } catch (ConnectException e){
            infoPane.startDialod("Verbindung", "Verbindung gescheitert...Host nicht erreichbar");
            logger.warning( "Client : Verbindung gescheitert...Host nicht erreichbar!");
        } catch (UnknownHostException| SocketException e) {
            e.printStackTrace();
            infoPane.startDialod("Verbindung", "Verbindung gescheitert...Host nicht erreichbar");
            logger.warning( "Client : Verbindung gescheitert...Host nicht erreichbar!");
        } catch (IOException  e) {
            e.printStackTrace();
            infoPane.startDialod("Verbindung", "Verbindung gescheitert...Host nicht erreichbar");
            logger.warning( "Client : Verbindung gescheitert...Host nicht erreichbar!");

        }
    }

    private void resultUserOnline(String s) {
        String name = lobbyView.getSearchField().getText();
        if (s.equals("true")){
            lobbyView.getSearchField().setStyle("-fx-background-color: green");
            lobbyView.getSearchField().setText(name +" ist ONLINE");
        }else {
            if (s.equals("false")){
                lobbyView.getSearchField().setText("");
                lobbyView.getSearchField().setStyle("-fx-background-color: red");
                lobbyView.getSearchField().setText(name +" ist OFFLINE");
            }
        }
    }

    private void resultSendMessage(String s) {
        if (s.equals("false")) {
            handleErrorMessages("Senden");
        }
    }
    private void createLogin(){
        this.sign.getConfirmBtn().setOnAction(e->{
            if (socket==null) {
                startClient();
            }
                String username = sign.getNameField().getText();
                String password = sign.getPassword().getText();
                String [] data = {"CreateLogin",username , password};
                CreateLogin createLogin = new CreateLogin(data);
                sendMessage(createLogin, data);
        });
        this.sign.getSkipBtn().setOnAction(e->{
            if (socket==null){
                startClient();
            }
            login.startLogin();
        });
    }
    private void startLogin(String isSuccess){
        if (isSuccess.equals("true")){
            login.startLogin();
            setLogin();
        }else{
            if (isSuccess.equals("false")){
                handleErrorMessages("CreateLogin");
            }
        }
    }
    private void setLogin(){
        this.login.getConfirmBtn().setOnAction(e->{
            String username = login.getNameField().getText();
            String password = login.getPassword().getText();
            String [] data = {"Login", username,password};
            Login setLogin = new Login(data);
            sendMessage(setLogin, data);
        });
    }
    private void startLobby(String[] msgContent){
        if (msgContent[2].equals("true")){
           this.client = new ClientModel(login.getNameField().getText(), msgContent[3]);
            lobbyView.startLobby(client.getName());
            requestRoomList();
        }else{
            handleErrorMessages("FalseLogin");
        }
    }
    private void createRoom(){
        RoomDialogWindow roomDialogPane = new RoomDialogWindow();
        lobbyView.getAddRoomBtn().setOnAction(e->{
            roomDialogPane.startDialog();
            roomDialogPane.getCreate().setOnAction(ev->{
                String roomName = roomDialogPane.getRoomNameField().getText();
                String [] data = {"CreateChatroom", client.getToken(),roomName,"true"};
                CreateChatroom create = new CreateChatroom(data);
                sendMessage(create, data);
                String [] data2 = {"ListChatrooms", client.getToken()};
                ListChatrooms chatrooms = new ListChatrooms(data2);
                sendMessage(chatrooms,data2);
                roomDialogPane.closeWindow();
            });
        });
    }
    private void resultDeleteLogin(String[] msgContent) throws Exception {
        if (msgContent[2].equals("true")){
            lobbyView.exitLobby();
            sign.startSignUpView();
        }else{
            handleErrorMessages("Delete Login");
        }
    }
    private void resultDeleteChatroom(String[] msgContent) {
        if (msgContent[2].equals("true")){
            requestRoomList();
        }else{
            handleErrorMessages("Delete Chatroom");
        }
    }
    private void refreshChatroomList(){
        lobbyView.getRefreshBtn().setOnAction(e->{
            String [] data = {"ListChatrooms", client.getToken()};
            ListChatrooms refresh = new ListChatrooms(data);
            sendMessage(refresh, data);
        });
    }
    private void resultListChatroom(String[] msgContent) {
        if (msgContent[2].equals("true")){
            lobbyView.getRoomBox().getChildren().clear();
            Chatroom.chatrooms.clear();
            for (int i = 3 ; i<msgContent.length; i++){
                createRoomLabel(msgContent[i]);
                Chatroom.chatrooms.add(new Chatroom(msgContent[i], "true"));
            }
        }else{
            handleErrorMessages("Chatrooms");
        }

    }
    private void createRoomLabel(String room){
        Chatroom created = new Chatroom(room, "true");
        Chatroom.chatrooms.add(created);
        HashMap<Label, Button> roomRow = lobbyView.addRoomRow(room);
        for (Map.Entry<Label, Button> map : roomRow.entrySet()) {
                map.getKey().setOnMouseClicked(selectEvent -> {
                    String[] data = {"JoinChatroom", client.getToken(), room, client.getName()};
                    JoinChatroom join = new JoinChatroom(data);
                    sendMessage(join, data);
                    currentJoined = map.getKey().getText();
                    ClientModel.joinedRooms.add(created);

                });
            map.getValue().setOnAction(deleteEvent -> {
                String[] data = {"DeleteChatroom", client.getToken(), room};
                DeleteChatroom delete = new DeleteChatroom(data);
                sendMessage(delete, data);
                requestRoomList();
            });
        }
    }
    private void requestRoomList(){
        String [] data = {"ListChatrooms", client.getToken()};
        ListChatrooms refresh = new ListChatrooms(data);
        sendMessage(refresh, data);
    }
    private void requestAccountDelete(){
        lobbyView.getRemoveBtn().setOnAction(e->{
            dialogPane.startDialog("Account löschen?","Account löschen? Bist du sicher?");
            dialogPane.getConfirmBtn().setOnAction(confirmEv->{
                String [] data = {"DeleteLogin", client.getToken()};
                DeleteLogin deleteLogin = new DeleteLogin(data);
                sendMessage(deleteLogin, data);
                dialogPane.closeDialog();
            });
            dialogPane.getRejectBtn().setOnAction(rejectEv->{
                dialogPane.closeDialog();
            });
        });
    }
    private void resultJoinChatroom(String[] msgContent) {
        if (msgContent[2].equals("true")){
           setChatView(currentJoined, "true");
        }else{
            handleErrorMessages("Chatraum");
        }
    }

    private void lookForUserOnline(){
        lobbyView.getSearchField().setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                String searchedUser = lobbyView.getSearchField().getText();
                String [] online = {"UserOnline",client.getToken(),searchedUser};
                UserOnline userOnline = new UserOnline(online);
                sendMessage(userOnline,online);
            }
            if (event.getCode()== KeyCode.BACK_SPACE){
                lobbyView.getSearchField().setText("");
                lobbyView.getSearchField().setStyle("-fx-background-color: grey");
                lobbyView.getSearchField().setText("");
            }
        });


    }
    private void setChatView(String currentJoin, String isPublic){
        ChatView view = new ChatView();
        Chatroom room = new Chatroom(currentJoin, isPublic);
        view.startChatView(currentJoin);
        viewRepository.put(room, view);

        view.getInput().getSendBtn().setOnAction(e->{
            String msg = view.getInput().getInputField().getText();
            String[] data = {"SendMessage", client.getToken(), currentJoin, msg};
            SendMessage send = new SendMessage(data);
            sendMessage(send, data);
        });

        view.getStage().setOnCloseRequest(e->{
            String[] data = {"LeaveChatroom", client.getToken(), currentJoin, client.getName()};
            LeaveChatroom leaveChatroom = new LeaveChatroom(data);
            sendMessage(leaveChatroom, data);
            client.deleteRoombyName(currentJoin);
        });

    }
    private void resultLeaveChatroom(String[] msgContent) {
        if (msgContent[2].equals("false")){
            handleErrorMessages("LeaveError");
        }else{
            infoPane.startDialod("Chatraum", "Du hast den Chatraum verlassen");
        }
    }
    private void displayMessage(String[] msgContent) {
        String roomName = msgContent[2];
        String sender = msgContent[1];
        String message = msgContent[3];
        ChatView view = findWindowByName(roomName);
        if (sender.equals(client.getName())&&view!=null){
            view.getScreen().addMessage("Du", message);
        }else{
            view.getScreen().addMessage(sender, message);
        }
        view.getInput().getInputField().setText("");
    }
    private ChatView findWindowByName(String roomName){
        ChatView view = null;
        for (Map.Entry<Chatroom,ChatView> room : viewRepository.entrySet()){
            if (room.getValue().getChatRoomName().equals(currentJoined)){
               view = room.getValue();
            }
        }
        return view;
    }
    private void globalHandler(){
        createLogin();
        setLogin();
        createRoom();
        refreshChatroomList();
        lookForUserOnline();
        requestAccountDelete();
        testConnection();
        logout();
    }


    public String [] breakMessageAPart(String msg){
        String [] msgContent = msg.split("\\|");
        return msgContent;
    }
    public void sendMessage(Message msg, String [] data) {
        try {
            msg.send(socket);
        } catch (IOException e) {
            e.printStackTrace();
            handleErrorMessages("Senden");

        }
    }
    private void handleErrorMessages(String messageType){
        switch (messageType){
            case "CreateLogin": infoPane.startDialod("Registrierung","Username bereits vergeben!");
                logger.warning(messageType+": Username bereits vergeben!\n");

                break;
            case "Senden" : infoPane.startDialod("Nachricht","Nachricht wurde nicht versendet!" );
                logger.warning(messageType+": Nachricht wurde nicht versendet!\n");

                break;
            case "MessageError" : infoPane.startDialod("Eingabefeld", "Eingabefeld bitte nicht leer lassen!");
                logger.warning(messageType+": Eingabefeld bitte nicht leer lassen!\n");

                break;
            case "FalseLogin" : infoPane.startDialod("Anmelden", "Username oder Passwort falsch eingegeben!");
                logger.warning(messageType+": Username oder Passwort falsch eingegeben!\n");

                break;
            case "Delete Login" : infoPane.startDialod("Account löschen", "Account konnte nicht gelöscht werden!");
                logger.warning(messageType+": Account konnte nicht gelöscht werden!\n");

                break;
            case "Delete Chatroom" : infoPane.startDialod("Delete Chatraum", "Chatraum kann nur vom Ersteller gelöscht werden");
                logger.warning(messageType+": Chatraum kann nur vom Ersteller gelöscht werden\n");

                break;
            case "Chatraum" : infoPane.startDialod(" Chatraum", "Chatraum-Liste konnte nicht aktualisiert werden!");
                logger.warning(messageType+": Chatraum-Liste konnte nicht aktualisiert werden!!");

                break;
            case "LeaveError" : infoPane.startDialod(" Chatraum", "Chatraum konnte nicht verlassen werden!");
                logger.warning(messageType+": Chatraum konnte nicht verlassen werden!");
                break;

        }
    }
    private void logout(){
        lobbyView.getStage().setOnCloseRequest(e->{
            String [] data = {"Logout"};
            Logout logout = new Logout(data);
            sendMessage(logout, data);
        });
        lobbyView.getLogoutBtn().setOnAction(e->{
            String [] data = {"Logout"};
            Logout logout = new Logout(data);
            sendMessage(logout, data);
        });
    }
    private void resultLogout(String[] msgContent){
        if (msgContent[2].equals("true")){
            lobbyView.getStage().close();
            System.exit(0);
            closeSocket();
            Logger.getLogger("Client: Socket geschlossen");
        }
    }

    private void testConnection() {
        sign.getTestConnection().setOnAction(e->{

            socket = null;
            TestConnectionWindow tst = new TestConnectionWindow();
            tst.startDialog();

            tst.getTest().setOnAction(test->{
                 String ipA = tst.getIpInputField().getText();
                String portN = tst.getPortInputField().getText();
                int portNumber = Integer.parseInt(portN);
                if (proofIP(ipA) && proofPort(portN)){
                   ClientHandler.IP = ipA;
                   ClientHandler.PORT = portNumber;
                       startClient();
                       String [] data = {"Ping"};
                       Ping ping = new Ping(data);
                       sendMessage(ping,data);

               }
                ipA = "";
                portN= "";
                portNumber = 0;

            });
            tst.getSave().setOnAction(saveConfig->{
                socket = null;
                String ip = tst.getIpInputField().getText();
                String port = tst.getPortInputField().getText();
                int portNumber = Integer.parseInt(port);
                ClientHandler.IP = ip;
                ClientHandler.PORT = portNumber;
                tst.closeWindow();
            });
        });
    }

    private boolean proofPort(String portField) {
        try {
            int i = Integer.parseInt(portField);
            if (i>1024&&i<65535){
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            infoPane.startDialod("Portnummer", "Bitte auf Syntax achten!");
            logger.warning("Portnummer: Bitte auf Syntax achten!");
            return false;
        }
        return false;
    }
    public boolean proofIP(String ip){
        int counter = 0;
        String [] ipContent = ip.split("\\.");
        if (ipContent.length==4){
            int [] container = new int[4];
            for (String s: ipContent){
                try {
                    int i = Integer.parseInt(s);
                    if (i<256){ container[counter] = i;counter++; }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    infoPane.startDialod("IP-Adresse", "Bitte auf Syntax achten!");
                  logger.warning("IP-Adresse: Bitte auf Syntax achten!");
                    return false;
                }
                if (counter==4){
                    return true;
                }
            }
        }
        return false;
    }
    public void closeSocket(){
        try {
            receiveLoop = false;
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}