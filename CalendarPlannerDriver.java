import java.awt.*;
import java.sql. * ;
import javax.swing.*;
import java.util.Date;
import java.time.YearMonth;
import javax.imageio.*;
import java.awt.event.*;

public class CalendarPlannerDriver 
        implements MouseListener {

    private Graphics graphics = null;
    private Statement statement  = null;
    private Connection connection = null;
    private JFrame frame, plan = null;
    private JPanel panel = null, plan2;
    private JButton previousMonth, nextMonth, todayMonth, cyclePlan;
    private String currentTheDate = "";
    private String cycleCurrentTheDate = "";
    int td =-1;
    private java.util.Date date;
    private int month, day, year;
    private boolean start, pleaseInsertMe;

    public CalendarPlannerDriver() {
        createConnectDB(); setAllThings();
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
                plan2 = new JPanel();
                plan2.setBackground(Color.RED);
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
                JLabel cl1 = new JLabel("Time");
                cl1.setBounds(220, 50, 50, 20);
                plan2.add(cl1);
                JTextField cl1t = new JTextField();
                cl1t.setBounds(330-50, 50, 100, 20);
                plan2.add(cl1t);
                JComboBox c2 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c2.setBounds(10, 230, 200, 20);
                plan2.add(c2);
                JLabel cl2 = new JLabel("Time");
                cl2.setBounds(220, 230, 50, 20);
                plan2.add(cl2);
                JTextField cl2t = new JTextField();
                cl2t.setBounds(330-50, 230, 100, 20);
                plan2.add(cl2t);
                JComboBox c3 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c3.setBounds(10, 400, 200, 20);
                plan2.add(c3);
                JLabel cl3 = new JLabel("Time");
                cl3.setBounds(220, 400, 50, 20);
                plan2.add(cl3);
                JTextField cl3t = new JTextField();
                cl3t.setBounds(330-50, 400, 100, 20);
                plan2.add(cl3t);
                JComboBox c4 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c4.setBounds(10, 540, 200, 20);
                plan2.add(c4);
                JLabel cl4 = new JLabel("Time");
                cl4.setBounds(220, 540, 50, 20);
                plan2.add(cl4);
                JTextField cl4t = new JTextField();
                cl4t.setBounds(330-50, 540, 100, 20);
                plan2.add(cl4t);
                JComboBox c5 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
                c5.setBounds(10, 720, 200, 20);
                plan2.add(c5);
                JLabel cl5 = new JLabel("Time");
                cl5.setBounds(220, 720, 50, 20);
                plan2.add(cl5);
                JTextField cl5t = new JTextField();
                cl5t.setBounds(330-50, 720, 100, 20);
                plan2.add(cl5t);
                
                JButton b = new JButton("Save");
                b.setBounds(10, 850, 200, 40);
                plan2.add(b);

                JButton cancelBttn = new JButton("Erase");
                cancelBttn.setBounds(240, 850, 200, 40);
                plan2.add(cancelBttn);
                
                JTextArea jta = new JTextArea();
                jta.setLineWrap(true);
                jta.setWrapStyleWord(true);
                JScrollPane jta_ = new JScrollPane(jta,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jta_.setBounds(10, 100, 500, 100);
                plan2.add(jta_);
                JTextArea jta2 = new JTextArea();
                jta2.setLineWrap(true);
                jta2.setWrapStyleWord(true);
                JScrollPane jta2_ = new JScrollPane(jta2,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jta2_.setBounds(10, 260, 500, 100);
                plan2.add(jta2_);
                JTextArea jta3 = new JTextArea();
                jta3.setLineWrap(true);
                jta3.setWrapStyleWord(true);
                JScrollPane jta3_ = new JScrollPane(jta3,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jta3_.setBounds(10, 420, 500, 100);
                plan2.add(jta3_);
                JTextArea jta4 = new JTextArea();
                jta4.setLineWrap(true);
                jta4.setWrapStyleWord(true);
                JScrollPane jta4_ = new JScrollPane(jta4,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jta4_.setBounds(10, 580, 500, 100);
                plan2.add(jta4_);
                JTextArea jta5 = new JTextArea();
                jta5.setLineWrap(true);
                jta5.setWrapStyleWord(true);
                JScrollPane jta5_ = new JScrollPane(jta5,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jta5_.setBounds(10, 740, 500, 100);
                plan2.add(jta5_);
                
                final int thei = i+1;
                try {
                    
                    statement = connection.createStatement();
                    String sqlString = "select * from calendar where thedate = '"+month + "/" + thei + "/" + year+"';";
                    ResultSet rs = statement.executeQuery(sqlString);
                    
                    if(rs.next()) {
                        cl1t.setText(rs.getString("c1t"));
                        cl2t.setText(rs.getString("c2t"));
                        cl3t.setText(rs.getString("c3t"));
                        cl4t.setText(rs.getString("c4t"));
                        cl5t.setText(rs.getString("c5t"));
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
                    }
                    else {
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

                            // // updateOrInsertToDB();
                            String sqlString = "update calendar set thecontent = '"+jta.getText()+"', content2 = '"+jta2.getText()+"', content3 = '"+jta3.getText()+"', content4 = '"+jta4.getText()+"', content5 = '"+jta5.getText()+"', c1 = "+c_1+", c2 = "+c_2+", c3 = "+c_3+", c4 = "+c_4+", c5 = "+c_5+", c1t = '"+cl1t.getText()+"', c2t = '"+cl2t.getText()+"', c3t = '"+cl3t.getText()+"', c4t = '"+cl4t.getText()+"', c5t = '"+cl5t.getText()+"' where thedate = '"+month + "/" + thei + "/" + year+"';";
                            if(pleaseInsertMe) {
                                String s = "select max(td) as ttdd from calendar;";
                                ResultSet rs = statement.executeQuery(s);
                                int ttdd = -1;
                                if(rs.next()) {
                                    ttdd = 1 + rs.getInt("ttdd");
                                }
                                sqlString = "insert into calendar (td, c1t, c2t, c3t, c4t, c5t, c1, c2, c3, c4, c5, thedate, thecontent, content2, content3, content4, content5) values ("+ttdd+", '"+cl1t.getText()+"','"+cl2t.getText()+"','"+cl3t.getText()+"','"+cl4t.getText()+"','"+cl5t.getText()+"',"+c_1+","+c_2+","+c_3+","+c_4+","+c_5+",'"+month + "/" + thei + "/" + year+"','"+jta.getText()+"','"+jta2.getText()+"','"+jta3.getText()+"','"+jta4.getText()+"','"+jta5.getText()+"');";
                            }
                            statement.execute(sqlString);
                            statement.close();
                            
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
                cancelBttn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            
                            statement = connection.createStatement();
                            String sqlString = "delete from calendar where thedate = '"+month + "/" + thei + "/" + year+"';";
                            statement.execute(sqlString);
                            statement.close();

                            plan.dispose();
                            
                            java.util.Calendar c = java.util.Calendar.getInstance();
                            c.setTime(new Date(year - 1900, month - 1, 1));
                            int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                            YearMonth yearMonthObject = YearMonth.of((year), month);
                            int daysInMonth = yearMonthObject.lengthOfMonth();
                            displayDayz(daysInMonth, dayOfWeek);
                        } catch(SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void setDate() {
        if (start) {
            this.date = new Date();
        }
        else {
            this.date = new Date(year, month, day);
        }
        if (start) {
            month = this.date.getMonth() + 1;
            day = this.date.getDate();
            year = this.date.getYear() + 1900;
            currentTheDate = month + "/" + day + "/" + year;
            cycleCurrentTheDate = month + "/" + day + "/" + year;
        }
        if (start) {
            start = false;
        }
    }

    public void setAllThings() {
        panel = new JPanel();
        panel.setBackground(Color.RED);
        frame = new JFrame();
        frame.setTitle("Calendar Planner by DC");
        frame.setLayout(null);
        frame.setBounds(0, 0, 1250, 760);
        panel.setBounds(frame.getBounds());
        frame.add(panel);
        todayMonth = new JButton("Current Month");
        previousMonth = new JButton("< Prev Month");
        nextMonth = new JButton("Next Month >");
        cyclePlan = new JButton("Cycle Event");
        panel.add(todayMonth);
        panel.add(previousMonth);
        panel.add(nextMonth);
        panel.add(cyclePlan);
        todayMonth.setBounds(11, 50, 200, 20);
        start = true;
        todayMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            graphics.setColor(Color.MAGENTA);
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
                        graphics.drawString(month + "-" + (year), 100, 100);
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
                            graphics.setColor(Color.MAGENTA);
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
                        graphics.drawString(month + "-" + (year), 100, 100);
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
                            graphics.setColor(Color.MAGENTA);
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
                        graphics.drawString(month + "-" + (year), 100, 100);
                        displayDayz(daysInMonth, dayOfWeek);
                    }
                };
                t.start();
            }
        });
        cyclePlan.setBounds(301, 40, 100, 20);
        cyclePlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            graphics.setColor(Color.MAGENTA);
                            graphics.fillRect(0, 50, 1250, 760);
                            Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                            graphics.drawImage(image, 650, 50, image.getWidth(null), image.getHeight(null), null);
                            findTheNextPlanMonth();
                        } catch(Exception ev) {
                            ev.printStackTrace();
                        }
                    }
                };
                t.start();
            }
        });
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphics = panel.getGraphics();
        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
        Thread t = new Thread() {
            public void run() {
                previousMonth.doClick();
                try {
                    Thread.sleep( 1000);
                } catch (InterruptedException ie) {
                }
                nextMonth.doClick();
            }
        };
        t.start();
        
        panel.addMouseListener(this);
    }

    public void createConnectDB() {
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

    public void findTheNextPlanMonth() {
        System.out.println(cycleCurrentTheDate);
        ResultSet rs = null;
        try {

            statement = connection.createStatement();
            String sql = "select thedate from calendar order by td asc;";
            rs = statement.executeQuery(sql);
            String t = "";
            if(rs.next()) {
                t = rs.getString("thedate");
            }
            sql = "select * from calendar order by td asc;";
            rs = statement.executeQuery(sql);
            String tt = "";
            int count = 0;
            while(rs.next()) {
                if(tt.equals(rs.getString("thedate"))) {
                    count--;
                }
                tt = rs.getString("thedate");
                count++;
            }
            System.out.println(tt+"werwer");
            System.out.println(cycleCurrentTheDate+"werwer");
            System.out.println(t+"werwer");
            if(cycleCurrentTheDate.equals(tt)) {
                cycleCurrentTheDate = t;
                System.out.println(cycleCurrentTheDate+"twterwer");
                int tmonth = Integer.parseInt(
                        cycleCurrentTheDate.substring(
                            0,
                            cycleCurrentTheDate.indexOf("/")));
                String str1 = cycleCurrentTheDate.substring(cycleCurrentTheDate.indexOf("/") + 1, cycleCurrentTheDate.length());
                int tdate = Integer.parseInt(
                        str1.substring(
                            0,
                            str1.indexOf("/")));
                String str2 = str1.substring(
                        str1.indexOf("/") + 1, str1.length());
                int tyer = Integer.parseInt(str2);
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(new Date(tyer - 1900, tmonth - 1, 1));
                int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                YearMonth yearMonthObject = YearMonth.of((tyer), tmonth);
                int daysInMonth = yearMonthObject.lengthOfMonth();
                try {
                    graphics.setColor(Color.MAGENTA);
                    graphics.fillRect(0, 50, 1250, 760);
                    Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                    graphics.drawImage(image, 650, 50, image.getWidth(null), image.getHeight(null), null);
                } catch(Exception ee) {
                    ee.printStackTrace();
                }
                graphics.setColor(Color.BLACK);
                graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                month = tmonth; year = tyer; day = tdate;
                displayDayz(daysInMonth, dayOfWeek);
                return;
            }
            sql = "select td from calendar where thedate = '"+cycleCurrentTheDate+"';";
            rs = statement.executeQuery(sql);
            if(rs.next()) {
                td = rs.getInt("td");
                System.out.println(td+"<><>");
            }
            String sqlString = "select distinct thedate, td from calendar where td >= "+td+" order by td asc;";
            rs = statement.executeQuery(sqlString);
            boolean oneMoe = false;
            boolean found = false;
            boolean reallyfound = false;
            boolean nmoe = false;
            boolean ass = true;
            String mybad = "";
            int countt = 0;
            while(rs.next()) {
                String thedate = rs.getString("thedate");
                if(countt == 1)
                mybad = thedate;
                
                countt++;
                if(found || nmoe) {
                    oneMoe = false;
                    cycleCurrentTheDate = thedate;
                    int tmonth = Integer.parseInt(
                            cycleCurrentTheDate.substring(
                                0,
                                cycleCurrentTheDate.indexOf("/")));
                    String str1 = cycleCurrentTheDate.substring(cycleCurrentTheDate.indexOf("/") + 1, cycleCurrentTheDate.length());
                    int tdate = Integer.parseInt(
                            str1.substring(
                                0,
                                str1.indexOf("/")));
                    String str2 = str1.substring(
                            str1.indexOf("/") + 1, str1.length());
                    int tyer = Integer.parseInt(str2);
                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(new Date(tyer - 1900, tmonth - 1, 1));
                    int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                    YearMonth yearMonthObject = YearMonth.of((tyer), tmonth);
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    if(!reallyfound) {
                        try {
                            graphics.setColor(Color.MAGENTA);
                            graphics.fillRect(0, 50, 1250, 760);
                            Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                            graphics.drawImage(image, 650, 50, image.getWidth(null), image.getHeight(null), null);
                        } catch(Exception ee) {
                            ee.printStackTrace();
                        }
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                        month = tmonth; year = tyer; day = tdate;
                        displayDayz(daysInMonth, dayOfWeek);
                        found = false;
                    } else {
                                    //cycleCurrentTheDate = mybad;
break;
                    }
                    nmoe = true;
                    oneMoe = false;
                    reallyfound = true;
                }
                if(!reallyfound && thedate.equals(cycleCurrentTheDate)) {
                    oneMoe = true;
                    found = true;
                }
            }
            if(!reallyfound) {
                sqlString = "select distinct thedate, td from calendar where td >= "+td+" order by td asc;";
                rs = statement.executeQuery(sqlString);
                if(rs.next()) {
                    String thedate = rs.getString("thedate");
                    cycleCurrentTheDate = thedate;
                    System.out.println(cycleCurrentTheDate+"asdfsdfa");
                    int tmonth = Integer.parseInt(
                            cycleCurrentTheDate.substring(
                                0,
                                cycleCurrentTheDate.indexOf("/")));
                    String str1 = cycleCurrentTheDate.substring(cycleCurrentTheDate.indexOf("/") + 1, cycleCurrentTheDate.length());
                    int tdate = Integer.parseInt(
                            str1.substring(
                                0,
                                str1.indexOf("/")));
                    String str2 = str1.substring(
                            str1.indexOf("/") + 1, str1.length());
                    int tyer = Integer.parseInt(str2);
                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(new Date(tyer - 1900, tmonth - 1, 1));
                    int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                    YearMonth yearMonthObject = YearMonth.of((tyer), tmonth);
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    try {
                        graphics.setColor(Color.MAGENTA);
                        graphics.fillRect(0, 50, 1250, 760);
                        Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                        graphics.drawImage(image, 650, 50, image.getWidth(null), image.getHeight(null), null);
                    } catch(Exception ee) {
                        ee.printStackTrace();
                    }
                    graphics.setColor(Color.BLACK);
                    graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                    month = tmonth; year = tyer; day = tdate;
                    displayDayz(daysInMonth, dayOfWeek);
                }                
            }
            System.out.println(cycleCurrentTheDate+"<>");
            statement.close();
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    public void displayDayz(final int daysInMonth, final int dayOfWeek) {
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                graphics.setColor(Color.GREEN);
                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 30));

                graphics.drawString("SUN  MON  TUES  WED  THUR  FRI  SAT", 100, 145);

                int x = 0;
                
                int y = 0;

                ResultSet rs = null;
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
                        statement.close();
                    } catch(SQLException sqle) {
                        sqle.printStackTrace();
                    }

                    if(((dayOfWeek-1)+i)%7 == 0) {
                        x = 0;
                        y += 70;
                    }
                    if (i==day-1 && month == new Date().getMonth()+1 && year == new Date().getYear()+1900) {
                        graphics.setColor(Color.black);
                        graphics.drawOval(100+x-25,y+200-42,70,70);
                    }

                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                    if(x == 0) {
                        graphics.setColor(Color.GREEN);
                        graphics.drawString(String.valueOf(i+1),100+x,y+200);
                    }
                    else {
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(String.valueOf(i+1),100+x,y+200);
                    }
                }
            }
        });
        tr.start();
    }

    public static void main(String args[]) {
        Thread tr = new Thread() {
            public void run() {
                new CalendarPlannerDriver();
            }
        };
        tr.start();
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
        // // System.exit(0);
    }
}