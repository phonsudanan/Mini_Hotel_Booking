import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Checkout extends JInternalFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private JPanel home;
    private JPanel room;

    private JPanel title_label;
    CheckoutDetails checkoutDetails = new CheckoutDetails();

    Checkout()  {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("Check-out ห้องพัก");
        setSize(900, 500);
        setContentPane(home);
        setClosable(true);
        setMaximizable(true);

        room.setLayout(new GridLayout(0, 4, 20, 10));  //row =0 มีแถวไม่จำกัด

        checkoutRoom();

    }

    private void checkoutRoom() {
        try {
            String sql = " SELECT room_number, booking_no, check_in, check_out, days, total_price "
                    + "FROM booking as b, room as r, booking_status as bs, room_status as rs "
                    + "WHERE b.room_id = r.room_id "
                    + "AND r.status_id = rs.status_id  "
                    + "AND b.booking_status_id = bs.booking_status_id "
                    + " AND check_out =  CONCAT(YEAR(NOW())+543, '-', MONTH(NOW()), '-', DAY(NOW()))  "
                    + "AND b.booking_status_id = 2 ORDER BY r.room_id ASC ";  //booking_status_id = 2 แขกกำลังพักในห้อง
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
                try {
                    String sql = " SELECT room_number, booking_no, customer_name, check_in, check_out, days, total_price "
                            + " FROM booking AS b, room AS r "
                            + " WHERE b.room_id = r.room_id "
                            + " AND check_out =  CONCAT(YEAR(NOW())+543, '-', MONTH(NOW()), '-', DAY(NOW()))  " //บวกเป็น พศ
                            + " AND room_number =  " + "'" + clickButton + "'";

                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery();
                    while (rs.next()) {
                        checkoutDetails.room.setText(rs.getString("room_number"));
                        checkoutDetails.booking_no.setText(rs.getString("booking_no"));
                        checkoutDetails.customer_name.setText(rs.getString("customer_name"));
                        checkoutDetails.check_in.setText(rs.getString("check_in"));
                        checkoutDetails.check_out.setText(rs.getString("check_out"));
                        checkoutDetails.days.setText(rs.getString("days"));
                        checkoutDetails.total_price.setText(rs.getString("total_price"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                checkoutDetails.setVisible(true);
            }
        });
        return null;
    }

}
