package com.webartifact.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.webartifact.dao.WishesDao;
import com.webartifact.model.WishesEntity;
import com.webartifact.web.AJAXmodel.AjaxResponseBody;
import com.webartifact.web.AJAXmodel.AjaxWishModel;
import com.webartifact.web.jsonview.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by trevorBye on 9/23/16.
 */

@RestController
public class RESTController {
    @Autowired
    WishesDao wishesDao;

    @JsonView(Views.Public.class)
    @RequestMapping("/addvote")
    public AjaxResponseBody addWishViaAjax(@RequestBody AjaxWishModel wish, Principal principal) {
        Boolean unique = true;
        AjaxResponseBody responseBody = new AjaxResponseBody();

        List<WishesEntity> wishList = wishesDao.findAll();
        for (WishesEntity record : wishList) {
            if (record.getWish().equals(wish.getWish())) {
                unique = false;
                break;
            }
        }

        if (unique) {
            //set AJAX response
            responseBody.setCode("200");
            responseBody.setMsg("Successfully added.");

            //add wish to db
            WishesEntity wishToAdd = new WishesEntity(wish.getWish(),1,principal.getName());
            wishesDao.save(wishToAdd);

            return responseBody;
        } else {
            responseBody.setCode("400");
            responseBody.setMsg("Wish already exists.");
            return responseBody;
        }

    }
}
