package com.CoreRopeMemory.TAPortal;

import com.CoreRopeMemory.TAPortal.Services.UserService;
import com.CoreRopeMemory.TAPortal.Services.WorkshiftService;
import com.CoreRopeMemory.TAPortal.model.User;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.time.Month;

@Controller
public class ApplicationController {



    @Autowired
    private WorkshiftService workshiftService;

    @Autowired
    private UserService userService;

    @GetMapping({"/", "/index"})
    public String hello(Model model) {


        model.addAttribute("workshifts", workshiftService.listALl());

        model.addAttribute("currentUser", userService.getByEmail(getCurrentUserEmail()));

        LinkedHashMap<String, List<WorkShift>> months = new LinkedHashMap<>();
        months.put("JANUARY", workshiftService.listByMonth(Month.JANUARY, getCurrentUserEmail()));
        months.put("FEBRUARY", workshiftService.listByMonth(Month.FEBRUARY, getCurrentUserEmail()));
        months.put("MARCH", workshiftService.listByMonth(Month.MARCH, getCurrentUserEmail()));
        months.put("APRIL", workshiftService.listByMonth(Month.APRIL, getCurrentUserEmail()));
        months.put("MAY", workshiftService.listByMonth(Month.MAY, getCurrentUserEmail()));
        months.put("JUNE", workshiftService.listByMonth(Month.JUNE, getCurrentUserEmail()));
        months.put("JULY", workshiftService.listByMonth(Month.JULY, getCurrentUserEmail()));
        months.put("AUGUST", workshiftService.listByMonth(Month.AUGUST, getCurrentUserEmail()));
        months.put("SEPTEMBER", workshiftService.listByMonth(Month.SEPTEMBER, getCurrentUserEmail()));
        months.put("OCTOBER", workshiftService.listByMonth(Month.OCTOBER, getCurrentUserEmail()));
        months.put("NOVEMBER", workshiftService.listByMonth(Month.NOVEMBER, getCurrentUserEmail()));
        months.put("DECEMBER", workshiftService.listByMonth(Month.DECEMBER, getCurrentUserEmail()));

        model.addAttribute("months", months);


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
    public String addWorkshift(@ModelAttribute ("workshift")WorkShift workShift){
        User user = userService.getByEmail(getCurrentUserEmail());
        workShift.setTa(user);
        user.addWorkshift(workShift);
        workshiftService.save(workShift);

        return "redirect:/";
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute ("workshift")WorkShift workShift, @PathVariable (value = "id") long id) {
        User user = userService.getByEmail(getCurrentUserEmail());
        workShift.setTa(user);
        workshiftService.save(workShift);
        return "redirect:/";
    }

    @RequestMapping(value = {"/delete_workshift/{id}"})
    public String delete(@PathVariable(value = "id") long id) {
        workshiftService.delete(id);
        return "redirect:/";
    }



    @RequestMapping ({"/user_details"})
    public String user(Model model){
        User user = userService.getByEmail(getCurrentUserEmail());
        model.addAttribute("user", user);
        return "user_details";
    }

    @RequestMapping(value = {"/saveUser"}, method = RequestMethod.POST)
    public String saveUserInfo(@ModelAttribute ("user")User user){
        userService.saveUserDetails(user);
        return "redirect:/user_details";
    }

    @RequestMapping({"/time_report/{month}"})
    public String timeReport(@PathVariable("month") Month month, Model model) {


        List<WorkShift> workshifts = workshiftService.listByMonth(month, getCurrentUserEmail());
        User user = userService.getByEmail(getCurrentUserEmail());
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

    @GetMapping("/login")
    public String login() {
        if (userService.isEmpty()){
            User user = new User("123456",
                    "test.person@mail.com",
                    "Person",
                    "Test",
                    "Kungsgatan 1",
                    12345,
                    "Göteborg",
                    false,
                    "123");
            userService.save(user);

            User user1 = new User("1234567",
                    "test.person1@mail.com",
                    "Person1",
                    "Test1",
                    "Kungsgatan 12",
                    12345,
                    "Göteborg1",
                    false,
                    "123");
            userService.save(user1);
        }
        return "login";
    }
    
    public String getCurrentUserEmail(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = (((UserDetails) principal).getUsername());
        return userEmail;
    }

}
