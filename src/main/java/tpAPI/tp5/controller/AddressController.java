package tpAPI.tp5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddressController {

    @GetMapping("/search_address")
    public String searchAddress(Model model){

        return "search_address";
    }

}
