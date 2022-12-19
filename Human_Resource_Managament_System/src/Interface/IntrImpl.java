package Interface;

import Bins.Department;
import Bins.Employee;
import Colors.ColorString;
import Connections.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class IntrImpl implements  Intr{

    @Override
    public void registerNewEmployee(String ename,  String email, String password, int deptid){
        Employee emp = null;

        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("insert into employee (ename, email,password, deptid) values (?,?,?,?)");
            ps.setString(1,ename);
            ps.setString(2,email);
            ps.setString(3,password);
            ps.setInt(4,deptid);

           int x =  ps.executeUpdate();

           if(x>0){
               System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Employee added successfully"+ColorString.TEXT_RESET);

           }
           else{
               System.out.println(ColorString.TEXT_BRIGHT_RED+"Invalid department Id or email already exists"+ColorString.TEXT_RESET);

           }

        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+"Invalid department Id or email already exists"+ColorString.TEXT_RESET);
        }


    }

    @Override
    public void addNewDepartment(String dname, String location) {

        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("insert into department (dname, location) values (?,?) ");
            ps.setString(1,dname);
            ps.setString(2,location);

            int x = ps.executeUpdate();
            if(x>0){
                System.out.println(ColorString.TEXT_BRIGHT_GREEN+"New Department added successfully"+ColorString.TEXT_RESET);
            }
            else{
                System.out.println(ColorString.TEXT_BRIGHT_RED+"Something went wrong"+ColorString.TEXT_RESET);
            }

        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }


    }

    @Override
    public void transferEmployeeToDepartment(int eid, int did) {
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("Update employee set deptid = ? where eid = ?");
            ps.setInt(1,did);
            ps.setInt(2,eid);

            int x = ps.executeUpdate();
            if(x>0){
                System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Employee's department has been changed successfully"+ColorString.TEXT_RESET);
            }
            else{
                System.out.println(ColorString.TEXT_BRIGHT_RED+"Invalid Employee Id or Department Id");
            }

        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+"Invalid Employee Id or Department Id"+ColorString.TEXT_RESET);
        }



    }

    @Override
    public void grantLeaveRequest() {
        Scanner sc = new Scanner(System.in);
        List<String> empList = new ArrayList<>();
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("select ename from employee where empleave = 'pending'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                empList.add(rs.getString("ename"));
            }
            if(empList.size()==0){
                System.out.println(ColorString.TEXT_BRIGHT_RED+"No leave request"+ColorString.TEXT_RESET);
            }
            else{
                System.out.println(ColorString.TEXT_BRIGHT_GREEN+empList.size()+" leave requests"+ColorString.TEXT_RESET);
                System.out.println("Press 1 to grant leave permission");
                System.out.println("Press 2 to decline leave request");
                int n = sc.nextInt();
                if(n==1){
                    try(Connection conn2 = ConnectionProvider.getConnection()) {

                        PreparedStatement ps2 = conn.prepareStatement("update employee set empleave = 'Accepted' where empleave = 'pending'");
                        int x2 = ps2.executeUpdate();
                        if(x2>0){
                            System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Permission granted"+ColorString.TEXT_RESET);
                        }
                        else {
                            System.out.println(ColorString.TEXT_BRIGHT_RED+"Something went wrong"+ColorString.TEXT_RESET);
                        }
                    }
                    catch (SQLException e){
                        System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                    }
                }
                else if(n==2){
                    try(Connection conn2 = ConnectionProvider.getConnection()) {

                        PreparedStatement ps2 = conn.prepareStatement("update employee set empleave = 'Declined' where empleave = 'pending'");
                        int x2 = ps2.executeUpdate();
                        if(x2>0){
                            System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Permission Declined"+ColorString.TEXT_RESET);
                        }
                        else {
                            System.out.println(ColorString.TEXT_BRIGHT_RED+"Something went wrong"+ColorString.TEXT_RESET);
                        }
                    }
                    catch (SQLException e){
                        System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
                    }
                }
                else{
                    System.out.println(ColorString.TEXT_BRIGHT_CYAN+"Please enter 1 or 2"+ColorString.TEXT_RESET);
                }
            }

        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }



    }

    @Override
    public Employee getEmployeeDetailsById(int eid) {
        Employee emp = null;
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("select * from employee where eid = ?");
            ps.setInt(1,eid);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                emp = new Employee();
                emp.setEid(rs.getInt("eid"));
                emp.setName(rs.getString("ename"));
                emp.setEmail(rs.getString("email"));
                emp.setPassword(rs.getString("password"));
                emp.setEmpLeave(rs.getString("empleave"));
                emp.setDeptid(rs.getInt("deptid"));

            }
            else{
                System.out.println(ColorString.TEXT_BRIGHT_RED+"No employee has Id : "+eid+ColorString.TEXT_RESET);
            }


        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }

        return emp;

    }

    @Override
    public List<Department> getAllDepartmentDetails() {

        List<Department> departments = new ArrayList<>();
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("select * from department");

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Department d = new Department();
                d.setDid(rs.getInt("did"));
                d.setDname(rs.getString("dname"));
                d.setLocation(rs.getString("location"));

                departments.add(d);

            }

            if(departments.size()==0){
                System.out.println(ColorString.TEXT_BRIGHT_RED+"No department available"+ColorString.TEXT_RESET);
            }



        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }

        return  departments;

    }

    @Override
    public List<Employee> getAllEmployeeDetails() {
        List<Employee> employees = new ArrayList<>();

        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("select * from employee");

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
               Employee emp = new Employee();
                emp.setEid(rs.getInt("eid"));
                emp.setName(rs.getString("ename"));
                emp.setEmail(rs.getString("email"));
                emp.setPassword(rs.getString("password"));
                emp.setEmpLeave(rs.getString("empleave"));
                emp.setDeptid(rs.getInt("deptid"));

                employees.add(emp);
            }

            if(employees.size()==0){
                System.out.println(ColorString.TEXT_BRIGHT_RED+"No Employees found"+ColorString.TEXT_RESET);
            }


        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }

        return employees;

    }

    @Override
    public void changePassword(int id, String password) {

        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("Update employee set password = ? where eid = ?");
           ps.setString(1,password);
           ps.setInt(2,id);
            int x = ps.executeUpdate();
           if(x>0){
               System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Password updated Successfully"+ColorString.TEXT_RESET);
           }
           else{
               System.out.println(ColorString.TEXT_BRIGHT_RED+"password updation Unsuccessfull"+ColorString.TEXT_RESET);
           }

        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }


    }

    @Override
    public void changeEmail(int id, String email) {
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("Update employee set email = ? where eid = ?");
            ps.setString(1,email);
            ps.setInt(2,id);
            int x = ps.executeUpdate();
            if(x>0){
                System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Email updated Successfully"+ColorString.TEXT_RESET);
            }
            else{
                System.out.println(ColorString.TEXT_BRIGHT_RED+"Email updation Unsuccessfull"+ColorString.TEXT_RESET);
            }

        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }
    }

    @Override
    public void requestForLeave(int id) {
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("Update employee set empleave = 'pending' where eid = ?");
            ps.setInt(1,id);
            int x = ps.executeUpdate();
            if(x>0){
                System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Requested for the leave .. Waiting for admin's permission to confirm"+ColorString.TEXT_RESET);
            }
            else{
                System.out.println(ColorString.TEXT_BRIGHT_RED+"Please enter valid id"+ColorString.TEXT_RESET);
            }

        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }



    }

    @Override
    public int loginEmployee(String email, String password) {
        int id = -1;

        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("Select eid, ename from employee where email = ? and password = ?");
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("eid");
                System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Login Successfull"+ColorString.TEXT_RESET);

                System.out.println(ColorString.TEXT_BRIGHT_PURPLE+"Welcome "+rs.getString("ename")+ColorString.TEXT_RESET);
            }
            else{
                System.out.println(ColorString.TEXT_BRIGHT_RED+"Invalid email or password"+ColorString.TEXT_RESET);
            }

        }
        catch (SQLException e){
            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
        }
        return id;
    }

    @Override
    public boolean adminLogin(String email, String password) {
        boolean flag = false;

        String emailcheck = "admin@123.com";
        String passwordCheck = "admin";

        if (emailcheck.equals(email) && passwordCheck.equals(password)) {

            flag = true;
        }

        return flag;


    }
}
