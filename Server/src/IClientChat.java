import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientChat extends Remote {
    public void receiveMessage(String message) throws RemoteException;
    public String getUserName() throws  RemoteException;
}
