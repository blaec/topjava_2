package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
//@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    private MealService service;

    @GetMapping(value = "/meals")
    public String meals(Model model) {
        List<MealWithExceed> meals = MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping(value = "/delete")
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        service.delete(id, AuthorizedUser.id());
        return "redirect:meals";
    }

    @GetMapping(value = {"/create","/update"})
    public String modify(HttpServletRequest request, Model model) {
        final Meal meal = "/create".equals(request.getServletPath()) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                service.get(getId(request), AuthorizedUser.id());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping(value = "/add")
    public String addChanges(HttpServletRequest request) {
        int userId = AuthorizedUser.id();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            service.create(meal, userId);
        } else {
            // TODO update's not working - constraint violation???
            // passes when this contraint is removed (initDB commented line meals_unique_user_datetime_idx)
            service.update(meal, userId);
        }
        return "redirect:meals";
    }

    @PostMapping(value = "/meals")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        List<Meal> mealsDateFiltered = service.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, AuthorizedUser.id());

        List<MealWithExceed> meals =  MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );

        model.addAttribute("meals", meals);
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
