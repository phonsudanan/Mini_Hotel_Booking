import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Checkin extends JInternalFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private JPanel home;
    private JPanel title_label;
    private JPanel room;
    CheckinDetails checkinDetails = new CheckinDetails();

    Checkin() {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("Check-in ห้องพัก");
        setSize(900, 500);
        setContentPane(home);
        setClosable(true);
        setMaximizable(true);

        room.setLayout(new GridLayout(0, 4, 20, 10));  //row =0 มีแถวไม่จำกัด

        checkinRoom();

    }

     void checkinRoom() {
        try {
            String sql = " SELECT room_number, booking_no, check_in, check_out, days, total_price "
                    + "FROM booking as b, room as r, booking_status as bs, room_status as rs "
                    + "WHERE b.room_id = r.room_id "
                    + "AND r.status_id = rs.status_id  "
                    + "AND b.booking_status_id = bs.booking_status_id "
                    + " AND check_in =  CONCAT(YEAR(NOW())+543, '-', MONTH(NOW()), '-', DAY(NOW()))  " //บวกเป็น พศ
                    + "AND b.booking_status_id = 1 ORDER BY r.room_id ASC ";  //booking_status_id = 1 รอแขกเข้าพัก ในห้องทั้งหมด
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                JButton button = new JButton();
                button.setText(rs.getString("room_number"));
                    room.add(button);
                    click(button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    MouseListener click(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String clickButton = button.getActionCommand();
                System.out.println(clickButton);

                try {
                    String sql = " SELECT room_number, booking_no, customer_name, check_in, check_out, days, total_price "
                            + " FROM booking AS b, room AS r "
                            + " WHERE b.room_id = r.room_id "
                            + " AND check_in =  CONCAT(YEAR(NOW())+543, '-', MONTH(NOW()), '-', DAY(NOW()))  " //บวกเป็น พศ
                            + " AND room_number =  " + "'" + clickButton + "'";

                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery();

                    while (rs.next()) {
                        checkinDetails.room.setText(rs.getString("room_number"));
                        checkinDetails.booking_no.setText(rs.getString("booking_no"));
                        checkinDetails.customer_name.setText(rs.getString("customer_name"));
                        checkinDetails.check_in.setText(rs.getString("check_in"));
                        checkinDetails.check_out.setText(rs.getString("check_out"));
                        checkinDetails.days.setText(rs.getString("days"));
                        checkinDetails.total_price.setText(rs.getString("total_price"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                checkinDetails.setVisible(true);
            }
        });
        return null;
    }

}
