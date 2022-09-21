import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Employee extends JInternalFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private DefaultTableModel modelEmployee;
      Employee(){
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("ข้อมูลพนักงาน");
        setSize(900, 500);
        setContentPane(home);
        setClosable (true);
        setMaximizable(true);

        modelEmployee = (DefaultTableModel) tableEmployee.getModel();

        initComponents();

        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                ShowDataEmployee();
            }
        });

    }


    private void initComponents(){
        modelEmployee.addColumn("รหัสพนักงาน");
        modelEmployee.addColumn("ชื่อ");
        modelEmployee.addColumn("ที่อยู่");
        modelEmployee.addColumn("เบอร์ติดต่อ");
        modelEmployee.addColumn("ตำแหน่งงาน");
        modelEmployee.addColumn("ระดับงาน");

        tableEmployee.getTableHeader().setFont(new Font("Leelawadee", Font.PLAIN, 14));
        tableEmployee.setRowHeight(20);
        tableEmployee.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

        scrollbar.setViewportView(tableEmployee);

    }


        private void ShowDataEmployee(){

            try{
                int totalRow = tableEmployee.getRowCount() -1 ;
                while ( totalRow > -1){
                    modelEmployee.removeRow(totalRow);
                    totalRow--;
                }
                String search = searchEmp.getText().trim();
                String sql = " SELECT * FROM employee "
                        + " where "
                        + " emp_id LIKE ('%" + search + "%') "
                        + " OR name LIKE ('%" + search + "%')"
                        + " OR address LIKE ('%" + search + "%')"
                        + " OR phone LIKE ('%" + search + "%')"
                        + " OR position LIKE ('%" + search + "%')"
                        + " OR level LIKE ('%" + search + "%')";
                pre = con.prepareStatement(sql);
                rs = pre.executeQuery();

                int row = 0;
                while (rs.next()){
                    modelEmployee.addRow(new Object[0]);
                    modelEmployee.setValueAt(rs.getString("emp_id"), row, 0);
                    modelEmployee.setValueAt(rs.getString("name"), row, 1);
                    modelEmployee.setValueAt(rs.getString("address"), row, 2);
                    modelEmployee.setValueAt(rs.getString("phone"), row, 3);
                    modelEmployee.setValueAt(rs.getString("position"), row, 4);
                    modelEmployee.setValueAt(rs.getString("level"), row, 5);
                    row++;
                }
                tableEmployee.setModel(modelEmployee);
            } catch (Exception e) {
                e.printStackTrace();
            }


//        try {
//            int totalRow = tableEmployee.getRowCount() -1;
//            while (totalRow > -1){
////                modelEmployee.removeRow(totalRow);
//                totalRow--;
//            }
//            String search = searchEmp.getText().trim();
//            String sql = " SELECT emp_id, name, address, phone, position, level FROM employee "
//                    + "where "
//                    + "emp_id LIKE ('%" + search + "%')"
//                    + "OR name LIKE ('%" + search + "%')"
//                    + "OR address LIKE ('%" + search + "%')"
//                    + "OR phone LIKE ('%" + search + "%')"
//                    + "OR position LIKE ('%" + search + "%')"
//                    + "OR level LIKE ('%" + search + "%')";
//            pre = con.prepareStatement(sql);
//            rs = pre.executeQuery(sql);
//            int row = 0;
//
//            while (rs.next()){
//                modelEmployee.addRow( new Object[0]);
//                modelEmployee.setValueAt(rs.getString("emp_id"),row,0);
//                modelEmployee.setValueAt(rs.getString("name"),row,1);
//                modelEmployee.setValueAt(rs.getString("address"),row,2);
//                modelEmployee.setValueAt(rs.getString("phone"),row,3);
//                modelEmployee.setValueAt(rs.getString("position"),row,4);
//                modelEmployee.setValueAt(rs.getString("level"),row,5);
//                row++;
//            }
//            tableEmployee.setModel(modelEmployee);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private JPanel home;
    private JTable tableEmployee;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField5;
    private JTextField textField6;
    private JButton เริ่มใหม่Button;
    private JButton ลบButton;
    private JButton แก้ไขButton;
    private JButton บันทึกButton;
    private JScrollPane scrollbar;
    private JTextField searchEmp;
}
