import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;

public class PersonalDetails extends JFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private JPanel home;
    private JTextField textName;
    private JTextField textAddress;
    private JTextField textPhone;
    private JTextField textPosition;
    private JTextField textLevel;
    private JTextField textUsername;
    private JTextField textPassword;
    private JButton cancelButton;
    private JButton chengeButton;
    private JTextField textId;
    private JLabel photo;
    private JButton choooseFileButton;
    BufferedImage newImg;
    ImageIcon imgEmployee;
    Employee emp = new Employee();
    Login l = new Login();
    PersonalDetails() throws ParseException {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("ข้อมูลส่วนตัว");
        setSize(500, 550);
        setContentPane(home);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        textId.setEditable(false);
        textPosition.setEditable(false);
        textLevel.setEditable(false);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                showPersonalDetails();
            }
        });

        chengeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                edit();
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });
        choooseFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setChoooseFileButton();
            }

        });
    }

    private void setChoooseFileButton(){
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setFont(new Font("Leelawadee", Font.PLAIN, 12));
        FileNameExtensionFilter filterImage = new FileNameExtensionFilter ("Image File", "png", "jpg");
        fileOpen.addChoosableFileFilter(filterImage);
        fileOpen.setAcceptAllFileFilterUsed ( false );
        int ret = fileOpen.showDialog(null, "Choose File");
        if ( ret == JFileChooser.APPROVE_OPTION ){
            String file = fileOpen.getSelectedFile().toString();
            Path source = Paths.get(file);
            try {
                newImg = emp.resizeImage(source, 150, 150);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            imgEmployee = new ImageIcon(newImg);
            photo.setText("");
            photo.setIcon(imgEmployee);
        }
    }

    private void showPersonalDetails(){
        try {
            String sql = " SELECT emp_id,name,address,phone,pos_th,level_name,username,password  FROM employee as e, position as p, position_level as pl "
                    + "  WHERE  e.pos_id = p.pos_id AND e.level_id = pl.level_id AND emp_id = " + l.id;
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();

            while (rs.next()) {

                textId.setText(rs.getString("emp_id"));
                textName.setText(rs.getString("name"));
                textAddress.setText(rs.getString("address"));
                textPhone.setText(rs.getString("phone"));
                textPosition.setText(rs.getString("pos_th"));
                textLevel.setText(rs.getString("level_name"));
                textUsername.setText(rs.getString("username"));
                textPassword.setText(rs.getString("password"));
            }
            imgEmployee = new ImageIcon(l.stringImg);
            photo.setText("");
            photo.setIcon(imgEmployee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void edit() {
        if (JOptionPane.showConfirmDialog(null, "คุณต้องการแก้ไขข้อมูล ?", "ยืนยันการแก้ไขข้อมูล", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {

            if (textName.getText().trim().isEmpty() || textAddress.getText().trim().isEmpty() || textPhone.getText().trim().isEmpty() ||
                    textUsername.getText().trim().isEmpty() || textPassword.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog
                        (this, "กรุณาใส่ข้อมูลให้ครบ ห้ามเว้นว่าง", "ผลการบันทึกรายการ", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    String sql = "update employee set "
                            + " name = ? ,"
                            + " address = ? ,"
                            + " phone = ? ,"
                            + " username = ? ,"
                            + " password = ? "
                            + " WHERE emp_id = ? ";

                    pre = con.prepareStatement(sql);
                    pre.setString(1, textName.getText().trim());
                    pre.setString(2, textAddress.getText().trim());
                    pre.setString(3, textPhone.getText().trim());
                    pre.setString(4, textUsername.getText().trim());
                    pre.setString(5, textPassword.getText().trim());
                    pre.setString(6, textId.getText().trim());
                    if( newImg != null){
                        File f = new File("imageEmployee\\" + textId.getText() + ".png");
                        try {
                            ImageIO.write(newImg, "png", f);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (pre.executeUpdate() != -1) {
                        JOptionPane.showMessageDialog(this, "แก้ไขข้อมูลเรียบร้อยแล้ว");
                        setVisible(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

        }

