import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Room {

    private String roomName;
    private String owner;
    private String password;
    private List<User> roomUsers = new ArrayList<>();
    private List<String> onlineUsers= new ArrayList<>();


    public Room(String roomName, String owner, String password) {
        this.roomName = roomName;
        this.owner = owner;
        this.password = password;
    }

    public void addRoomUser(User user){
        roomUsers.add(user);
    }
    public void addOnlineUser(String user){
        onlineUsers.add(user);
    }
    public Boolean userExists(String newUser){
        for (User user: roomUsers){
            //check if the user exists;
            if (user.getUserName().equals(newUser)){
                 return true;
            }
        }
        return false;
    }

    public Boolean userOnline(String name){
        for (String user: onlineUsers){
            //check if the user exists;
            if (user.equals(name)){
                return true;
            }
        }
        return false;
    }

    public Boolean removeOnlineUser(String name){
        for (String user: onlineUsers){
            //check if the user exists;
            if (user.equals(name)){
                onlineUsers.remove(name);
                return true;
            }
        }
        return false;
    }

    public void printRoom(){
        System.out.println("Room: "+ this.roomName+" Owned by the :"+this.owner + ".");
    }

    public void printRoomUsers(){
        if (roomUsers.isEmpty()){
            System.out.println("There is no user in this room");
            return;
        }
        for(User user: roomUsers){
            System.out.println(user.getUserName());
        }
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
}

