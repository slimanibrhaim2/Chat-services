import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientChatImpl extends UnicastRemoteObject implements IClientChat {


    private String firstName, lastName, userName,password;
    private IServerChat iServerChat;

    protected ClientChatImpl() throws RemoteException {
    }

    public ClientChatImpl(String firstName , String lastName , String userName , String password , String server) throws RemoteException, MalformedURLException, NotBoundException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.iServerChat = (IServerChat) Naming.lookup("rmi://"+server+":9999/ChatSvc");
    }

    public void addRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
//        if (iServerChat.addRoom(roomName, password, iClientChat).equals("success")) {
//            System.out.println("Done, room added successfully");
//            return ;
//        }
//        System.out.println("The room name is not available, try another name");
//        return ;
        System.out.println(iServerChat.addRoom(roomName, password, iClientChat));
    }

    public void deleteRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
        System.out.println(iServerChat.deleteRoom(roomName,password,iClientChat));
    }

    public void showRooms() throws RemoteException {
        List<String>result = iServerChat.showRooms();
        if(result.isEmpty()){
            System.out.println("There is no room available, you can create one.");
            return;
        }
        System.out.println("This is the available room:");
        for(String room: result){
            System.out.println(room);
        }
    }

    public void showClients(String roomName) throws RemoteException {
        List<String>users= new ArrayList<>();
        users= iServerChat.showClients(roomName);
        if(users.isEmpty()){
            System.out.println("This room hava no user it contains only the owner");
            return;
        }
        System.out.println("This is the users in the room "+roomName);
        for(String user: users){
            System.out.println(user);
        }
    }
    public void signUp(String username, String password, String firstName, String lastName, String roomName) throws RemoteException {
        String status= iServerChat.signUp(username, password, firstName, lastName, roomName);
        System.out.println(status);
    }

    public void signIn(IClientChat iClientChat, String password, String roomName) throws RemoteException {
        String status= iServerChat.signIn(iClientChat,password,roomName);
        System.out.println(status);
    }

    public void signOut(IClientChat iClientChat, String roomName) throws RemoteException{
        String status= iServerChat.signOut(iClientChat,roomName);
        System.out.println(status);
    }

    public void uniCastMessage(String message, IClientChat senderIClientChat, IClientChat receiverIClientChat) throws RemoteException {
        iServerChat.uniCastMessage(message,senderIClientChat,receiverIClientChat);
    }



        @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println("received " + message);
    }

    @Override
    public String getUserName() throws RemoteException {
        return this.userName;
    }


}
