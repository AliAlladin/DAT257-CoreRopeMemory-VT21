package com.CoreRopeMemory.TAPortal;

import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApplicationController {

    @GetMapping({"/", "/index"})
    public String hello(Model model){
        model.addAttribute("workshifts", Database.getWorkShifts());
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
        Database.addWorkShift(workShift);

        return "redirect:/";
    }
}
