import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerChat  extends Remote {
    public Boolean addRoom(String roomName,String password, IClientChat iClientChat) throws RemoteException;
    public Boolean deleteRoom(String roomName,String password ,IClientChat iClientChat) throws RemoteException;
    public Boolean signUp(String username, String password, String firstName, String lastName, String roomName) throws RemoteException;
    public Boolean signIn (IClientChat iClientChat, String password, String roomName) throws RemoteException;
    public Boolean signOut(IClientChat iClientChat, String roomName)throws RemoteException;
    public List<Room> showRooms(IClientChat iClientChat)throws  RemoteException;
    public List<IClientChat>showClients(String roomName, IClientChat iClientChat)throws RemoteException;
    public void sendSingleMessage (String message, IClientChat iClientChat) throws RemoteException;
    public void broadCastMessage(String message,IClientChat iClientChat) throws RemoteException;





}
