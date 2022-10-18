import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDetails extends JInternalFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private DefaultTableModel modelBooking;
    BookingDetails(){
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("ข้อมูลการจอง");
        setSize(900, 500);
        setContentPane(home);
        setClosable(true);
        setMaximizable(true);

        changeStatusCC();
        initComponents();
        showData();


    }

    private void initComponents() {

        String[] columnName = new String[]  {"เลขที่การจอง", "ห้องที่จอง", "วันที่ Check-In", "วันที่ Check-Out", "ชื่อลูกค้า", "เบอร์ติดต่อ", "สถานะการจอง"};
        modelBooking = new DefaultTableModel ();
        int col = columnName.length;
        int i = 0;
        while (  i < col ){
        modelBooking.addColumn(columnName[i]);
            i++;
        }
        tableBooking.getTableHeader().setFont(new Font("Leelawadee", Font.PLAIN, 14));
        tableBooking.setRowHeight(20);
        tableBooking.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        scrollbar.setViewportView(tableBooking);
        tableBooking.setModel(modelBooking);

        textSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                setTextSearch();
            }
        });

    }

    private void setTextSearch(){
        String search = textSearch.getText().trim();
        try {
            if( !search.isEmpty() ){
                showData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showData();
    }

    private void showData(){
        try {
            int totalRow = tableBooking.getRowCount() -1 ;
            while ( totalRow > -1){
                modelBooking.removeRow(totalRow);
                totalRow--;
            }
            String search = textSearch.getText().trim();
            String sql = " SELECT booking_no, room_number, check_in, check_out, customer_name, customer_phone, booking_status_name_th "
            + " FROM booking as b, room as r, booking_status as bs, room_status as rs "
            + " WHERE b.room_id = r.room_id AND r.status_id = rs.status_id AND b.booking_status_id = bs.booking_status_id "
            + " AND ( booking_no LIKE ('%" + search + "%') "
            + " OR room_number LIKE ('%" + search + "%')"
            + " OR check_in LIKE ('%" + search + "%')"
            + " OR check_out LIKE ('%" + search + "%')"
            + " OR customer_name LIKE ('%" + search + "%')"
            + " OR customer_phone LIKE ('%" + search + "%')"
            + " OR booking_status_name_th LIKE ('%" + search + "%') ) "
            + " ORDER BY booking_no DESC ";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();

            int row = 0;
            while (rs.next()){
                modelBooking.addRow(new Object[0]);
                modelBooking.setValueAt(rs.getString("booking_no"), row, 0);
                modelBooking.setValueAt(rs.getString("room_number"), row, 1);
                modelBooking.setValueAt(rs.getString("check_in"), row, 2);
                modelBooking.setValueAt(rs.getString("check_out"), row, 3);
                modelBooking.setValueAt(rs.getString("customer_name"), row, 4);
                modelBooking.setValueAt(rs.getString("customer_phone"), row, 5);
                modelBooking.setValueAt(rs.getString("booking_status_name_th"), row, 6);
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void changeStatusCC(){  //ยกเลิกการจองที่เลยวันที่เข้าพัก
        try {
            String sql = " UPDATE booking SET booking_status_id = 4 where booking_status_id = 1  "
            + " AND check_in < CONCAT(YEAR(NOW())+543, '-', MONTH(NOW()), '-', DAY(NOW())) ";
            pre = con.prepareStatement(sql);
            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JPanel home;
    private JTable tableBooking;
    private JTextField textSearch;
    private JScrollPane scrollbar;


}
