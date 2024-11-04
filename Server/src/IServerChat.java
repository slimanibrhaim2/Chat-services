import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerChat extends Remote {
    public String  addRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException;

    public String deleteRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException;

    public String signUp(String username, String password, String firstName, String lastName, String roomName) throws RemoteException;

    public String signIn(IClientChat iClientChat, String password, String roomName) throws RemoteException;

    public String signOut(IClientChat iClientChat, String roomName) throws RemoteException;

    public List<String> showRooms() throws RemoteException;

    public List<String> showClients(String roomName) throws RemoteException;

    public void uniCastMessage(String message, IClientChat senderIClientChat, IClientChat receiverIClientChat) throws RemoteException;

    public void multiCastMessage(String message, IClientChat iClientChat, List<IClientChat> iClientChats) throws RemoteException;

    public void broadCastMessage(String message, String roomName,IClientChat iClientChat) throws RemoteException;


}
