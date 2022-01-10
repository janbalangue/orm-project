package io.github.computeruser88.orm;

import io.github.computeruser88.annotation.Inject;
import io.github.computeruser88.beanmanager.BeanManager;
import io.github.computeruser88.util.ColumnField;
import io.github.computeruser88.util.Metamodel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;


public class ManagedEntityManager<T> implements EntityManager<T> {

    @Inject
    private Connection connection;

    private AtomicLong idGenerator = new AtomicLong(0L);

    @Override
    public void persist(T t) throws SQLException, IllegalArgumentException, IllegalAccessException {
        Metamodel metamodel = Metamodel.of(t.getClass());
        String sql = metamodel.buildInsertRequest();
        try (PreparedStatement statement = prepareStatementWith(sql).andParameters(t);) {
            statement.executeUpdate();
        }
    }

    @Override
    public T find(Class<T> clss, long primaryKey) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Metamodel metamodel = Metamodel.of(clss);
        String sql = metamodel.buildSelectRequest();
        try (PreparedStatement statement = prepareStatementWith(sql).andPrimaryKey(primaryKey);) {
            ResultSet resultSet = statement.executeQuery();
            return buildInstanceFrom(clss, resultSet);
        }
    }

    private T buildInstanceFrom(Class<T> clss, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Metamodel metamodel = Metamodel.of(clss);
        T t = clss.getConstructor().newInstance();
        Field primaryKeyField = metamodel.getPrimaryKey().getField();
        String primaryKeyColumnName = metamodel.getPrimaryKey().getName();
        Class<?> primaryKeyType = primaryKeyField.getType();
        resultSet.next();
        if (primaryKeyType == long.class) {
            long primaryKey = resultSet.getInt(primaryKeyColumnName);
            primaryKeyField.setAccessible(true);
            primaryKeyField.set(t, primaryKey);
        }
        for (ColumnField columnField: metamodel.getColumns()) {
            Field field = columnField.getField();
            field.setAccessible(true);
            Class<?> columnType = columnField.getType();
            String columnName = columnField.getName();
            if (columnType == String.class) {
                String value = resultSet.getString(columnName);
                field.set(t, value);
            }
        }
        return t;
    }

    private PreparedStatementWrapper prepareStatementWith(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        return new PreparedStatementWrapper(statement);
    }

    private class PreparedStatementWrapper {

        private PreparedStatement statement;

        public PreparedStatementWrapper(PreparedStatement statement) {
            this.statement = statement;
        }

        public PreparedStatement andParameters(T t) throws SQLException, IllegalArgumentException, IllegalAccessException {
            Metamodel metamodel = Metamodel.of(t.getClass());
            Class<?> primaryKeyType = metamodel.getPrimaryKey().getType();
            if (primaryKeyType == long.class) {
                long id = idGenerator.incrementAndGet();
                statement.setLong(1, id);
                Field field = metamodel.getPrimaryKey().getField();
                field.setAccessible(true);
                field.set(t, id);
            }

            for (int columnIndex = 0; columnIndex < metamodel.getColumns().size(); columnIndex++) {
                ColumnField columnField = metamodel.getColumns().get(columnIndex);
                Class<?> fieldType = columnField.getType();
                Field field = columnField.getField();
                field.setAccessible(true);
                Object value = field.get(t);
                if (fieldType == String.class) {
                    statement.setString(columnIndex + 2, (String) value);
                }
            }
            return statement;
        }

        public PreparedStatement andPrimaryKey(Object primaryKey) throws SQLException {
            if (primaryKey.getClass() == Long.class) {
                statement.setLong(1, (Long) primaryKey);
            }
            return statement;
        }
    }
}
