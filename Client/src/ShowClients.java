import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

public class ShowClients extends JFrame {
    private JPanel ClientsList;
    private JButton btnClose;
    private JList<String> Clients;
    private JTextField tfRoomName;
    private JButton getNamesButton;
    private DefaultListModel<String> listModel;

    public ShowClients(JFrame parent) {
        setTitle("Show Clients in Room");
        setContentPane(ClientsList);
        setSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        Clients.setModel(listModel);

        // Center the text in the JList
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) Clients.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        getNamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName = tfRoomName.getText().trim();
                if (roomName.isEmpty()) {
                    JOptionPane.showMessageDialog(ShowClients.this, "Please enter a room name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                updateClientList(roomName);
            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void updateClientList(String roomName) {
        listModel.clear();
        try {
            List<String> clients = getClientsFromServer(roomName);
            for (String client : clients) {
                listModel.addElement(client);
            }
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving clients: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<String> getClientsFromServer(String roomName) throws RemoteException {
        try {
            ClientChatImpl iClientChat = new ClientChatImpl("admin", "admin", "admin", "admin", "localhost");
            return iClientChat.showClients(roomName);
        } catch (Exception e) {
            throw new RemoteException("Failed to retrieve clients from server", e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShowClients(null);
            }
        });
    }
}