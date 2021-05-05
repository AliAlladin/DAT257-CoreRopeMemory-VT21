package com.CoreRopeMemory.TAPortal;

import com.CoreRopeMemory.TAPortal.Services.UserService;
import com.CoreRopeMemory.TAPortal.Services.WorkshiftService;
import com.CoreRopeMemory.TAPortal.model.User;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.time.Month;

@Controller
public class ApplicationController {

    @Autowired
    private WorkshiftService workshiftService;

    @Autowired
    private UserService userService;

    String courseCode = "TEST";
    String finalForm = "No";
    String newAddress = "No";
    String salaryPrev = "Yes";

    @GetMapping({"/", "/index"})
    public String hello(Model model) {

        if (userService.isEmpty()) {
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


        LinkedHashMap<String, List<WorkShift>> months = new LinkedHashMap<>();
        months.put("JANUARY", workshiftService.listByMonth(Month.JANUARY));
        months.put("FEBRUARY", workshiftService.listByMonth(Month.FEBRUARY));
        months.put("MARCH", workshiftService.listByMonth(Month.MARCH));
        months.put("APRIL", workshiftService.listByMonth(Month.APRIL));
        months.put("MAY", workshiftService.listByMonth(Month.MAY));
        months.put("JUNE", workshiftService.listByMonth(Month.JUNE));
        months.put("JULY", workshiftService.listByMonth(Month.JULY));
        months.put("AUGUST", workshiftService.listByMonth(Month.AUGUST));
        months.put("SEPTEMBER", workshiftService.listByMonth(Month.SEPTEMBER));
        months.put("OCTOBER", workshiftService.listByMonth(Month.OCTOBER));
        months.put("NOVEMBER", workshiftService.listByMonth(Month.NOVEMBER));
        months.put("DECEMBER", workshiftService.listByMonth(Month.DECEMBER));

        model.addAttribute("months", months);

        model.addAttribute("courseCode", courseCode);
        model.addAttribute("finalForm", finalForm);
        model.addAttribute("newAddress", newAddress);
        model.addAttribute("salaryPrev", salaryPrev);

        return "index";
    }

    @RequestMapping({"/add_workshift"})
    public String newWorkshift(Model model) {
        WorkShift workShift = new WorkShift();
        model.addAttribute("workshift", workShift);
        return "add_workshift";
    }

    @RequestMapping({"/edit_workshift/{id}"})
    public String editWorkshift(@PathVariable(value = "id") long id, Model model) {
        WorkShift workShift = workshiftService.getWorkshift(id);
        model.addAttribute("workshift", workShift);
        return "edit_workshift";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String addWorkshift(@ModelAttribute("workshift") WorkShift workShift) {
        User user = userService.get("123456");

        workShift.setTa(user);
        user.addWorkshift(workShift);

        workshiftService.save(workShift);

        return "redirect:/";
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute("workshift") WorkShift workShift, @PathVariable(value = "id") long id) {
        User user = userService.get("123456");
        workShift.setTa(user);
        workshiftService.save(workShift);
        return "redirect:/";
    }

    @RequestMapping(value = {"/delete_workshift/{id}"})
    public String delete(@PathVariable(value = "id") long id) {
        workshiftService.delete(id);
        return "redirect:/";
    }

    @RequestMapping({"/user_details"})
    public String user(Model model) {
        User user = userService.get("123456");
        model.addAttribute("user", user);
        return "user_details";
    }

    @RequestMapping(value = {"/saveUser"}, method = RequestMethod.POST)
    public String saveUserInfo(@ModelAttribute("user") User user) {
        //Database.saveUserInfo(user);
        userService.save(user);
        return "redirect:/user_details";
    }

    @RequestMapping(value = {"/time_report/{month}"}, method = RequestMethod.POST)
    public String timeReport(@PathVariable("month") Month month,
                             @ModelAttribute("courseCode") String courseCode,
                             Model model) {
        List<WorkShift> workshifts = workshiftService.listByMonth(month);

        User user = userService.get("123456");
        model.addAttribute("user", user);

        model.addAttribute("lectureExercises", typeOfWorkshift(workshifts, "Lectures and exercise sessions"));
        model.addAttribute("supervision", typeOfWorkshift(workshifts, "Project supervision and lab supervision"));
        model.addAttribute("other", typeOfWorkshift(workshifts, "Other activities"));
        model.addAttribute("labGrading", typeOfWorkshift(workshifts, "Lab grading"));
        Set<LocalDate> dates = new HashSet<>();
        for (WorkShift workShift : typeOfWorkshift(workshifts, "Exam grading")) {
            dates.add(workShift.getDate());
        }
        model.addAttribute("examGrading", dates.size());

        this.courseCode = courseCode;

        model.addAttribute("courseCode", courseCode);
        model.addAttribute("finalForm", finalForm);
        model.addAttribute("newAddress", newAddress);
        model.addAttribute("salaryPrev", salaryPrev);

        return "time_report";
    }

    /**
     * Method that gives a list of a specific type of workshift.
     *
     * @param workShifts A list of all workshifts.
     * @param type       The name of the type of workshifts wanted.
     * @return A list of workshifts of a specific type.
     */
    private List<WorkShift> typeOfWorkshift(List<WorkShift> workShifts, String type) {
        List<WorkShift> typeOfWorkshift = new ArrayList<>();
        for (WorkShift workshift : workShifts) {
            if (workshift.getType().equals(type)) {
                typeOfWorkshift.add(workshift);
            }
        }
        return typeOfWorkshift;
    }

}
