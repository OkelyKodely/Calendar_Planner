import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
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
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CalendarPlannerAll implements MouseListener {

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

    public CalendarPlannerAll() {

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
                plan. setBounds(0, 0, 530, 950);
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
                
                JComboBox c1 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c1.setBounds(10, 50, 200, 20);
                plan2.add(c1);
                JComboBox c2 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c2.setBounds(10, 230, 200, 20);
                plan2.add(c2);
                JComboBox c3 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c3.setBounds(10, 400, 200, 20);
                plan2.add(c3);
                JComboBox c4 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c4.setBounds(10, 540, 200, 20);
                plan2.add(c4);
                JComboBox c5 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c5.setBounds(10, 720, 200, 20);
                plan2.add(c5);
                
                JTextArea jta = new JTextArea();
                jta.setBounds(10, 100, 500, 100);
                plan2.add(jta);
                JTextArea jta2 = new JTextArea();
                jta2.setBounds(10, 260, 500, 100);
                plan2.add(jta2);
                JTextArea jta3 = new JTextArea();
                jta3.setBounds(10, 420, 500, 100);
                plan2.add(jta3);
                JTextArea jta4 = new JTextArea();
                jta4.setBounds(10, 580, 500, 100);
                plan2.add(jta4);
                JTextArea jta5 = new JTextArea();
                jta5.setBounds(10, 740, 500, 100);
                plan2.add(jta5);
                JButton b = new JButton("Update");
                b.setBounds(10, 850, 200, 40);
                plan2.add(b);
                
                final int thei = i+1;
                
                try {
                    
                    statement = connection.createStatement();

                    String sqlString = "select * from calendar where thedate = '"+month + "/" + thei + "/" + year+"';";

                    ResultSet rs = statement.executeQuery(sqlString);
                    
                    if(rs.next()) {
                        jta.setText(rs.getString("thecontent"));
                        jta2.setText(rs.getString("content2"));
                        jta3.setText(rs.getString("content3"));
                        jta4.setText(rs.getString("content4"));
                        jta5.setText(rs.getString("content5"));
                        if(rs.getString("c1").equals("1"))
                            c1.setSelectedIndex(0);
                        if(rs.getString("c1").equals("2"))
                            c1.setSelectedIndex(1);
                        if(rs.getString("c1").equals("3"))
                            c1.setSelectedIndex(2);
                        if(rs.getString("c2").equals("1"))
                            c2.setSelectedIndex(0);
                        if(rs.getString("c2").equals("2"))
                            c2.setSelectedIndex(1);
                        if(rs.getString("c2").equals("3"))
                            c2.setSelectedIndex(2);
                        if(rs.getString("c3").equals("1"))
                            c3.setSelectedIndex(0);
                        if(rs.getString("c3").equals("2"))
                            c3.setSelectedIndex(1);
                        if(rs.getString("c3").equals("3"))
                            c3.setSelectedIndex(2);
                        if(rs.getString("c4").equals("1"))
                            c4.setSelectedIndex(0);
                        if(rs.getString("c4").equals("2"))
                            c4.setSelectedIndex(1);
                        if(rs.getString("c4").equals("3"))
                            c4.setSelectedIndex(2);
                        if(rs.getString("c5").equals("1"))
                            c5.setSelectedIndex(0);
                        if(rs.getString("c5").equals("2"))
                            c5.setSelectedIndex(1);
                        if(rs.getString("c5").equals("3"))
                            c5.setSelectedIndex(2);
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
                            
                            int c_1, c_2, c_3, c_4, c_5;
                            c_1 = -1;
                            c_2 = -2;
                            c_3 = -3;
                            c_4 = -4;
                            c_5 = -5;
                            if(c1.getSelectedIndex() == 0)
                                c_1 = 1;
                            if(c1.getSelectedIndex() == 1)
                                c_1 = 2;
                            if(c1.getSelectedIndex() == 2)
                                c_1 = 3;
                            if(c2.getSelectedIndex() == 0)
                                c_2 = 1;
                            if(c2.getSelectedIndex() == 1)
                                c_2 = 2;
                            if(c2.getSelectedIndex() == 2)
                                c_2 = 3;
                            if(c3.getSelectedIndex() == 0)
                                c_3 = 1;
                            if(c3.getSelectedIndex() == 1)
                                c_3 = 2;
                            if(c3.getSelectedIndex() == 2)
                                c_3 = 3;
                            if(c4.getSelectedIndex() == 0)
                                c_4 = 1;
                            if(c4.getSelectedIndex() == 1)
                                c_4 = 2;
                            if(c4.getSelectedIndex() == 2)
                                c_4 = 3;
                            if(c5.getSelectedIndex() == 0)
                                c_5 = 1;
                            if(c5.getSelectedIndex() == 1)
                                c_5 = 2;
                            if(c5.getSelectedIndex() == 2)
                                c_5 = 3;
                            
                            String sqlString = "update calendar set thecontent = '"+jta.getText()+"', content2 = '"+jta2.getText()+"', content3 = '"+jta3.getText()+"', content4 = '"+jta4.getText()+"', content5 = '"+jta5.getText()+"', c1 = "+c_1+", c2 = "+c_2+", c3 = "+c_3+", c4 = "+c_4+", c5 = "+c_5+" where thedate = '"+month + "/" + thei + "/" + year+"';";
                            
                            if(pleaseInsertMe) {
                                sqlString = "insert into calendar (c1, c2, c3, c4, c5, thedate, thecontent, content2, content3, content4, content5) values ("+c_1+","+c_2+","+c_3+","+c_4+","+c_5+",'"+month + "/" + thei + "/" + year+"','"+jta.getText()+"','"+jta2.getText()+"','"+jta3.getText()+"','"+jta4.getText()+"','"+jta5.getText()+"');";
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
        frame.setTitle("Calendar Planner");
        frame.setLayout(null);
        frame.setBounds(0, 0, 1250, 760);
        panel.setBounds(frame.getBounds());
        frame.add(panel);
        todayMonth = new JButton("Tonight");
        previousMonth = new JButton("< Prev Month");
        nextMonth = new JButton("Next Month >");
        panel.add(todayMonth);
        panel.add(previousMonth);
        panel.add(nextMonth);
        todayMonth.setBounds(11, 50, 100, 20);
        todayMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            graphics.setColor(Color.white);
                            graphics.fillRect(0, 50, 1250, 760);
                            Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                            graphics.drawImage(image, 650, 50, image.getWidth(null), image.getHeight(null), null);
                        } catch(Exception ee) {
                            ee.printStackTrace();
                        }
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
                        try {
                            graphics.setColor(Color.white);
                            graphics.fillRect(0, 50, 1250, 760);
                            Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                            graphics.drawImage(image, 650, 50, image.getWidth(null), image.getHeight(null), null);
                        } catch(Exception ee) {
                            ee.printStackTrace();
                        }
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
                        try {
                            graphics.setColor(Color.white);
                            graphics.fillRect(0, 50, 1250, 760);
                            Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                            graphics.drawImage(image, 650, 50, image.getWidth(null), image.getHeight(null), null);
                        } catch(Exception ee) {
                            ee.printStackTrace();
                        }
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
                
                graphics.setColor(Color.gray);
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
                            graphics.setColor(Color.GREEN);
                            graphics.drawString("---",100+x+10-12,y+200+40-20);
                        }

                    } catch(SQLException sqle) {
                        sqle.printStackTrace();
                    }

                    if (((dayOfWeek-1)+i)%7 == 0) {
                        y+=70;
                        x=0;
                    }

                    if (i == day-1 && month == new Date().getMonth()+1 && year == new Date().getYear()+1900) {
                        graphics.setColor(Color.black);
                        graphics.drawOval(100+x-25,y+200-42,70,70);
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
                CalendarPlannerAll c = new CalendarPlannerAll();
                c.doNothing();
            }
        });
    }
}