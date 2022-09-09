import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class Test  {
    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        public TestPane() {
            UtilDateModel model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            setLayout(new GridBagLayout());
            add(datePicker);
        }

    }



}







//            LocalDateTime date = LocalDateTime.now();
//
//            CalendarSearch maxC = CalendarSearch.getInstance();
//            maxC.set(date.getYear(),date.getMonthValue()+3, date.getDayOfMonth());
//            Date maxD = maxC.getTime();
//
//            CalendarSearch minC = CalendarSearch.getInstance();
//            minC.set(date.getYear(),date.getMonthValue(), date.getDayOfMonth());
//            Date minD = minC.getTime();
//
//            JCalendar calendar = new JCalendar();
//            calendar.setSize(100,100);
//            calendar.setLocation(200,200);
//            calendar.setDate(new Date());
//            calendar.setMaxSelectableDate(maxD);
//            calendar.setMinSelectableDate(minD);
//            f.add(calendar);
//            System.out.println(date.getYear()+""+date.getMonthValue()+""+ date.getDayOfMonth());
//            System.out.println(minD);
//            System.out.println(maxD);