package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Withdrawing is the action of retrieving the money which is on a bank account to the current user account of the application
 */

@Controller
public class WithDrawMoneyControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @GetMapping(value = "/userHome/withdrawMoney/withdraw")
    public String withdrawMoney(Model model) {
        model.addAttribute("withdrawMoney", new SendMoney());
        model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
        return "withdrawMoney";
    }

    @PostMapping(value = "/userHome/withdrawMoney/withdrawing")
    public String withdrawing(SendMoney deposit) {
        if(moneyTransferService.withDrawMoneyFromBankAndAddOnTheAccount(deposit)) {
            return "redirect:/userHome";
        }
        return "redirect:/userHome/withdrawMoney/withdraw";
    }
}
