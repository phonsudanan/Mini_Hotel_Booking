import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;

public class Login extends JFrame{

    private Connection con = Connect.ConnectDB();

    private JPanel panelLogin;
    private JTextField userName;
    private JPasswordField password;
    private JButton logIn;
    private JButton reset;

    public  static  String id,uName;
    public  static  int level;
    public static void main(String[] args) throws ParseException {
        new Login().setVisible(true);
    }
    public Login() throws ParseException {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("เข้าสู่ระบบ");
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelLogin);
        setLocationRelativeTo(null);

        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userName.setText("");
                password.setText("");
            }
        });
        logIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String u = userName.getText().trim();
                String p = new String(password.getPassword());
                try {
                    String sql = "SELECT * FROM employee";
                    PreparedStatement pre = con.prepareStatement(sql);
                    ResultSet rs = pre.executeQuery(sql);
                    while (rs.next()){
                        if ( rs.getString("username").equals(u) && rs.getString("password").equals(p) ){
                             id = rs.getString("emp_id");
                            uName = rs.getString("name");
                            level = rs.getInt("level_id");
//                            System.out.println(id +"\n"+ level);
                            CalendarSearch s = new CalendarSearch();
                            s.setVisible(true);
                            setVisible(false);
                            break;
                        }
                    }
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog
                                (null, "คุณใส่ Username หรือ Password ไม่ถูกต้อง", "ERROR", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });



    }
}
