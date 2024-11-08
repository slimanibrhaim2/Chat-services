import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SignIn extends JDialog {
    private JPanel SignInPanel;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JTextField tfRoomName;
    private JButton btnSignIn;
    private JButton btnCancel;

    public SignIn(JFrame parent) {
        super(parent);
        setTitle("Sign In to Room");
        setContentPane(SignInPanel);
        setSize(new Dimension(800, 400));
        setModal(true);
        setLocationRelativeTo(parent);

        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = String.valueOf(pfPassword.getPassword());
                String roomName = tfRoomName.getText();

                if (username.isEmpty() || password.isEmpty() || roomName.isEmpty()) {
                    JOptionPane.showMessageDialog(SignInPanel, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ClientChatImpl clientChat = new ClientChatImpl("admin", "admin", username, password, "localhost");
                    String result = clientChat.signIn(clientChat, password, roomName);
                    if (result.equals("Sign in successful")) {
                        JOptionPane.showMessageDialog(SignInPanel, "Sign in successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(SignInPanel, result, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                    JOptionPane.showMessageDialog(SignInPanel, "Error during sign in: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                new SignIn(null);
            }
        });
    }
}