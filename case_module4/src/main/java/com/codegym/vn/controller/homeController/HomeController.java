package com.codegym.vn.controller.homeController;


import com.codegym.vn.model.*;
import com.codegym.vn.service.interfaceImpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private IAppUserService iAppUserService;
    @Autowired
    private IStudentService iStudentService;
    @Autowired
    private  IFeeService iFeeService;
    @Autowired
    private ITeacherService iTeacherService;
    @Autowired
    private IClassesService iClassesService;

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }



    @GetMapping("/teachers")
    public ModelAndView teachers(){
        ModelAndView modelAndView = new ModelAndView("teacher-home");
       modelAndView.addObject("user", getPrincipal());

//        modelAndView.addObject("listStudentByClass", students);
        return modelAndView;
    }

    @GetMapping("/students")
    public ModelAndView students(){
        ModelAndView modelAndView = new ModelAndView("student-home");
        Optional<AppUser> appUser = iAppUserService.findByUsername(getPrincipal());
        Optional<Student> student = iStudentService.findStudentByAppUserId(appUser.get().getId());
        Optional<Fee> fee = iFeeService.findStudentByStudentID(student.get().getId());
        modelAndView.addObject("user", getPrincipal());
        modelAndView.addObject("userStudent", student);
        modelAndView.addObject("fee", fee);
        return modelAndView;
    }
    @GetMapping("/officers")
    public String officer (Model model){
        model.addAttribute("user", getPrincipal());
        return "/officer-home";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("user", getPrincipal());
        return "/admin-home1";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "/access-denied";
    }
    @GetMapping("/home")
    public String home(){
        return "/index";
    }

    @GetMapping("/")
    public String home1(){
        return "/index";
    }

    @GetMapping("/home/courses")
    public String courseEducation(){
        return "/template-view/courses";
    }

    @GetMapping("/home/event")
    public String event(){
        return "/template-view/events";
    }


}
