import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AddRoom extends JDialog {
    private JPasswordField pfPassword;
    private JTextField tfRoomName;
    private JButton btnAdd;
    private JPanel addRoom;
    private JTextField tfusername;

    public AddRoom(JFrame parent) {
        super(parent);
        setTitle("Add Room");
        setContentPane(addRoom);
        setSize(new Dimension(800, 300));
        setModal(true);
        setLocationRelativeTo(parent);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName = tfRoomName.getText();
                String username = tfusername.getText();
                String roomPassword = String.valueOf(pfPassword.getPassword());
                if (roomName.isEmpty() || roomPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(addRoom, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Assuming the client is already registered and logged in
                    ClientChatImpl clientChat = new ClientChatImpl("admin", "admin", "admin", "1234", "localhost");

                    IClientChat iClientChat = clientChat.findUserByUsername(username);
                    if (iClientChat == null) {
                        JOptionPane.showMessageDialog(addRoom, "User does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ClientChatImpl clientChat1 = new ClientChatImpl(iClientChat.getFirstName(), iClientChat.getLastName(), iClientChat.getUserName(), iClientChat.getPassword(), "localhost");
                    String result = clientChat1.addRoom(roomName, roomPassword, clientChat1);
                    JOptionPane.showMessageDialog(addRoom, result, "Room Creation", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the dialog after successful room creation
                } catch (MalformedURLException | NotBoundException | RemoteException ex) {
                    JOptionPane.showMessageDialog(addRoom, "Error connecting to server: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddRoom(null).setVisible(true);
            }
        });
    }
}