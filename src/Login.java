import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame{

    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private JPanel panelLogin;
    private JTextField userName;
    private JPasswordField password;
    private JButton logIn;
    private JButton reset;
    private JButton register;

    static  String uName;
    public static void main(String[] args) {
        new Login().setVisible(true);
    }
    public Login() {
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
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                register re = new register();
//                re.setVisible(true);
            }
        });
        logIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String u = userName.getText().trim();
                String p = new String(password.getPassword());
                try {
                    String sql = "SELECT username,password,name FROM employee";
                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery(sql);
                    int i = 0;
                    while (rs.next()){
                        if ( rs.getString("username").equals(u) && rs.getString("password").equals(p) ){
                            uName = rs.getString("name");
                            ++i;
//                            JOptionPane.showMessageDialog
//                                    (null, "ยินดีต้อนรับ", "WELCOME", JOptionPane.INFORMATION_MESSAGE);
//                            home.getName(uName);
                            HomePage homePage = new HomePage();
                            homePage.emp_name.setText(uName);
                            CalendarSearch s = new CalendarSearch();
                            s.setVisible(true);
                            setVisible(false);
                            break;
                        }
                    }if (i==0){
                        JOptionPane.showMessageDialog
                                (null, "คุณใส่ Username หรือ Password ไม่ถูกต้อง", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });



    }
}
