package com.logex.demo.controller;

import com.logex.demo.config.URLConstants;
import com.logex.demo.dto.AuthenticationRequestDto;
import com.logex.demo.dto.UserDto;
import com.logex.demo.enums.UserType;
import com.logex.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/registration")
    private ModelAndView registrationView(){
        ModelAndView m = new ModelAndView("registration");
        m.addObject("user",new UserDto());
        m.addObject("roleList", UserType.keyValues);
        m.addObject("userList",userService.findAll());
        return m;
    }

    @GetMapping(value = "/dashboard")
    private ModelAndView dashboardView(){
        ModelAndView m = new ModelAndView("dashboard");
        return m;
    }

    @PostMapping(value = "/saveUser")
    public String save(@Valid @ModelAttribute UserDto user){

        userService.saveNupdateUser(user);
        return "redirect:/registration";
    }
    @GetMapping(value = "/user"+ URLConstants.EDIT_URL)
    private ModelAndView editUser(@PathVariable Long id){
        ModelAndView modelAndView =  new ModelAndView("registration");
        modelAndView.addObject("user",userService.getUserDto(userService.findOne(id)));
        modelAndView.addObject("roleList", UserType.keyValues);
        modelAndView.addObject("userList",userService.findAll());
        return modelAndView;
    }

    @GetMapping(value = "/user"+ URLConstants.DELETE_URL)
    private String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/registration";
    }

    @RequestMapping(value={"/","/login"}, method = RequestMethod.GET)
    public String login(){

        return userService.checkUserAuthenticate(new ModelAndView());
    }


    @RequestMapping(value="/userLogin", method = RequestMethod.POST)
    public String login(@ModelAttribute AuthenticationRequestDto authenticationRequestDto, HttpServletRequest httpServletRequest, Model model){

        return userService.login(authenticationRequestDto,model);
    }


    @RequestMapping(value="/default", method = RequestMethod.GET)
    public String defaultAccess(HttpServletRequest request){

        if(request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/employee";
        }
        else if(request.isUserInRole("ROLE_USER")) {

            return "redirect:/rentout";

        }

        return "";
    }

}
