package io.github.computeruser88;

import io.github.computeruser88.model.Exercise;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class PlayWithMethodHandle {

    public static void main(String[] args) throws Throwable {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType emptyConstructorMethodType = MethodType.methodType(void.class);
        MethodHandle emptyConstructor = lookup.findConstructor(Exercise.class, emptyConstructorMethodType);

        Exercise e = (Exercise) emptyConstructor.invoke();
        System.out.println(e);

        MethodType constructorMethodType = MethodType.methodType(void.class, String.class, String.class);
        MethodHandle constructor = lookup.findConstructor(Exercise.class, constructorMethodType);

        Exercise e2 = (Exercise) constructor.invoke("Jogging", "CARDIO");
        System.out.println(e2);

        MethodType nameGetterMethodType = MethodType.methodType(String.class);
        MethodHandle nameGetter = lookup.findVirtual(Exercise.class, "getName", nameGetterMethodType);

        String name = (String) nameGetter.invoke(e2);
        System.out.println(name);

        MethodType nameSetterMethodType = MethodType.methodType(void.class, String.class);
        MethodHandle nameSetter = lookup.findVirtual(Exercise.class, "setName", nameSetterMethodType);

        nameSetter.invoke(e2, "Running");
        System.out.println(e2);

        MethodHandles.Lookup privateLookup = MethodHandles.privateLookupIn(Exercise.class, lookup);
        MethodHandle nameReader = privateLookup.findGetter(Exercise.class, "name", String.class);
        String name2 = (String) nameReader.invoke(e2);
        System.out.println(name2);
    }

}
