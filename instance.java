import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import db.JDataBase;

public class instance {
    private MonthDisplayManager mgr = null;
    
    private boolean pleaseInsertMe = false;
    
    private int month, day, year;
    private JFrame frame, plan = null;
    private JPanel  plan2 = null;
    private JDataBase  jdb = null;
    
    private Connection connection;
    private Statement stmt;
    private int i;
    
    public Graphics  graphics;

    public instance(final int month, final int day, final int year, JDataBase jdb, int thei, Graphics graphics, MonthDisplayManager mgr) {
        this.mgr = mgr;
        this.graphics = graphics;

        this.month = month; this.day = day; this.year = year; this.jdb = jdb;
        this.i = thei;
        this.connection = jdb.getConnection();
        this.stmt = jdb.getStmt();
    }

    public void show() {
        plan = new JFrame();
        plan.setLayout(null);
        plan2 = new JPanel();
        plan2.setBackground(new Color(190, 200, 210));
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

        JButton delBttn = new JButton("Erase");
        delBttn.setBounds(240, 850, 200, 40);
        plan2.add(delBttn);

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

            stmt = connection.createStatement();
            String sqlString = "select * from calendar where thedate = '"+month + "/" + thei + "/" + year+"';";
            ResultSet rs = stmt.executeQuery(sqlString);

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

                    stmt = connection.createStatement();

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
                        ResultSet rs = stmt.executeQuery(s);
                        int ttdd = -1;
                        if(rs.next()) {
                            ttdd = 1 + rs.getInt("ttdd");
                        }
                        sqlString = "insert into calendar (td, c1t, c2t, c3t, c4t, c5t, c1, c2, c3, c4, c5, thedate, thecontent, content2, content3, content4, content5) values ("+ttdd+", '"+cl1t.getText()+"','"+cl2t.getText()+"','"+cl3t.getText()+"','"+cl4t.getText()+"','"+cl5t.getText()+"',"+c_1+","+c_2+","+c_3+","+c_4+","+c_5+",'"+month + "/" + thei + "/" + year+"','"+jta.getText()+"','"+jta2.getText()+"','"+jta3.getText()+"','"+jta4.getText()+"','"+jta5.getText()+"');";
                    }
                    stmt.execute(sqlString);

                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(new Date(year - 1900, month - 1, 1));
                    int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                    YearMonth yearMonthObject = YearMonth.of((year), month);
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, stmt);

                } catch(SQLException sqle) {
                    sqle.printStackTrace();
                }

                plan.dispose();
            }
        });
        delBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    stmt = connection.createStatement();
                    String sqlString = "delete from calendar where thedate = '"+month + "/" + thei + "/" + year+"';";
                    stmt.execute(sqlString);

                    plan.dispose();

                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(new Date(year - 1900, month - 1, 1));
                    int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                    YearMonth yearMonthObject = YearMonth.of((year), month);
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    mgr.displayDayz(month, day, year, daysInMonth, dayOfWeek, graphics, connection, stmt);
                } catch(SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        });
    }   
}