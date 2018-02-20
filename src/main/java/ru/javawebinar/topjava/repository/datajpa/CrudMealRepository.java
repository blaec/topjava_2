package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Override
    List<Meal> findAll(Sort sort);

    @Modifying
    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAllByQuery(@Param("userId") int userId);

    @Modifying
    @Query(name = Meal.GET_BETWEEN)
    List<Meal> findAllByQueryAndGetBetween(@Param("userId") int userId, @Param("startDate") LocalDateTime start, @Param("endDate") LocalDateTime end);

    @Override
    Optional<Meal> findById(Integer id);

    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
//    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);
}
