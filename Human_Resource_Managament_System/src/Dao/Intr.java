package Dao;

import Models.Department;
import Models.Employee;
import Exceptions.AdminException;
import Exceptions.DepartmentException;
import Exceptions.EmployeeException;

import java.sql.SQLException;
import java.util.List;

public interface Intr {

//   ############# for admin ################

    public void registerNewEmployee(String ename,  String email, String password,int deptid ) throws EmployeeException, DepartmentException, SQLException;

    public void addNewDepartment(String dname, String location) throws SQLException, DepartmentException;

    public void transferEmployeeToDepartment(int eid, int did) throws EmployeeException, DepartmentException, SQLException;

    public void grantLeaveRequest();

    public Employee getEmployeeDetailsById(int eid) throws EmployeeException, SQLException;

    public List<Department> getAllDepartmentDetails() throws DepartmentException, SQLException;

    public List<Employee> getAllEmployeeDetails() throws EmployeeException, SQLException;

//    ####### for employee ############

    public void changePassword(int id , String password) throws EmployeeException, SQLException;

    public void changeEmail(int id, String email) throws EmployeeException, SQLException;

    public void requestForLeave(int id) throws EmployeeException, SQLException;

    public int loginEmployee(String email , String password) throws Exception;

    public boolean adminLogin(String email, String password) throws AdminException;

}
