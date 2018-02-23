package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT_DATE = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private CrudMealRepository mealRepository;

    @Autowired
    private CrudUserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(getUser(userId));
        return mealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return mealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal found = mealRepository.findById(id).orElse(null);
        return found != null && isUsersMeal(found, userId) ? found : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
//        return mealRepository.findAll(SORT_DATE).stream()
//                .filter(m -> isUsersMeal(m, userId))
//                .collect(Collectors.toList());

        return mealRepository.findAllByQuery(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
//        return this.getAll(userId).stream()
////                .filter(m -> m.getDateTime().isAfter(startDate) && m.getDateTime().isBefore(endDate))
//                .filter(m -> DateTimeUtil.isBetween(m.getDateTime(), startDate, endDate))
//                .collect(Collectors.toList());

        return mealRepository.findAllByQueryAndGetBetween(userId, startDate, endDate);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return mealRepository.getWithUser(id, userId);
    }

    private User getUser(int userId) {
        return userRepository.getOne(userId);
    }

    private boolean isUsersMeal(Meal meal, int userId) {
        return meal.getUser().getId() == userId;
    }
}
