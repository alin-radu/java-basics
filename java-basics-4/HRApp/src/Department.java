import java.util.Arrays;
import java.util.Objects;

public class Department {
    private String name;
    private final Employee[] employees = new Employee[10];

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null) {
                employees[i] = employee;
                break;
            }
        }
    }

    public Employee[] getEmployees() {
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .toArray(Employee[]::new);
    }

    public int getEmployeesNumber() {
        return (int) Arrays.stream(employees)
                .filter(Objects::nonNull)
                .count();
    }

    public Employee getEmployee(int employeeId) {
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(emp -> emp.getId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    public double getDepartmentEmployeesSalary() {
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public double getDepartmentEmployeesAverageSalary() {
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    @Override
    public String toString() {
        return "Department: " + " " + name;
    }
}
