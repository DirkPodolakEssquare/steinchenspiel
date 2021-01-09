package de.essquare.steinchenspiel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class Controller {

    @PostMapping("email")
    public void postEmail(@RequestParam String email) {
        // case 1: user not found by email, create new one
        // case 2: user found by email, use it
        // both: create new logincode, write to database, send to email
    }
    
    @PostMapping("phone")
    public void postPhone(@RequestParam String phone) {
        // case 1: user not found by phone, create new one
        // case 2: user found by phone, use it
        // both: create new logincode, write to database, send to phone
    }
    
    @PostMapping("code")
    public void postCode(@RequestParam String emailOrPhone, @RequestParam String logincode) {
        // search user by email, if not found search user by phone
        // assert logincode matches
        // create new accesscode, store in database, return to caller
    }
}
