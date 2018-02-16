package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);

        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            if (this.get(meal.getId(), userId) == null)
                return null;
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return this.getAll(userId).stream()
                .filter(m -> m.getId()==id)
                .findFirst().orElse(null);

/*        List<Meal> meals = em.createNamedQuery(Meal.GET_MEAL, Meal.class)
                .setParameter(1, userId)
                .setParameter(2, id)
                .getResultList();
        Meal foundMeal = null;
        if (!meals.isEmpty()) {
            foundMeal = meals.get(0);
        }
        return foundMeal;*/
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.All_SORTED, Meal.class)
                .setParameter(1, userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("userId", userId)
                .setParameter("from", startDate)
                .setParameter("to", endDate)
                .getResultList();
    }
}