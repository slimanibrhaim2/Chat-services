import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class DeleteRoom extends JDialog {
    private JTextField tfUsername;
    private JTextField tfRoomName;
    private JPasswordField pfPassword;
    private JButton btnCancel;
    private JButton btnDelete;
    private JPanel DeletePanel;

    public DeleteRoom(JFrame parent) {
        super(parent);
        setTitle("Delete Room");
        setContentPane(DeletePanel);
        setSize(new Dimension(800, 300));
        setModal(true);
        setLocationRelativeTo(parent);

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName = tfRoomName.getText();
                String username = tfUsername.getText();
                String roomPassword = String.valueOf(pfPassword.getPassword());
                if (roomName.isEmpty() || roomPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(DeletePanel, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Assuming the client is already registered and logged in
                    ClientChatImpl clientChat = new ClientChatImpl("admin", "admin", "admin", "1234", "localhost");

                    IClientChat iClientChat = clientChat.findUserByUsername(username);
                    if (iClientChat == null) {
                        JOptionPane.showMessageDialog(DeletePanel, "User does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ClientChatImpl clientChat1 = new ClientChatImpl(iClientChat.getFirstName(), iClientChat.getLastName(), iClientChat.getUserName(), iClientChat.getPassword(), "localhost");
                    String result = clientChat1.deleteRoom(roomName, roomPassword, clientChat1);
                    JOptionPane.showMessageDialog(DeletePanel, result, "Room Deletion", JOptionPane.INFORMATION_MESSAGE);
                    if (result.equals("Room deleted successfully.")){
                        dispose(); // Close the dialog after successful room deletion
                    }
                } catch (MalformedURLException | NotBoundException | RemoteException ex) {
                    JOptionPane.showMessageDialog(DeletePanel, "Error connecting to server: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog when cancel is clicked
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeleteRoom(null).setVisible(true);
            }
        });
    }
}