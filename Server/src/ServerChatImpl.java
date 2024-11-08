import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerChatImpl extends UnicastRemoteObject implements IServerChat {

    private List<Room> roomsDataBase = new ArrayList<>();
    private List<IClientChat> userDatabase = new ArrayList<>();
    private List<String> logs = new ArrayList<>();

    protected ServerChatImpl() throws RemoteException {
    }

    @Override
    public String addRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
        // Check if the room already exists in the database
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                String log = "User: " + iClientChat.getUserName() + " attempted to create a room that already exists.";
                logs.add(log);
                return "Room name is unavailable. Please choose a different name.";
            }
        }
        // Create a new room and add it to the database
        Room room = new Room(roomName, iClientChat.getUserName(), password);
        roomsDataBase.add(room);
        String log = "User: " + iClientChat.getUserName() + " successfully created the room: " + roomName;
        logs.add(log);
        return "Room created successfully.";
    }

    @Override
    public String deleteRoom(String roomName, String password, IClientChat iClientChat) throws RemoteException {
        String log;
        for (Room room : roomsDataBase) {
            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                // Verify if the user is the owner
                if (!room.getOwner().equals(iClientChat.getUserName())) {
                    log = "User: " + iClientChat.getUserName() + " attempted to delete a room they do not own.";
                    logs.add(log);
                    return "You do not have permission to delete this room.";
                } else {
                    // Verify the password
                    if (!room.getPassword().equals(password)) {
                        log = "User: " + iClientChat.getUserName() + " attempted to delete a room with an incorrect password.";
                        logs.add(log);
                        return "Incorrect password.";
                    } else {
                        roomsDataBase.remove(room);
                        log = "User: " + iClientChat.getUserName() + " successfully deleted the room: " + roomName;
                        logs.add(log);
                        return "Room deleted successfully.";
                    }
                }
            }
        }
        log = "User: " + iClientChat.getUserName() + " attempted to delete a non-existent room.";
        logs.add(log);
        return "Room does not exist.";
    }

    @Override
    public String register(String firstName, String lastName, String userName, String password, IClientChat iClientChat) throws RemoteException {
        // Check if the username is already taken
        for (IClientChat user : userDatabase) {
            if (user.getUserName().equals(userName)) {
                String log = "User: " + userName + " attempted to register with an existing username.";
                logs.add(log);
                return "Username is already taken. Please choose another.";
            }
        }

        // Add the new user to the user database
        userDatabase.add(iClientChat);

        // Log the registration event
        String log = "User: " + userName + " registered successfully.";
        logs.add(log);

        // Return a success message
        return "Registration successful. Welcome, " + firstName + "!";
    }

    @Override
    public String logIn(String userName, String password) throws RemoteException {
        // Check if the user exists
        for (IClientChat user : userDatabase) {
            if (user.getUserName().equals(userName)) {
                // Verify the password
                if (user.checkPassword(password)) {
                    String log = "User: " + userName + " logged in successfully.";
                    logs.add(log);
                    return "Login successful. Welcome back";
                } else {
                    String log = "User: " + userName + " failed to log in due to incorrect password.";
                    logs.add(log);
                    return "Incorrect password. Please try again.";
                }
            }
        }

        // If user is not found
        String log = "User: " + userName + " attempted to log in but does not exist.";
        logs.add(log);
        return "User not found. Please check your username.";
    }

    @Override
    public String signUp(IClientChat clientChat, String username, String password, String firstName, String lastName, String roomName) throws RemoteException {
        String log;
        for (Room room : roomsDataBase) {
            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                // Check if the user is already in the room
                if (room.userExists(username)) {
                    log = "User: " + username + " attempted to sign up to a room they are already in.";
                    logs.add(log);
                    return "You are already a member of this room.";
                }
                // Add the user to the room
                room.addRoomUser(clientChat);
                userDatabase.add(clientChat);
                log = "User: " + username + " signed up to room " + roomName;
                logs.add(log);
                return "User added successfully. Room password: " + room.getPassword() + " (needed for future sign-ins).";
            }
        }
        log = "User: " + username + " attempted to sign up to a non-existent room.";
        logs.add(log);
        return "Room does not exist.";
    }

    @Override
    public String signIn(IClientChat iClientChat, String password, String roomName) throws RemoteException {
        String log;
        for (Room room : roomsDataBase) {
            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                // Verify if the user belongs to this room
                if (!room.userExists(iClientChat.getUserName())) {
                    log = "User: " + iClientChat.getUserName() + " attempted to sign in to a room they do not belong to.";
                    logs.add(log);
                    return "You do not belong to this room.";
                }
                // Verify the room password
                else if (!room.getPassword().equals(password)) {
                    log = "User: " + iClientChat.getUserName() + " attempted to sign in with the wrong password to room " + roomName;
                    logs.add(log);
                    return "Incorrect password.";
                }
                // User exists and password is correct
                else {
                    // Check if user is already signed in
                    if (room.clientOnline(iClientChat)) {
                        log = "User: " + iClientChat.getUserName() + " attempted to sign in, but was already online in room " + roomName;
                        logs.add(log);
                        return "You are already signed in.";
                    } else {
                        room.addOnlineClient(iClientChat);
                        log = "User: " + iClientChat.getUserName() + " successfully signed in to room " + roomName;
                        logs.add(log);
                        return "Welcome back to the chat.";
                    }
                }
            }
        }
        log = "User: " + iClientChat.getUserName() + " attempted to sign in to a non-existent room.";
        logs.add(log);
        return "Room does not exist.";
    }

    @Override
    public String signOut(IClientChat iClientChat, String roomName) throws RemoteException {
        String log;
        for (Room room : roomsDataBase) {
            // Check if the room exists
            if (room.getRoomName().equals(roomName)) {
                // Verify if the user belongs to this room
                if (!room.userExists(iClientChat.getUserName())) {
                    log = "User: " + iClientChat.getUserName() + " attempted to sign out from a room they do not belong to.";
                    logs.add(log);
                    return "You do not belong to this room.";
                }
                // Verify if the user is currently online in the room
                else if (!room.clientOnline(iClientChat)) {
                    log = "User: " + iClientChat.getUserName() + " attempted to sign out from room " + roomName + " but was not signed in.";
                    logs.add(log);
                    return "You are not signed in.";
                }
                // If user is online, proceed to sign out
                else {
                    room.removeOnlineClient(iClientChat);
                    log = "User: " + iClientChat.getUserName() + " successfully signed out from room " + roomName;
                    logs.add(log);
                    return "You have successfully signed out.";
                }
            }
        }
        log = "User: " + iClientChat.getUserName() + " attempted to sign out from a non-existent room.";
        logs.add(log);
        return "Room does not exist.";
    }

    @Override
    public List<String> showRooms() throws RemoteException {
        List<String> result = new ArrayList<>();
        for (Room room : roomsDataBase) {
            result.add(room.getRoomName());
        }
        String log = "A user requested to view the list of rooms.";
        logs.add(log);
        return result;
    }

    @Override
    public List<String> showClients(String roomName) throws RemoteException {
        List<String> result = new ArrayList<>();
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                result = room.getRoomUsers();
                break;
            }
        }
        String log = "A user requested to view the clients in room " + roomName;
        logs.add(log);
        return result;
    }

    @Override
    public void uniCastMessage(String message, IClientChat senderIClientChat, String username) throws RemoteException {
        String messageToSend = senderIClientChat.getUserName() + ": " + message;
        for (IClientChat user : userDatabase) {
            if (user.getUserName().equals(username)) {
                user.receiveMessage(messageToSend, username);
                String log = "Unicast message from " + senderIClientChat.getUserName() + " to " + username + ": " + message;
                logs.add(log);
                break;
            }
        }
    }

    @Override
    public void multiCastMessage(String message, IClientChat iClientChat, List<String> users) throws RemoteException {
        String messageToSend = iClientChat.getUserName() + ": " + message;
        for (String name : users) {
            for (IClientChat user : userDatabase) {
                if (user.getUserName().equals(name)) {
                    user.receiveMessage(messageToSend, name);
                    String log = "Multicast message from " + iClientChat.getUserName() + " to " + name + ": " + message;
                    logs.add(log);
                }
            }
        }
    }

    @Override
    public void broadCastMessage(String message, String roomName, IClientChat iClientChat) throws RemoteException {
        String messageToSend = "(Broadcast) " + message;
        for (Room room : roomsDataBase) {
            if (room.getRoomName().equals(roomName)) {
                multiCastMessage(messageToSend, iClientChat, room.getAllUsers());
                String log = "Broadcast message from " + iClientChat.getUserName() + " in room " + roomName + ": " + message;
                logs.add(log);
                break;
            }
        }
    }

    public IClientChat findUserByUsername(String username) throws RemoteException {
        for (IClientChat user : userDatabase) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null; // Return null if the user is not found
    }

    public void printLogs() {
        for (String log : logs) {
            System.out.println(log);
        }
    }
}