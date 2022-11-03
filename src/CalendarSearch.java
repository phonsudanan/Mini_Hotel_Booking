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

    Login l = new Login();
    static CalendarDateInDateOut c = new CalendarDateInDateOut();
    static JDateChooser dateChooserStart =  c.calendarIn();
    static JDateChooser dateChooserEnd = c.calendarOut();
    static  String dateStart;
    static String dateEnd;
    static long daysBetween;
//    public static void main(String[] args) throws ParseException {
//        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
//        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));
//        new CalendarSearch().setVisible(true);
//    }

    public CalendarSearch() throws ParseException {
        setTitle("เลือกวันที่เข้าพัก");
        setSize(600, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(home);

        panelIn.setLayout(new BoxLayout(panelIn, BoxLayout.PAGE_AXIS));
        panelIn.add(dateChooserStart, new GridBagLayout());
        panelOut.setLayout(new BoxLayout(panelOut, BoxLayout.PAGE_AXIS));
        panelOut.add(dateChooserEnd, new GridBagLayout());

        bookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setBookButton();
            }
        });
    }

    void setBookButton(){
        HomePage homePage = null;
        dateStart = c.pattern.format(dateChooserStart.getDate());
        dateEnd = c.pattern.format(dateChooserEnd.getDate());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1 = LocalDate.parse(dateStart, df);
        LocalDate date2 = LocalDate.parse(dateEnd, df);
        daysBetween = ChronoUnit.DAYS.between(date1, date2);
//        System.out.println(dateStart +"\n"+ dateEnd);

        try {
            homePage = new HomePage();
            homePage.emp_name.setText(l.uName);
            homePage.changeStatusRoom();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        homePage.setVisible(true);
        setVisible(false);
    }

    private JButton bookButton;
    JPanel panelOut;
    JPanel panelIn;
    private JPanel home;


}
