package project.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table
public class History{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    String fromCurrency;

    @Column
    String toCurrency;

    @Column
    long time;

    @Column
    double close;

    @Column
    double high;

    @Column
    double low;

    @Column
    double open;

    @Column
    double volumefrom;

    @Column
    double volumeto;

    @Column
    String timesignal;

    @Column
    Date date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getVolumefrom() {
        return volumefrom;
    }

    public void setVolumefrom(double volumefrom) {
        this.volumefrom = volumefrom;
    }

    public double getVolumeto() {
        return volumeto;
    }

    public void setVolumeto(double volumeto) {
        this.volumeto = volumeto;
    }

    public String getTimesignal() {
        return timesignal;
    }

    public void setTimesignal(String timesignal) {
        this.timesignal = timesignal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(long time) {
        long timemillis = time*1000;
        Date dateTime = new Date(timemillis);
        this.date = dateTime;
    }
}
