package Dao;

import Models.Department;
import Models.Employee;
import Colors.ColorString;
import ConnectionProvider.ConnectionProvider;
import Exceptions.AdminException;
import Exceptions.DepartmentException;
import Exceptions.EmployeeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class IntrImpl implements  Intr{

    @Override
    public void registerNewEmployee(String ename,  String email, String password, int deptid) throws EmployeeException, DepartmentException, SQLException {
        Employee emp = null;

        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps1 = conn.prepareStatement("Select ename from employee where email = ?");
            ps1.setString(1, email);
            ResultSet rs  = ps1.executeQuery();

            if(rs.next()){
                throw new EmployeeException("Employee already exists with email : "+email);
            }

            PreparedStatement ps3 = conn.prepareStatement("select dname from department where did = ?");
            ps3.setInt(1,deptid);
            ResultSet rs2 = ps3.executeQuery();
            if(rs2.next()){
                PreparedStatement ps2 = conn.prepareStatement("insert into employee (ename, email,password, deptid) values (?,?,?,?)");
                ps2.setString(1, ename);
                ps2.setString(2, email);
                ps2.setString(3, password);
                ps2.setInt(4, deptid);


                int x = ps2.executeUpdate();

                if (x > 0) {
                    System.out.println(ColorString.TEXT_BRIGHT_GREEN + "Employee added successfully" + ColorString.TEXT_RESET);

                } else {
                    System.out.println(ColorString.TEXT_BRIGHT_RED + "Invalid department Id or email already exists" + ColorString.TEXT_RESET);

                }
            }
            else{
                throw new DepartmentException("Department does not exists with department id : "+deptid);
            }

        }
        catch (SQLException e){
            throw new SQLException("Invalid department Id or email already exists");
        }


    }

    @Override
    public void addNewDepartment(String dname, String location) throws DepartmentException, SQLException {

        try(Connection conn = ConnectionProvider.getConnection()){

           PreparedStatement ps1 =  conn.prepareStatement("select dname from department where dname = ?");
           ps1.setString(1,dname);

           ResultSet rs = ps1.executeQuery();

           if(rs.next()){
               throw new DepartmentException("Department already exists with same name : "+dname);
           }

            else {
               PreparedStatement ps = conn.prepareStatement("insert into department (dname, location) values (?,?) ");
               ps.setString(1, dname);
               ps.setString(2, location);

               int x = ps.executeUpdate();
               if (x > 0) {
                   System.out.println(ColorString.TEXT_BRIGHT_GREEN + "New Department added successfully" + ColorString.TEXT_RESET);
               } else {
                   System.out.println(ColorString.TEXT_BRIGHT_RED + "Something went wrong" + ColorString.TEXT_RESET);
               }
           }

        }
        catch (SQLException e){
//            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
            throw new SQLException(e.getMessage());

        }


    }

    @Override
    public void transferEmployeeToDepartment(int eid, int did) throws EmployeeException, DepartmentException, SQLException {
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps1 = conn.prepareStatement("select ename from employee where eid = ?");
            ps1.setInt(1,eid);

            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()==false){
                throw new EmployeeException("Employee does not exist with id : "+eid);
            }

            PreparedStatement ps2 = conn.prepareStatement("select dname from department where did = ?");
            ps2.setInt(1,did);

            ResultSet rs2 = ps2.executeQuery();
            if(rs2.next()==false){
                throw new DepartmentException("Department does not exixts with id : "+did);
            }

            else{
                PreparedStatement ps3 = conn.prepareStatement("Update employee set deptid = ? where eid = ?");
                ps3.setInt(1,did);
                ps3.setInt(2,eid);

                int x = ps3.executeUpdate();
                if(x>0){
                    System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Employee's department has been changed successfully"+ColorString.TEXT_RESET);
                }
                else{
                    System.out.println(ColorString.TEXT_BRIGHT_RED+"Invalid Employee Id or Department Id");
                }
            }

        }
        catch (SQLException e){
           throw new SQLException(e.getMessage());
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
    public Employee getEmployeeDetailsById(int eid) throws EmployeeException, SQLException {
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
//                System.out.println(ColorString.TEXT_BRIGHT_RED+"No employee has Id : "+eid+ColorString.TEXT_RESET);
                throw new EmployeeException("Employee does not exists with eid : "+eid);
            }


        }
        catch (SQLException e){
//            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
            throw new SQLException(e.getMessage());
        }

        return emp;

    }

    @Override
    public List<Department> getAllDepartmentDetails() throws DepartmentException, SQLException {

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
//                System.out.println(ColorString.TEXT_BRIGHT_RED+"No department available"+ColorString.TEXT_RESET);
                throw new DepartmentException("No departments available");
            }



        }
        catch (SQLException e){
//            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
            throw new SQLException(e.getMessage());
        }


        return  departments;

    }

    @Override
    public List<Employee> getAllEmployeeDetails() throws EmployeeException, SQLException {
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
//                System.out.println(ColorString.TEXT_BRIGHT_RED+"No Employees found"+ColorString.TEXT_RESET);
                throw new EmployeeException("Employees not found");
            }


        }
        catch (SQLException e){
//            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
            throw new SQLException(e.getMessage());
        }

        return employees;

    }

    @Override
    public void changePassword(int id, String password) throws EmployeeException, SQLException {

        try(Connection conn = ConnectionProvider.getConnection()){



            PreparedStatement ps = conn.prepareStatement("Update employee set password = ? where eid = ?");
           ps.setString(1,password);
           ps.setInt(2,id);
            int x = ps.executeUpdate();
           if(x>0){
               System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Password updated Successfully"+ColorString.TEXT_RESET);
           }
           else{
//               System.out.println(ColorString.TEXT_BRIGHT_RED+"password updation Unsuccessfull"+ColorString.TEXT_RESET);
               throw new EmployeeException("Something went wrong");
           }

        }
        catch (SQLException e){
//            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
            throw new SQLException(e.getMessage());
        }


    }

    @Override
    public void changeEmail(int id, String email) throws EmployeeException, SQLException {
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("Update employee set email = ? where eid = ?");
            ps.setString(1,email);
            ps.setInt(2,id);
            int x = ps.executeUpdate();
            if(x>0){
                System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Email updated Successfully"+ColorString.TEXT_RESET);
            }
            else{
//                System.out.println(ColorString.TEXT_BRIGHT_RED+"Email updation Unsuccessfull"+ColorString.TEXT_RESET);
                throw new EmployeeException("Something went wrong");

            }

        }
        catch (SQLException e){
//            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void requestForLeave(int id) throws EmployeeException, SQLException {
        try(Connection conn = ConnectionProvider.getConnection()){

            PreparedStatement ps = conn.prepareStatement("Update employee set empleave = 'pending' where eid = ?");
            ps.setInt(1,id);
            int x = ps.executeUpdate();
            if(x>0){
                System.out.println(ColorString.TEXT_BRIGHT_GREEN+"Requested for the leave .. Waiting for admin's permission to confirm"+ColorString.TEXT_RESET);
            }
            else{
//                System.out.println(ColorString.TEXT_BRIGHT_RED+"Please enter valid id"+ColorString.TEXT_RESET);
                throw new EmployeeException("please enter valid id");

            }

        }
        catch (SQLException e){
//            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
            throw new SQLException(e.getMessage());
        }



    }

    @Override
    public int loginEmployee(String email, String password) throws EmployeeException, SQLException {
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
//                System.out.println(ColorString.TEXT_BRIGHT_RED+"Invalid email or password"+ColorString.TEXT_RESET);
                throw new EmployeeException("Invalid email or password");
            }

        }
        catch (SQLException e){
//            System.out.println(ColorString.TEXT_BRIGHT_RED+e.getMessage()+ColorString.TEXT_RESET);
            throw new SQLException(e.getMessage());
        }
        return id;
    }

    @Override
    public boolean adminLogin(String email, String password) throws AdminException {
        boolean flag = false;

        String emailcheck = "admin@123.com";
        String passwordCheck = "admin";

        if (emailcheck.equals(email) && passwordCheck.equals(password)) {

            flag = true;
        }
        else{
            throw new AdminException("Invalid Username or password ");
        }

        return flag;


    }
}
