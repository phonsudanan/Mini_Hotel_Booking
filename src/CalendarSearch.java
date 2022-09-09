import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CalendarSearch extends JFrame {
    Booking b = new Booking();
    static CalendarDateInDateOut c = new CalendarDateInDateOut();
    JDateChooser d1  = c.CalendarIn();
    JDateChooser d2  = c.CalendarOut();
    static  String in;
    static String out;
    static JDateChooser jdIn;
    static JDateChooser jdOut;
    static long daysBetween;
    public static void main(String[] args) throws ParseException {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));
        new CalendarSearch().setVisible(true);
    }

    public CalendarSearch() throws ParseException {
        setTitle("เลือกวันที่เข้าพัก");
        setSize(600, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(home);

        panelIn.setLayout(new BoxLayout(panelIn, BoxLayout.PAGE_AXIS));
        panelIn.add(d1, new GridBagLayout());
        panelOut.setLayout(new BoxLayout(panelOut, BoxLayout.PAGE_AXIS));
        panelOut.add(d2, new GridBagLayout());

        bookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                 in = c.pattern.format(d1.getDate());
                 out = c.pattern.format(d2.getDate());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date1 = LocalDate.parse(in, df);
                LocalDate date2 = LocalDate.parse(out, df);
                daysBetween = ChronoUnit.DAYS.between(date1, date2);
                System.out.println(in +"\n"+ out);

                HomePage homePage = null;
                try {
                    homePage = new HomePage();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                      jdIn =c.CalendarIn(in);
                      jdOut = c.CalendarOut(out);
                    homePage.panelIn.add(jdIn, new GridBagLayout());
                    homePage.panelOut.add(jdOut, new GridBagLayout());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                homePage.setVisible(true);
                setVisible(false);
            }
        });
    }

    private JButton bookButton;
    JPanel panelOut;
    public JPanel panelIn;
    private JPanel home;


}
