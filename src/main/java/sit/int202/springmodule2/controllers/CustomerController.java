package sit.int202.springmodule2.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int202.springmodule2.dto.CustomerCreateDTO;
import sit.int202.springmodule2.dto.CustomerResponseDTO;
import sit.int202.springmodule2.entities.Customer;
import sit.int202.springmodule2.exceptions.CustomerNotFoundException;
import sit.int202.springmodule2.services.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable @Max(value = 15) @Min(value =  10, message = "Id must be greater or equals to 10") Integer id) {
        var customer = service.findById(id);

        if (customer == null) {
            throw new CustomerNotFoundException();
        }

        CustomerResponseDTO customerResponseDTO = modelMapper.map(customer, CustomerResponseDTO.class);

        return ResponseEntity.status(200).body(customerResponseDTO);
    }

    @PostMapping("")
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerCreateDTO payload) {
        var customer = service.create(payload);

        CustomerResponseDTO customerResponseDTO = modelMapper.map(customer, CustomerResponseDTO.class);

        return ResponseEntity.status(200).body(customerResponseDTO);
    }
}
