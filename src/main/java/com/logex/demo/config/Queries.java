package com.logex.demo.config;

public class Queries {


    public static String employeeFilter = "Select a.first_name,a.last_name,a.dob,a.cnic,a.cnic_expiry_date,a.license_number,a.license_class," +
            "a.license_expiry_date,a.salary,a.city,a.state,a.country,a.phone_number,a.employee_type,a.job_status,a.address from employee a\n" +
            "where a.is_deleted = 0 \n" +
            "criteria"+
            "order by a.id ";

    public static String foodPaymentFilter = "Select a.from_date,a.to_date,a.total_days,a.total_amount," +
            "a.amount,b.first_name,b.last_name,b.cnic,b.phone_number,b.job_status \n" +
            "from food_payment_log a INNER JOIN employee b \n" +
            "ON b.id = a.employee_id \n"+
            "where a.is_deleted = 0 \n" +
            "criteria \n"+
            "order by b.first_name ";


    public static String totalAmountOfEmpByMonth = "Select SUM(a.total_amount) from food_payment_log a "+
            "INNER JOIN employee b ON b.id = a.employee_id \n"+
            "where b.is_deleted = 0 \n"+
            "criteria";

    public static String periodExistOfEmployee = "Select a.id from food_payment_log a "+
            "INNER JOIN employee b ON b.id = a.employee_id \n"+
            "where b.is_deleted = 0 \n"+
            "criteria";
}
