package Models;

import Colors.ColorString;

public class Department {
    private int did;
    private String dname;
    private String location;

    public Department() {
    }

    public Department(int did, String dname, String location) {
        this.did = did;
        this.dname = dname;
        this.location = location;
    }

    @Override
    public String toString() {
        return ColorString.TEXT_BRIGHT_CYAN+ "Department{" +ColorString.TEXT_RESET+ColorString.TEXT_BRIGHT_YELLOW+
                "did=" + did +
                ", dname='" + dname + '\'' +
                ", location='" + location + '\'' +
                '}'+ColorString.TEXT_RESET;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
