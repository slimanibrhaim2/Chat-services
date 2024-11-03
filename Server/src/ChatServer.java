import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {
    public static void main(String[] args) throws RemoteException, InterruptedException {
        System.setProperty("java.security.policy" , "../RmiPolicy.policy");
        Registry registry = LocateRegistry.createRegistry(9999);
        ServerChatImpl serverChatImp = new ServerChatImpl();
        registry.rebind("ChatSvc" , serverChatImp);
        System.out.println("Chat Server is running...");
        while (true){
            Thread.sleep(10000);
            System.out.println("\n\n\n\n\n\n\n\n");
            serverChatImp.printLogs();
        }


    }
}
