package com.springboot.thymeleafdemo.controller;
//mvc-1
import com.springboot.thymeleafdemo.entity.Employee;
import com.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    //controller <---> service <---> repository <---> database
    //define field for employee service
    private EmployeeService employeeService;

    //define constructor to inject dependency injection
    public EmployeeController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }

    //mapping for "/list"
    @GetMapping("/list")
    public String listEmployees(Model theModel){
        //get employee from db
        List<Employee> theEmployees = employeeService.findAll();

        //add to spring model
        theModel.addAttribute("employees",theEmployees);
        return "employees/list-employees";
    }

    //mvc-2-1.2
    //for showing form after clicking add employee
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        //model attribute to bind form data created
        Employee theEmployee = new Employee(); //object created it it will be used in post mapping i.e its object
        theModel.addAttribute("employee",theEmployee);
        return "employees/employee-form";
    }

    //mvc-2.2.2
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
        //save the employee
        employeeService.save(theEmployee);

        //using redirect to prevent duplicate submissions
        return "redirect:/employees/list";

    }

    //mvc-3.2
    @GetMapping("/showFormForUpdate") //used request param because we need id
    public String showFormForUpdate(@RequestParam("employeeId") int theId,
                                    Model theModel){
        //get the employee from the service   , -> this will be used to get form with data already field
        Employee theEmployee = employeeService.findById(theId);

        //set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);

        //send over to our form
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId){
        //delete the employee
        employeeService.deleteById(theId);

        //redirect to employees/list
        return "redirect:/employees/list";
    }
}
