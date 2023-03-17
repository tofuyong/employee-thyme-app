package ibf2022.day21thyme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.day21thyme.model.Employee;
import ibf2022.day21thyme.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService empSvc;

    @GetMapping
    public String listEmployees(Model model) {
        List<Employee> employees = empSvc.findAll();
        model.addAttribute("employees", employees);
        return "employeelist";
    }

    @GetMapping("/addnew")
    public String createEmployee(Model model) {
        Employee employeeForm = new Employee();
        model.addAttribute("employeeForm", employeeForm);
        return "newemployee";
    }

    @PostMapping("/save")
    // use model attribute to contain the form data
    // BindingResult has to be the last parameter!
    public String saveEmployee(@ModelAttribute("employForm") Employee empForm, BindingResult result) {
        if (result.hasErrors()) {
            return "addnew";
        }

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(empForm.getFirstName());
        newEmployee.setLastName(empForm.getLastName());
        newEmployee.setSalary(empForm.getSalary());
        empSvc.save(newEmployee);
        return "redirect:/employees"; //this is a get endpoint; redirecting back to the listing endpoint
        // record will be saved (double check on SQL) but not shown on webpage due to the inner join - no dependant so won't show on the employee list
        // Solution: change repo function from inner join to left join
    }

    // Why GetMapping? By default, hyperlinks are get, not delete
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        empSvc.delete(id);
        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String editEmployee(@PathVariable("id") Integer id, Model model) {
        // First retrieve the employee
        Employee retrievedEmp = empSvc.findById(id);
        model.addAttribute("employeeForm", retrievedEmp);
        // This will show the retrieved employee. create update page, similar to newemployee, but different end point
        return "updateemployee";
    }

    @PostMapping("/saveUpdate") 
    public String saveUpdateEmployee(@ModelAttribute("employeeForm") Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "updateemployee";
        }
        empSvc.update(employee);
        return "redirect:/employees";
    }
}
