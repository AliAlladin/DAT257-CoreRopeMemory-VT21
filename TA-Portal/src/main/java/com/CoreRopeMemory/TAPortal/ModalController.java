package com.CoreRopeMemory.TAPortal;

import java.util.List;

import com.CoreRopeMemory.TAPortal.Services.CourseService;
import com.CoreRopeMemory.TAPortal.Services.WorkshiftService;
import com.CoreRopeMemory.TAPortal.model.Course;
import com.CoreRopeMemory.TAPortal.model.WorkShift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("modals")
public class ModalController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private WorkshiftService workshiftService;

    @GetMapping("modal1")
    public String modal1(Model model) {
        WorkShift workshift = new WorkShift();
        model.addAttribute("workshift", workshift);
        List<Course> courses = courseService.listALl();
        model.addAttribute("courses", courses);
        return "modal1";
    }

    @GetMapping("modal2")
    public String modal2(@RequestParam(value = "id") long id, Model model) {
        WorkShift workShift = workshiftService.getWorkshift(id);
        model.addAttribute("workshift", workShift);
        List<Course> courses = courseService.listALl();
        model.addAttribute("courses", courses);
        return "modal2";
    }
}
