package com.bolsasdete.springboot.apirestspringboot.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.repository.CrudRepository;
import com.bolsasdete.springboot.apirestspringboot.Models.CustomerModel;

public interface ICustomerDao extends JpaRepository<CustomerModel, Long>{

    public CustomerModel findByEmail(String email);
    
}
