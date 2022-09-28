import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Rooms extends JInternalFrame {

    private JPanel home;
    private JTable tableRooms;
    private JTextField searchRoom;
    private JTextField room_id;
    private JTextField room_number;
    private JTextField area;
    private JTextField price;
    private JButton resetButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;
    private JScrollPane scrollbar;
    private JComboBox status_id;
    private JComboBox bed;
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private DefaultTableModel modelRoom;
    Rooms(){
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("ข้อมูลห้องพัก");
        setSize(900, 500);
        setContentPane(home);
        setClosable(true);
        setMaximizable(true);

        modelRoom = (DefaultTableModel) tableRooms.getModel();
        initComponents();
        setStatus_id();
        setBed();


        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                showData();
            }
        });



    }

    private void initComponents(){
        modelRoom.addColumn("ลำดับห้อง");
        modelRoom.addColumn("เลขที่ห้อง");
        modelRoom.addColumn("พื้นที่");
        modelRoom.addColumn("เตียง");
        modelRoom.addColumn("ราคา");
        modelRoom.addColumn("สถานะ");
        tableRooms.getTableHeader().setFont(new Font("Leelawadee", Font.PLAIN, 14));
        tableRooms.setRowHeight(20);
        tableRooms.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

        scrollbar.setViewportView(tableRooms);

        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setResetButton();
            }
        });
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDeleteButton();
            }
        });
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setEditButton();
            }
        });
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setAddButton();
            }
        });
        searchRoom.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchRoom();
            }
        });
        tableRooms.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableRoomsMouseClicked();
            }
        });

    }
    private void tableRoomsMouseClicked(){
        int row = tableRooms.getSelectedRow();
        room_id.setText(tableRooms.getValueAt(row,0).toString());
        room_number.setText(tableRooms.getValueAt(row,1).toString());
        area.setText(tableRooms.getValueAt(row,2).toString());
        bed.setSelectedItem(tableRooms.getValueAt(row,3).toString());
        price.setText(tableRooms.getValueAt(row,4).toString());
        status_id.setSelectedItem(tableRooms.getValueAt(row,5).toString());
        room_id.setEditable(false);
        addButton.setEnabled(false);
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    private void showData(){

        try {
            int totalRow = tableRooms.getRowCount() - 1;
            while (totalRow > -1){
                modelRoom.removeRow(totalRow);
                totalRow--;
            }
            String search = searchRoom.getText().trim();
            String sql = " SELECT room_id, room_number, type_th, area, price, status_name FROM room as r, room_status as s, room_type as t "
                    + " WHERE r.status_id = s.status_id AND r.type_id = t.type_id "
                    + " AND (room_id LIKE ('%" +search+ "%')  "
                    + " OR room_number LIKE ('%" +search+ "%') "
                    + " OR area LIKE ('%" +search+ "%') "
                    + " OR type_th LIKE ('%" +search+ "%') "
                    + " OR price LIKE ('%" +search+ "%') "
                    + " OR status_name LIKE ('%" +search+ "%')) ORDER BY room_id ASC  ";

            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            int row = 0;
            while (rs.next()){
                modelRoom.addRow(new Object[0]);
                modelRoom.setValueAt(rs.getString("room_id"), row, 0);
                modelRoom.setValueAt(rs.getString("room_number"), row, 1);
                modelRoom.setValueAt(rs.getString("area"), row, 2);
                modelRoom.setValueAt(rs.getString("type_th"), row, 3);
                modelRoom.setValueAt(rs.getString("price"), row, 4);
                modelRoom.setValueAt(rs.getString("status_name"), row, 5);
                row++;
            }
            tableRooms.setModel(modelRoom);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void searchRoom(){
        String search = searchRoom.getText().trim();
        try {
            if( !search.isEmpty()){
                showData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showData();
    }

    private void setResetButton(){
        room_id.setEditable(true);
        room_id.setText("");
        room_number.setText("");
        area.setText("");
        bed.setSelectedIndex(0);
        price.setText("");
        status_id.setSelectedIndex(0);

        addButton.setEnabled(true);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    private void setEditButton(){
            if( tableRooms.getSelectedRow() == -1){
                return;
            }else {

        int n = JOptionPane.showConfirmDialog(
                this, "ยืนยันการแก้ไขข้อมูลห้องพัก ?", "EDIT ROOM", JOptionPane.YES_NO_OPTION);
        if ( n == JOptionPane.YES_OPTION ){
        try {
            String sql = " UPDATE room SET "
                    + " room_number = ? ,"
                    + " area = ? ,"
                    + " price = ? ,"
                    + " status_id = ? ,"
                    + " type_id = ? "
                    + " WHERE room_id = ? ";
            pre = con.prepareStatement(sql);
            pre.setString(1,room_number.getText().trim());
            pre.setString(2,area.getText().trim());
            pre.setString(3,price.getText().trim());
            pre.setInt(4,setStatus_id(status_id.getSelectedItem().toString()));
            pre.setInt(5,setBed(bed.getSelectedItem().toString()));
            pre.setString(6,room_id.getText().trim());
            if (pre.executeUpdate() != -1){
                JOptionPane.showMessageDialog(this,"แก้ไขข้อมูล ห้อง "  + room_number.getText() + " เรียบร้อยแล้ว","",JOptionPane.INFORMATION_MESSAGE);
                room_id.setEditable(false);
                addButton.setEnabled(false);
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                showData();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
            }
    }
    private void setDeleteButton(){
                if( tableRooms.getSelectedRow() == -1 ){
                    return;
                }else {
                    int n = JOptionPane.showConfirmDialog(
                            this, "คุณต้องการลบห้อง เลขที่" + room_number.getText() + "?", "DELETE ROOM", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        try {
                            String sql = " DELETE FROM room WHERE room_id = ? ";
                            pre = con.prepareStatement(sql);
                            pre.setString(1, room_id.getText().trim());
                            if (pre.executeUpdate() != -1) {
                                JOptionPane.showMessageDialog(this, "ลบ ห้องเลขที่  " + room_number.getText() + " เรียบร้อยแล้ว");
                                showData();
                                setResetButton();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
    }
    private void setAddButton()  {
        if (room_id.getText().trim().isEmpty() || room_number.getText().trim().isEmpty() || area.getText().trim().isEmpty() || price.getText().trim().isEmpty() ||
                bed.getSelectedIndex() == 0 || status_id.getSelectedIndex() == 0)  {
            JOptionPane.showMessageDialog
                    (this, "กรุณาใส่ข้อมูลให้ครบ ห้ามเว้นว่าง", "ผลการบันทึกรายการ", JOptionPane.ERROR_MESSAGE);
        } else {
            int n = JOptionPane.showConfirmDialog
                    (this, "บันทึกข้อมูลห้องพัก", "ADD ROOM", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                try {
                    String sql = " INSERT INTO room(room_id, room_number, area, price, status_id, type_id) Values(?,?,?,?,?,?) ";
                    pre = con.prepareStatement(sql);

                    pre.setString(1, room_id.getText().trim());
                    pre.setString(2, room_number.getText().trim());
                    pre.setString(3, area.getText().trim());
                    pre.setString(4, price.getText().trim());
                    pre.setInt(5, setStatus_id(status_id.getSelectedItem().toString()));
                    pre.setInt(6, setBed(bed.getSelectedItem().toString()));

                    pre.executeUpdate();
                    JOptionPane.showMessageDialog
                            (this, "บันทึกรายการแล้ว", "ผลการบันทึกรายการ", JOptionPane.INFORMATION_MESSAGE);
                    showData();
                    setResetButton();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void setBed(){
        try {
            String sql = " SELECT type_th FROM room_type ";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()){
                bed.addItem(rs.getString("type_th"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setStatus_id(){
        try {
            String sql = " SELECT status_name FROM room_status ";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()){
                status_id.addItem(rs.getString("status_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int setStatus_id(String status){
        int id = 0;
        status = status_id.getSelectedItem().toString();
        switch (status){
            case "ว่าง" : id = 1; break;
            case "กำลังใช้งาน" : id = 2; break;
            case "ซ่อมแซม" : id = 3; break;
        }
        return id;
    }

    int setBed(String bedName){
        int id = 0;
        bedName = bed.getSelectedItem().toString();
        switch (bedName){
            case "ห้องเตียงเดี่ยว" : id = 1; break;
            case "ห้องเตียงคู่เล็ก" : id = 2; break;
            case "ห้องเตียงคู่ใหญ่" : id = 3; break;
            case "ห้อง 3 เตียง" : id = 4; break;
        }
        return id;
    }


}
