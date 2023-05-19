public class Payroll {
    //Instance Class
    private Employee employee;
    private double teachingHours, payRate, overtimePay, allowance, deductions;
    //Constructor for Teaching Employee
    public Payroll(Employee employee, double teachingHours, double payRate, double overloadPay, double allowance, double deductions){
        this.employee = employee;
        this.teachingHours = teachingHours;
        this.payRate = payRate;
        this.overtimePay = overloadPay;
        this.allowance = allowance;
        this.deductions = deductions;
    }
    //Constructor for Non-Teaching Employee
    public Payroll(Employee employee, double payRate, double overtimePay, double allowance, double deductions){
        this.employee = employee;
        this.payRate = payRate;
        this.overtimePay = overtimePay;
        this.allowance = allowance;
        this.deductions = deductions;
    }
    //Getter and Setter
}
