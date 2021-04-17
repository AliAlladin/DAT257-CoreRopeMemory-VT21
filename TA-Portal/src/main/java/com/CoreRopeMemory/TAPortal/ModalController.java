package com.CoreRopeMemory.TAPortal;

import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("modals")
public class ModalController {

    @GetMapping("modal1")
    public String modal1(Model model) {
        WorkShift workshift = new WorkShift();
        model.addAttribute("workshift", workshift);
        return "modal1";
    }

    @GetMapping("modal2")
    public String modal2(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "modal2";
    }
}
