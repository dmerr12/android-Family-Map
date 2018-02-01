package example.com.familymap.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeMap;

import model.Event;
import model.Person;

import static org.junit.Assert.*;

/**
 * Created by dcmer on 12/13/2017.
 */
public class ModelTestFindSpouseBirth {
    @Test
    public void mapPersonIDtoSpouse() throws Exception {
        Person person = new Person("a","b","c","d","e","f","g","f");
        Person person2 = new Person("f","b","c","d","e","i","g","a");
        Event event1 = new Event("a","a","f",3.0,2.0,"a","a","birth",1219);
        Event event2 = new Event("b","b","f",3.0,2.0,"b","b","marriage",1500);
        Event event3 = new Event("c","b","f",3.0,2.0,"b","b","death",1600);
        Event event4 = new Event("a","a","a",3.0,2.0,"a","a","birth",1455);
        Model model = Model.getInstance();
        ArrayList<Person> people = new ArrayList<Person>();
        ArrayList<Event> events = new ArrayList<Event>();
        people.add(person);
        people.add(person2);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        model.setPeople(people);
        model.setEvents(events);
        model.mapEventIDtoLife();
        model.mapPersonIDtoSpouse();
        Event spouseBirth = model.getPersonToSpouseBirth().get("a");
        assertTrue(spouseBirth.getYear().equals(1219));
        assertTrue(spouseBirth.getEventType().equals("birth"));

    }

}