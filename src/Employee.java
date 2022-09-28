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

public class Employee extends JInternalFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private DefaultTableModel modelEmployee;

    Employee() {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("ข้อมูลพนักงาน");
        setSize(900, 500);
        setContentPane(home);
        setClosable(true);
        setMaximizable(true);

        modelEmployee = (DefaultTableModel) tableEmployee.getModel();

        initComponents();
        showPosition();
        showLevel();

        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                showDataEmployee();
            }
        });


    }

    private void initComponents() {
        searchEmp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                seachEmpKeyReleased();
            }
        });
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

        tableEmployee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableMouseClicked();
            }
        });

        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reset();
            }
        });
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delete();
            }
        });
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                edit();
            }
        });
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                save();
            }
        });
    }

    private void reset() {
        textId.setEditable(true);
        textUsername.setEditable(true);
        textPassword.setEditable(true);
        textId.setText("");
        textName.setText("");
        textAddress.setText("");
        textPhone.setText("");
        textPosition.setSelectedIndex(0);
        textLevel.setSelectedIndex(0);
        textUsername.setText("");
        textPassword.setText("");

        addButton.setEnabled(true);
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
    }

    private void delete() {
        String id = textId.getText().trim();
        if (!id.isEmpty()) {
            int n = JOptionPane.showConfirmDialog(
                    this, "คุณต้องการลบพนักงาน ?", "DELETE EMPLOYEE", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                try {
                    if (tableEmployee.getSelectedRow() == -1) {
                        return;
                    }
                    String sql = " delete from employee where emp_id = ? ";
                    pre = con.prepareStatement(sql);
                    pre.setString(1, id);

                    if (pre.executeUpdate() != -1) {
                        JOptionPane.showMessageDialog(this, "ลบ รหัสพนักงาน " + id + " เรียบร้อยแล้ว");
                        textId.setText("");
                        textName.setText("");
                        textAddress.setText("");
                        textPhone.setText("");
                        textPosition.setSelectedIndex(0);
                        textLevel.setSelectedIndex(0);
                        textUsername.setText("");
                        textPassword.setText("");
                        showDataEmployee();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void edit() {
        String id = textId.getText().trim();
        if (!id.isEmpty()) {
            int n = JOptionPane.showConfirmDialog(
                    this, "ยืนยันการแก้ไขข้อมูลพนักงาน ?", "EDIT EMPLOYEE", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                try {
                    if (tableEmployee.getSelectedRow() == -1) {
                        return;
                    }

                    String sql = "update employee set "
                            + " name = ? ,"
                            + " address = ? ,"
                            + " phone = ? ,"
                            + " pos_id = ? ,"
                            + " level_id = ? "
                            + " WHERE emp_id = ? ";

                    pre = con.prepareStatement(sql);
                    pre.setString(1, textName.getText().trim());
                    pre.setString(2, textAddress.getText().trim());
                    pre.setString(3, textPhone.getText().trim());
                    pre.setInt(4, posID(textPosition.getSelectedItem().toString().trim()));
                    pre.setInt(5, levelID(textLevel.getSelectedItem().toString().trim()));
                    pre.setString(6, textId.getText().trim());

                    if (pre.executeUpdate() != -1) {
                        JOptionPane.showMessageDialog(this, "แก้ไขข้อมูล รหัสพนักงาน " + id + " เรียบร้อยแล้ว");
                        textId.setText("");
                        textName.setText("");
                        textAddress.setText("");
                        textPhone.setText("");
                        textPosition.setSelectedIndex(0);
                        textLevel.setSelectedIndex(0);
                        textUsername.setText("");
                        textPassword.setText("");
                        showDataEmployee();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void save() {

        if (textId.getText().trim().isEmpty() || textName.getText().trim().isEmpty() || textAddress.getText().trim().isEmpty() || textPhone.getText().trim().isEmpty() ||
                textPosition.getSelectedIndex() == 0 || textLevel.getSelectedIndex() == 0 || textUsername.getText().trim().isEmpty() || textPassword.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog
                    (this, "กรุณาใส่ข้อมูลให้ครบ ห้ามเว้นว่าง", "ผลการบันทึกรายการ", JOptionPane.ERROR_MESSAGE);
        } else {
            int n = JOptionPane.showConfirmDialog(
                    this, "บันทึกข้อมูลพนักงาน ?", "ADD EMPLOYEE", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                try {
                    String user = " SELECT emp_id, username FROM employee ";
                    pre = con.prepareStatement(user);
                    rs = pre.executeQuery();
                    int u = 0;
                    while (rs.next()) {
                        if ( textUsername.getText().trim().equals(rs.getString("username")) && textId.getText().trim().equals(rs.getString("emp_id")) ) {
                            JOptionPane.showMessageDialog(this,"รหัสพนักงาน และ Username นี้ ถูกใช้แล้ว กรุณาตั้งใหม่","",JOptionPane.ERROR_MESSAGE);
                        u++;
                        }
                        else if ( textId.getText().trim().equals(rs.getString("emp_id")) ) {
                            JOptionPane.showMessageDialog(this,"รหัสพนักงานไม่สามารถซ้ำกันได้ กรุณาตั้งใหม่","",JOptionPane.ERROR_MESSAGE);
                            u++;
                        }
                        else if ( textUsername.getText().trim().equals(rs.getString("username")) ) {
                            JOptionPane.showMessageDialog(this,"Username นี้ ถูกใช้แล้ว กรุณาตั้งใหม่","",JOptionPane.ERROR_MESSAGE);
                            u++;
                        }
                    }
                    if(u==0) {
                        try {
                            String sql = " INSERT INTO employee (emp_id, name, address, phone, pos_id, level_id, username, password) Values(?,?,?,?,?,?,?,?) ";
                            pre = con.prepareStatement(sql);
                            pre.setString(1, textId.getText().trim());
                            pre.setString(2, textName.getText().trim());
                            pre.setString(3, textAddress.getText().trim());
                            pre.setString(4, textPhone.getText().trim());
                            pre.setInt(5, posID(textPosition.getSelectedItem().toString()));
                            pre.setInt(6, levelID(textLevel.getSelectedItem().toString()));
                            pre.setString(7, textUsername.getText().trim());
                            pre.setString(8, textPassword.getText().trim());
                            pre.executeUpdate();
                            JOptionPane.showMessageDialog
                                    (this, "บันทึกรายการแล้ว", "ผลการบันทึกรายการ", JOptionPane.INFORMATION_MESSAGE);
                            showDataEmployee();
                            textId.setText("");
                            textName.setText("");
                            textAddress.setText("");
                            textPhone.setText("");
                            textPosition.setSelectedIndex(0);
                            textLevel.setSelectedIndex(0);
                            textUsername.setText("");
                            textPassword.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void showDataEmployee() {
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addButton.setEnabled(true);
        textId.setEditable(true);
        textUsername.setEditable(true);
        textPassword.setEditable(true);
        try {
            int totalRow = tableEmployee.getRowCount() - 1;
            while (totalRow > -1) {
                modelEmployee.removeRow(totalRow);
                totalRow--;
            }
            String search = searchEmp.getText().trim();
            String sql = " SELECT emp_id,name,address,phone,pos_th,level_name  FROM employee as e, position as p, position_level as pl " +
                    " WHERE  e.pos_id = p.pos_id AND e.level_id = pl.level_id"
                    + " AND ( emp_id LIKE ('%" + search + "%') "
                    + " OR name LIKE ('%" + search + "%')"
                    + " OR address LIKE ('%" + search + "%')"
                    + " OR phone LIKE ('%" + search + "%')"
                    + " OR pos_th LIKE ('%" + search + "%')"
                    + " OR level_name LIKE ('%" + search + "%') ) ORDER BY emp_id ASC ";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();

            int row = 0;
            while (rs.next()) {
                modelEmployee.addRow(new Object[0]);
                modelEmployee.setValueAt(rs.getString("emp_id"), row, 0);
                modelEmployee.setValueAt(rs.getString("name"), row, 1);
                modelEmployee.setValueAt(rs.getString("address"), row, 2);
                modelEmployee.setValueAt(rs.getString("phone"), row, 3);
                modelEmployee.setValueAt(rs.getString("pos_th"), row, 4);
                modelEmployee.setValueAt(rs.getString("level_name"), row, 5);
                row++;
            }
            tableEmployee.setModel(modelEmployee);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void seachEmpKeyReleased() {
        String searchText = searchEmp.getText().trim();
        try {
            if (!searchText.isEmpty())
                showDataEmployee();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showDataEmployee();
    }

    private void tableMouseClicked() {
        int index = tableEmployee.getSelectedRow();
        textId.setEditable(false);
        textUsername.setEditable(false);
        textPassword.setEditable(false);
        textId.setText(tableEmployee.getValueAt(index, 0).toString());
        textName.setText(tableEmployee.getValueAt(index, 1).toString());
        textAddress.setText(tableEmployee.getValueAt(index, 2).toString());
        textPhone.setText(tableEmployee.getValueAt(index, 3).toString());
        textPosition.setSelectedItem(tableEmployee.getValueAt(index, 4).toString());
        textLevel.setSelectedItem(tableEmployee.getValueAt(index, 5).toString());

        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        addButton.setEnabled(false);
    }

    void showPosition() {
        try {
            String sql = " SELECT pos_th FROM position ORDER BY pos_th ASC ";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                textPosition.addItem(rs.getString("pos_th"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showLevel() {
        try {
            String sql = " SELECT level_name FROM position_level ORDER BY level_name ASC ";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                textLevel.addItem(rs.getString("level_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int posID(String pos) {
        int pos_id = 0;
        pos = textPosition.getSelectedItem().toString();
        switch (pos) {
            case "ผู้จัดการ":
                pos_id = 1;
                break;
            case "พนักงานต้อนรับ":
                pos_id = 2;
                break;
            case "พนักงานแคชเชียร์":
                pos_id = 3;
                break;
            case "พนักงานรับโทรศัพท์":
                pos_id = 4;
                break;
            case "พนักงานฝ่ายบัญชี":
                pos_id = 5;
                break;
            case "พนักงานขายและการตลาด":
                pos_id = 6;
                break;
        }
        return pos_id;
    }

     int levelID(String level) {
        int level_id = 0;
        level = textLevel.getSelectedItem().toString();
        switch (level) {
            case "Expert":
                level_id = 1;
                break;
            case "Senior":
                level_id = 2;
                break;
            case "Junior":
                level_id = 3;
                break;
        }
        return level_id;
    }

    private JPanel home;
    private JTable tableEmployee;
    private JTextField textId;
    private JTextField textName;
    private JTextField textAddress;
    private JTextField textPhone;
    private JComboBox textPosition;
    private JComboBox textLevel;
    private JTextField textUsername;
    private JTextField textPassword;
    private JButton resetButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;
    private JScrollPane scrollbar;
    private JTextField searchEmp;
}
