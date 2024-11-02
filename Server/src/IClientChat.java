import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientChat extends Remote {
    public String receiveMessage() throws RemoteException;
    public String getUserName() throws  RemoteException;
//    public Boolean checkPassword() throws RemoteException;
}