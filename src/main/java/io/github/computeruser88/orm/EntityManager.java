package io.github.computeruser88.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface EntityManager<T> {


   void persist(T t) throws SQLException, IllegalArgumentException, IllegalAccessException;


    T find(Class<T> clss, long primaryKey) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
