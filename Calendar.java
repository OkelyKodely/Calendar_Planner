import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Calendar implements MouseListener {

    private boolean pleaseInsertMe;
    private Date date;
    private JFrame frame;
    private JPanel panel;
    private JFrame plan;
    private JPanel plan2;
    private Graphics graphics;
    private JButton todayMonth;
    private JButton previousMonth, nextMonth;
    private int month;
    private int day;
    private int year;
    private boolean start;
    private Connection connection = null;
    private Statement statement  = null;

    public Calendar() {

        this.createConnect();
        
        this.setAllThings();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date(year - 1900, month - 1, 1));
        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
        YearMonth yearMonthObject = YearMonth.of((year), month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        int x=0;
        int y=0;
        for (int i = 0; i < daysInMonth; i++) {
            x=(((dayOfWeek-1)+i)%7)*(80)+20;
            if (((dayOfWeek-1)+i)%7 == 0) {
                y+=70;
                x=0;
            }
            
            int thex = e.getX(); int they = e.getY() + 70;
            
            if(thex >= x+100 && thex <= x+100 + 80 && they >= y+200 && they <= y+200 + 80) {
                plan = new JFrame();
                plan.setLayout(null);
                plan2= new JPanel();
                plan2.setLayout(null);
                plan. setBounds(0, 0, 500, 800);
                plan2.setBounds(plan.getBounds());
                
                plan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                plan.setVisible(true);
                
                JLabel l = new JLabel("Date");
                l.setBounds(10, 10, 100, 20);
                plan.add(plan2);
                
                plan2.add(l);
                JLabel l2 = new JLabel();
                l2.setBounds(120, 10, 200, 20);
                plan2.add(l2);
                l2.setText(month + "/" + (i+1) + "/" + year);
                
                JTextArea jta = new JTextArea();
                jta.setBounds(10, 100, 500, 400);
                plan2.add(jta);
                JButton b = new JButton("Update");
                b.setBounds(10, 650, 200, 40);
                plan2.add(b);
                
                final int thei = i+1;
                
                try {
                    
                    statement = connection.createStatement();

                    String sqlString = "select * from calendar where thedate = '"+month + "/" + thei + "/" + year+"';";

                    ResultSet rs = statement.executeQuery(sqlString);
                    
                    if(rs.next()) {
                        jta.setText(rs.getString("thecontent"));
                        pleaseInsertMe = false;
                    } else {
                        pleaseInsertMe = true;
                    }
                
                } catch(SQLException sqle) {
                    sqle.printStackTrace();
                }

                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            
                            statement = connection.createStatement();
                            
                            String sqlString = "update calendar set thecontent = '"+jta.getText()+"' where thedate = '"+month + "/" + thei + "/" + year+"';";
                            
                            if(pleaseInsertMe) {
                                sqlString = "insert into calendar (thedate, thecontent) values ('"+month + "/" + thei + "/" + year+"','"+jta.getText()+"');";
                            }
                            
                            statement.execute(sqlString);
                            
                            java.util.Calendar c = java.util.Calendar.getInstance();
                            c.setTime(new Date(year - 1900, month - 1, 1));
                            int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                            YearMonth yearMonthObject = YearMonth.of((year), month);
                            int daysInMonth = yearMonthObject.lengthOfMonth();
                            displayDayz(daysInMonth, dayOfWeek);

                        } catch(SQLException sqle) {
                            sqle.printStackTrace();
                        }
                        
                        plan.dispose();
                    }
                });
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void setDate() {
        if (start) {
            this.date = new Date();
        } else {
            this.date = new Date(year, month, day);
        }
        if (start) {
            month = this.date.getMonth() + 1;
            day = this.date.getDate();
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
                        day = new Date().getDate();
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
        
        panel.addMouseListener(this);
        
    }

    public void createConnect() {
        try {

            String hostName = "localhost";

            String dbName = "calendar";

            String userName = "root";
            String passWord = "";

            String url = "jdbc:mysql://" + hostName + ":3320/" + dbName + "?user=" + userName + "&password=" + passWord;

            connection = DriverManager.getConnection(url);

            statement = connection.createStatement();

        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    public void displayDayz(final int daysInMonth, final int dayOfWeek) {
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                
                graphics.setColor(Color.orange);
                graphics.drawString("SUN     MON     TUES      WED     THUR     FRI    SAT",100,145);

                int x=0;
                int y=0;

                ResultSet rs;

                for (int i = 0; i < daysInMonth; i++) {

                    x=(((dayOfWeek-1)+i)%7)*(80)+20;

                    final int thei = i+1;

                    try {
                        
                        statement = connection.createStatement();

                        String sqlString = "select * from calendar where thedate = '"+month + "/" + thei + "/" + year+"';";

                        rs = statement.executeQuery(sqlString);

                        if(rs.next()) {
                            graphics.setColor(Color.ORANGE);
                            graphics.drawString("~",100+x+10-12,y+200+40-20);
                        }

                    } catch(SQLException sqle) {
                        sqle.printStackTrace();
                    }

                    if (((dayOfWeek-1)+i)%7 == 0) {
                        y+=70;
                        x=0;
                    }

                    if (i == day-1 && month == new Date().getMonth()+1 && year == new Date().getYear()+1900) {
                        graphics.setColor(Color.blue);
                        graphics.drawOval(100+x-30,y+200-42,70,70);
                    }

                    graphics.setColor(Color.BLACK);
                    graphics.drawString(String.valueOf(i+1),100+x,y+200);
                }
            }
        });
        tr.start();
    }
    
    public void doNothing() {}

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calendar c = new Calendar();
                c.doNothing();
            }
        });
    }
}