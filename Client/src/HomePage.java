import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HomePage extends JFrame {
    private JButton btnAddRoom;
    private JButton btnShowRooms;
    private JButton btnDeleteRoom;
    private JButton btnShowClients;
    private JButton btnSignOut;
    private JButton btnSignIn;
    private JButton btnSignUp;
    private JTextArea messagesArea;
    private JTextField tfUnicastMessage;
    private JButton btnUnicast;
    private JTextField tfReciever;
    private JButton btnBroadCast;
    private JTextField tfBroadCastMessge;
    private JTextField tfRoom;
    private JPanel HomePagePanel;


    public HomePage(ClientChatImpl clientChat) {
        setTitle("Home Page");
        setContentPane(HomePagePanel);
        setSize(new Dimension(1200, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        messagesArea.setEditable(false);

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUp(HomePage.this);
            }
        });

        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignIn(HomePage.this);
            }
        });

        btnSignOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignOut(HomePage.this);
            }
        });

        btnShowRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ShowRooms(HomePage.this);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnShowClients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowClients(HomePage.this);
            }
        });

        btnAddRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddRoom(HomePage.this);
            }
        });

        btnDeleteRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new  DeleteRoom(HomePage.this);
            }
        });

        btnUnicast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = tfUnicastMessage.getText().trim();
                String receiver = tfReciever.getText().trim();
                if (message.isEmpty() || receiver.isEmpty()) {
                    JOptionPane.showMessageDialog(HomePage.this, "Please enter a message and a receiver.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        clientChat.uniCastMessage(message, clientChat, receiver);
                        messagesArea.append("To " + receiver + ": " + message + "\n");
                        tfUnicastMessage.setText("");
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        btnBroadCast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = tfBroadCastMessge.getText().trim();
                if (!message.isEmpty()) {
                    try {
                        clientChat.broadCastMessage(message, String.valueOf(tfRoom), clientChat);
                        messagesArea.append("BroadCast : " + message + "\n");
                        tfBroadCastMessge.setText("");
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(HomePage.this, "Please enter a message to broadcast.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage(null);
            }
        });
    }
}