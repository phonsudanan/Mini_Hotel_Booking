import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CheckoutDetails extends JFrame {
    private Connection con = Connect.ConnectDB();
    private PreparedStatement pre = null;
    JButton CANCELButton;
    JButton CHECKOUTButton;
    private JPanel home;
     JLabel booking_no;
    JLabel room;
    JLabel check_in;
    JLabel check_out;
    JLabel days;
    JLabel total_price;
     JLabel customer_name;

    CheckoutDetails() {
        setTitle("ข้อมูลห้องพัก");
        setSize(400, 400);
        setContentPane(home);
        setLocationRelativeTo(null);
        setButton();
    }

    void setButton(){
        CANCELButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });

        CHECKOUTButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCHECKOUTButton();
            }
        });
    }

    void setCHECKOUTButton(){
        int result = JOptionPane.showConfirmDialog(null, "คุณต้องการ CHECK-OUT ? ", "CHECK-OUT", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_NO_OPTION) {
            try {
                int b = Integer.parseInt(booking_no.getText());
                String sql = " UPDATE booking SET booking_status_id = 3 WHERE booking_no = " + b;
                pre = con.prepareStatement(sql);
                if (pre.executeUpdate() != -1) {
                    JOptionPane.showMessageDialog(null,"CHECK-OUT เรียบร้อย", "CHECK-OUT",JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }


}
