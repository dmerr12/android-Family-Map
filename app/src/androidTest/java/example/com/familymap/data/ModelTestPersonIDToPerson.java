package example.com.familymap.data;

import org.junit.Test;

import java.util.ArrayList;

import model.Person;

import static org.junit.Assert.*;

/**
 * Created by dcmer on 12/13/2017.
 */
public class ModelTestPersonIDToPerson {
    @Test
    public void mapPersonIDtoPerson() throws Exception {

        Person person = new Person("a","b","c","d","e","f","g","h");
        Person person2 = new Person("f","f","f","q","e","i","g","h");
        Person person3 = new Person("i","t","r","u","e","j","g","h");
        Person person4 = new Person("j","v","x","e","e","k","g","h");
        Model model = Model.getInstance();
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(person);
        people.add(person2);
        people.add(person3);
        people.add(person4);
        model.setPeople(people);
        model.mapPersonIDtoPerson();
        Person find = model.getPersonIDPersonMap().get("a");
        assertTrue(find.getFirstName().equals("c"));
        assertTrue(find.getLastName().equals("d"));

    }

}