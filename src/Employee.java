public class Employee {
    //Instance Fields
    private String fname, lname, department, position, type;
    private String code;
    private String salary;
    //Constructor
    public Employee(String code, String fname, String lname, String department, String position, String type, String salary){
        this.code = code;
        this.fname = fname;
        this.lname = lname;
        this.department = department;
        this.position = position;
        this.type = type;
        this.salary = salary;
    }
    //Getter and Setter
}
