package Interface;

import Bins.Department;
import Bins.Employee;
import Exceptions.DepartmentException;
import Exceptions.EmployeeException;

import java.util.List;

public interface Intr {

//   ############# for admin ################

    public void registerNewEmployee(String ename,  String email, String password,int deptid ) ;

    public void addNewDepartment(String dname, String location);

    public void transferEmployeeToDepartment(int eid, int did) ;

    public void grantLeaveRequest();

    public Employee getEmployeeDetailsById(int eid);

    public List<Department> getAllDepartmentDetails();

    public List<Employee> getAllEmployeeDetails();

//    ####### for employee ############

    public void changePassword(int id , String password);

    public void changeEmail(int id, String email);

    public void requestForLeave(int id);

    public int loginEmployee(String email , String password);

    public boolean adminLogin(String email, String password);

}
