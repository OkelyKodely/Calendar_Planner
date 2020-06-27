package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.YearMonth;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mysql.JDataBase;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JViewport;

public class instance {

    private MonthDisplayManager mgr = null;

    private boolean pleaseInsertMe = false;

    private int month, day, year;
    private JFrame frame, plan = null;
    private ImagePanel plan2 = null;
    private JDataBase jdb = null;

    private Connection connection;
    private Statement stmt;
    private int i;

    public Graphics graphics;

    public JLabel howMany;

    public instance(JLabel ho, final int month, final int day, final int year, JDataBase jdb, int thei, Graphics graphics, MonthDisplayManager mgr) {

        this.howMany = ho;

        this.mgr = mgr;
        this.graphics = graphics;

        this.month = month;
        this.day = day;
        this.year = year;
        this.jdb = jdb;
        this.i = thei;
        this.connection = jdb.getConnection();
        this.stmt = jdb.getStmt();
    }

    public class ImagePanel extends JPanel {

        public static final int TILED = 0;
        public static final int SCALED = 1;
        public static final int ACTUAL = 2;

        private BufferedImage image;
        private int style;
        private float alignmentX = 0.5f;
        private float alignmentY = 0.5f;

        public ImagePanel(BufferedImage image) {
            this(image, TILED);
        }

        public ImagePanel(BufferedImage image, int style) {
            this.image = image;
            this.style = style;
            setLayout(new BorderLayout());
        }

        public void setImageAlignmentX(float alignmentX) {
            this.alignmentX = alignmentX > 1.0f ? 1.0f : alignmentX < 0.0f ? 0.0f : alignmentX;
        }

        public void setImageAlignmentY(float alignmentY) {
            this.alignmentY = alignmentY > 1.0f ? 1.0f : alignmentY < 0.0f ? 0.0f : alignmentY;

        }

        public void add(JComponent component) {
            add(component, null);
        }

        public void add(JComponent component, Object constraints) {
            component.setOpaque(false);

            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                JViewport viewport = scrollPane.getViewport();
                viewport.setOpaque(false);
                Component c = viewport.getView();

                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
            }

            super.add(component, constraints);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (image == null) {
                return;
            }

            switch (style) {
                case TILED:
                    drawTiled(g);
                    break;

                case SCALED:
                    Dimension d = getSize();
                    g.drawImage(image, 0, 0, 530, 950, null);
                    break;

                case ACTUAL:
                    drawActual(g);
                    break;
            }
        }

        private void drawTiled(Graphics g) {
            Dimension d = getSize();
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            for (int x = 0; x < d.width; x += width) {
                for (int y = 0; y < d.height; y += height) {
                    g.drawImage(image, x, y, null, null);
                }
            }
        }

