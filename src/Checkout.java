import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;

public class Checkout extends JInternalFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private JPanel home;
    private JPanel room;
    private JPanel test;
//    HomePage h = new HomePage();
    CheckoutDetails checkoutDetails = new CheckoutDetails();
    Checkout() throws ParseException {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("Check-out ห้องพัก");
        setSize(900, 500);
        setContentPane(home);
        setClosable(true);
        setMaximizable(true);

        room.setLayout(new GridLayout(0,4,20,10));  //row =0 มีแถวไม่จำกัด

        checkoutRoom();




    }

    private void checkoutRoom(){
        try {
            String sql = " SELECT room_number FROM room as r, booking as b, booking_status as bs "
            + " WHERE b.room_id = r.room_id AND b.booking_status_id = bs.booking_status_id AND b.booking_status_id = 2 ";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()){
                JButton button = new JButton();
                button.setText(rs.getString("room_number"));
                room.add(button);
                click(button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    MouseListener click(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String clickButton = button.getActionCommand();
                System.out.println(clickButton);
                roomId(clickButton);
                System.out.println(roomId(clickButton));
                checkoutDetails.bookingRoom(roomId(clickButton));

                checkoutDetails.setVisible(true);



            }

        });
        return null;
    }

    int roomId(String r){
        int room_id = 0;
        if(r.equals("A01")){                    room_id = 1;
        } else if (r.equals("A02")){                    room_id = 2;
        }else if (r.equals("A03")){                    room_id = 3;
        }else if (r.equals("A04")){                    room_id = 4;
        }else if (r.equals("B01")){                    room_id = 5;
        }else if (r.equals("B02")){                    room_id = 6;
        }else if (r.equals("B03")){                    room_id = 7;
        }else if (r.equals("B04")){                    room_id = 8;
        }else if (r.equals("C01")){                    room_id = 9;
        }else if (r.equals("C02")){                    room_id = 10;
        }else if (r.equals("C03")){                    room_id = 11;
        }else if (r.equals("C04")){                    room_id = 12;
        }else if (r.equals("D01")){                    room_id = 13;
        }else if (r.equals("D02")){                    room_id = 14;
        }else if (r.equals("D03")){                    room_id = 15;
        }else if (r.equals("D04")){                    room_id = 16;
        } else {                    room_id = 0;
        }
        return room_id;
    }





}
