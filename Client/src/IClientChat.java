import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientChat extends Remote {
    public void receiveMessage(String message, String username) throws RemoteException;
    public String getUserName() throws  RemoteException;
    public boolean checkPassword(String password)throws RemoteException;
}
