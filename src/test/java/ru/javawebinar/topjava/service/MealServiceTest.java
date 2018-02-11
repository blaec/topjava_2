package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        MealTestData.assertMatch(meal, USER_MEAL1);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_MEAL_ID, USER_ID);
        MealTestData.assertMatch(service.getAll(USER_ID), Arrays.asList(USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2));
    }

    @Test
    public void getBetweenDates() {
        List<Meal> sorted = service.getBetweenDates(
                LocalDate.of(2015, 05, 31),
                LocalDate.of(2015, 05, 31),
                USER_ID);
        MealTestData.assertMatch(sorted, USER_MEAL6, USER_MEAL5, USER_MEAL4);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> sorted = service.getBetweenDateTimes(
                LocalDateTime.of(2015, 05, 31,10,0),
                LocalDateTime.of(2015, 05, 31,13,0),
                USER_ID);
        MealTestData.assertMatch(sorted, USER_MEAL5, USER_MEAL4);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        MealTestData.assertMatch(all, USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(USER_MEAL1);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        MealTestData.assertMatch(service.get(USER_MEAL_ID, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2018, Month.MAY, 30, 13, 0), "New",  1555);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MealTestData.assertMatch(service.getAll(USER_ID), created, USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteOtherOnesMeal() {
        service.delete(ADMIN_MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getOtherOnesMeal() {
        service.get(ADMIN_MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherOnesMeal() {
        Meal updated = new Meal(USER_MEAL1);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(330);
        service.update(updated, ADMIN_ID);
    }

    @Test(expected = Exception.class)
    public void createWithSameDateTime() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "New",  1555);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MealTestData.assertMatch(service.getAll(USER_ID), created, USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1);
    }
}