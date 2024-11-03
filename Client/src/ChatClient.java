import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ChatClient {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String firstName = "sliman";
        String lastName = "ibrahim";
        String userName = "sliman";
        String password = "1234";
        String server = "localhost";


//        IServerChat serverChat = (IServerChat) Naming.lookup("rmi://localhost:9999/ChatSvc");
        ClientChatImpl clientChat = new ClientChatImpl( firstName,lastName,userName,password,server);
        ClientChatImpl clientChat1 = new ClientChatImpl( "kheder",lastName,userName,password,server);
        ClientChatImpl clientChat2 = new ClientChatImpl( "ali",lastName,userName,password,server);



        clientChat.addRoom("room1", "1234", clientChat);
        clientChat.addRoom("room11", "1234", clientChat);

//        clientChat.deleteRoom("room1","1234",clientChat);
//        clientChat.addRoom("room1", "1234", clientChat);

//        clientChat.deleteRoom("room4","1234",clientChat);

        clientChat.showRooms();
        clientChat.showClients("room133");
        clientChat1.signUp("ahmad","1243","ahmad","ali","room1");
        clientChat.showRooms();
        clientChat.showClients("room1");

//         serverChat.signUp(userName,password,firstName,lastName,"room1");


    }




}
