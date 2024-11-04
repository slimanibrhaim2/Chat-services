import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

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
                String log = "user: " + iClientChat.getUserName() + " tried to create an exists room";
                logs.add(log);
                return "The room name is not available, try another name";
            }
        }
        //creating a room and add it to the database;
        Room room = new Room(roomName, iClientChat.getUserName(), password);
        roomsDataBase.add(room);
        String log = "user: " + iClientChat.getUserName() + " created a room : " + roomName;
        logs.add(log);
        return "Done, room added successfully";
    }

    @Override
    public String deleteRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {

        String log;
        for (Room room : roomsDataBase) {

            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                // Check if the user is the owner
                if (!room.getOwner().equals(iClientChat.getUserName())) {
                    log = "user:" + iClientChat.getUserName() + "tried to delete room doesn't belong to him";
                    logs.add(log);
                    return "This room is not yours, only the owner and the admin can delete it.";
                } else {
                    // Check if the password is correct
                    if (!room.getPassword().equals(password)) {
                        log = "user:" + iClientChat.getUserName() + "tried to delete room doesn't with wrong password";
                        logs.add(log);
                        return "Error!, Wrong password.";
                    } else {
                        roomsDataBase.remove(room);
                        log = "user: " + iClientChat.getUserName() + " has deleted his own room : " + roomName + " successfully";
                        logs.add(log);
                        return "The room deleted successfully.";
                    }
                }
            }
        }
        log = "user: " + iClientChat.getUserName() + " tried to delete a room doesn't exists";
        logs.add(log);
        return "The room doesn't exists";
    }

    @Override
    public String signUp(IClientChat clientChat,String username, String password, String firstName, String lastName, String roomName) throws RemoteException {
        String log;
        for (Room room : roomsDataBase) {
            //check if the room exists
            if (room.getRoomName().equals(roomName)) {
                //check if the user exists
                if(room.userExists(username)){
                    log= "user: "+ username +"tried to sign up to room he exists in it.";
                    return "You already in this room";
                }
//                User newUser = new User(firstName, lastName, username, password);
//                room.addRoomUser(newUser);
                room.addRoomUser(clientChat);
                log= "user: "+ username +" signed up to room "+ roomName;
                logs.add(log);
                return "User added successfully. \nThe password of this room chat is :" + room.getPassword() + " you will need it to sign in later";
            }
        }
        log= "user: "+ username +"tried to sign up to room doesn't exists";
        logs.add(log);
        return "The room doesn't exists";
    }


    @Override
    public String signIn(IClientChat iClientChat, String password, String roomName) throws RemoteException {
        String log;
        for (Room room : roomsDataBase) {
            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                // Check if the user belongs to this room
                if (room.userExists(iClientChat.getUserName()).equals(false)) {
                    log = "user: " + iClientChat.getUserName() + " tried to sign in to a room they do not belong to.";
                    logs.add(log);
                    return "This user doesn't belong to this room.";
                }
                // Check if the room password is correct
                else if (!room.getPassword().equals(password)) {
                    log = "user: " + iClientChat.getUserName() + " attempted sign in with the wrong password to room " + roomName;
                    logs.add(log);
                    return "Wrong password.";
                }
                // User exists and password is correct
                else {
                    // Check if user is already signed in
                    if (room.clientOnline(iClientChat)) {
                        log = "user: " + iClientChat.getUserName() + " attempted to sign in, but was already online in room " + roomName;
                        logs.add(log);
                        return "You are already signed in.";
                    } else {
                        room.addOnlineClient(iClientChat);
                        log = "user: " + iClientChat.getUserName() + " successfully signed in to room " + roomName;
                        logs.add(log);
                        room.addOnlineClient(iClientChat);
                        return "Welcome back to this chat.";
                    }
                }
            }
        }
        log = "user: " + iClientChat.getUserName() + " tried to sign in to a room that doesn't exist.";
        logs.add(log);
        return "The room doesn't exist.";
    }

    @Override
    public String signOut(IClientChat iClientChat, String roomName) throws RemoteException {
        String log;
        for (Room room : roomsDataBase) {
            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                // Check if the user belongs to this chat
                if (!room.userExists(iClientChat.getUserName())) {
                    log = "user: " + iClientChat.getUserName() + " tried to sign out from a room they do not belong to.";
                    logs.add(log);
                    return "This user doesn't belong to this room.";
                }
                // Check if the user is currently online in the room
                else if (!room.clientOnline(iClientChat)) {
                    log = "user: " + iClientChat.getUserName() + " tried to sign out from room " + roomName + " but was not signed in.";
                    logs.add(log);
                    return "You are not signed in.";
                }
                // If user is online, proceed to sign out
                else {
                    room.removeOnlineClient(iClientChat);
                    log = "user: " + iClientChat.getUserName() + " successfully signed out from room " + roomName;
                    logs.add(log);
                    return "You have successfully signed out.";
                }
            }
        }
        log = "user: " + iClientChat.getUserName() + " attempted to sign out from a room that doesn't exist.";
        logs.add(log);
        return "The room doesn't exist.";
    }

    @Override
    public List<String> showRooms() throws RemoteException {
        List<String> result = new ArrayList<>();
        for (Room room : roomsDataBase) {
            result.add(room.getRoomName());
        }
        String log = "Some user show the rooms";
        logs.add(log);
        return result;
    }

    @Override
    public List<String> showClients(String roomName) throws RemoteException {
        List<String> result = new ArrayList<>();
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                result = room.getRoomUsers();
            }
        }
        String  log = "some user show the client in room "+ roomName;
        logs.add(log);
        return result;
    }

    @Override
    public void uniCastMessage(String message, IClientChat senderIClientChat, IClientChat receiverIClientChat) throws RemoteException {
        String messageToSend = senderIClientChat.getUserName() + " : " + message;
        receiverIClientChat.receiveMessage(messageToSend);
    }

    @Override
    public void multiCastMessage(String message, IClientChat iClientChat, List<IClientChat> iClientChats) throws RemoteException {
        String messageToSend = iClientChat.getUserName() + " : " + message;
        for (IClientChat receiver : iClientChats) {
            uniCastMessage(messageToSend, iClientChat, receiver);
        }
    }

    @Override
    public void broadCastMessage(String message, String roomName, IClientChat iClientChat) throws RemoteException {
        String messageToSend = iClientChat.getUserName() + " : " + message;
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                multiCastMessage(message,iClientChat,room.getAllUsers());
            }
        }
    }

    public void printLogs() {
        for (String log : logs) {
            System.out.println(log);
        }
    }

}
