import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerChatImpl extends UnicastRemoteObject implements IServerChat {

    private List<Room> roomsDataBase = new ArrayList<>();
    private List<User> usersDataBase = new ArrayList<>();

    protected ServerChatImpl() throws RemoteException {
    }

    @Override
    public Boolean addRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
        // check if the room exists in database
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                System.out.println("\nThe room name is not available, try another name\n");
                return false;
            }
        }
        //creating a room and add it to the database;
        Room room = new Room(roomName, iClientChat.getUserName(), password);
        roomsDataBase.add(room);
        return true;
    }

    @Override
    public Boolean deleteRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
        for (Room room : roomsDataBase) {

            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                System.out.println("\nThe room is exists, deleting the room...\n");

                // Check if the user is the owner
                if (!room.getOwner().equals(iClientChat.getUserName())) {
                    System.out.println("\nThis room is not yours only the owner and the admin can delete it.\n");
                    return false;
                }
                else {
                    // Check if the password is correct
                    if (!room.getPassword().equals(password)) {
                        System.out.println("\nWrong Password.\n");
                        return false;
                    }
                    else {
                        roomsDataBase.remove(room);
                        System.out.println("\nThe room " + roomName + "has deleted successfully.\n");
                        return true;
                    }
                }
            }
        }
        System.out.println("\nThe room name is not exists.\n");
        return false;
    }

    @Override
    public Boolean signUp(String username, String password, String firstName, String lastName, String roomName) throws RemoteException {
        for (Room room : roomsDataBase){
            //check if the room exists
            if (room.getRoomName().equals(roomName)){
                //check if the user exists
                if(!room.userExists(username)){
                    User newUser = new User(firstName, lastName,username,password);
                    room.addRoomUser(newUser);
                }
                System.out.println("\nThe password of this room chat is :"+room.getPassword()+" you will need it to sign in later");
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean signIn(IClientChat iClientChat, String password,String roomName) throws RemoteException {

        //check if the room exists
        for (Room room : roomsDataBase){
            if (room.getRoomName().equals(roomName)){
                //check if the user belong to this chat
                if(!room.userExists(iClientChat.getUserName())){
                    System.out.println("\nThis user doesn't belong to this room.");
                    return false;
                }
                //check if the password of the room is correct
                else if (!room.getPassword().equals(password)){
                    System.out.println("\nWrong password.");
                }
                //the user exists
                else {
                    //check if he online
                    if(room.userOnline(iClientChat.getUserName())){
                        System.out.println("\nYou are already signed in.");
                    }
                    else {
                        room.addOnlineUser(iClientChat.getUserName());
                        System.out.println("\nwelcome back to this chat.");
                    }
                    return true;
                }
            }
        }
        System.out.println("\nThis room doesn't exists.");
        return  false;
    }

    @Override
    public Boolean signOut(IClientChat iClientChat,String roomName) throws RemoteException {
        // Check if the room exists
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                // Check if the user belongs to this chat
                if (!room.userExists(iClientChat.getUserName())) {
                    System.out.println("\nThis user doesn't belong to this room.");
                    return false;
                }
                // Check if the user is currently online in the room
                else if (!room.userOnline(iClientChat.getUserName())) {
                    System.out.println("\nYou are not signed in.");
                    return false;
                }
                // If user is online, proceed to sign out
                else {
                    room.removeOnlineUser(iClientChat.getUserName());
                    System.out.println("\nYou have successfully signed out.");
                    return true;
                }
            }
        }
        System.out.println("\nThis room doesn't exist.");
        return false;
    }

    @Override
    public List<Room> showRooms(IClientChat iClientChat) throws RemoteException {
        return roomsDataBase;
    }

    @Override
    public List<IClientChat> showClients(String roomName, IClientChat iClientChat) throws RemoteException {
        return List.of();
    }

    @Override
    public void sendSingleMessage(String message, IClientChat iClientChat) throws RemoteException {

    }

    @Override
    public void broadCastMessage(String message, IClientChat iClientChat) throws RemoteException {

    }
}
