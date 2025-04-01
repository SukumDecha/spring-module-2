package sit.int202.springmodule2.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int202.springmodule2.dto.CustomerCreateDTO;
import sit.int202.springmodule2.entities.Customer;
import sit.int202.springmodule2.exceptions.CustomerNotFoundException;
import sit.int202.springmodule2.repositories.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Customer create(CustomerCreateDTO customerCreateDTO) {
        if(findById(customerCreateDTO.getId()) != null) {
            throw new EntityExistsException("Customer existed");
        }

        Customer customer = modelMapper.map(customerCreateDTO, Customer.class);

        repository.save(customer);
        return customer;
    }

}
