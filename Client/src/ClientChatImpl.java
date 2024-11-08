import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientChatImpl extends UnicastRemoteObject implements IClientChat {


    private String firstName, lastName, userName, password;
    private IServerChat iServerChat;

    protected ClientChatImpl() throws RemoteException {
    }

    public ClientChatImpl(String firstName, String lastName, String userName, String password, String server) throws RemoteException, MalformedURLException, NotBoundException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.iServerChat = (IServerChat) Naming.lookup("rmi://" + server + ":9999/ChatSvc");
    }

    public String addRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
//        if (iServerChat.addRoom(roomName, password, iClientChat).equals("success")) {
//            System.out.println("Done, room added successfully");
//            return ;
//        }
//        System.out.println("The room name is not available, try another name");
//        return ;
        String result = iServerChat.addRoom(roomName, password, iClientChat);
        System.out.println(result);
        return result;
    }

    public void deleteRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
        System.out.println(iServerChat.deleteRoom(roomName, password, iClientChat));
    }

    public void showRooms() throws RemoteException {
        List<String> result = iServerChat.showRooms();
        if (result.isEmpty()) {
            System.out.println("There is no room available, you can create one.");
            return;
        }
        System.out.println("This is the available room:");
        for (String room : result) {
            System.out.println(room);
        }
    }

    public void showClients(String roomName) throws RemoteException {
        List<String> users = new ArrayList<>();
        users = iServerChat.showClients(roomName);
        if (users.isEmpty()) {
            System.out.println("This room hava no user it contains only the owner");
            return;
        }
        System.out.println("This is the users in the room " + roomName);
        for (String user : users) {
            System.out.println(user);
        }
    }

    public void register(String firstName, String lastName, String userName, String password, IClientChat iClientChat) throws RemoteException {
        iServerChat.register(firstName,lastName,userName,password,iClientChat);
    }

    public Boolean logIn(String userName, String password) throws RemoteException {

        String result= iServerChat.logIn(userName,password);
        System.out.println(result);
        return result.equals("Login successful. Welcome back");
    }

    public void signUp(IClientChat clientChat, String username, String password, String firstName, String lastName, String roomName) throws RemoteException {
        String status = iServerChat.signUp(clientChat, username, password, firstName, lastName, roomName);
        System.out.println(status);
    }

    public void signIn(IClientChat iClientChat, String password, String roomName) throws RemoteException {
        String status = iServerChat.signIn(iClientChat, password, roomName);
        System.out.println(status);
    }

    public void signOut(IClientChat iClientChat, String roomName) throws RemoteException {
        String status = iServerChat.signOut(iClientChat, roomName);
        System.out.println(status);
    }

    public void uniCastMessage(String message, IClientChat senderIClientChat, String userName) throws RemoteException {
        iServerChat.uniCastMessage(message, senderIClientChat, userName);
    }

    public void broadCastMessage(String message, String roomName, IClientChat iClientChat) throws RemoteException {
        iServerChat.broadCastMessage(message,roomName,iClientChat);
    }

    @Override
    public void receiveMessage(String message, String username) throws RemoteException {
        System.out.println(message);
    }


    public IClientChat findUserByUsername(String username) throws RemoteException {
        return iServerChat.findUserByUsername(username);
    }
    @Override
    public boolean checkPassword(String pass) {
        return this.password.equals(pass);
    }

    @Override
    public String getUserName() throws RemoteException {
        return this.userName;
    }
    @Override
    public String getPassword() throws RemoteException {
        return this.password;
    }
    @Override
    public String getFirstName() throws RemoteException {
        return this.firstName;
    }

    @Override
    public String getLastName() throws RemoteException {
        return this.lastName;
    }

}
