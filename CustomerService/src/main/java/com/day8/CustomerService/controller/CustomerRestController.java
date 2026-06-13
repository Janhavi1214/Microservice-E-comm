package com.day8.CustomerService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.day8.CustomerService.entity.Customer;
import com.day8.CustomerService.service.CustomerService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody @Valid Customer c) {

        Customer resp = service.save(c);
        return new ResponseEntity<Customer>(resp, HttpStatus.CREATED);
    }

    @GetMapping("/{cid}")
    public ResponseEntity<Customer> findByid(@PathVariable int cid) {

        Customer resp = service.findById(cid);
        return new ResponseEntity<Customer>(resp, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listAll() {

        List<Customer> list = service.listAll();
        return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Customer> findByMobile(
            @RequestParam String mobile) {

        Customer resp = service.findByMobile(mobile);
        return new ResponseEntity<Customer>(resp, HttpStatus.OK);
    }
}