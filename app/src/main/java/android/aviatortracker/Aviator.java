package android.aviatortracker;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Aviator {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    protected int id;
    @ColumnInfo(name = "status")
    protected String status;
    @ColumnInfo(name = "srcCode")
    protected String srcCode;
    @ColumnInfo(name = "srcName")
    protected String srcName;
    @ColumnInfo(name = "srcTerminal")
    protected String srcTerminal;
    @ColumnInfo(name = "srcGate")
    protected String srcGate;
    @ColumnInfo(name = "srcSched")
    protected String srcSched;
    @ColumnInfo(name = "srcDelay")
    protected String srcDelay;

    @ColumnInfo(name = "dstCode")
    protected String dstCode;
    @ColumnInfo(name = "dstName")
    protected String dstName;
    @ColumnInfo(name = "dstTerminal")
    protected String dstTerminal;
    @ColumnInfo(name = "dstGate")
    protected String dstGate;
    @ColumnInfo(name = "dstSched")
    protected String dstSched;
    @ColumnInfo(name = "dstDelay")
    protected String dstDelay;

    @ColumnInfo(name = "flightCode")
    protected String flightCode;
    @ColumnInfo(name = "airline")
    protected String airline;

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSrcCode(String srcCode) {
        this.srcCode = srcCode;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public void setSrcTerminal(String srcTerminal) {
        this.srcTerminal = srcTerminal;
    }

    public void setSrcGate(String srcGate) {
        this.srcGate = srcGate;
    }

    public void setSrcSched(String srcSched) {
        this.srcSched = srcSched;
    }

    public void setSrcDelay(String srcDelay) {
        this.srcDelay = srcDelay;
    }

    public void setDstCode(String dstCode) {
        this.dstCode = dstCode;
    }

    public void setDstName(String dstName) {
        this.dstName = dstName;
    }

    public void setDstTerminal(String dstTerminal) {
        this.dstTerminal = dstTerminal;
    }

    public void setDstGate(String dstGate) {
        this.dstGate = dstGate;
    }

    public void setDstSched(String dstSched) {
        this.dstSched = dstSched;
    }

    public void setDstDelay(String dstDelay) {
        this.dstDelay = dstDelay;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }



    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getSrcCode() {
        return srcCode;
    }

    public String getSrcName() {
        return srcName;
    }

    public String getSrcTerminal() {
        return srcTerminal;
    }

    public String getSrcGate() {
        return srcGate;
    }

    public String getSrcSched() {
        return srcSched;
    }

    public String getSrcDelay() {
        return srcDelay;
    }

    public String getDstCode() {
        return dstCode;
    }

    public String getDstName() {
        return dstName;
    }

    public String getDstTerminal() {
        return dstTerminal;
    }

    public String getDstGate() {
        return dstGate;
    }

    public String getDstSched() {
        return dstSched;
    }

    public String getDstDelay() {
        return dstDelay;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getAirline() {
        return airline;
    }


    public Aviator (String status,
                    String srcCode, String srcName, String srcTerminal,
                    String srcGate, String srcSched, String srcDelay,
                    String dstCode, String dstName, String dstTerminal,
                    String dstGate, String dstSched, String dstDelay,
                    String flightCode, String airline) {

        this.status = status;

        this.srcCode = srcCode;
        this.srcName = srcName;
        this.srcTerminal = srcTerminal;
        this.srcGate = srcGate;
        this.srcSched = srcSched;
        this.srcDelay = srcDelay;

        this.dstCode = dstCode;
        this.dstName = dstName;
        this.dstTerminal = dstTerminal;
        this.dstGate = dstGate;
        this.dstSched = dstSched;
        this.dstDelay = dstDelay;

        this.flightCode = flightCode;
        this.airline = airline;
    }

    public Aviator(){}

}
