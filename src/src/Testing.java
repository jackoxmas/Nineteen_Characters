/**
 * Implementor: Alex Stewart
 * Last Update: 15-02-11
 */
package src;

import src.controller.Avatar;

import java.lang.reflect.Field;

/**
 * This class holds static methods for testing individual elements of the 
 * project.
 * 
 * @author Alex Stewart
 */
public class Testing {

    public static void main (String[] args) {
        System.out.println("DECL:");
        for (Field f : Avatar.class.getDeclaredFields())
            System.out.println(f.getName());

        System.out.println("OTHER:");
        for (Field f : Avatar.class.getFields())
            System.out.println(f.getName());
    }
}
