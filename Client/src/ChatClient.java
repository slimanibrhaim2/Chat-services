import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ChatClient {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
//        Register r = new Register(null);
//        LogIn l = new LogIn(null);
//        AddRoom a =  new AddRoom(null);
//        DeleteRoom d  = new DeleteRoom(null);

        String firstName = "sliman";
        String lastName = "ibrahim";
        String userName = "sliman";
        String password = "1234";
        String server = "localhost";


        ClientChatImpl clientChat = new ClientChatImpl(firstName, lastName, userName, password, server);
//        clientChat.register(firstName, lastName, userName, password, clientChat);









        //        IServerChat serverChat = (IServerChat) Naming.lookup("rmi://localhost:9999/ChatSvc");

//        ClientChatImpl clientChat1 = new ClientChatImpl( "alie",lastName,"kheder",password,server);
//        ClientChatImpl clientChat2 = new ClientChatImpl( "ali",lastName,"ali",password,server);


        clientChat.addRoom("room1", "1234", clientChat);
//        clientChat.signUp(clientChat,userName,"1234",firstName,lastName,"room1");

//        clientChat.addRoom("room11", "1234", clientChat);
//        clientChat.deleteRoom("room1","1234",clientChat);
//        clientChat.addRoom("room1", "1234", clientChat);
//        clientChat.deleteRoom("room4","1234",clientChat);
//        clientChat.showRooms();
//        clientChat.showClients("room133");
//        clientChat1.signUp(clientChat1,"kheder","1234","ahmad","ali","room1");
//        clientChat.showRooms();
//        clientChat.showClients("room1");
//        clientChat1.signIn(clientChat1,"1234","room1");
//        clientChat.signIn(clientChat,"1234", "room1");
//        clientChat2.signOut(clientChat1,"room2");
//        clientChat2.signOut(clientChat1,"room2");
//        clientChat1.signOut(clientChat1,"room1");
////         serverChat.signUp(userName,password,firstName,lastName,"room1");
//            clientChat.uniCastMessage("helllo",clientChat,"ali");
//            clientChat.broadCastMessage("broadcast11111","room1",clientChat);
    }

}
