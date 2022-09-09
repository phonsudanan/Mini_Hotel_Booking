import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class Booking extends JFrame {
    private Connection conn = Connect.ConnectDB();
    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));
       new Booking().setVisible(true);
    }

    public Booking(){
        setTitle("ข้อมูลห้องพัก");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setContentPane(home);
//        BookingRoom(001);
    }

    void BookingRoom(int room_number){
try {
    CalendarSearch c = new CalendarSearch();
//    c.panelIn.
    String sql = "SELECT * FROM room where room_id = " + room_number;
    ResultSet rs = conn.createStatement().executeQuery(sql);
    while (rs.next()) {
        room.setText(rs.getString("room_number"));
        area.setText(rs.getString("area"));
        bed.setText(rs.getString("bed"));
        price.setText(rs.getString("price"));
    }

}catch (Exception e){
    e.printStackTrace();
}


    }

    private JPanel home;
    private JButton CANCELButton;
    private JButton CONFIRMButton;
    private JTextField textField1;
    private JLabel area;
    private JLabel bed;
    private JLabel price;
    private JLabel room;
    public JLabel in;
    public JLabel out;
    public JLabel day;
}
