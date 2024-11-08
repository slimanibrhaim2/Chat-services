import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ShowRooms extends JFrame {
    private JList<String> Rooms;
    private JPanel RoomList;
    private JButton closeButton;
    private DefaultListModel<String> listModel;

    public ShowRooms(JFrame parent) throws RemoteException {
        List<String>rooms;
        try {
            ClientChatImpl iClientChat = new ClientChatImpl("admin", "admin", "admin", "admin", "localhost");
            rooms= iClientChat.showRooms();
        } catch (Exception e) {
            throw new RemoteException("Failed to retrieve rooms from server", e);
        }
        setTitle("Available Rooms");
        setContentPane(RoomList);
        setSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        for (String room : rooms) {
            listModel.addElement(room);
        }
        Rooms.setModel(listModel);

        // Center the text in the JList
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) Rooms.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        closeButton.addActionListener(new ActionListener() {
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

            }
        });

    }


}