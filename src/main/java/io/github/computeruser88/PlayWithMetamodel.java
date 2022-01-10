package io.github.computeruser88;

import io.github.computeruser88.model.Exercise;
import io.github.computeruser88.util.ColumnField;
import io.github.computeruser88.util.Metamodel;
import io.github.computeruser88.util.PrimaryKeyField;

import java.util.List;

public class PlayWithMetamodel {

    public static void main(String[] args) {

        Metamodel metamodel = Metamodel.of(Exercise.class);

        PrimaryKeyField primaryKeyField = metamodel.getPrimaryKey();
        List<ColumnField> columnFields = metamodel.getColumns();

        System.out.println("Primary key name = " + primaryKeyField.getName() +
                ", type = " + primaryKeyField.getType());

        for (ColumnField columnField: columnFields) {
            System.out.println("Column name = " + columnField.getName() +
                    ", type = " + columnField.getType());
        }
    }
}
