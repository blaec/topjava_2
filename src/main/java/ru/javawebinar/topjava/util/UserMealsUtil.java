package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).toString());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> sumCalories = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate localDate = meal.getDateTime().toLocalDate();
            if (sumCalories.containsKey(localDate)) {
                sumCalories.put(localDate, sumCalories.get(localDate) + meal.getCalories());
            } else {
                sumCalories.put(localDate, meal.getCalories());
            }
        }

        List<UserMealWithExceed> filtered = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean exceeded = sumCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                filtered.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded));
            }
        }
        return filtered;
    }
}
