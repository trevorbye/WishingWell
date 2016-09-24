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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @GetMapping("/")
    public String homePage(Model model, Principal principal) {

        //todo display link to user page if logged in, else display login/sign-up
        if (principal != null) {
            model.addAttribute("currentUser", principal.getName());
        }

        return "index";
    }


    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("user") UserProfileEntity user, @ModelAttribute("userNameTaken") String conflict, Model model) {

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserProfileEntity());
        }


        if (model.asMap().containsKey("BindingResult")) {
            model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("BindingResult"));
        }

        return "sign-up";
    }

    @PostMapping("account-create")
    public String signUpSubmit(@Valid @ModelAttribute("user") final UserProfileEntity user, BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("BindingResult", result);

            return "redirect:/sign-up";
        }

        List<UserProfileEntity> userList = userProfileService.findAll();
        for (UserProfileEntity record : userList) {
            if (user.getUsername().equals(record.getUsername())) {
                redirectAttributes.addFlashAttribute("userNameTaken", "This username is already taken.");
                return "redirect:/sign-up";
            }
        }

        RoleEntity roleEntity = roleService.findByName("ROLE_USER");
        user.setEnabled(true);
        user.setRole(roleEntity);
        userProfileService.save(user);

        //auto-login after registration
        request.login(user.getUsername(), user.getPassword());

        return "redirect:/user/" + user.getUsername();
    }

    @GetMapping("/user/{username}")
    public String userHome(@PathVariable String username, Model model, Principal principal) {
        //get current username
        model.addAttribute("currentUser", principal.getName());

        return "user-home";
    }

}
