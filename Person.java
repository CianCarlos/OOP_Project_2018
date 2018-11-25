package Checkers;

/**
 * Created by CianC on 17/11/2018.
 */
public abstract class Person implements PlayerNames{
    private String name;

    Person() {this("Unknown");
    }

    Person(String name) {
        setName(name);
    }

    String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Name: " + getName();
    }
}
