import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CheckinDetails extends CheckoutDetails {
    private Connection con = Connect.ConnectDB();
    private PreparedStatement pre = null;
    CheckinDetails() {
        CHECKOUTButton.setText("CHECK_IN");
    }
    @Override
    void setCHECKOUTButton(){
        int result = JOptionPane.showConfirmDialog(null, "คุณต้องการ CHECK-IN เข้าห้องพัก ? ", "CHECK-IN", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_NO_OPTION) {
            try {
                int b = Integer.parseInt(booking_no.getText());
                String sql = " UPDATE booking SET booking_status_id = 2 WHERE booking_no = " + b;
                System.out.println(sql);
                pre = con.prepareStatement(sql);
                if (pre.executeUpdate() != -1) {
                    JOptionPane.showMessageDialog(null, "CHECK-IN เรียบร้อย", "CHECK-IN", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
