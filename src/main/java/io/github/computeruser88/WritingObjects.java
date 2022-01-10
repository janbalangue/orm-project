package io.github.computeruser88;

import io.github.computeruser88.model.Exercise;
import io.github.computeruser88.orm.EntityManager;

public class WritingObjects {

    public static void main(String[] args) throws Exception {
        EntityManager<Exercise> entityManager = EntityManager.of(Exercise.class);

        Exercise squat = new Exercise("Squat", "STRENGTH_TRAINING");
        Exercise benchPress = new Exercise("Bench Press", "STRENGTH_TRAINING");
        Exercise deadlift = new Exercise("Deadlift", "STRENGTH_TRAINING");
        Exercise walk = new Exercise("Walk", "CARDIO");
        Exercise foamRoll = new Exercise("Foam Roll", "MOBILITY");

        entityManager.persist(squat);
        entityManager.persist(benchPress);
        entityManager.persist(deadlift);
        entityManager.persist(walk);
        entityManager.persist(foamRoll);

        System.out.println("Writing to DB");

        System.out.println(squat);
        System.out.println(benchPress);
        System.out.println(deadlift);
        System.out.println(walk);
        System.out.println(foamRoll);
    }

}
