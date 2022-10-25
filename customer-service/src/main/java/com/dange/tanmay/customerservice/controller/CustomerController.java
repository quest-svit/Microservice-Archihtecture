package com.dange.tanmay.customerservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {
    private List<String> customerList;

    CustomerController(){
       customerList=new ArrayList<>();
       customerList.add("Atmaram");
        customerList.add("Jethalal");
        customerList.add("Popatlal");
        customerList.add("Iyer");
        customerList.add("Sodhi");
        customerList.add("Dayaben");
        customerList.add("Babita");
    }

    @GetMapping("/count")
    public int getCount(){
        return customerList.size();
    }

    @PostMapping("/add")
    public String addCustomer(@RequestBody String name){
        System.out.println(name);
        customerList.add(name);
        return "Success";
    }

    @GetMapping("/getAll")
    public List<String> getAllCustomers(){
        return customerList;
    }

    @GetMapping("/getId/{id}")
    public String getId(@PathVariable int id){
        if (id < customerList.size())
            return customerList.get(id);
        else
            return "InvalidId";
    }
}
