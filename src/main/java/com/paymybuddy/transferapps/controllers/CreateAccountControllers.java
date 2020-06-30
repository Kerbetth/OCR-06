package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * A non-authetificated guest can just either logging, or create an account with email, name and password
 */

@Controller
public class CreateAccountControllers {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/account/create")
    public String createAccount(Model model) {
        model.addAttribute("createAccount", new CreateAccount());
        return "CreateAccount";
    }

    @PostMapping(value = "/account/creating")
    public String creatingAccount(@Valid CreateAccount createAccount, BindingResult result) {
        if (!result.hasErrors()) {
            userService.createAnAccount(createAccount);
            return "redirect:/";
        }
        return "redirect:/account/create";
    }
}
