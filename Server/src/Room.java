import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Room {

    private String roomName;
    private String owner;
    private String password;
//    private List<User> roomUsers = new ArrayList<>();
    private List<IClientChat>allUsers= new ArrayList<>();
    //    private List<String> onlineUsers= new ArrayList<>();
    private List<IClientChat> onlineClinets = new ArrayList<>();


    public Room(String roomName, String owner, String password) {
        this.roomName = roomName;
        this.owner = owner;
        this.password = password;
    }

//    public void addRoomUser(User user) {
//        roomUsers.add(user);
//    }
public void addRoomUser(IClientChat user) {
    allUsers.add(user);
}

    public void addOnlineClient(IClientChat client) {
        onlineClinets.add(client);
    }

//    public Boolean userExists(String newUser) {
//        for (User user : roomUsers) {
//            //check if the user exists;
//            if (user.getUserName().equals(newUser)) {
//                return true;
//            }
//        }
//        return false;
//    }
public Boolean userExists(String newUser) throws RemoteException {
    for (IClientChat user : allUsers) {
        //check if the user exists;
        if (user.getUserName().equals(newUser)) {
            return true;
        }
    }
    return false;
}

    public Boolean clientOnline(IClientChat clientChat) throws RemoteException {
        for (IClientChat client : onlineClinets) {
            //check if the user exists;
            if (client.getUserName().equals(clientChat.getUserName())) {
                return true;
            }
        }
        return false;
    }

    public Boolean removeOnlineClient(IClientChat clientChat) throws RemoteException {
        for (IClientChat client : onlineClinets) {
            //check if the user exists;
            if (client.getUserName().equals(clientChat.getUserName())) {
                onlineClinets.remove(client);
                return true;
            }
        }
        return false;
    }

    public void printRoom() {
        System.out.println("Room: " + this.roomName + " Owned by the :" + this.owner + ".");
    }

//    public List<String> getRoomUsers() {
//        List<String> users = new ArrayList<>();
//        for (User user : roomUsers) {
//            users.add(user.getUserName());
//        }
//        return users;
//    }
    public List<String> getRoomUsers() throws RemoteException {
        List<String> users = new ArrayList<>();
        for (IClientChat user : allUsers) {
            users.add(user.getUserName());
        }
        return users;
    }


    public String getRoomName() {
        return roomName;
    }

    public String getOwner() {
        return owner;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getAllUsers() throws RemoteException {
        List<String> resault= new ArrayList<>();
        for(IClientChat user: allUsers){
            resault.add(user.getUserName());
        }
        return resault;
    }
}

