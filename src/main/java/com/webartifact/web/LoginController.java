package com.webartifact.web;

import com.webartifact.model.UserProfileEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by trevorBye on 9/21/16.
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new UserProfileEntity());
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);
        } catch (Exception ex) {

        }
        return "login";
    }
}
