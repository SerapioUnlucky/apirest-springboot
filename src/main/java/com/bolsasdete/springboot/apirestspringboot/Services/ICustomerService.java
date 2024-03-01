package com.bolsasdete.springboot.apirestspringboot.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsasdete.springboot.apirestspringboot.Models.CustomerModel;

public interface ICustomerService {
    
    public List<CustomerModel> findAll();
    public Page<CustomerModel> findAll(Pageable pageable);
    public CustomerModel findById(Long id);
    public CustomerModel save(CustomerModel customer);
    public void delete(Long id);
    public CustomerModel findByEmail(String email);
    
}
