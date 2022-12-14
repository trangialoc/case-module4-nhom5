package com.codegym.vn.controller.student;

import com.codegym.vn.model.*;
import com.codegym.vn.service.interfaceImpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/students")
public class StudentController {

    @Autowired
    IFeeService iFeeService;
    @Autowired
    IStudentService iStudentService;
    @Autowired
    IAppUserService iAppUserService;
    @Autowired
    IFeeInformationService iFeeInformationService;
    @Autowired
    IPointService iPointService;

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


    @GetMapping("feeInfo")
    public ResponseEntity<FeeInformation> feeInfo(){
    Optional<AppUser> appUser = iAppUserService.findByUsername(getPrincipal());
    Optional<Student> student = iStudentService.findStudentByAppUserId(appUser.get().getId());
    Optional<Fee> fee = iFeeService.findStudentByStudentID(student.get().getId());
    double feeTotal = student.get().getCourse().getTotalFee();
    double feePaid = fee.get().getFeeStudent();
    double remainingPay = 0;
    if (feePaid == feeTotal){
        remainingPay = 0;
    }else if(feePaid < feeTotal){
        remainingPay = feeTotal - feePaid;
    }
    FeeInformation feeInformation = new FeeInformation(feeTotal, feePaid, remainingPay);
//        System.out.println(feeInformation.getFeeTotal());
    return new ResponseEntity<>(feeInformation, HttpStatus.OK);
    }

    @GetMapping("point")
    public ResponseEntity<Iterable<Point>> showPoint(){
        Optional<AppUser> appUser = iAppUserService.findByUsername(getPrincipal());
        Optional<Student> student = iStudentService.findStudentByAppUserId(appUser.get().getId());
        Iterable<Point> points = iPointService.findPointByStudentID(student.get().getId());
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

}
