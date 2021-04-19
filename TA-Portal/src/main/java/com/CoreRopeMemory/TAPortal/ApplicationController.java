package com.CoreRopeMemory.TAPortal;

import com.CoreRopeMemory.TAPortal.Repositories.WorkshiftRepository;
import com.CoreRopeMemory.TAPortal.Services.WorkshiftService;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApplicationController {

    @Autowired
    private WorkshiftService workshiftService;

    @GetMapping({"/", "/index"})
    public String hello(Model model){
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
        Database.addWorkShift(workShift);
        workshiftService.save(workShift);

        return "redirect:/";
    }
}
