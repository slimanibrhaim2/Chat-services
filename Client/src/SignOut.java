import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SignOut extends JDialog {
    private JPanel SignOutPanel;
    private JTextField tfUserName;
    private JTextField tfRoomName;
    private JButton btnSignOut;
    private JButton btnCancel;

    public SignOut(JFrame parent) {
        super(parent);
        setTitle("Sign Out from Room");
        setContentPane(SignOutPanel);
        setSize(new Dimension(800, 400));
        setModal(true);
        setLocationRelativeTo(parent);

        btnSignOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUserName.getText();
                String roomName = tfRoomName.getText();

                if (username.isEmpty() || roomName.isEmpty()) {
                    JOptionPane.showMessageDialog(SignOutPanel, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ClientChatImpl clientChat = new ClientChatImpl("admin", "admin", username, "admin", "localhost");
                    String result = clientChat.signOut(clientChat, roomName);
                    if (result.equals("Sign out successful")) {
                        JOptionPane.showMessageDialog(SignOutPanel, "Sign out successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(SignOutPanel, result, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                    JOptionPane.showMessageDialog(SignOutPanel, "Error during sign out: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignOut(null);
            }
        });
    }
}