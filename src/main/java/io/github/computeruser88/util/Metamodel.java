package io.github.computeruser88.util;

import io.github.computeruser88.annotation.Column;
import io.github.computeruser88.annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Metamodel {

    private Class<?> clss;

    public static Metamodel of(Class<?> clss) {
        return new Metamodel(clss);
    }

    public Metamodel(Class<?> clss) {
        this.clss = clss;
    }

    public PrimaryKeyField getPrimaryKey() {

        Field[] fields = clss.getDeclaredFields();
        for (Field field: fields) {
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                PrimaryKeyField primaryKeyField = new PrimaryKeyField(field);
                return primaryKeyField;
            }
        }
        throw new IllegalArgumentException("No primary key found for class " + clss.getName());
    }

    public List<ColumnField> getColumns() {
        List<ColumnField> columnFields = new ArrayList<>();
        Field[] fields = clss.getDeclaredFields();
        for (Field field: fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                ColumnField columnField = new ColumnField(field);
                columnFields.add(columnField);
            }
        }
        return columnFields;
    }

    public String buildInsertRequest() {
        String columnElement = buildColumnNames();
        int numberOfColumns = getColumns().size() + 1;
        String questionMarksElement =
                buildQuestionMarksElement(numberOfColumns);

        return "insert into " + this.clss.getSimpleName() +
                " (" + columnElement + ") values (" + questionMarksElement + ")";
    }

    private String buildQuestionMarksElement(int numberOfColumns) {
        return IntStream.range(0, numberOfColumns)
            .mapToObj(index -> "?")
            .collect(Collectors.joining(", "));
    }

    private String buildColumnNames() {
        String primaryKeyColumnName = getPrimaryKey().getName();
        List<String> columnNames = getColumns().stream().map(ColumnField::getName).collect(Collectors.toList());
        columnNames.add(0, primaryKeyColumnName);
        String columnElement = String.join(", ", columnNames);
        return columnElement;
    }

    public String buildSelectRequest() {
        String columnElement = buildColumnNames();
        return "select " + columnElement + " from " + this.clss.getSimpleName() +
                " where " + getPrimaryKey().getName() + "=?";
    }
}