        private void drawActual(Graphics g) {
            Dimension d = getSize();
            float x = (d.width - image.getWidth()) * alignmentX;
            float y = (d.height - image.getHeight()) * alignmentY;
            g.drawImage(image, (int) x, (int) y, this);
        }
    }

    public void show() {
        plan = new JFrame();
        plan.setLayout(null);
        BufferedImage bimg = null;
        try {
            if(month == 1)
                bimg = ImageIO.read(getClass().getResourceAsStream("background.jpg"));

            if(month == 2)
                bimg = ImageIO.read(getClass().getResourceAsStream("background2.jpg"));

            if(month == 3)
                bimg = ImageIO.read(getClass().getResourceAsStream("background3.jpg"));

            if(month == 4)
                bimg = ImageIO.read(getClass().getResourceAsStream("background4.jpg"));

            if(month == 5)
                bimg = ImageIO.read(getClass().getResourceAsStream("background5.jpg"));

            if(month == 6)
                bimg = ImageIO.read(getClass().getResourceAsStream("background6.jpg"));

            if(month == 7)
                bimg = ImageIO.read(getClass().getResourceAsStream("background7.jpg"));

            if(month == 8)
                bimg = ImageIO.read(getClass().getResourceAsStream("background8.jpg"));

            if(month == 9)
                bimg = ImageIO.read(getClass().getResourceAsStream("background9.jpg"));

            if(month == 10)
                bimg = ImageIO.read(getClass().getResourceAsStream("background10.jpg"));

            if(month == 11)
                bimg = ImageIO.read(getClass().getResourceAsStream("background11.jpg"));

            if(month == 12)
                bimg = ImageIO.read(getClass().getResourceAsStream("background12.jpg"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        plan2 = new ImagePanel(bimg, ImagePanel.SCALED);
        plan2.setLayout(null);
        plan.setBounds(0, 0, 530, 950);
        plan2.setBounds(plan.getBounds());
        plan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        plan.getContentPane().add(plan2);
        plan.setVisible(true);

        Graphics graphics2 = plan2.getGraphics();

        JLabel l = new JLabel("For Date: ");
        l.setBounds(10, 10, 100, 20);
        l.setBackground(Color.red);
        plan2.add(l);

        JLabel l2 = new JLabel();
        l2.setBounds(80, 10, 200, 20);
        l2.setBackground(Color.red);
        plan2.add(l2);
        l2.setText(month + "/" + (i + 1) + "/" + year);

        Date d = new Date();
        int mn = d.getMonth() + 1;
        graphics2.setColor(Color.BLACK);
        graphics2.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
        graphics2.drawString("RIGHT NOW: " + mn + "/" + day + "/" + (year), 200, 10);

        JLabel l3 = new JLabel();
        l3.setBounds(320, 10, 200, 20);
        l3.setBackground(Color.red);
        plan2.add(l3);
        l3.setText("RIGHT NOW: " + mn + "/" + day + "/" + year);

        JComboBox c1 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
        c1.setBounds(10, 50, 200, 20);
        plan2.add(c1);
        JLabel cl1 = new JLabel("Time");
        cl1.setBounds(220, 50, 50, 20);
        plan2.add(cl1);
        JTextField cl1t = new JTextField();
        cl1t.setBounds(330 - 50, 50, 100, 20);
        plan2.add(cl1t);
        JComboBox c2 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
        c2.setBounds(10, 230, 200, 20);
        plan2.add(c2);
        JLabel cl2 = new JLabel("Time");
        cl2.setBounds(220, 230, 50, 20);
        plan2.add(cl2);
        JTextField cl2t = new JTextField();
        cl2t.setBounds(330 - 50, 230, 100, 20);
        plan2.add(cl2t);
        JComboBox c3 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
        c3.setBounds(10, 400, 200, 20);
        plan2.add(c3);
        JLabel cl3 = new JLabel("Time");
        cl3.setBounds(220, 400, 50, 20);
        plan2.add(cl3);
        JTextField cl3t = new JTextField();
        cl3t.setBounds(330 - 50, 400, 100, 20);
        plan2.add(cl3t);
        JComboBox c4 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
        c4.setBounds(10, 540, 200, 20);
        plan2.add(c4);
        JLabel cl4 = new JLabel("Time");
        cl4.setBounds(220, 540, 50, 20);
        plan2.add(cl4);
        JTextField cl4t = new JTextField();
        cl4t.setBounds(330 - 50, 540, 100, 20);
        plan2.add(cl4t);
        JComboBox c5 = new JComboBox(new Object[]{"Unimportant", "Important", "Urgent"});
        c5.setBounds(10, 720, 200, 20);
        plan2.add(c5);
        JLabel cl5 = new JLabel("Time");
        cl5.setBounds(220, 720, 50, 20);
        plan2.add(cl5);
        JTextField cl5t = new JTextField();
        cl5t.setBounds(330 - 50, 720, 100, 20);
        plan2.add(cl5t);

        JButton b = new JButton("Save");
        b.setBounds(10, 850, 200, 40);
        plan2.add(b);

        JButton delBttn = new JButton("Erase");
        delBttn.setBounds(240, 850, 200, 40);
        plan2.add(delBttn);

        JTextArea jta = new JTextArea();
        jta.setBackground(new Color(170, 140, 120));
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        JScrollPane jta_ = new JScrollPane(jta,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jta_.setBounds(10, 100, 500, 100);
        plan2.add(jta_);
        JTextArea jta2 = new JTextArea();
        jta2.setBackground(new Color(170, 140, 120));
        jta2.setLineWrap(true);
        jta2.setWrapStyleWord(true);
        JScrollPane jta2_ = new JScrollPane(jta2,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jta2_.setBounds(10, 260, 500, 100);
        plan2.add(jta2_);
        JTextArea jta3 = new JTextArea();
        jta3.setBackground(new Color(170, 140, 120));
        jta3.setLineWrap(true);
        jta3.setWrapStyleWord(true);
        JScrollPane jta3_ = new JScrollPane(jta3,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jta3_.setBounds(10, 420, 500, 100);
        plan2.add(jta3_);
        JTextArea jta4 = new JTextArea();
        jta4.setBackground(new Color(170, 140, 120));
        jta4.setLineWrap(true);
        jta4.setWrapStyleWord(true);
        JScrollPane jta4_ = new JScrollPane(jta4,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jta4_.setBounds(10, 580, 500, 100);
        plan2.add(jta4_);
        JTextArea jta5 = new JTextArea();
        jta5.setBackground(new Color(170, 140, 120));
        jta5.setLineWrap(true);
        jta5.setWrapStyleWord(true);
        JScrollPane jta5_ = new JScrollPane(jta5,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jta5_.setBackground(new Color(170, 140, 120));
        jta5_.setBounds(10, 740, 500, 100);
        plan2.add(jta5_);

        final int thei = i + 1;
        try {

            stmt = connection.createStatement();
            String sqlString = "select * from calendar where thedate = '" + month + "/" + thei + "/" + year + "';";
            ResultSet rs = stmt.executeQuery(sqlString);

            if (rs.next()) {
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
                if (rs.getString("c1").equals("1")) {
                    c1.setSelectedIndex(0);
                }
                if (rs.getString("c1").equals("2")) {
                    c1.setSelectedIndex(1);
                }
                if (rs.getString("c1").equals("3")) {
                    c1.setSelectedIndex(2);
                }
                if (rs.getString("c2").equals("1")) {
                    c2.setSelectedIndex(0);
                }
                if (rs.getString("c2").equals("2")) {
                    c2.setSelectedIndex(1);
                }
                if (rs.getString("c2").equals("3")) {
                    c2.setSelectedIndex(2);
                }
                if (rs.getString("c3").equals("1")) {
                    c3.setSelectedIndex(0);
                }
                if (rs.getString("c3").equals("2")) {
                    c3.setSelectedIndex(1);
                }
                if (rs.getString("c3").equals("3")) {
                    c3.setSelectedIndex(2);
                }
                if (rs.getString("c4").equals("1")) {
                    c4.setSelectedIndex(0);
                }
                if (rs.getString("c4").equals("2")) {
                    c4.setSelectedIndex(1);
                }
                if (rs.getString("c4").equals("3")) {
                    c4.setSelectedIndex(2);
                }
                if (rs.getString("c5").equals("1")) {
                    c5.setSelectedIndex(0);
                }
                if (rs.getString("c5").equals("2")) {
                    c5.setSelectedIndex(1);
                }
                if (rs.getString("c5").equals("3")) {
                    c5.setSelectedIndex(2);
                }
                pleaseInsertMe = false;
            } else {
                pleaseInsertMe = true;
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    graphics.setColor(new Color(170, 140, 120));
                    graphics.fillRect(0, 50, 1250, 760);

                    String pic = "";
                    if (month == 1) {
                        pic = "background.jpg";
                    }
                    if (month == 2) {
                        pic = "background2.jpg";
                    }
                    if (month == 3) {
                        pic = "background3.jpg";
                    }
                    if (month == 4) {
                        pic = "background4.jpg";
                    }
                    if (month == 5) {
                        pic = "background5.jpg";
                    }
                    if (month == 6) {
                        pic = "background6.jpg";
                    }
                    if (month == 7) {
                        pic = "background7.jpg";
                    }
                    if (month == 8) {
                        pic = "background8.jpg";
                    }
                    if (month == 9) {
                        pic = "background9.jpg";
                    }
                    if (month == 10) {
                        pic = "background10.jpg";
                    }
                    if (month == 11) {
                        pic = "background11.jpg";
                    }
                    if (month == 12) {
                        pic = "background12.jpg";
                    }
                    Image image = ImageIO.read(getClass().getResourceAsStream(pic));
                    graphics.drawImage(image, 680, 50, 550, 710, null);

                    stmt = connection.createStatement();

                    int c_1, c_2, c_3, c_4, c_5;
                    c_1 = -1;
                    c_2 = -2;
                    c_3 = -3;
                    c_4 = -4;
                    c_5 = -5;
                    if (c1.getSelectedIndex() == 0) {
                        c_1 = 1;
                    }
                    if (c1.getSelectedIndex() == 1) {
                        c_1 = 2;
                    }
                    if (c1.getSelectedIndex() == 2) {
                        c_1 = 3;
                    }
                    if (c2.getSelectedIndex() == 0) {
                        c_2 = 1;
                    }
                    if (c2.getSelectedIndex() == 1) {
                        c_2 = 2;
                    }
                    if (c2.getSelectedIndex() == 2) {
                        c_2 = 3;
                    }
                    if (c3.getSelectedIndex() == 0) {
                        c_3 = 1;
                    }
                    if (c3.getSelectedIndex() == 1) {
                        c_3 = 2;
                    }
                    if (c3.getSelectedIndex() == 2) {
                        c_3 = 3;
                    }
                    if (c4.getSelectedIndex() == 0) {
                        c_4 = 1;
                    }
                    if (c4.getSelectedIndex() == 1) {
                        c_4 = 2;
                    }
                    if (c4.getSelectedIndex() == 2) {
                        c_4 = 3;
                    }
                    if (c5.getSelectedIndex() == 0) {
                        c_5 = 1;
                    }
                    if (c5.getSelectedIndex() == 1) {
                        c_5 = 2;
                    }
                    if (c5.getSelectedIndex() == 2) {
                        c_5 = 3;
                    }

                    // // updateOrInsertToDB();
                    String sqlString = "update calendar set thecontent = '" + jta.getText() + "', content2 = '" + jta2.getText() + "', content3 = '" + jta3.getText() + "', content4 = '" + jta4.getText() + "', content5 = '" + jta5.getText() + "', c1 = " + c_1 + ", c2 = " + c_2 + ", c3 = " + c_3 + ", c4 = " + c_4 + ", c5 = " + c_5 + ", c1t = '" + cl1t.getText() + "', c2t = '" + cl2t.getText() + "', c3t = '" + cl3t.getText() + "', c4t = '" + cl4t.getText() + "', c5t = '" + cl5t.getText() + "' where thedate = '" + month + "/" + thei + "/" + year + "';";
                    if (pleaseInsertMe) {
                        String s = "select max(td) as ttdd from calendar;";
                        ResultSet rs = stmt.executeQuery(s);
                        int ttdd = -1;
                        if (rs.next()) {
                            ttdd = 1 + rs.getInt("ttdd");
                        }
                        sqlString = "insert into calendar (td, c1t, c2t, c3t, c4t, c5t, c1, c2, c3, c4, c5, thedate, thecontent, content2, content3, content4, content5) values (" + ttdd + ", '" + cl1t.getText() + "','" + cl2t.getText() + "','" + cl3t.getText() + "','" + cl4t.getText() + "','" + cl5t.getText() + "'," + c_1 + "," + c_2 + "," + c_3 + "," + c_4 + "," + c_5 + ",'" + month + "/" + thei + "/" + year + "','" + jta.getText() + "','" + jta2.getText() + "','" + jta3.getText() + "','" + jta4.getText() + "','" + jta5.getText() + "');";
                    }
                    stmt.execute(sqlString);

                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(new Date(year - 1900, month - 1, 1));
                    int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                    YearMonth yearMonthObject = YearMonth.of((year), month);
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, stmt);

                    Connection conn = jdb.getConnection();
                    Statement st = jdb.getStmt();
                    try {
                        String s = "select count(td) as cnt from calendar;";
                        ResultSet rs = st.executeQuery(s);
                        if (rs.next()) {
                            graphics.setColor(Color.BLACK);
                            graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                            graphics.drawString("How Many: " + rs.getInt("cnt"), 100, 600);
                            howMany.setText("How Many: " + rs.getInt("cnt"));
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                } catch (Exception sqle) {
                    sqle.printStackTrace();
                }

                plan.dispose();

                Date d = new Date();
                int mn = d.getMonth() + 1;
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));

                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                graphics.drawString(month + "-" + (year), 100, 100);
            }
        });
        delBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    graphics.setColor(new Color(170, 140, 120));
                    graphics.fillRect(0, 50, 1250, 760);

                    String pic = "";
                    if (month == 1) {
                        pic = "background.jpg";
                    }
                    if (month == 2) {
                        pic = "background2.jpg";
                    }
                    if (month == 3) {
                        pic = "background3.jpg";
                    }
                    if (month == 4) {
                        pic = "background4.jpg";
                    }
                    if (month == 5) {
                        pic = "background5.jpg";
                    }
                    if (month == 6) {
                        pic = "background6.jpg";
                    }
                    if (month == 7) {
                        pic = "background7.jpg";
                    }
                    if (month == 8) {
                        pic = "background8.jpg";
                    }
                    if (month == 9) {
                        pic = "background9.jpg";
                    }
                    if (month == 10) {
                        pic = "background10.jpg";
                    }
                    if (month == 11) {
                        pic = "background11.jpg";
                    }
                    if (month == 12) {
                        pic = "background12.jpg";
                    }
                    Image image = ImageIO.read(getClass().getResourceAsStream(pic));
                    graphics.drawImage(image, 680, 50, 550, 710, null);

                    stmt = connection.createStatement();
                    String sqlString = "delete from calendar where thedate = '" + month + "/" + thei + "/" + year + "';";
                    stmt.execute(sqlString);

                    plan.dispose();

                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(new Date(year - 1900, month - 1, 1));
                    int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                    YearMonth yearMonthObject = YearMonth.of((year), month);
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, stmt);

                    Date d = new Date();
                    int mn = d.getMonth() + 1;
                    graphics.setColor(Color.BLACK);
                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 10));
                    graphics.drawString(mn + "/" + day + "/" + (year), 100, 60);
                    graphics.setColor(Color.BLACK);
                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));

                    graphics.setColor(Color.BLACK);
                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                    graphics.drawString(month + "-" + (year), 100, 100);

                    Connection conn = jdb.getConnection();
                    Statement st = jdb.getStmt();
                    try {
                        String s = "select count(td) as cnt from calendar;";
                        ResultSet rs = st.executeQuery(s);
                        if (rs.next()) {
                            graphics.setColor(Color.BLACK);
                            graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                            graphics.drawString("How Many: " + rs.getInt("cnt"), 100, 600);
                            howMany.setText("How Many: " + rs.getInt("cnt"));
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } catch (Exception sqle) {
                    sqle.printStackTrace();
                }
            }
        });
    }
}