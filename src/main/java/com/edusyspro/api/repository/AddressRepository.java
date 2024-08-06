package com.edusyspro.api.repository;

import com.edusyspro.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressByNumberAndStreetAndNeighborhood(int number, String street, String neighborhood);

}
