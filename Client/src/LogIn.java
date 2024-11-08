import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LogIn extends JDialog {
    private JTextField textField1; // Assuming this is for the username
    private JPasswordField pfPassword;
    private JPanel tfUsername; // This seems like it should be a JTextField, but keeping as JPanel per your snippet
    private JButton btnOk;
    private JButton btnCancel;
    private JPanel LoginPanel;

    public LogIn(JFrame parent) {
        super(parent);
        setTitle("Login to System");
        setContentPane(LoginPanel);
        setSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = textField1.getText();
                String password = String.valueOf(pfPassword.getPassword());

                if (userName.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginPanel, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ClientChatImpl clientChat = new ClientChatImpl("admin", "admin", "admin", "1234", "localhost");
                    String result = clientChat.logIn(userName, password);
                    if (result.equals("Login successful. Welcome back")) {
                        JOptionPane.showMessageDialog(LoginPanel, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Close the login dialog
                        new HomePage(parent).setVisible(true); // Open the HomePage
                    } else {
                        JOptionPane.showMessageDialog(LoginPanel, result, "Field", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                    JOptionPane.showMessageDialog(LoginPanel, "Error during login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                new LogIn(null);
            }
        });
    }
}