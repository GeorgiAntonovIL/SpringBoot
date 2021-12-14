package com.Controller;
import com.Service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;



@RestController
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @PostMapping("/address/create")
    public String createAddress(@RequestParam UUID userId) {
        String address = addressService.createAddress(userId);
        return address;
    }

    @GetMapping("/address/{userId}")
    public String getAddress(@PathVariable UUID userId) {
        String address = addressService.getAddress(userId);
        return address;
    }
}
