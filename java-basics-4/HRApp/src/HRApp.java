
public class HRApp {
    public static void main(String[] args) {

        Department department = new Department("Department Education");

        department.addEmployee(new Employee("Ann", 1234.56));
        department.addEmployee(new Employee("Bob", 1200.34));
        department.addEmployee(new Employee("Ray", 1122.33));

        System.out.println("HR App Starts");
        System.out.println(department);

        for (Employee employee : department.getEmployees()) {
            System.out.println(employee);
        }

        System.out.println("Total salary: " + department.getDepartmentEmployeesSalary());
        System.out.println("Average salary: " + department.getDepartmentEmployeesAverageSalary());
    }
}