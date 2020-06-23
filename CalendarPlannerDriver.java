import java.awt.*;
import java.sql. * ;
import javax.swing.*;
import java.util.Date;
import java.time.YearMonth;
import javax.imageio.*;
import java.awt.event.*;

import music.Player;

import db.JDataBase;

public class CalendarPlannerDriver implements java.awt.event.MouseListener {

    public  CalendarPlannerDriver() {

        //getasingletonOF_MGR
        this.mgr = MonthDisplayManager.getSingleton();

        //does not override or implement a iterfatopr
        introSplashScreenOnScreen();

        jdb.createConnectionToDataBase();

        setDbCredents();

        this.setAllThings();
    }
    
    private  void setDbCredents() {

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
        int x=0;
        int y=0;
        for (int i = 0; i < daysInMonth; i++) {
            x=(((dayOfWeek-1)+i)%7)*(80)+20;
            if (((dayOfWeek-1)+i)%7 == 0) {
                y+=70;
                x=0;
            }
            int thex = e.getX(); int they = e.getY() + 70;
            if(thex >= x+100 && thex <= x+100 + 80 && they >= y+200+35 && they <= y+200 + 150-30 ) {
                new instance(month, day, year, jdb, i, graphics, mgr).show();
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
            cycleCurrentTheDate = month + "/" + day + "/" + year;
        }
        if (start) {
            start = false;
        }
    }

    public void setAllThings() {
        panel = new JPanel();
        panel.setBackground(new Color(190, 200, 210));
        frame = new JFrame();
        frame.setTitle("Calendar Planner by okelykodely");
        frame.setLayout(null);
        frame.setBounds(0, 0, 1250, 760);
        // make the frame half the height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setBounds(0, 0, 1250, 760);
        panel.setBounds(frame.getBounds());
        frame.add(panel);

        // here's the part where i center the jframe on screen
        frame.setLocationRelativeTo(null);
        todayMonth = new JButton("Current Month");
        previousMonth = new JButton("< Prev Month");
        nextMonth = new JButton("Next Month >");
        cyclePlan = new JButton("Cycle Event");
        panel.add(tgglPlay);
        panel.add(todayMonth);
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
                            graphics.setColor(Color.WHITE);
                            graphics.fillRect(0, 50, 1250, 760);
                            String pic = "";
                            if(month == 1)
                                pic = "background.jpg";
                            if(month == 2)
                                pic = "background2.jpg";
                            if(month == 3)
                                pic = "background3.jpg";
                            if(month == 4)
                                pic = "background4.jpg";
                            if(month == 5)
                                pic = "background5.jpg";
                            if(month == 6)
                                pic = "background6.jpg";
                            if(month == 7)
                                pic = "background7.jpg";
                            if(month == 8)
                                pic = "background8.jpg";
                            if(month == 9)
                                pic = "background9.jpg";
                            if(month == 10)
                                pic = "background10.jpg";
                            if(month == 11)
                                pic = "background11.jpg";
                            if(month == 12)
                                pic = "background12.jpg";
                            Image image = ImageIO.read(getClass().getResourceAsStream(pic));
                            graphics.drawImage(image, 680, 50, 550, 710, null);
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
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
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
                            graphics.setColor(Color.WHITE);
                            graphics.fillRect(0, 50, 1250, 760);
                            String pic = "";
                            if(month == 1)
                                pic = "background.jpg";
                            if(month == 2)
                                pic = "background2.jpg";
                            if(month == 3)
                                pic = "background3.jpg";
                            if(month == 4)
                                pic = "background4.jpg";
                            if(month == 5)
                                pic = "background5.jpg";
                            if(month == 6)
                                pic = "background6.jpg";
                            if(month == 7)
                                pic = "background7.jpg";
                            if(month == 8)
                                pic = "background8.jpg";
                            if(month == 9)
                                pic = "background9.jpg";
                            if(month == 10)
                                pic = "background10.jpg";
                            if(month == 11)
                                pic = "background11.jpg";
                            if(month == 12)
                                pic = "background12.jpg";
                            Image image = ImageIO.read(getClass().getResourceAsStream(pic));
                                                        graphics.drawImage(image, 680, 50, 550, 710, null);
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
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
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
                            graphics.setColor(Color.WHITE);
                            graphics.fillRect(0, 50, 1250, 760);
                            String pic = "";
                            if(month == 1)
                                pic = "background.jpg";
                            if(month == 2)
                                pic = "background2.jpg";
                            if(month == 3)
                                pic = "background3.jpg";
                            if(month == 4)
                                pic = "background4.jpg";
                            if(month == 5)
                                pic = "background5.jpg";
                            if(month == 6)
                                pic = "background6.jpg";
                            if(month == 7)
                                pic = "background7.jpg";
                            if(month == 8)
                                pic = "background8.jpg";
                            if(month == 9)
                                pic = "background9.jpg";
                            if(month == 10)
                                pic = "background10.jpg";
                            if(month == 11)
                                pic = "background11.jpg";
                            if(month == 12)
                                pic = "background12.jpg";
                            Image image = ImageIO.read(getClass().getResourceAsStream(pic));
                                                        graphics.drawImage(image, 680, 50, 550, 710, null);
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
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
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
                            graphics.setColor(Color.WHITE);
                            graphics.fillRect(0, 50, 1250, 760);
                            String pic = "";
                            if(month == 1)
                                pic = "background.jpg";
                            if(month == 2)
                                pic = "background2.jpg";
                            if(month == 3)
                                pic = "background3.jpg";
                            if(month == 4)
                                pic = "background4.jpg";
                            if(month == 5)
                                pic = "background5.jpg";
                            if(month == 6)
                                pic = "background6.jpg";
                            if(month == 7)
                                pic = "background7.jpg";
                            if(month == 8)
                                pic = "background8.jpg";
                            if(month == 9)
                                pic = "background9.jpg";
                            if(month == 10)
                                pic = "background10.jpg";
                            if(month == 11)
                                pic = "background11.jpg";
                            if(month == 12)
                                pic = "background12.jpg";
                            Image image = ImageIO.read(getClass().getResourceAsStream(pic));
                                                        graphics.drawImage(image, 680, 50, 550, 710, null);
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
                    Thread.sleep(1000 );
                } catch (InterruptedException ie) {
                }
                nextMonth.doClick();
            }
        };
        t.start();
        
        panel.addMouseListener(this);
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
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 50, 1250, 760);
                    Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                                                graphics.drawImage(image, 680, 50, 550, 710, null);
                } catch(Exception ee) {
                    ee.printStackTrace();
                }
                graphics.setColor(Color.BLACK);
                graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                month = tmonth; year = tyer; day = tdate;
                mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
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
                    if(!reallyfound) {
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
                    if(!reallyfound) {
                        try {
                            graphics.setColor(Color.WHITE);
                            graphics.fillRect(0, 50, 1250, 760);
                            String pic = "";
                            if(month == 1)
                                pic = "background.jpg";
                            if(month == 2)
                                pic = "background2.jpg";
                            if(month == 3)
                                pic = "background3.jpg";
                            if(month == 4)
                                pic = "background4.jpg";
                            if(month == 5)
                                pic = "background5.jpg";
                            if(month == 6)
                                pic = "background6.jpg";
                            if(month == 7)
                                pic = "background7.jpg";
                            if(month == 8)
                                pic = "background8.jpg";
                            if(month == 9)
                                pic = "background9.jpg";
                            if(month == 10)
                                pic = "background10.jpg";
                            if(month == 11)
                                pic = "background11.jpg";
                            if(month == 12)
                                pic = "background12.jpg";
                            Image image = ImageIO.read(getClass().getResourceAsStream(pic));
                                                        graphics.drawImage(image, 680, 50, 550, 710, null);
                        } catch(Exception ee) {
                            ee.printStackTrace();
                        }
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                        month = tmonth; year = tyer; day = tdate;
                        mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                        found = false;
                    } else {
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
                        graphics.setColor(Color.WHITE);
                        graphics.fillRect(0, 50, 1250, 760);
                        Image image = ImageIO.read(getClass().getResourceAsStream("background.jpg"));
                                                    graphics.drawImage(image, 680, 50, 550, 710, null);
                    } catch(Exception ee) {
                        ee.printStackTrace();
                    }
                    graphics.setColor(Color.BLACK);
                    graphics.drawString(tmonth + "-" + (tyer), 100, 100);
                    month = tmonth; year = tyer; day = tdate;
                    mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, statement);
                }                
            }
            System.out.println(cycleCurrentTheDate+"<>");
        } catch(SQLException sqle) {
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

    public void mouseExited(MouseEvent e) {    }

    private  boolean  playOn  =  true;
    private Player player = new Player(playOn);
    public  MonthDisplayManager mgr;
    private JSplashScreen sS = new JSplashScreen();
    private Statement statement  = null;
    private Connection connection = null;
    private JPanel panel = null;
    private JButton tgglPlay = new JButton("Toggle Music");
    private JButton previousMonth, nextMonth, todayMonth, cyclePlan;
    private String cycleCurrentTheDate = "";
    private int td = -1;
    private java.util.Date date;
    private int month, day, year;
    private Graphics graphics = null;
    private boolean start, pleaseInsertMe;
    private JDataBase jdb = new JDataBase();
    public JFrame  frame;
}