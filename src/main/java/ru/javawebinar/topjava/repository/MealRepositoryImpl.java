package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealRepositoryImpl implements MealRepository {
    private List<Meal> repository;

    public MealRepositoryImpl() {
        this.repository = getMealsList();
    }

    @Override
    public void save(Meal meal) {
        repository.add(meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(getById(id));
    }

    @Override
    public void update(Meal newMeal) {
        delete(newMeal.getId());
        save(newMeal);
    }


    @Override
    public List<Meal> getAll() {
        return repository;
    }

    @Override
    public Meal getById(int id) {
        return repository.get(id);
    }

    public static List<Meal> getMealsList() {
        return Arrays.asList(
                new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
    }
}
