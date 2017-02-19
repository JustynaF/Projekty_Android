package justynafirkowska.datacollector;

public class History {
    private String msg;
    private String date;

    public History(String msg, String date) {
        this.msg = msg;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg + ": " + this.date;
    }
}
