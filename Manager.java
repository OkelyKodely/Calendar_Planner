import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Manager {
    
    private static Manager m;
  
    private Manager(){}
  
    public static Manager getSingleton() 
    { 
        if (m == null) 
            m = new Manager();
  
        return m; 
    }     
    
    public void displayDayz(int month, int day, int year, final int daysInMonth, final int dayOfWeek, final Graphics graphics, final Connection connection, java.sql.Statement statement) {
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
                        
                        String sqlString = "select * from calendar where thedate = '"+month + "/" + thei + "/" + year+"';";
                        rs = statement.executeQuery(sqlString);
                        if(rs.next()) {
                            graphics.setColor(Color.GREEN);
                            graphics.drawString("---",100+x+10-12,y+200+40-20);
                        }
                    } catch(SQLException sqle) {
                        sqle.printStackTrace();
                    }

                    if(((dayOfWeek-1)+i)%7 == 0) {
                        x = 0;
                        y += 70;
                    }
                    if (i==day-1 && month == new Date().getMonth()+1 && year == new Date().getYear()+1900) {
                        graphics.setColor(Color.BLUE);
                        graphics.drawOval(100+x-25+20,y+200-42,50,50);
                    }

                    graphics.setFont(new Font("Serif", Font.TRUETYPE_FONT, 40));
                    graphics.setColor(Color.lightGray);
                    graphics.drawRect(100+x-20,y+200-20-80+50,70,70);
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
}