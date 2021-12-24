package tpAPI.tp5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tpAPI.tp5.model.AddressRepository;

@Controller
public class AllAddressesController {

    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/all-addresses")
    public String showAddresses(Model model) {
        model.addAttribute("allAddresses", addressRepository.findAll());
        return "all_addresses";
    }
}
