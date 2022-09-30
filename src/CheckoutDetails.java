import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;

public class CheckoutDetails extends Booking {
    private Connection conn = Connect.ConnectDB();
    CheckoutDetails(){
    setTitle("ข้อมูลห้องพัก");
    setSize(400, 400);
    setLocationRelativeTo(null);

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
    @Override
    public void bookingRoom(int room_id) {
        try {
        String sql = " SELECT room_number, area, type_th, check_in, check_out, days, total_price, customer_name, customer_phone " +
                " FROM booking,room,room_type WHERE booking.room_id = room.room_id AND room_type.type_id = room.type_id AND room.room_id = " + room_id;
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            room.setText(rs.getString("room_number"));
            area.setText(rs.getString("area"));
            bed.setText(rs.getString("type_th"));
            in.setText(rs.getString("check_in"));
            out.setText(rs.getString("check_out"));
            day.setText(rs.getString("days"));
            price.setText(rs.getString("total_price"));
            customerName.setText(rs.getString("customer_name"));
            customerPhone.setText(rs.getString("customer_phone"));
        }
            customerName.setEditable(false);
            customerPhone.setEditable(false);
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public void setConfirmButton() {

    }

    @Override
    public void setCancelButton() {

    }


}
