package com.webartifact.web;

import com.webartifact.model.RoleEntity;
import com.webartifact.model.UserProfileEntity;
import com.webartifact.service.RoleService;
import com.webartifact.service.UserProfileService;
import com.webartifact.service.WishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

/**
 * Created by trevorBye on 9/21/16.
 */

@Controller
public class MainController {

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    WishesService wishesService;

    @Autowired
    RoleService roleService;







    @PostMapping("account-create")
    public String signUpSubmit(@ModelAttribute("user") final UserProfileEntity user, BindingResult result) throws Exception {

        if (result.hasErrors()) {


            return "redirect:/sign-up";
        }

        List<UserProfileEntity> userList = userProfileService.findAll();
        for (UserProfileEntity record : userList) {
            if (user.getUsername().equals(record.getUsername())) {

                return "redirect:/sign-up";
            }
        }

        RoleEntity roleEntity = roleService.findByName("ROLE_USER");
        user.setEnabled(true);
        user.setRole(roleEntity);
        userProfileService.save(user);



        return "redirect:/user/" + user.getUsername();
    }

    @GetMapping("/user/{username}")
    public String userHome(@PathVariable String username, Model model, Principal principal) {
        //get current username
        model.addAttribute("currentUser", principal.getName());

        return "user-home";
    }

}
