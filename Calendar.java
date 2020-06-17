
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
public class Calendar {


    private Date date;
    private JFrame frame;
    private JPanel panel;
    private Graphics graphics;
    private JButton todayMonth;
    private JButton previousMonth, nextMonth;
    private int month;
    private int day;
    private int year;
    private boolean start;


    public Calendar() {

        this.setAllThings();

    }

    public void setDate() {
        if (start) {
            this.date = new Date();
        } else {
            this.date = new Date(year, month, day);
        }
        if (start) {
            month = this.date.getMonth() + 1;
            day = this.date.getDay();
            year = this.date.getYear() + 1900;
        }
        if (start) {
            start = false;
        }
    }

    public void setAllThings() {
        frame = new JFrame();
        panel = new JPanel();
        start = true;
        frame.setTitle("Calendar");
        frame.setLayout(null);
        frame.setBounds(0, 0, 1200, 760);
        panel.setBounds(frame.getBounds());
        frame.add(panel);
        todayMonth = new JButton("Today");
        previousMonth = new JButton("Prev Mn.");
        nextMonth = new JButton("Next Mn.");
        panel.add(todayMonth);
        panel.add(previousMonth);
        panel.add(nextMonth);
        todayMonth.setBounds(11, 50, 100, 20);
        todayMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        graphics.setColor(Color.gray);
                        graphics.fillRect(0, 40, 1200, 760);
                        month = new Date().getMonth() + 1;
                        year = new Date().getYear() + 1900;
                        day = new Date().getDay();
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(new Date(year - 1900, month - 1, 1));
                        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                        YearMonth yearMonthObject = YearMonth.of((year), month);
                        int daysInMonth = yearMonthObject.lengthOfMonth();
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(month + "-" + day + "-" + (year), 100, 100);
                        displayDayz(daysInMonth, dayOfWeek);
                    }
                };
                t.start();

            }
        });
        nextMonth.setBounds(211, 50, 100, 20);
        nextMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        graphics.setColor(Color.gray);
                        graphics.fillRect(0, 40, 1200, 760);
                        setDate();
                        month++;
                        if (month == 13) {
                            year++;
                        }
                        if (month == 13) {
                            month = 1;
                        }
                        setDate();
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(new Date(year - 1900, month - 1, 1));
                        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                        YearMonth yearMonthObject = YearMonth.of((year), month);
                        int daysInMonth = yearMonthObject.lengthOfMonth();
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(month + "-" + day + "-" + (year), 100, 100);
                        displayDayz(daysInMonth, dayOfWeek);
                    }
                };
                t.start();

            }
        });
        previousMonth.setBounds(101, 40, 100, 20);
        previousMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        graphics.setColor(Color.gray);
                        graphics.fillRect(0, 40, 1200, 760);
                        setDate();
                        month--;
                        if (month == 0) {
                            year--;
                        }
                        if (month == 0) {
                            month = 12;
                        }
                        setDate();
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(new Date(year - 1900, month - 1, 1));
                        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                        YearMonth yearMonthObject = YearMonth.of((year), month);
                        int daysInMonth = yearMonthObject.lengthOfMonth();
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(month + "-" + day + "-" + (year), 100, 100);
                        displayDayz(daysInMonth, dayOfWeek);
                    }
                };
                t.start();

            }
        });
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphics = panel.getGraphics();
        graphics.setFont(new Font("tAHOMA", Font.TRUETYPE_FONT, 21));
        Thread t = new Thread() {
            public void run() {
                previousMonth.doClick();
                try {

                    Thread.sleep(1000);

                } catch (InterruptedException ie) {

                    ie.printStackTrace();

                }
                nextMonth.doClick();
            }
        };
        t.start();
    }

    public void displayDayz(final int daysInMonth, final int dayOfWeek) {
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                graphics.setColor(Color.BLUE);
                while (true) {
                    try {

                        Thread. sleep( 100 );

                    } catch (InterruptedException ie) {

                        ie.printStackTrace();

                    }
                    graphics.setColor(Color.orange);
                    graphics.drawString("SUN         MON         TUES      WED      THUR        FRI        SAT",100,145);
                    int x=0;
                    int y=0;
                    for (int i = 0; i < daysInMonth; i++) {
                        x=(((dayOfWeek-1)+i)%7)*(80)+20;
                        if (((dayOfWeek-1)+i)%7 == 0) {
                            y+=70;
                            x=0;
                        }
                        if (i == day-1 && month == new Date().getMonth()+1 && year == new Date().getYear()+1900) {
                            graphics.setColor(Color.RED);
                            graphics.drawOval(100+x-30,y+200-42,70,70);
                        }
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(String.valueOf(i+1),100+x,y+200);
                    }
                    return;
                }
            }
        });
        tr.start();
    }

    public static void main(String args[]) {
        Calendar c =        new Calendar();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
}
