package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * The user can registered one or many bank accounts in order to withdraw or deposit some money
 */

@Controller
public class AddBankAccountControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @GetMapping(value = "/userHome/bankAccount/add")
    public String addABankAccountToYourList(Model model) {
        model.addAttribute("bankAccount", new BankAccount());
        return "bankAccountAdd";
    }

    @PostMapping(value = "/userHome/bankAccount/adding")
    public String addingABankAccount( BankAccount bankAccount) {
        if (!moneyTransferService.addABankAccount(bankAccount)) {
            return "redirect:/userHome/bankAccount/add";
        }
        return "redirect:/userHome";
    }
}
