package com.webartifact.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.webartifact.model.RoleEntity;
import com.webartifact.model.UserProfileEntity;
import com.webartifact.model.VoteByUser;
import com.webartifact.model.WishesEntity;
import com.webartifact.service.RoleService;
import com.webartifact.service.UserProfileService;
import com.webartifact.service.VoteService;
import com.webartifact.service.WishesService;
import com.webartifact.web.AJAXmodel.AjaxResponseBody;
import com.webartifact.web.AJAXmodel.AjaxWishModel;
import com.webartifact.web.jsonview.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by trevorBye on 9/23/16.
 */

@RestController
public class RESTController {
    @Autowired
    private WishesService wishesService;

    @Autowired
    private VoteService voteService;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    RoleService roleService;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/user2")
    public AjaxResponseBody user2(Principal user) {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setMsg(user.getName());
        responseBody.setCode("200");
        return responseBody;
    }

    @JsonView(Views.Public.class)
    @RequestMapping("/addwish")
    public AjaxResponseBody addWishViaAjax(@RequestBody AjaxWishModel wish, Principal principal) {
        Boolean unique = true;
        AjaxResponseBody responseBody = new AjaxResponseBody();

        List<WishesEntity> wishList = wishesService.findAllWishes();
        for (WishesEntity record : wishList) {
            if (record.getWish().equals(wish.getWish())) {
                unique = false;
                break;
            }
        }

        int wishLength = wish.getWish().length();

        //condition to check is wish is unique and not empty
        if (unique && wishLength > 0 && wishLength <= 20) {
            //set AJAX response
            responseBody.setCode("200");
            responseBody.setMsg("Successfully added.");

            //add wish to db
            WishesEntity wishToAdd = new WishesEntity(wish.getWish(), 1, principal.getName());
            wishesService.saveWish(wishToAdd);

            return responseBody;
        } else if (!unique && wishLength > 0 && wishLength <= 20) {
            responseBody.setCode("400");
            responseBody.setMsg("Wish already exists.");
            return responseBody;
        } else if (wishLength > 20) {
            responseBody.setCode("400");
            responseBody.setMsg("Wish cannot be more than 20 characters.");
            return responseBody;
        } else {
            responseBody.setCode("400");
            responseBody.setMsg("Wish cannot be blank.");
            return responseBody;
        }
    }

    //method to add vote; restricted if user has already voted for wish
    @RequestMapping("/addvote")
    public AjaxResponseBody addVoteViaAjax(@RequestBody AjaxWishModel wish, Principal principal) {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        //return vote/user vote combination if it exists
        VoteByUser voteByUser = voteService.findByWishAndVoter(wish.getWish(), principal.getName());

        if (voteByUser.getWish().isEmpty()) {
            //if the combo was empty, create combo and save in db
            voteByUser.setVoter(principal.getName());
            voteByUser.setWish(wish.getWish());
            voteService.saveVote(voteByUser);

            //add vote to wish in db
            wishesService.addVote(wish.getWish());

            responseBody.setCode("200");
            responseBody.setMsg("Vote added.");
            return responseBody;
        } else {
            responseBody.setCode("400");
            responseBody.setMsg("You have already voted for this wish.");
            return responseBody;
        }
    }

    //returns JSON of all wishes in db
    @RequestMapping("/getwishesJSON")
    public List<WishesEntity> wishesEntityList() {
        List<WishesEntity> list = wishesService.findAllWishes();
        return list;
    }

    @RequestMapping("/registeruser")
    public AjaxResponseBody registerUser(@RequestBody UserProfileEntity userProfileEntity, HttpServletRequest request) throws ServletException {

        AjaxResponseBody responseBody = new AjaxResponseBody();
        String requestedName = userProfileEntity.getUsername();
        String requestedPassword = userProfileEntity.getPassword();


        List<UserProfileEntity> userList = userProfileService.findAll();
        for (UserProfileEntity record : userList) {
            if (requestedName.equals(record.getUsername())) {
                responseBody.setCode("400");
                responseBody.setMsg("Username taken.");

                return responseBody;
            }
        }

        RoleEntity roleEntity = roleService.findByName("ROLE_USER");
        userProfileEntity.setEnabled(true);
        userProfileEntity.setRole(roleEntity);
        userProfileService.save(userProfileEntity);

        //auto login after registering
        request.login(requestedName,requestedPassword);

        responseBody.setCode("200");
        responseBody.setMsg("Account created.");
        return responseBody;
    }
}
