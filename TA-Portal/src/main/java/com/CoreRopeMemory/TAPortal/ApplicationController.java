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

@Controller
public class ApplicationController {

    @Autowired
    private WorkshiftService workshiftService;

    @Autowired
    private UserService userService;

    @GetMapping({"/", "/index"})
    public String hello(Model model){

        User user = new User("123456",
                "test.person@mail.com",
                "Person",
                "Test",
                "Kungsgatan 1",
                12345,
                "Göteborg",
                false);

        userService.save(user);

        model.addAttribute("workshifts", workshiftService.listALl());
        return "index";
    }

    @RequestMapping ({"/add_workshift"})
    public String newWorkshift(Model model){
        WorkShift workShift = new WorkShift();
        model.addAttribute("workshift", workShift);
        return "add_workshift";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String addWorkshift(@ModelAttribute ("workshift")WorkShift workShift){

        User user = userService.get("123456");

        workShift.setTa(user);

        workshiftService.save(workShift);

        return "redirect:/";
    }
}
