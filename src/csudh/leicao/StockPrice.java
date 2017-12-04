package csudh.leicao;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by caolei on 10/30/17.
 */
public class StockPrice implements java.io.Serializable, Comparable {
    private double prevPrice, currPrice;
    private LocalDateTime dateTime;

    public StockPrice () {
        prevPrice = 0.0;
        currPrice = 0.0;
        dateTime = LocalDateTime.now();
    }

    public StockPrice(double prevPrice, double currPrice, LocalDateTime dateTime) {
        this.prevPrice = prevPrice;
        this.currPrice = currPrice;
        this.dateTime = dateTime;
    }

    // Getters
    public double getPrevPrice() {
        return prevPrice;
    }

    public double getCurrPrice() {
        return currPrice;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Setters
    public void setPrevPrice(double prevPrice) {
        this.prevPrice = prevPrice;
    }

    public void setCurrPrice(double currPrice) {
        this.currPrice = currPrice;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    protected void setToTodayAndTimeAt10am(){
        this.dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10,0,0));
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Object o) {
        StockPrice obj = (StockPrice)o;
        if (currPrice < obj.getCurrPrice())
            return -1;
        else if(currPrice == obj.getCurrPrice())
            return 0;
        else
            return 1;
    }

    @Override
    public boolean equals(Object o) {
        StockPrice obj = (StockPrice)o;
        if (currPrice == obj.getCurrPrice())
            return true;
        return false;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        String dateTimeString = dateTime.toString();
        sb.append(dateTimeString.substring(0,10) + ", ");
        sb.append(dateTimeString.substring(11,16) + ", ");
        sb.append("Price: " + decimalFormat.format(currPrice));

        return sb.toString();
    }
}
