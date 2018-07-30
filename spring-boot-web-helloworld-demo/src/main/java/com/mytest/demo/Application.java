package com.mytest.demo;

import com.mytest.demo.entity.Employee;
import com.mytest.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello world!";
    }

//    @GetMapping(value = "/hello/{name}")
//    public String hello(@PathVariable String name) {
//        return "Hello " + name + "!";
//    }

    @GetMapping(value = "/hello/insert/{id}/{name}")
    public String insert(@PathVariable String id, @PathVariable String name) {
        Employee employee = new Employee(id, name);
        employeeRepository.saveAndFlush(employee);
        return String.format("Inserted [id='%s', name='%s'] OK.", id, name);
    }

    @GetMapping(value = "/hello/get/{id}")
    public String get(@PathVariable String id) {
        Employee employee = employeeRepository.getOne(id);
        if (employee == null) {
            return "No record found.";
        }

        return "Hello " + employee.getName() + "!";
    }
}

