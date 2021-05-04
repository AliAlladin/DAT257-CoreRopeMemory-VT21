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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.Month;

@Controller
public class ApplicationController {

    @Autowired
    private WorkshiftService workshiftService;

    @Autowired
    private UserService userService;

    @GetMapping({"/", "/index"})
    public String hello(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            System.out.println(((UserDetails) principal).getUsername());
        } else{
            System.out.println(principal.toString());
        }

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
        }

        model.addAttribute("workshifts", workshiftService.listALl());

        model.addAttribute("currentUser", userService.getByEmail(((UserDetails) principal).getUsername()));

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
        userService.saveUserDetails(user);
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
        Set<LocalDate> dates = new HashSet<>();
        for (WorkShift workShift : typeOfWorkshift(user.getWorkshifts(), "Exam grading")){
            dates.add(workShift.getDate());
        }
        model.addAttribute("examGrading", dates.size());
        return "time_report";
    }

    /**
     * Method that gives a list of a specific type of workshift.
     * @param workShifts A list of all workshifts.
     * @param type The name of the type of workshifts wanted.
     * @return A list of workshifts of a specific type.
     */
    private List<WorkShift> typeOfWorkshift(List<WorkShift> workShifts, String type){
        List<WorkShift> typeOfWorkshift = new ArrayList<>();
        for (WorkShift workshift: workShifts) {
            if (workshift.getType().equals(type)){
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

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUser(Principal principal){
        System.out.println(principal.getName());
        return principal.getName();
    }

}
