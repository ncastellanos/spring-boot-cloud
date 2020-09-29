package academy.digitallab.store.customer.service;

import academy.digitallab.store.customer.repository.CustomerRepository;
import academy.digitallab.store.customer.repository.entity.Customer;
import academy.digitallab.store.customer.repository.entity.Region;
import academy.digitallab.store.customer.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CustomerServiceImpl  implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {

        Customer customerDB = customerRepository.findByNumberID ( customer.getNumberID () );
        if (customerDB != null){
            return  customerDB;
        }

        customer.setState ( Constant.STATE_CREATED );
        customer.setCreatedBy ( "admin" );
        customerDB = customerRepository.save ( customer );
        return customerDB;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if (customerDB == null){
            return  null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        customerDB.setUpdatedBy ( "admin" );
        return  customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if (customerDB ==null){
            return  null;
        }
        customerDB.setState ( Constant.STATE_DELETED );
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException ie) {
//            Thread.currentThread().interrupt();
//        }
        return  customerRepository.findById(id).orElse(null);
    }
}
