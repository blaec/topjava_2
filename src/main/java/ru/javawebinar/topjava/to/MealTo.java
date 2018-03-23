package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MealTo extends BaseTo {
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Range(min = 10, max = 5000, message = "must not be within range of 10 to 5,000")
    private Integer calories;

    // TODO should be LocalDateTime, need to find conversion
    @NotNull
    private String dateTime;

    public MealTo(Integer id, String description, Integer calories, String dateTime) {
        super(id);
        this.description = description;
        this.calories = calories;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.parse(dateTime);
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", dateTime=" + dateTime +
                '}';
    }
}
