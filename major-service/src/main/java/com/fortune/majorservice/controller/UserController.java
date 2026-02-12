package com.fortune.majorservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class UserController {


    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public String getUser() {
        return "getUser";
    }


}
