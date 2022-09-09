import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

public class HomePage extends JFrame {
    Booking b = new Booking();
    CalendarDateInDateOut c = new CalendarDateInDateOut();
    CalendarSearch s = new CalendarSearch();

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
                        String in = c.pattern.format(s.jdIn.getDate());
                        String out = c.pattern.format(s.jdOut.getDate());
                        System.out.println(in +"\n"+ out);
                    }
                });

                A01.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int n = 001;
                        b.in.setText(s.in);
                        b.out.setText(s.out);
                        b.day.setText(String.valueOf(s.daysBetween) + "\t วัน");
                        b.BookingRoom(n);
                        b.setVisible(true);
                    }
                });

                buttonBooking.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        s.setVisible(true);
                    }
                });
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
}
