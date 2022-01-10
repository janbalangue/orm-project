package io.github.computeruser88;

import io.github.computeruser88.beanmanager.BeanManager;
import io.github.computeruser88.model.Exercise;
import io.github.computeruser88.orm.EntityManager;
import io.github.computeruser88.orm.ManagedEntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class ReadingObjects {
    public static void main(String[] args) throws Exception {

        BeanManager beanManager = BeanManager.getInstance();

        EntityManager<Exercise> entityManager = beanManager.getInstance(ManagedEntityManager.class);

        Exercise squat = entityManager.find(Exercise.class, 1L);
        Exercise benchPress = entityManager.find(Exercise.class, 2L);
        Exercise deadlift = entityManager.find(Exercise.class, 3L);
        Exercise walk = entityManager.find(Exercise.class, 4L);
        Exercise foamRoll = entityManager.find(Exercise.class, 5L);

        System.out.println(squat);
        System.out.println(benchPress);
        System.out.println(deadlift);
        System.out.println(walk);
        System.out.println(foamRoll);
    }



}
