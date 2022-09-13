import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class HomePage extends JFrame {
    Booking b = new Booking();
    CalendarDateInDateOut c = new CalendarDateInDateOut();
    CalendarSearch s = new CalendarSearch();
    static long daysBetween;
    static String room_id;
    static String dateStart;
    static String dateEnd;

    public static void main(String[] args) throws ParseException {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));
        new HomePage().setVisible(true);
    }

    public HomePage() throws ParseException {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
                UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));
                setTitle("Homepage");
                setSize(1000, 600);
                setContentPane(home);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                panelIn.setLayout(new BoxLayout(panelIn, BoxLayout.PAGE_AXIS));
                panelOut.setLayout(new BoxLayout(panelOut, BoxLayout.PAGE_AXIS));

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
                        System.out.println(dateStart +"\n"+ dateEnd);
                    }
                });
        buttonBooking.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                s.setVisible(true);
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
    }


    MouseListener click(JButton buttonR){
        buttonR.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String click = buttonR.getActionCommand();
                if(click.equals("A01")){
                    room_id = "001";
                } else if (click.equals("A02")){
                    room_id = "002";
                }else if (click.equals("A03")){
                    room_id = "003";
                }else if (click.equals("A04")){
                    room_id = "004";
                }else if (click.equals("B01")){
                    room_id = "005";
                }else if (click.equals("B02")){
                    room_id = "006";
                }else if (click.equals("B03")){
                    room_id = "007";
                }else if (click.equals("B04")){
                    room_id = "008";
                }else if (click.equals("C01")){
                    room_id = "009";
                }else if (click.equals("C02")){
                    room_id = "010";
                }else if (click.equals("C03")){
                    room_id = "011";
                }else if (click.equals("C04")){
                    room_id = "012";
                }else if (click.equals("D01")){
                    room_id = "013";
                }else if (click.equals("D02")){
                    room_id = "014";
                }else if (click.equals("D03")){
                    room_id = "015";
                }else if (click.equals("D04")){
                    room_id = "016";
                } else {
                    room_id = "0";
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
    b.BookingRoom(room_id);
    b.setVisible(true);
}

void CSearch(){
    b.in.setText(s.in);
    b.out.setText(s.out);
    b.day.setText(String.valueOf(s.daysBetween) );
    b.BookingRoom(room_id);
    b.setVisible(true);
}

    private JPanel home;
    private JButton ออกจากระบบButton;
    private JButton buttonBooking;
    private JButton ดูข้อมูลการจองButton;
    private JButton ข้อมูลห้องพักButton;
    private JButton ข้อมูลพนักงานButton;
    private JButton checkOutห้องพักButton;
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
}
