package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.MEAL_LIST;

public class MealRepositoryImpl implements MealRepository {
    private List<Meal> repository;

    public MealRepositoryImpl() {
        this.repository = MEAL_LIST;
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

}
