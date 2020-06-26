package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Relation;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * A friend or relative is a user of the application whose the current user added to his friendlist
 * Only friends/relatives can send money to each other
 */

@Controller
public class AddfriendControllers {

    @Autowired
    private RelativeService relativeService;

    @GetMapping(value = "/userHome/friend/add")
    public String addAFriendToYourList(Model model) {
            model.addAttribute("relative", new Relation());
            return "FriendAdd";
    }

    @PostMapping(value = "/userHome/friend/adding")
    public String addingAFriend(Relation relationEmail) {
            if (!relativeService.addAFriend(relationEmail.getEmail())) {
                return "redirect:/userHome/friend/add";
            }
            return "redirect:/userHome";
    }
}
