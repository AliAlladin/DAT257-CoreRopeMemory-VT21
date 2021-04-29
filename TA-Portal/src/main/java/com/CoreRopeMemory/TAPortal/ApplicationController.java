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

import java.time.Month;

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

        model.addAttribute("currentUser", userService.get("123456"));

        model.addAttribute("january_workshifts", workshiftService.listByMonth(Month.JANUARY));
        model.addAttribute("february_workshifts", workshiftService.listByMonth(Month.FEBRUARY));
        model.addAttribute("march_workshifts", workshiftService.listByMonth(Month.MARCH));
        model.addAttribute("april_workshifts", workshiftService.listByMonth(Month.APRIL));
        model.addAttribute("may_workshifts", workshiftService.listByMonth(Month.MAY));
        model.addAttribute("june_workshifts", workshiftService.listByMonth(Month.JUNE));
        model.addAttribute("july_workshifts", workshiftService.listByMonth(Month.JULY));
        model.addAttribute("august_workshifts", workshiftService.listByMonth(Month.AUGUST));
        model.addAttribute("september_workshifts", workshiftService.listByMonth(Month.SEPTEMBER));
        model.addAttribute("october_workshifts", workshiftService.listByMonth(Month.OCTOBER));
        model.addAttribute("november_workshifts", workshiftService.listByMonth(Month.NOVEMBER));
        model.addAttribute("december_workshifts", workshiftService.listByMonth(Month.DECEMBER));

        return "index";
    }

    @RequestMapping ({"/add_workshift"})
    public String newWorkshift(Model model){
        WorkShift workShift = new WorkShift();
        model.addAttribute("workshift", workShift);
        return "add_workshift";
    }

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


}
