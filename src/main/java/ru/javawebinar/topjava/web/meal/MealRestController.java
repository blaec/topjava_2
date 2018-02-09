package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private MealService service;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = getUserId();
        checkNew(meal);
        log.info("save {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public Meal update(Meal meal, int id) {
        int userId = getUserId();
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, userId);
        return service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = getUserId();
        log.info("delete {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = getUserId();
        log.info("get {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public List<MealWithExceed> getAll() {
        int userId = getUserId();
        log.info("getAll for user {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime,
                                           LocalDate endDate, LocalTime endTime) {
        int userId = getUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenDates(
                startDate != null ? startDate : LocalDate.MIN,
                endDate != null ? endDate : LocalDate.MAX,
                userId);

        return MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                userId);
    }

    private int getUserId() {
        return AuthorizedUser.id();
    }
}