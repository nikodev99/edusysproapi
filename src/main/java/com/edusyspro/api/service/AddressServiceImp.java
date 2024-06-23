package com.edusyspro.api.service;

import com.edusyspro.api.entities.Address;
import com.edusyspro.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImp implements AddressService {

    AddressRepository repo;

    @Autowired
    public AddressServiceImp(AddressRepository repo) {
        this.repo = repo;
    }

    @Override
    public Address findAddress(int number, String street, String neighborhood) {
        Optional<Address> address = repo.findAddressByNumberAndStreetAndNeighborhood(number, street, neighborhood);
        return address.orElse(null);
    }
}
