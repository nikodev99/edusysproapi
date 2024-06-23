package com.edusyspro.api.service;

import com.edusyspro.api.entities.Address;

public interface AddressService {

    Address findAddress(int number, String street, String neighborhood);

}
