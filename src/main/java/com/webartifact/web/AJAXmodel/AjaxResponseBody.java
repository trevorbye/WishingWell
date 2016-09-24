package com.webartifact.web.AJAXmodel;

import com.fasterxml.jackson.annotation.JsonView;
import com.webartifact.web.jsonview.Views;

/**
 * Created by trevorBye on 9/23/16.
 */
public class AjaxResponseBody {

    @JsonView(Views.Public.class)
    String msg;

    @JsonView(Views.Public.class)
    String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
