package BusinessLogicLayer;

import DataAccessLayer.DALEmployee;
import Entity.EntityEmployee;

import java.util.List;

public class BLLEmployee {
    private DALEmployee dalEmployee = new DALEmployee();

    public List<EntityEmployee> getAllEmployees() {
        return dalEmployee.getAllEmployees();
    }
    public boolean addEmployee(EntityEmployee employee) {
        return dalEmployee.addEmployee(employee);
    }
    public void updateEmployee(EntityEmployee employee) {
        dalEmployee.updateEmployee(employee);
    }

    public void deleteEmployee(int employeeId) {
        dalEmployee.deleteEmployee(employeeId);
    }

}
