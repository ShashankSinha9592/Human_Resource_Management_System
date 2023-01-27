package Models;

import Colors.ColorString;

public class Employee {
    private int eid;
    private String name;
    private String email;
    private String password;
    private int deptid;
    private String empLeave;

    public Employee() {
    }

    public Employee(int eid, String name, String email, String password, int deptid, String empLeave) {
        this.eid = eid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.deptid = deptid;
        this.empLeave = empLeave;
    }

    @Override
    public String toString() {
        return ColorString.TEXT_BRIGHT_CYAN+ "Employee{" +ColorString.TEXT_RESET+ColorString.TEXT_BRIGHT_YELLOW+
                "eid = " + eid +
                ", name = '" + name + '\'' +
                ", email = '" + email + '\'' +
                ", password = '" + password + '\'' +
                ", deptid = '" + deptid + '\'' +
                ", empLeave = '" + empLeave + '\'' +
                '}'+ColorString.TEXT_RESET;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }

    public String getEmpLeave() {
        return empLeave;
    }

    public void setEmpLeave(String empLeave) {
        this.empLeave = empLeave;
    }
}
