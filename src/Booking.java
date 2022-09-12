import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        CANCELButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(null,"ออกจากหน้าจอการจอง ?","Close",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    setVisible(false);
                }

            }
        });
        CONFIRMButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(null,"ยืนยันการจองห้องพัก ?","ยืนยันการจอง",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){

                    JOptionPane.showMessageDialog(null,"การจองสำเร็จ");
                    setVisible(false);
                }
            }
        });
    }

    void BookingRoom(String room_id){
try {
    CalendarSearch c = new CalendarSearch();
//    c.panelIn.
    String sql = "SELECT * FROM room where room_id = " + room_id;
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
    private JTextField customer;
    private JLabel area;
    private JLabel bed;
    private JLabel price;
    private JLabel room;
    public JLabel in;
    public JLabel out;
    public JLabel day;
}
