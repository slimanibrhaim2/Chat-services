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

        clientChat.addRoom("room1", "1234", clientChat);
        clientChat.addRoom("room1", "1234", clientChat);

        clientChat.deleteRoom("room1","123334",clientChat);
        clientChat.deleteRoom("room2","1234",clientChat);


//         serverChat.signUp(userName,password,firstName,lastName,"room1");


    }




}
