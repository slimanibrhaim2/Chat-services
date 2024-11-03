import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerChatImpl extends UnicastRemoteObject implements IServerChat {

    private List<Room> roomsDataBase = new ArrayList<>();
    private List<User> usersDataBase = new ArrayList<>();
    private List<String> logs = new ArrayList<>();

    protected ServerChatImpl() throws RemoteException {
    }

    @Override
    public String addRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
        // check if the room exists in database
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                String log ="user: " + iClientChat.getUserName()+" tried to create an exists room";
                logs.add(log);
                return "The room name is not available, try another name";
            }
        }
        //creating a room and add it to the database;
        Room room = new Room(roomName, iClientChat.getUserName(), password);
        roomsDataBase.add(room);
        String log ="user: " + iClientChat.getUserName()+" created a room : "+ roomName;
        logs.add(log);
        return "Done, room added successfully" ;
    }

    @Override
    public String deleteRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {

        String log;
        for (Room room : roomsDataBase) {

            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                // Check if the user is the owner
                if (!room.getOwner().equals(iClientChat.getUserName())) {
                    log= "user:" + iClientChat.getUserName()+"tried to delete room doesn't belong to him";
                    logs.add(log);
                    return "This room is not yours, only the owner and the admin can delete it.";
                }
                else {
                    // Check if the password is correct
                    if (!room.getPassword().equals(password)) {
                        log ="user:" + iClientChat.getUserName()+"tried to delete room doesn't with wrong password";
                        logs.add(log);
                        return "Error!, Wrong password.";
                    } else {
                        roomsDataBase.remove(room);
                        log ="user: " + iClientChat.getUserName()+" has deleted his own room : "+roomName + " successfully";
                        logs.add(log);
                        return "The room deleted successfully.";
                    }
                }
            }
        }
        log ="user:" + iClientChat.getUserName()+" tried to delete a room doesn't exists";
        logs.add(log);
        return "The room doesn't exists";
    }

    @Override
    public Boolean signUp(String username, String password, String firstName, String lastName, String roomName) throws RemoteException {
        for (Room room : roomsDataBase) {
            //check if the room exists
            if (room.getRoomName().equals(roomName)) {
                //check if the user exists
                if (!room.userExists(username)) {
                    User newUser = new User(firstName, lastName, username, password);

                    System.out.println("User added successfully");
                    room.addRoomUser(newUser);
                }
                System.out.println("\nThe password of this room chat is :" + room.getPassword() + " you will need it to sign in later");
                return true;
            }
        }
        System.out.println("The room doesn't exists");
        return false;
    }

    @Override
    public Boolean signIn(IClientChat iClientChat, String password, String roomName) throws RemoteException {

        //check if the room exists
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                //check if the user belong to this chat
                if (!room.userExists(iClientChat.getUserName())) {
                    System.out.println("\nThis user doesn't belong to this room.");
                    return false;
                }
                //check if the password of the room is correct
                else if (!room.getPassword().equals(password)) {
                    System.out.println("\nWrong password.");
                }
                //the user exists
                else {
                    //check if he online
                    if (room.userOnline(iClientChat.getUserName())) {
                        System.out.println("\nYou are already signed in.");
                    } else {
                        room.addOnlineUser(iClientChat.getUserName());
                        System.out.println("\nwelcome back to this chat.");
                    }
                    return true;
                }
            }
        }
        System.out.println("\nThis room doesn't exists.");
        return false;
    }

    @Override
    public Boolean signOut(IClientChat iClientChat, String roomName) throws RemoteException {
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
    public void showRooms() throws RemoteException {
        if (roomsDataBase.isEmpty()) {
            System.out.println("There is no room available, you can create one.");
            return;
        }
        System.out.println("This is the available room:\n");
        for (Room room : roomsDataBase) {
            System.out.println(room.getRoomName());
        }
    }

    @Override
    public void showClients(String roomName) throws RemoteException {
        //check if the room exists
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                room.printRoomUsers();
            }
        }
    }

    @Override
    public void uniCastMessage(String message, IClientChat senderIClientChat, IClientChat receiverIClientChat) throws RemoteException {
        String messageToSend = senderIClientChat.getUserName() + " : " + message;
        receiverIClientChat.receiveMessage(messageToSend);
    }

    @Override
    public void multiCastMessage(String message, IClientChat iClientChat, List<IClientChat> iClientChats) throws RemoteException {
        String messageToSend = iClientChat.getUserName() + " : " + message;
        for (IClientChat receiver: iClientChats){
            uniCastMessage(messageToSend, iClientChat,receiver);
        }
    }

    @Override
    public void broadCastMessage(String message, String roomName, IClientChat iClientChat) throws RemoteException {
        String messageToSend = iClientChat.getUserName() + " : " + message;
        for (Room room: roomsDataBase){
            if(room.getRoomName().equals(roomName)){
                System.out.println("sdfdfsdf");
            }
        }
    }

    public void printLogs(){
        for (String log: logs){
            System.out.println(log);
        }
    }

}
