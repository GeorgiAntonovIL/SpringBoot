package com.Service;


import java.util.UUID;

public interface AddressService {

    String createAddress(UUID userId);

    String getAddress(UUID userId);
}
