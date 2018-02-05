package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meal.setUserId(meal.getUserId());
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        if (isUsersMeal(id)) {
            repository.remove(id);
        }
    }

    @Override
    // TODO get-update-delete - следите, чтобы не было NPE (NullPointException может быть, если в хранилище отсутствует юзер или еда).
    public Meal get(int id) {
        if (isUsersMeal(id)) {
            return repository.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream()
                .filter(m -> m.getUserId() == AuthorizedUser.id())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private boolean isUsersMeal(int id) {
        return repository.get(id).getUserId() == AuthorizedUser.id();
    }
}

