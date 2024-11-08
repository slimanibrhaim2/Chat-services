import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Register extends JDialog {
    private ClientChatImpl clientChat;
    private JTextField tfFirstName;
    private JTextField tfSecondName;
    private JTextField tfUserName;
    private JPasswordField pfPassword;
    private JPasswordField tfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;

    public Register(JFrame parent) {
        super(parent);
        setTitle("Register to System");
        setContentPane(registerPanel);
        setMaximumSize(new Dimension(450, 474));
        setSize(new Dimension(800, 400));
        setModal(true);
        setLocationRelativeTo(parent);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = tfFirstName.getText();
                String lastName = tfSecondName.getText();
                String userName = tfUserName.getText();
                String password = String.valueOf(pfPassword.getPassword());
                String confirmPassword = String.valueOf(tfConfirmPassword.getPassword());

                if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(registerPanel, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(registerPanel, "Password and confirm password don't match", "Try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    clientChat = new ClientChatImpl(firstName, lastName, userName, password, "localhost");
                    clientChat.register(firstName, lastName, userName, password, clientChat);
                    JOptionPane.showMessageDialog(registerPanel, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                    JOptionPane.showMessageDialog(registerPanel, "Error during registration: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                new Register(null);
            }
        });
    }
}