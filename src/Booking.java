import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Booking extends JFrame {
    private Connection conn = Connect.ConnectDB();
    private CalendarDateInDateOut c = new CalendarDateInDateOut();


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
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCancelButton();
            }
        });
        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setConfirmButton();
            }
        });
    }
    void setCancelButton(){
        int result = JOptionPane.showConfirmDialog(null,"ออกจากหน้าจอการจอง ?","Close",JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            setVisible(false);
        }
    }

    void setConfirmButton(){
        int result = JOptionPane.showConfirmDialog(null,"ยืนยันการจองห้องพัก ?","ยืนยันการจอง",JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            try {
                HomePage h = new HomePage();
                Login l = new Login();
                int d1 = Integer.parseInt(day.getText());
                int d2 = Integer.parseInt(price.getText());
                int totalPrice = d1*d2;
                Date now = new Date();
                Date parseDateIn = c.pattern.parse(in.getText());
                Date parseDateOut = c.pattern.parse(out.getText());
//                        System.out.println(c.patternSQL.format(now));
//                        System.out.println(c.patternSQL.format(parseDateIn));
//                        System.out.println(c.patternSQL.format(parseDateOut));
                String sql = "INSERT INTO booking (booking_no,booking_date,check_in,check_out,days,customer_name,customer_phone," +
                        "room_id,emp_name,total_price, booking_status_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement pre = conn.prepareStatement(sql);
                pre.setString(1, null);
                pre.setString(2, c.patternSQL.format(now));
                pre.setString(3, c.patternSQL.format(parseDateIn));
                pre.setString(4, c.patternSQL.format(parseDateOut));
                pre.setInt(5, Integer.parseInt(day.getText()));
                pre.setString(6, customerName.getText());
                pre.setString(7, customerPhone.getText());
                pre.setInt(8, h.room_id);
                pre.setString(9, l.uName);
                pre.setInt(10, totalPrice);
                pre.setInt(11, 1);
                if (pre.executeUpdate() != -1) {
                    JOptionPane.showMessageDialog(null,"การจองสำเร็จ");
                    customerName.setText("");
                    customerPhone.setText("");
                    h.changeStatusRoom();
                    setVisible(false);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    }

    void bookingRoom(int room_id){
try {
    CalendarSearch c = new CalendarSearch();
//    c.panelIn.
    String sql = "SELECT room_id, room_number, type_th, area, price, status_name  FROM room as r, room_status as s, room_type as t "
            + " WHERE r.status_id = s.status_id AND r.type_id = t.type_id AND room_id = " + room_id;
    ResultSet rs = conn.createStatement().executeQuery(sql);
    while (rs.next()) {
        room.setText(rs.getString("room_number"));
        area.setText(rs.getString("area"));
        bed.setText(rs.getString("type_th"));
        price.setText(rs.getString("price"));
    }

}catch (Exception e){
    e.printStackTrace();
}


    }

     JPanel home;
     JButton cancelButton;
     JButton confirmButton;
     JTextField customerName;
     JLabel area;
     JLabel bed;
     JLabel price;
     JLabel room;
     JLabel in;
     JLabel out;
      JLabel day;
     JTextField customerPhone;
}
