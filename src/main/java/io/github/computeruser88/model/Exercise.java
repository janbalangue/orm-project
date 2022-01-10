package io.github.computeruser88.model;

import io.github.computeruser88.annotation.Column;
import io.github.computeruser88.annotation.PrimaryKey;

public class Exercise {

    @PrimaryKey(name="k_id")
    private long id;

    @Column(name="c_name")
    private String name;

    @Column(name="e_type")
    private String exerciseType;

    public Exercise() {
    }

    public Exercise(String name, String exerciseType) {
        this.name = name;
        this.exerciseType = exerciseType;
    }

    public Exercise of(String name, String exerciseType) {
        return new Exercise(name, exerciseType);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exerciseType=" + exerciseType +
                '}';
    }
}
