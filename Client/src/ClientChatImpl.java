import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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

    public Boolean signUp(String username, String password, String firstName, String lastName, String roomName) throws RemoteException {
        return iServerChat.signUp(username, password, firstName, lastName, roomName);
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
