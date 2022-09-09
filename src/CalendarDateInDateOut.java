import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CalendarDateInDateOut {
    String dIn = new String();
    String dOut = new String();

    SimpleDateFormat pattern = new SimpleDateFormat("dd-MM-yyyy");
    public JDateChooser CalendarIn () throws ParseException {
        Locale locale = new Locale("th", "TH");
        ThaiBuddhistDate thai = ThaiBuddhistDate.now(ZoneId.systemDefault());

        int year = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("yyyy")));
        int month = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("MM")));
        int day = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("dd")));

        java.util.Calendar minC = java.util.Calendar.getInstance();
        Date minCa = minC.getTime();

        java.util.Calendar maxx = java.util.Calendar.getInstance();
        maxx.set(year, month + 2, day);
        Date maxCx = maxx.getTime();

        JDateChooser calendarIn = new JDateChooser();
        calendarIn.setDate(new Date());
        calendarIn.getJCalendar().setSelectableDateRange(minCa, maxCx);
        calendarIn.setLocale(locale);
        calendarIn.setFont(new Font("Leelawadee", Font.PLAIN, 12));

        return calendarIn;
    }

    public JDateChooser CalendarOut() throws ParseException {
        Locale locale = new Locale("th", "TH");
        ThaiBuddhistDate thai = ThaiBuddhistDate.now(ZoneId.systemDefault());

        int year = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("yyyy")));
        int month = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("MM")));
        int day = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("dd")));

        java.util.Calendar minC = java.util.Calendar.getInstance();
        Date minCa = minC.getTime();

        java.util.Calendar maxC = java.util.Calendar.getInstance();
        maxC.set(year, month + 11, day);
        Date maxCa = maxC.getTime();

        JDateChooser calendarOut = new JDateChooser();
        calendarOut.setDate(new Date());
        calendarOut.getJCalendar().setSelectableDateRange(minCa, maxCa);
        calendarOut.setLocale(locale);
        calendarOut.setFont(new Font("Leelawadee", Font.PLAIN, 12));

        return calendarOut;
    }

    public JDateChooser CalendarIn (String s) throws ParseException {

        Locale locale = new Locale("th", "TH");
        ThaiBuddhistDate thai = ThaiBuddhistDate.now(ZoneId.systemDefault());

        int year = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("yyyy")));
        int month = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("MM")));
        int day = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("dd")));

        java.util.Calendar minC = java.util.Calendar.getInstance();
        Date minCa = minC.getTime();

        java.util.Calendar maxx = java.util.Calendar.getInstance();
        maxx.set(year, month + 2, day);
        Date maxCa = maxx.getTime();

        JDateChooser calendarDateIn = new JDateChooser();
        SimpleDateFormat pattern = new SimpleDateFormat("dd-MM-yyyy");
        Date d = pattern.parse(s);
        calendarDateIn.setDate(d);
        calendarDateIn.getJCalendar().setSelectableDateRange(minCa, maxCa);
        calendarDateIn.setLocale(locale);
        calendarDateIn.setFont(new Font("Leelawadee", Font.PLAIN, 12));

        return calendarDateIn;
    }


    public JDateChooser CalendarOut (String s) throws ParseException {

        Locale locale = new Locale("th", "TH");
        ThaiBuddhistDate thai = ThaiBuddhistDate.now(ZoneId.systemDefault());

        int year = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("yyyy")));
        int month = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("MM")));
        int day = Integer.parseInt(thai.format(DateTimeFormatter.ofPattern("dd")));

        java.util.Calendar minC = java.util.Calendar.getInstance();
        Date minCa = minC.getTime();

        java.util.Calendar maxC = java.util.Calendar.getInstance();
        maxC.set(year, month + 11, day);
        Date maxCa = maxC.getTime();

        JDateChooser calendarDateOut = new JDateChooser();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d = sdf.parse(s);
        calendarDateOut.setDate(d);
        calendarDateOut.getJCalendar().setSelectableDateRange(minCa, maxCa);
        calendarDateOut.setLocale(locale);
        calendarDateOut.setFont(new Font("Leelawadee", Font.PLAIN, 12));

        return calendarDateOut;
    }
    public String stringCal(JDateChooser jd){
        SimpleDateFormat pattern = new SimpleDateFormat("dd-MM-yyyy");
        String s = pattern.format(jd.getDate());
        return s;
    }

    public String getDateIn() {
        return dIn;
    }
    public String getDateOut() {
        return dOut;
    }
    public void setDateIn(String newdIn) {
        this.dIn = newdIn;
    }
    public void setDateOut(String newdOut) {
        this.dOut = newdOut;
    }



}
