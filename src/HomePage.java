import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class HomePage extends JFrame {
    Login l = new Login();
    Booking b = new Booking();
    CalendarDateInDateOut c = new CalendarDateInDateOut();
    CalendarSearch s = new CalendarSearch();
    private Connection con = Connect.ConnectDB();
    PreparedStatement pre = null;
    ResultSet rs = null;
    static long daysBetween;
    static int room_id;
    static String dateStart;
    static String dateEnd;
    static  JDesktopPane desktop;
    static  JLayeredPane layeredPane;


    public static void main(String[] args) throws ParseException {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));
        new HomePage().setVisible(true);
    }

    public HomePage() throws ParseException {
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
//                         UnsupportedLookAndFeelException ex) {
//                    ex.printStackTrace();
//                }
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));

        setTitle("Homepage");
        setSize(1000,600);
        setContentPane(home);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktop = new JDesktopPane();
        layeredPane = getLayeredPane();
        layeredPane.add(desktop, new Integer(1));
        layeredPane.add(home, new Integer(2));

        panelIn.setLayout(new BoxLayout(panelIn, BoxLayout.PAGE_AXIS));
        panelOut.setLayout(new BoxLayout(panelOut, BoxLayout.PAGE_AXIS));


//        if (l.level.equals("junior") || l.level.equals("senior")){
//            roomsButton.setEnabled(false);
//            employeeButton.setEnabled(false);
//        }
        bookingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                s.setVisible(true);
            }
        });


        buttonSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buttonSearch.setSelected(true);
                dateStart = c.pattern.format(s.jdIn.getDate());
                dateEnd = c.pattern.format(s.jdOut.getDate());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date1 = LocalDate.parse(dateStart, df);
                LocalDate date2 = LocalDate.parse(dateEnd, df);
                daysBetween = ChronoUnit.DAYS.between(date1, date2);
                changeStatusRoom();
            }
        });

        A01.addMouseListener(click(A01));
        A02.addMouseListener(click(A02));
        A03.addMouseListener(click(A03));
        A04.addMouseListener(click(A04));
        B01.addMouseListener(click(B01));
        B02.addMouseListener(click(B02));
        B03.addMouseListener(click(B03));
        B04.addMouseListener(click(B04));
        C01.addMouseListener(click(C01));
        C02.addMouseListener(click(C02));
        C03.addMouseListener(click(C03));
        C04.addMouseListener(click(C04));
        D01.addMouseListener(click(D01));
        D02.addMouseListener(click(D02));
        D03.addMouseListener(click(D03));
        D04.addMouseListener(click(D04));
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowOpened(WindowEvent e) {
//                changeStatusRoom();
//            }
//        });
//        changeStatusRoom();


        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog
                        (null,"คุณต้องการออกจากระบบ ?","Close",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION){
                System.exit(0);
                }
            }
        });
        employeeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Employee emp = new Employee();
                emp.setVisible(true);
                layeredPane.add(emp, new Integer(3));
            }
        });
    }



    void roomAvailable(JButton button){
        button.setBackground(Color.GREEN);
    }
    void roomUnavailable(JButton button){
            button.setBackground(Color.GRAY);
    }
    void roomRepair(JButton button){
            button.setBackground(Color.RED);
    }

    MouseListener click(JButton buttonR){
        buttonR.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String click = buttonR.getActionCommand();

                if(buttonR.getBackground().equals(Color.GRAY)  || buttonR.getBackground().equals(Color.RED) ){
                    b.setVisible(false);
                } else {
                    b.setVisible(true);
                }

                if(click.equals("A01")){                    room_id = 1;
                } else if (click.equals("A02")){                    room_id = 2;
                }else if (click.equals("A03")){                    room_id = 3;
                }else if (click.equals("A04")){                    room_id = 4;
                }else if (click.equals("B01")){                    room_id = 5;
                }else if (click.equals("B02")){                    room_id = 6;
                }else if (click.equals("B03")){                    room_id = 7;
                }else if (click.equals("B04")){                    room_id = 8;
                }else if (click.equals("C01")){                    room_id = 9;
                }else if (click.equals("C02")){                    room_id = 10;
                }else if (click.equals("C03")){                    room_id = 11;
                }else if (click.equals("C04")){                    room_id = 12;
                }else if (click.equals("D01")){                    room_id = 13;
                }else if (click.equals("D02")){                    room_id = 14;
                }else if (click.equals("D03")){                    room_id = 15;
                }else if (click.equals("D04")){                    room_id = 16;
                } else {                    room_id = 0;
                }
                if( buttonSearch.isSelected()){
                    MSearch();
                }else {
                    CSearch();
                }
            }
        });
        return null;
    }

