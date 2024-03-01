package com.bolsasdete.springboot.apirestspringboot.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsasdete.springboot.apirestspringboot.Dao.ICustomerDao;
import com.bolsasdete.springboot.apirestspringboot.Models.CustomerModel;

@Service
public class CustomerServiceImpl implements ICustomerService {
    
    @Autowired
    private ICustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerModel> findAll() {
        
        return (List<CustomerModel>) customerDao.findAll();
    
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerModel> findAll(Pageable pageable) {
        
        return customerDao.findAll(pageable);

    }

    @Override
    @Transactional(readOnly = true)
    public CustomerModel findById(Long id) {
        
        return customerDao.findById(id).orElse(null);
    
    }

    @Override
    @Transactional
    public CustomerModel save(CustomerModel customer) {
        
        return customerDao.save(customer);
    
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
        customerDao.deleteById(id);
    
    }

    @Transactional(readOnly = true)
    public CustomerModel findByEmail(String email) {
        
        return customerDao.findByEmail(email);
    
    }

}
