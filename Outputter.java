package cereal;

public class Outputter implements java.io.Serializable {
    public int month, date, year;
    public Outputter(int m, int d, int y) {
        month = m;
        date = d;
        year = y;
    }
    public Object getObj() {
        class Obj {
            private int month = -2, date = -3, year = -5;
        };
        Obj obj = new Obj();
        obj.month = month;
        obj.date = date;
        obj.year = year;
        return obj;
    }
}