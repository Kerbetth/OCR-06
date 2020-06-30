package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.service.UserService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * profile page give information about the user and give the possibility to ass a bank account
 */

@Controller
public class ProfileControllers {

    @Autowired
    private UserService userService;
    @Autowired
    private MoneyTransferService moneyTransferService;
    @Autowired
    private RelativeService relativeService;

    @GetMapping(value = "/userHome/profile")
    public String getAccountInfo(Model model) {
        model.addAttribute("userAccount", userService.getAccountInfo());
        model.addAttribute("relatives", relativeService.getRelatives());
        model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
        return "Profile";
    }

    @GetMapping(value = "/userHome/contact")
    public String contact() {
        return "Contact";
    }
}