void MSearch(){
    b.in.setText(dateStart);
    b.out.setText(dateEnd);
    b.day.setText(String.valueOf(daysBetween));
    b.bookingRoom(room_id);
}

void CSearch(){
    b.in.setText(s.in);
    b.out.setText(s.out);
    b.day.setText(String.valueOf(s.daysBetween) );
    b.bookingRoom(room_id);
}
    void changeColorRoom(){

        try {
            String sql = "SELECT room_number FROM minihotel.room as r,minihotel.room_status as s where r.status_id = s.status_id AND r.status_id = 1";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                String r = rs.getString("room_number");
                if (A01.getText().equals(r)){                    roomAvailable(A01);
                } else if (A02.getText().equals(r)) {                    roomAvailable(A02);
                } else if (A03.getText().equals(r)) {                    roomAvailable(A03);
                } else if (A04.getText().equals(r)) {                    roomAvailable(A04);
                } else if (B01.getText().equals(r)) {                    roomAvailable(B01);
                } else if (B02.getText().equals(r)) {                    roomAvailable(B02);
                } else if (B03.getText().equals(r)) {                    roomAvailable(B03);
                } else if (B04.getText().equals(r)) {                    roomAvailable(B04);
                } else if (C01.getText().equals(r)) {                    roomAvailable(C01);
                } else if (C02.getText().equals(r)) {                    roomAvailable(C02);
                } else if (C03.getText().equals(r)) {                    roomAvailable(C03);
                } else if (C04.getText().equals(r)) {                    roomAvailable(C04);
                } else if (D01.getText().equals(r)) {                    roomAvailable(D01);
                } else if (D02.getText().equals(r)) {                    roomAvailable(D02);
                } else if (D03.getText().equals(r)) {                    roomAvailable(D03);
                } else if (D04.getText().equals(r)) {                    roomAvailable(D04);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            String sql = "SELECT room_number FROM minihotel.room as r,minihotel.room_status as s where r.status_id = s.status_id AND r.status_id = 2";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                String r = rs.getString("room_number");
                if (A01.getText().equals(r)){                    roomUnavailable(A01);
                } else if (A02.getText().equals(r)) {                    roomUnavailable(A02);
                } else if (A03.getText().equals(r)) {                    roomUnavailable(A03);
                } else if (A04.getText().equals(r)) {                    roomUnavailable(A04);
                } else if (B01.getText().equals(r)) {                    roomUnavailable(B01);
                } else if (B02.getText().equals(r)) {                    roomUnavailable(B02);
                } else if (B03.getText().equals(r)) {                    roomUnavailable(B03);
                } else if (B04.getText().equals(r)) {                    roomUnavailable(B04);
                } else if (C01.getText().equals(r)) {                    roomUnavailable(C01);
                } else if (C02.getText().equals(r)) {                    roomUnavailable(C02);
                } else if (C03.getText().equals(r)) {                    roomUnavailable(C03);
                } else if (C04.getText().equals(r)) {                    roomUnavailable(C04);
                } else if (D01.getText().equals(r)) {                    roomUnavailable(D01);
                } else if (D02.getText().equals(r)) {                    roomUnavailable(D02);
                } else if (D03.getText().equals(r)) {                    roomUnavailable(D03);
                } else if (D04.getText().equals(r)) {                    roomUnavailable(D04);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            String sql = "SELECT room_number FROM minihotel.room as r,minihotel.room_status as s where r.status_id = s.status_id AND r.status_id = 3";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                String r = rs.getString("room_number");
                if (A01.getText().equals(r)){                    roomRepair(A01);
                } else if (A02.getText().equals(r)) {                    roomRepair(A02);
                } else if (A03.getText().equals(r)) {                    roomRepair(A03);
                } else if (A04.getText().equals(r)) {                    roomRepair(A04);
                } else if (B01.getText().equals(r)) {                    roomRepair(B01);
                } else if (B02.getText().equals(r)) {                    roomRepair(B02);
                } else if (B03.getText().equals(r)) {                    roomRepair(B03);
                } else if (B04.getText().equals(r)) {                    roomRepair(B04);
                } else if (C01.getText().equals(r)) {                    roomRepair(C01);
                } else if (C02.getText().equals(r)) {                    roomRepair(C02);
                } else if (C03.getText().equals(r)) {                    roomRepair(C03);
                } else if (C04.getText().equals(r)) {                    roomRepair(C04);
                } else if (D01.getText().equals(r)) {                    roomRepair(D01);
                } else if (D02.getText().equals(r)) {                    roomRepair(D02);
                } else if (D03.getText().equals(r)) {                    roomRepair(D03);
                } else if (D04.getText().equals(r)) {                    roomRepair(D04);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void changeStatusRoom(){
        ArrayList<Integer> roomUna = new ArrayList<Integer>();
        try {
            String sql = "SELECT booking_no, r.room_id, check_in, check_out FROM booking as b, room as r " +
                    "where  b.room_id = r.room_id "; //AND booking_status_id Not in (3,4)
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            String i, o;
            int room_id;
            String s1 = c.patternSQL.format(s.jdIn.getDate());
            String s2 = c.patternSQL.format(s.jdOut.getDate());
//            JDateChooser n = c.DatenowSQL();
//            String sNow = c.patternSQL.format(n.getDate());
                LocalDate start = LocalDate.parse(s1);
                LocalDate end = LocalDate.parse(s2);
            while (rs.next()) {
                room_id = rs.getInt("room_id");
                i = rs.getString("check_in");
                o = rs.getString("check_out");
//                LocalDate now = LocalDate.parse(sNow);
                LocalDate check_in = LocalDate.parse(i);
                LocalDate check_out = LocalDate.parse(o);

                if (start.isBefore(check_out) && end.isAfter(check_in) ) { //เช็คห้องไม่ว่าง
                    roomUna.add(room_id);
                } else {
                    try {
                        String un = "UPDATE room SET status_id = 1 WHERE room_id = ? ";
                        pre = con.prepareStatement(un);
                        pre.setInt(1, room_id);
                        pre.executeUpdate();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            try {
                String una = "UPDATE room SET status_id = 2 WHERE room_id = ? ";
                pre = con.prepareStatement(una);
                    for (int r = 0; r < roomUna.size(); r++) {
                        pre.setInt(1, roomUna.get(r));
                        pre.executeUpdate();
//                        if (pre.executeUpdate() != -1) {
//                        System.out.println(roomUna.get(r));
//                        }
            }
            } catch (Exception ex) {
                ex.printStackTrace();
        }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            String sql = "SELECT COUNT(room_id) FROM room";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                rooms.setText(rs.getString("COUNT(room_id)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String sql = "SELECT COUNT(room_id) FROM minihotel.room where status_id = 1";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                available.setText(rs.getString("COUNT(room_id)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String sql = "SELECT COUNT(room_id) FROM minihotel.room where status_id = 2";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                unavailable.setText(rs.getString("COUNT(room_id)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String sql = "SELECT COUNT(room_id) FROM minihotel.room where status_id = 3";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                repair.setText(rs.getString("COUNT(room_id)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

                changeColorRoom();
    }
    private JPanel home;
    private JButton closeButton;
    private JButton bookingButton;
    private JButton ดูข้อมูลการจองButton;
    private JButton roomsButton;
    private JButton employeeButton;
    private JButton checkOutButton;
    private JButton A01;
    private JButton A02;
    private JButton A03;
    private JButton A04;
    private JButton buttonSearch;
    private JTextField tex;

    public JPanel panelIn;
    public JPanel panelOut;
    private JButton B01;
    private JButton B02;
    private JButton B03;
    private JButton B04;
    private JButton C01;
    private JButton C03;
    private JButton C04;
    private JButton D01;
    private JButton D02;
    private JButton D04;
    private JButton D03;
    private JButton C02;
    JLabel emp_name;
    private JLabel rooms;
    private JLabel unavailable;
    private JLabel available;
    private JLabel repair;


}
