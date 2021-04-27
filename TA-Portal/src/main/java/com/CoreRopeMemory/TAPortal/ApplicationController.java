package com.CoreRopeMemory.TAPortal;

import com.CoreRopeMemory.TAPortal.Repositories.WorkshiftRepository;
import com.CoreRopeMemory.TAPortal.Services.UserService;
import com.CoreRopeMemory.TAPortal.Services.WorkshiftService;
import com.CoreRopeMemory.TAPortal.model.User;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationController {

    @Autowired
    private WorkshiftService workshiftService;

    @Autowired
    private UserService userService;

    @GetMapping({"/", "/index"})
    public String hello(Model model){

        if (userService.isEmpty()){
            User user = new User("123456",
                    "test.person@mail.com",
                    "Person",
                    "Test",
                    "Kungsgatan 1",
                    12345,
                    "Göteborg",
                    false);
            userService.save(user);
        }

        model.addAttribute("workshifts", workshiftService.listALl());
        return "index";
    }

    @RequestMapping ({"/add_workshift"})
    public String newWorkshift(Model model){
        WorkShift workShift = new WorkShift();
        model.addAttribute("workshift", workShift);
        return "add_workshift";
    }

    //Getmapping?
    @RequestMapping ({"/edit_workshift/{id}"})
    public String editWorkshift(@PathVariable (value = "id") long id, Model model){
        WorkShift workShift = workshiftService.getWorkshift(id);
        model.addAttribute("workshift", workShift);
        return "edit_workshift";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String addWorkshift(@ModelAttribute ("workshift")WorkShift workShift){
        User user = userService.get("123456");

        workShift.setTa(user);
        user.addWorkshift(workShift);

        workshiftService.save(workShift);

        return "redirect:/";
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute ("workshift")WorkShift workShift, @PathVariable (value = "id") long id) {
        User user = userService.get("123456");
        workShift.setTa(user);
        workshiftService.save(workShift);
        return "redirect:/";
    }

    @RequestMapping(value = {"/delete_workshift/{id}"})
    public String delete(@PathVariable (value = "id") long id) {
        workshiftService.delete(id);
        return "redirect:/";
    }

    @RequestMapping ({"/user_details"})
    public String user(Model model){
        User user = userService.get("123456");
        model.addAttribute("user", user);
        return "user_details";
    }

    @RequestMapping(value = {"/saveUser"}, method = RequestMethod.POST)
    public String saveUserInfo(@ModelAttribute ("user")User user){
        //Database.saveUserInfo(user);
        userService.save(user);
        return "redirect:/user_details";
    }

    @RequestMapping ({"/time_report"})
    public String timeReport(Model model){
        User user = userService.get("123456");
        model.addAttribute("user", user);

        model.addAttribute("lectureExercises",typeOfWorkshift(user.getWorkshifts(),"Lectures and exercise sessions"));
        model.addAttribute("supervision",typeOfWorkshift(user.getWorkshifts(),"Project supervision and lab supervision"));
        model.addAttribute("other",typeOfWorkshift(user.getWorkshifts(),"Other activities"));
        model.addAttribute("labGrading",typeOfWorkshift(user.getWorkshifts(),"Lab grading"));
        Set<String> dates = new HashSet<>();
        for (WorkShift workShift : typeOfWorkshift(user.getWorkshifts(), "Exam grading")){
            dates.add(workShift.getDate());
        }
        model.addAttribute("examGrading", dates.size());
        return "time_report";
    }

    private List<WorkShift> typeOfWorkshift(List<WorkShift> workShifts, String type){
        List<WorkShift> typeOfWorkshift = new ArrayList<>();
        for (WorkShift workshift: workShifts) {
            if (workshift.getType().equals(type)){
                typeOfWorkshift.add(workshift);
            }
        }
        return typeOfWorkshift;
    }

}
