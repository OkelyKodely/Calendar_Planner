import cereal.Inputter;
import cereal.Outputter;
import util.MonthDisplayManager;
import util.instance;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.util.Date;
import java.time.YearMonth;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

import instruments.Player;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;

import mysql.JDataBase;

import util.BackgroundDrawer;

public class CalendarPlannerDriver implements java.awt.event.MouseListener {

    public Outputter object= null;
    private JTextArea jta = new JTextArea();
    private JScrollPane pp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private boolean nah = false;
    private MouseWheelListener syswheel;

    public CalendarPlannerDriver() {

        //getasingletonOF_MGR
        this.mgr = MonthDisplayManager.getSingleton();

        //does not override or implement a iterfatopr
        introSplashScreenOnScreen();

        jdb.createConnectionToDataBase();

        setDbCredents();

        this.setAllThings();

        addEvent();
    }
    
    public void addEvent() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setSerializableConnection();
                System.exit(0);
            }
        });
    }
    
    private void setDeSerializableConnection() throws IOException {
       String filename = "file.ser"; 
       Outputter object1 = null; 
       int error = -1;
       
       
        try
        {    
            FileInputStream file = new FileInputStream(filename); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            object1 = (Outputter)in.readObject(); 
            
            object =object1;
              
            in.close(); 
            file.close(); 
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
            ex.printStackTrace();
            error = 1;
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        }
        
        if(error == 1) {
            throw new IOException();
        }
    }
    
    private void setSerializableConnection() {
        this. object = new Outputter(month, day, year); 
        String filename = "file.ser"; 
          
        try
        {    
            FileOutputStream file = new FileOutputStream(filename); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
              
            out.writeObject(object); 
              
            out.close(); 
            file.close(); 
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        }     
        
    }

    private void setDbCredents() {

        setDbConnection();

        setDbStatement();
    }

    public void setDbStatement() {
        this.statement = jdb.getStmt();
    }

    public void setDbConnection() {
        this.connection = jdb.getConnection();
    }

    public void introSplashScreenOnScreen() {
        this.sS.show();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date(year - 1900, month - 1, 1));
        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
        YearMonth yearMonthObject = YearMonth.of((year), month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        int x = 0;
        int y = 0;
        for (int i = 0; i < daysInMonth; i++) {
            x = (((dayOfWeek - 1) + i) % 7) * (80) + 20;
            if (((dayOfWeek - 1) + i) % 7 == 0) {
                y += 70;
                x = 0;
            }
            int thex = e.getX();
            int they = e.getY() + 70;
            if (thex >= x + 100 && thex <= x + 100 + 80 && they >= y + 200 + 35 && they <= y + 200 + 150 - 30) {
                new instance(howMany, month, day, year, jdb, i, graphics, mgr).show();
            }
        }
    }

    public void setDate() throws IOException {
        if (start) {
            this.date = new Date();
        } else {
            this.date = new Date(year, month, day);
        }
        if (start) {
            month = this.date.getMonth() + 1;
            day = this.date.getDate();
            year = this.date.getYear() + 1900;
            cycleCurrentTheDate = month + "/" + day + "/" + year;
        }
        if (start) {
            start = false;
            setDeSerializableConnection();
            month = object.month;
            day = object.date;
            year = object.year;
        }
    }

    public void setAllThings() {
        panel = new JPanel();
        panel.setBackground(new Color(190, 200, 210));
        frame = new JFrame();
        frame.setTitle("Calendar (By Okely)");
        frame.setLayout(null);
        frame.setBounds(0, 0, 1250, 760);
        // make the frame half the height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setBounds(0, 0, 1250, 760);
        try {
            ArrayList list = new ArrayList();
            Connection conn = jdb.getConnection();
            Statement st = jdb.getStmt();
            try {
                String s = "select thedate from calendar order by td asc;";
                ResultSet rs = st.executeQuery(s);
                while (rs.next()) {
                    list.add(rs.getString("thedate"));
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }

            Object str[] = list.toArray();

            JTree t = new JTree(str);
            JPanel panel2 = new JPanel();
            //panel2.setLayout(null);
            JFrame jj = new JFrame("Controller");
            jj.setBounds(0, 0, 300, 700);
            panel2.setBounds(jj.getBounds());
            jj.add(panel2);
            jj.setLayout(null);
            JLabel l = new JLabel("Entries");
            l.setBounds(0,100,100,20);
            panel2.add(l);
            t.setBounds(0, 130, 100, 500);
            panel2.add(t);
            jj.setVisible(true);
            panel2.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getX() >= 0 && e.getX() <= 100 &&
                            e.getY() >= 0 && e.getY() <= 40) {
                        System.exit(0);
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
            });
            Graphics gg = panel2.getGraphics();
            try {
                Image imag = ImageIO.read(getClass().getResourceAsStream("xr.jpg"));
                gg.drawImage(imag, 0, 0, 100, 40, null);
            } catch(Exception ee) {
                ee.printStackTrace();
            }
            t.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    t.getLastSelectedPathComponent();
                    Object nodeInfo = node.getUserObject();
                    String v = nodeInfo.toString();
                    System.out.println(v);
                    int tmonth = Integer.parseInt(
                        v.substring(
                                0,
                                v.indexOf("/")));
                    String str1 = v.substring(v.indexOf("/") + 1, v.length());
                    int tdate = Integer.parseInt(
                        str1.substring(
                                0,
                                str1.indexOf("/")));
                    String str2 = str1.substring(
                        str1.indexOf("/") + 1, str1.length());
                    int tyer = Integer.parseInt(str2);
                    new instance(howMany, tmonth, tdate, tyer, jdb, tdate-1, graphics, mgr).show();
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
            });
            jj.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.setBounds(frame.getBounds());
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() >= 520 && e.getX() <= 560
                        && e.getY() >= 60 && e.getY() <= 100) {
                    Connection conn = jdb.getConnection();
                    Statement st = jdb.getStmt();
                    try {
                        String notes = jta.getText();
                        String s = "select * from notes;";
                        ResultSet rs = st.executeQuery(s);
                        if (rs.next()) {
                            jta.setText(rs.getString("notes"));
                            nah = true;
                        }
                    } catch (Exception ee) {
                        ///ee.printStackTrace();
                    }
                    JFrame j = new JFrame();
                    j.setBounds(0, 0, 500, 500);
                    JPanel p = new JPanel();
                    p.setBackground(new Color(170, 140, 120));
                    j.setTitle("Notes");
                    p.setBounds(j.getBounds());
                    JButton b = new JButton("Save");
                    b.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            jdb.createConnectionToDataBase();
                            Connection conn = jdb.getConnection();
                            Statement st = jdb.getStmt();
                            try {
                                if (!nah) {
                                    String notes = jta.getText();
                                    String s = "insert into notes (notes) values ('" + notes + "');";
                                    st.execute(s);
                                } else {
                                    String notes = jta.getText();
                                    String s = "update notes set notes = '" + notes + "';";
                                    st.execute(s);
                                }
                            } catch (Exception ee) {
                                ///ee.printStackTrace();
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
                    });
                    b.setBounds(10, 420, 80, 40);
                    jta.setWrapStyleWord(true);
                    jta.setLineWrap(true);
                    jta.setBounds(10, 10, 460, 400);
                    syswheel=pp.getMouseWheelListeners() [0];//Get the system scroll event
                    pp.removeMouseWheelListener (syswheel);//Remove system scrolling,Add as needed
                    pp.addMouseWheelListener (new event ());
                    pp.setBounds(10, 10, 460, 400);
                    p.add(pp);
                    p.add(b);
                    j.add(p);
                    p.setLayout(null);
                    j.setLayout(null);
                    j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    j.setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getX() >= 580 && e.getX() <= 610
                        && e.getY() >= 70 && e.getY() <= 110) {
                    previousMonth.doClick();
                }
                if (e.getX() >= 615 && e.getX() <= 655
                        && e.getY() >= 70 && e.getY() <= 110) {
                    nextMonth.doClick();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        frame.add(panel);

        // here's the part where i center the jframe on screen
        frame.setLocationRelativeTo(null);
        todayMonth = new JButton("Current Month");
        todayMonth.setForeground(Color.RED);
        previousMonth = new JButton("< Prev Month");
        previousMonth.setForeground(Color.BLUE);
        nextMonth = new JButton("Next Month >");
        nextMonth.setForeground(Color.CYAN);
        cyclePlan = new JButton("Cycle Event");
        cyclePlan.setForeground(Color.BLACK);
        previousYear = new JButton("< Prev Year");
        previousYear.setForeground(Color.MAGENTA);
        nextYear = new JButton("Next Year >");
        nextYear.setForeground(Color.ORANGE);
        tgglPlay.setForeground(Color.GREEN);
        panel.add(tgglPlay);
        panel.add(todayMonth);
        panel.add(previousYear);
        panel.add(nextYear);
        panel.add(previousMonth);
        panel.add(nextMonth);
        panel.add(cyclePlan);
        todayMonth.setBounds(11, 50, 200, 20);
        start = true;
        tgglPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {

                        playMusic();
                    }
                };

                t.start();
            }
        });
        todayMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            graphics.setColor(new Color(170, 140, 120));
                            graphics.fillRect(0, 50, 1250, 760);

                            new BackgroundDrawer(graphics).show(month);

                        } catch (Exception ee) {
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
                        Date d = new Date();
                        int mn = d.getMonth() + 1;
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                        graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(month + "-" + (year), 100, 100);
                        graphics.setColor(Color.CYAN);
                        if (month == 1) {
                            graphics.drawString("JANUARY", 300, 100);
                        }
                        if (month == 2) {
                            graphics.drawString("FEBRUARY", 300, 100);
                        }
                        if (month == 3) {
                            graphics.drawString("MARCH", 300, 100);
                        }
                        if (month == 4) {
                            graphics.drawString("APRIL", 300, 100);
                        }
                        if (month == 5) {
                            graphics.drawString("MAY", 300, 100);
                        }
                        if (month == 6) {
                            graphics.drawString("JUNE", 300, 100);
                        }
                        if (month == 7) {
                            graphics.drawString("JULY", 300, 100);
                        }
                        if (month == 8) {
                            graphics.drawString("AUGUST", 300, 100);
                        }
                        if (month == 9) {
                            graphics.drawString("SEPTEMBER", 300, 100);
                        }
                        if (month == 10) {
                            graphics.drawString("OCTOBER", 300, 100);
                        }
                        if (month == 11) {
                            graphics.drawString("NOVEMBER", 300, 100);
                        }
                        if (month == 12) {
                            graphics.drawString("DECEMBER", 300, 100);
                        }
                        graphics.setColor(Color.BLACK);
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                        Connection conn = jdb.getConnection();
                        Statement st = jdb.getStmt();
                        try {
                            String s = "select count(td) as cnt from calendar;";
                            ResultSet rs = st.executeQuery(s);
                            if (rs.next()) {
                                graphics.setColor(Color.orange);
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                                graphics.drawString("Entries: " + rs.getInt("cnt"), 100, 600);
                                howMany.setForeground(Color.RED);
                                howMany.setText("Entries: " + rs.getInt("cnt"));
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                            }
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
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
                            graphics.setColor(new Color(170, 140, 120));
                            graphics.fillRect(0, 50, 1250, 760);

                            new BackgroundDrawer(graphics).show(month);

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        try {
                            setDate();
                        } catch(IOException i) {
                            month = new Date().getMonth() + 1;
                            day = new Date().getDate();
                            year = new Date().getYear() + 1900;
                            cycleCurrentTheDate = month + "/" + day + "/" + year;
                        }
                        month++;
                        if (month == 13) {
                            year++;
                        }
                        if (month == 13) {
                            month = 1;
                        }
                        try {
                            setDate();
                        } catch(IOException i) {
                            month = new Date().getMonth() + 1;
                            day = new Date().getDate();
                            year = new Date().getYear() + 1900;
                            cycleCurrentTheDate = month + "/" + day + "/" + year;
                        }
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(new Date(year - 1900, month - 1, 1));
                        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                        YearMonth yearMonthObject = YearMonth.of((year), month);
                        int daysInMonth = yearMonthObject.lengthOfMonth();
                        Date d = new Date();
                        int mn = d.getMonth() + 1;
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                        graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(month + "-" + (year), 100, 100);
                        graphics.setColor(Color.CYAN);
                        if (month == 1) {
                            graphics.drawString("JANUARY", 300, 100);
                        }
                        if (month == 2) {
                            graphics.drawString("FEBRUARY", 300, 100);
                        }
                        if (month == 3) {
                            graphics.drawString("MARCH", 300, 100);
                        }
                        if (month == 4) {
                            graphics.drawString("APRIL", 300, 100);
                        }
                        if (month == 5) {
                            graphics.drawString("MAY", 300, 100);
                        }
                        if (month == 6) {
                            graphics.drawString("JUNE", 300, 100);
                        }
                        if (month == 7) {
                            graphics.drawString("JULY", 300, 100);
                        }
                        if (month == 8) {
                            graphics.drawString("AUGUST", 300, 100);
                        }
                        if (month == 9) {
                            graphics.drawString("SEPTEMBER", 300, 100);
                        }
                        if (month == 10) {
                            graphics.drawString("OCTOBER", 300, 100);
                        }
                        if (month == 11) {
                            graphics.drawString("NOVEMBER", 300, 100);
                        }
                        if (month == 12) {
                            graphics.drawString("DECEMBER", 300, 100);
                        }
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                        Connection conn = jdb.getConnection();
                        Statement st = jdb.getStmt();
                        try {
                            String s = "select count(td) as cnt from calendar;";
                            ResultSet rs = st.executeQuery(s);
                            if (rs.next()) {
                                graphics.setColor(Color.orange);
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                                graphics.drawString("Entries: " + rs.getInt("cnt"), 100, 600);
                                howMany.setForeground(Color.RED);
                                howMany.setText("Entries: " + rs.getInt("cnt"));
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                            }
                        } catch (Exception e) {
                            ////e.printStackTrace();
                        }
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
                            graphics.setColor(new Color(170, 140, 120));
                            graphics.fillRect(0, 50, 1250, 760);

                            new BackgroundDrawer(graphics).show(month);

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        try {
                            setDate();
                        } catch(IOException i) {
                            month = new Date().getMonth() + 1;
                            day = new Date().getDate();
                            year = new Date().getYear() + 1900;
                            cycleCurrentTheDate = month + "/" + day + "/" + year;
                        }
                        month--;
                        if (month == 0) {
                            year--;
                        }
                        if (month == 0) {
                            month = 12;
                        }
                        try {
                            setDate();
                        } catch(IOException i) {
                            month = new Date().getMonth() + 1;
                            day = new Date().getDate();
                            year = new Date().getYear() + 1900;
                            cycleCurrentTheDate = month + "/" + day + "/" + year;
                        }
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(new Date(year - 1900, month - 1, 1));
                        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                        YearMonth yearMonthObject = YearMonth.of((year), month);
                        int daysInMonth = yearMonthObject.lengthOfMonth();
                        Date d = new Date();
                        int mn = d.getMonth() + 1;
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                        graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                        graphics.setColor(Color.CYAN);
                        graphics.drawString(month + "-" + (year), 100, 100);
                        if (month == 1) {
                            graphics.drawString("JANUARY", 300, 100);
                        }
                        if (month == 2) {
                            graphics.drawString("FEBRUARY", 300, 100);
                        }
                        if (month == 3) {
                            graphics.drawString("MARCH", 300, 100);
                        }
                        if (month == 4) {
                            graphics.drawString("APRIL", 300, 100);
                        }
                        if (month == 5) {
                            graphics.drawString("MAY", 300, 100);
                        }
                        if (month == 6) {
                            graphics.drawString("JUNE", 300, 100);
                        }
                        if (month == 7) {
                            graphics.drawString("JULY", 300, 100);
                        }
                        if (month == 8) {
                            graphics.drawString("AUGUST", 300, 100);
                        }
                        if (month == 9) {
                            graphics.drawString("SEPTEMBER", 300, 100);
                        }
                        if (month == 10) {
                            graphics.drawString("OCTOBER", 300, 100);
                        }
                        if (month == 11) {
                            graphics.drawString("NOVEMBER", 300, 100);
                        }
                        if (month == 12) {
                            graphics.drawString("DECEMBER", 300, 100);
                        }
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                        Connection conn = jdb.getConnection();
                        Statement st = jdb.getStmt();
                        try {
                            String s = "select count(td) as cnt from calendar;";
                            ResultSet rs = st.executeQuery(s);
                            if (rs.next()) {
                                graphics.setColor(Color.orange);
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                                graphics.drawString("Entries: " + rs.getInt("cnt"), 100, 600);
                                howMany.setForeground(Color.RED);
                                howMany.setText("Entries: " + rs.getInt("cnt"));
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                            }
                        } catch (Exception e) {
                            ///e.printStackTrace();
                        }
                    }
                };
                t.start();
            }
        });
        nextYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            graphics.setColor(new Color(170, 140, 120));
                            graphics.fillRect(0, 50, 1250, 760);

                            new BackgroundDrawer(graphics).show(month);

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        try {
                            setDate();
                        } catch(IOException i) {
                            month = new Date().getMonth() + 1;
                            day = new Date().getDate();
                            year = new Date().getYear() + 1900;
                            cycleCurrentTheDate = month + "/" + day + "/" + year;
                        }
                        year++;
                        try {
                            setDate();
                        } catch(IOException i) {
                            month = new Date().getMonth() + 1;
                            day = new Date().getDate();
                            year = new Date().getYear() + 1900;
                            cycleCurrentTheDate = month + "/" + day + "/" + year;
                        }
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(new Date(year - 1900, month - 1, 1));
                        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                        YearMonth yearMonthObject = YearMonth.of((year), month);
                        int daysInMonth = yearMonthObject.lengthOfMonth();
                        Date d = new Date();
                        int mn = d.getMonth() + 1;
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                        graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(month + "-" + (year), 100, 100);
                        graphics.setColor(Color.CYAN);
                        if (month == 1) {
                            graphics.drawString("JANUARY", 300, 100);
                        }
                        if (month == 2) {
                            graphics.drawString("FEBRUARY", 300, 100);
                        }
                        if (month == 3) {
                            graphics.drawString("MARCH", 300, 100);
                        }
                        if (month == 4) {
                            graphics.drawString("APRIL", 300, 100);
                        }
                        if (month == 5) {
                            graphics.drawString("MAY", 300, 100);
                        }
                        if (month == 6) {
                            graphics.drawString("JUNE", 300, 100);
                        }
                        if (month == 7) {
                            graphics.drawString("JULY", 300, 100);
                        }
                        if (month == 8) {
                            graphics.drawString("AUGUST", 300, 100);
                        }
                        if (month == 9) {
                            graphics.drawString("SEPTEMBER", 300, 100);
                        }
                        if (month == 10) {
                            graphics.drawString("OCTOBER", 300, 100);
                        }
                        if (month == 11) {
                            graphics.drawString("NOVEMBER", 300, 100);
                        }
                        if (month == 12) {
                            graphics.drawString("DECEMBER", 300, 100);
                        }
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                        Connection conn = jdb.getConnection();
                        Statement st = jdb.getStmt();
                        try {
                            String s = "select count(td) as cnt from calendar;";
                            ResultSet rs = st.executeQuery(s);
                            if (rs.next()) {
                                graphics.setColor(Color.orange);
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                                graphics.drawString("Entries: " + rs.getInt("cnt"), 100, 600);
                                howMany.setForeground(Color.RED);
                                howMany.setText("Entries: " + rs.getInt("cnt"));
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                            }
                        } catch (Exception e) {
                            ////e.printStackTrace();
                        }
                    }
                };

                t.start();
            }
        });
        previousYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            graphics.setColor(new Color(170, 140, 120));
                            graphics.fillRect(0, 50, 1250, 760);

                            new BackgroundDrawer(graphics).show(month);

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        try {
                            setDate();
                        } catch(IOException i) {
                            month = new Date().getMonth() + 1;
                            day = new Date().getDate();
                            year = new Date().getYear() + 1900;
                            cycleCurrentTheDate = month + "/" + day + "/" + year;
                        }
                        year--;
                        try {
                            setDate();
                        } catch(IOException i) {
                            month = new Date().getMonth() + 1;
                            day = new Date().getDate();
                            year = new Date().getYear() + 1900;
                            cycleCurrentTheDate = month + "/" + day + "/" + year;
                        }
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(new Date(year - 1900, month - 1, 1));
                        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                        YearMonth yearMonthObject = YearMonth.of((year), month);
                        int daysInMonth = yearMonthObject.lengthOfMonth();
                        Date d = new Date();
                        int mn = d.getMonth() + 1;
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                        graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(month + "-" + (year), 100, 100);
                        graphics.setColor(Color.CYAN);
                        if (month == 1) {
                            graphics.drawString("JANUARY", 300, 100);
                        }
                        if (month == 2) {
                            graphics.drawString("FEBRUARY", 300, 100);
                        }
                        if (month == 3) {
                            graphics.drawString("MARCH", 300, 100);
                        }
                        if (month == 4) {
                            graphics.drawString("APRIL", 300, 100);
                        }
                        if (month == 5) {
                            graphics.drawString("MAY", 300, 100);
                        }
                        if (month == 6) {
                            graphics.drawString("JUNE", 300, 100);
                        }
                        if (month == 7) {
                            graphics.drawString("JULY", 300, 100);
                        }
                        if (month == 8) {
                            graphics.drawString("AUGUST", 300, 100);
                        }
                        if (month == 9) {
                            graphics.drawString("SEPTEMBER", 300, 100);
                        }
                        if (month == 10) {
                            graphics.drawString("OCTOBER", 300, 100);
                        }
                        if (month == 11) {
                            graphics.drawString("NOVEMBER", 300, 100);
                        }
                        if (month == 12) {
                            graphics.drawString("DECEMBER", 300, 100);
                        }
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                        Connection conn = jdb.getConnection();
                        Statement st = jdb.getStmt();
                        try {
                            String s = "select count(td) as cnt from calendar;";
                            ResultSet rs = st.executeQuery(s);
                            if (rs.next()) {
                                graphics.setColor(Color.orange);
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                                graphics.drawString("Entries: " + rs.getInt("cnt"), 100, 600);
                                howMany.setForeground(Color.RED);
                                howMany.setText("Entries: " + rs.getInt("cnt"));
                                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                            }
                        } catch (Exception e) {
                            ////e.printStackTrace();
                        }
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
                            graphics.setColor(new Color(170, 140, 120));
                            graphics.fillRect(0, 50, 1250, 760);

                            new BackgroundDrawer(graphics).show(month);

                            findTheNextPlanMonth();
                            Connection conn = jdb.getConnection();
                            Statement st = jdb.getStmt();
                            try {
                                String s = "select count(td) as cnt from calendar;";
                                ResultSet rs = st.executeQuery(s);
                                if (rs.next()) {
                                    graphics.setColor(Color.red);
                                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                                    graphics.drawString("Entries: " + rs.getInt("cnt"), 100, 600);
                                    howMany.setForeground(Color.RED);
                                    howMany.setText("Entries: " + rs.getInt("cnt"));
                                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                                }
                            } catch (Exception e) {
                                // /e.printStackTrace();
                            }
                        } catch (Exception ev) {
                            ev.printStackTrace();
                        }
                    }
                };
                t.start();
            }
        });
        frame.setVisible(true);
        graphics = panel.getGraphics();
        panel.add(howMany);
        howMany.setBounds(100, 600, 200, 20);
        howMany.setText("How many: ");
        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
        Thread t = new Thread() {
            public void run() {
                previousMonth.doClick();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
                nextMonth.doClick();
                Connection conn = jdb.getConnection();
                Statement st = jdb.getStmt();
                try {
                    String s = "select count(td) as cnt from calendar;";
                    ResultSet rs = st.executeQuery(s);
                    if (rs.next()) {
                        graphics.setColor(Color.orange);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                        graphics.drawString("Entries: " + rs.getInt("cnt"), 100, 600);
                        howMany.setForeground(Color.RED);
                        howMany.setText("Entries: " + rs.getInt("cnt"));
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                    }
                } catch (Exception e) {
                    // /e.printStackTrace();
                }
            }
        };
        t.start();

        panel.addMouseListener(this);
    }

    public void findTheNextPlanMonth() {
        ResultSet rs = null;
        try {

            statement = connection.createStatement();
            String sql = "select thedate from calendar order by td asc;";
            rs = statement.executeQuery(sql);
            String t = "";
            if (rs.next()) {
                t = rs.getString("thedate");
            }
            sql = "select * from calendar order by td asc;";
            rs = statement.executeQuery(sql);
            String tt = "";
            int count = 0;
            while (rs.next()) {
                if (tt.equals(rs.getString("thedate"))) {
                    count--;
                }
                tt = rs.getString("thedate");
                count++;
            }
            if (cycleCurrentTheDate.equals(tt)) {
                cycleCurrentTheDate = t;
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
                    graphics.setColor(new Color(170, 140, 120));
                    graphics.fillRect(0, 50, 1250, 760);
                    new BackgroundDrawer(graphics).show(month);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                Date d = new Date();
                int mn = d.getMonth() + 1;
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                graphics.setColor(Color.BLACK);
                graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                month = tmonth;
                year = tyer;
                day = tdate;
                mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                return;
            }
            sql = "select td from calendar where thedate = '" + cycleCurrentTheDate + "';";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                td = rs.getInt("td");
            }
            String sqlString = "select distinct thedate, td from calendar where td >= " + td + " order by td asc;";
            rs = statement.executeQuery(sqlString);
            boolean oneMoe = false;
            boolean found = false;
            boolean reallyfound = false;
            boolean nmoe = false;
            boolean ass = true;
            String mybad = "";
            int countt = 0;
            while (rs.next()) {
                String thedate = rs.getString("thedate");
                if (countt == 1) {
                    mybad = thedate;
                }

                countt++;
                if (found || nmoe) {
                    oneMoe = false;
                    if (!reallyfound) {
                        cycleCurrentTheDate = thedate;
                    }
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
                    if (!reallyfound) {
                        try {
                            graphics.setColor(new Color(170, 140, 120));
                            graphics.fillRect(0, 50, 1250, 760);

                            new BackgroundDrawer(graphics).show(month);

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        Date d = new Date();
                        int mn = d.getMonth() + 1;
                        graphics.setColor(Color.red);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                        graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                        graphics.setColor(Color.BLACK);
                        graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                        month = tmonth;
                        year = tyer;
                        day = tdate;
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                        found = false;
                    } else {
                        break;
                    }
                    nmoe = true;
                    oneMoe = false;
                    reallyfound = true;
                }
                if (!reallyfound && thedate.equals(cycleCurrentTheDate)) {
                    oneMoe = true;
                    found = true;
                }
            }
            if (!reallyfound) {
                sqlString = "select distinct thedate, td from calendar where td >= " + td + " order by td asc;";
                rs = statement.executeQuery(sqlString);
                if (rs.next()) {
                    String thedate = rs.getString("thedate");
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
                    try {
                        graphics.setColor(new Color(170, 140, 120));
                        graphics.fillRect(0, 50, 1250, 760);
                        new BackgroundDrawer(graphics).show(month);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                    Date d = new Date();
                    int mn = d.getMonth() + 1;
                    graphics.setColor(Color.red);
                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                    graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                    graphics.setColor(Color.BLACK);
                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                    graphics.setColor(Color.BLACK);
                    graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                    month = tmonth;
                    year = tyer;
                    day = tdate;
                    mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    private void playKill() {
        player.start();
    }

    private void playMusic() {
        player.toggleMusic();
    }

    public static void main(String args[]) {
        Thread tr = new Thread() {
            public void run() {

                CalendarPlannerDriver cpd = new CalendarPlannerDriver();
                cpd.playKill();
                cpd.playMusic();

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

    public void mouseExited(MouseEvent e) {
    }

    private class event extends MouseAdapter {
        @Override
      public void mouseWheelMoved (MouseWheelEvent e) {
        if (e.isControlDown() ) {//When the ctrl key is pressed,Scroll to zoom in and out
          Font f=jta.getFont ();
          if (e.getWheelRotation() < 0) {//Enlarge text if scroll bar goes forward
            jta.setFont (new Font (f.getFamily (), f.getStyle (), f.getSize () + 1));
          } else if (e.getWheelRotation() > 0) {//Scroll down to reduce text
            jta.setFont (new Font (f.getFamily (), f.getStyle (), f.getSize ()-1));
          }
        } else {///when ctrl is not pressed,System scroll
          pp.addMouseWheelListener (syswheel);
          syswheel.mouseWheelMoved(e);//Trigger the system scroll event.
          pp.removeMouseWheelListener(syswheel);
        };
      }
    }

    private boolean playOn = true;
    private Player player = new Player(playOn);
    public MonthDisplayManager mgr;
    private JSplashScreen sS = new JSplashScreen();
    private Statement statement = null;
    private Connection connection = null;
    private JPanel panel = null;
    private JLabel howMany = new JLabel("Entries: ");
    private JButton tgglPlay = new JButton("Toggle Music");
    private JButton previousMonth, nextMonth, todayMonth, cyclePlan, previousYear, nextYear;
    private String cycleCurrentTheDate = "";
    private int td = -1;
    private java.util.Date date;
    private int month, day, year;
    private Graphics graphics = null;
    private boolean start, pleaseInsertMe;
    private JDataBase jdb = new JDataBase();
    public Frame frame;
}