import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Calendar {

    private Date date = null;
    
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private Graphics graphics;
    private JButton previousMonth, nextMonth;

    private int month;
    private int day;
    private int year;
    boolean start = true;
    
    public Calendar() {
        
        previousMonth = new JButton("Prev Mn.");
        nextMonth =   new JButton("Next Mn.");
        
        frame.setTitle("Calendar");
        frame.setLayout(null);
        frame.setBounds(0, 0, 1200, 760);
        panel.setBounds(frame.getBounds());
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(previousMonth); panel.add(nextMonth);
        nextMonth.setBounds(211, 50, 100, 20);
        nextMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDate();
                month++;
                if(month == 13)year++;
                if(month == 13)month = 1;
                setDate();
                graphics.setColor(Color.red);
                graphics.fillRect(0, 0, 1200, 750);
                panel.updateUI();
                display_date();
                YearMonth yearMonthObject = YearMonth.of((year+1900), month);
                int daysInMonth = yearMonthObject.lengthOfMonth();
                displayDayz(daysInMonth);
            }
        });
        previousMonth.setBounds(101, 40, 100, 20);
        previousMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDate();
                month--;
                if(month == 0) year--;
                if(month == 0) month = 12;
                setDate();
                graphics.setColor(Color.gray);
                graphics.fillRect(0, 0, 1200, 750);
                panel.updateUI();
                display_date();
                YearMonth yearMonthObject = YearMonth.of((year+1900), month);
                int daysInMonth = yearMonthObject.lengthOfMonth();
                displayDayz(daysInMonth);
            }
        });
        frame.setVisible(true);
        
        
        graphics = panel.getGraphics();
        
        setDate();
        YearMonth yearMonthObject = YearMonth.of((this.date.getYear()+1900),this.date.getMonth());
        int daysInMonth = yearMonthObject.lengthOfMonth();
        show(daysInMonth);
    }
    
    public void setDate() {
        if(start)
            this.  date = new Date();
        else
            this. date = new Date(year, month, day);
        if( start) {
            month = this.date.getMonth();
            day = this.date.getDay();
            year = this.date.getYear();
        }
        if  (start) start= false  ;
    }
    
    private void display_date() {
        Thread myt = new Thread() {
            public void run() {
                panel.setBackground(Color.cyan);
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("arial", Font.BOLD, 27));
                graphics.drawString(date.getMonth() + "-" + date.getDate() + "-" + (date.getYear()+1900), 100, 100);
            }
        };
        
        myt.start();
    }
    
    public void show(int daysInMonth) {
        display_date();
        
        displayDayz(daysInMonth);
    }
    
    public void displayDayz(int daysInMonth) {
        Thread obm = new Thread(new Runnable() {
            @Override
            public void run() {
                int x;int y;int md =0;
                x = 0;y =0;
                for(int i=0; i<daysInMonth; i++) {
                    x = (i%7)*(45) + 13;
                    if(i % 7 == 0) {
                        y += 35;
                        x = 0;
                    }
                    graphics.setColor(Color.BLUE);
                    graphics.drawString("" + (i +1), 100+x, y+200);
                }
            }
        });
        
        obm.start();
    }
    
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Calendar();
            }
        });
    }
}