import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerChat extends Remote {
    public String  addRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException;

    public String deleteRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException;

    public String register (String firstName, String lastName, String userName, String password,IClientChat iClientChat)throws RemoteException;

    public String logIn (String userName, String password)throws RemoteException;

    public String signUp(IClientChat clientChat,String username, String password, String firstName, String lastName, String roomName) throws RemoteException;

    public String signIn(IClientChat iClientChat, String password, String roomName) throws RemoteException;

    public String signOut(IClientChat iClientChat, String roomName) throws RemoteException;

    public List<String> showRooms() throws RemoteException;

    public List<String> showClients(String roomName) throws RemoteException;

    public void uniCastMessage(String message, IClientChat senderIClientChat, String username) throws RemoteException;

    public void multiCastMessage(String message, IClientChat iClientChat, List<String> users) throws RemoteException;

    public void broadCastMessage(String message, String roomName,IClientChat iClientChat) throws RemoteException;

    public IClientChat findUserByUsername(String userName);
}
