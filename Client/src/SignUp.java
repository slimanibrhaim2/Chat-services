import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SignUp extends JDialog {
    private JTextField tfUsername;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfRoomName;
    private JButton btnSignUp;
    private JButton btnCancel;
    private JPanel SignUpPanel;

    public SignUp(JFrame parent) {
        super(parent);
        setTitle("Sign Up for a Room");
        setContentPane(SignUpPanel);
        setSize(new Dimension(800, 400));
        setModal(true);
        setLocationRelativeTo(parent);

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String firstName = tfFirstName.getText();
                String lastName = tfLastName.getText();
                String roomName = tfRoomName.getText();

                if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || roomName.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpPanel, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ClientChatImpl clientChat = new ClientChatImpl(firstName, lastName, username, "admin", "localhost");
                    String result = clientChat.signUp(clientChat, username, firstName, lastName, roomName);
                    if (result.contains("User added successfully")) {
                        JOptionPane.showMessageDialog(SignUpPanel, result, "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(SignUpPanel, result, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                    JOptionPane.showMessageDialog(SignUpPanel, "Error during sign up: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                new SignUp(null);
            }
        });
    }
}