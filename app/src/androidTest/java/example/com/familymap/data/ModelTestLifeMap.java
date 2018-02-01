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
public class ModelTestLifeMap {
    @Test
    public void mapEventIDtoLife() throws Exception {
        Person person = new Person("a","b","c","d","e","f","g","h");
        Person person2 = new Person("f","b","c","d","e","i","g","h");
        Person person3 = new Person("i","b","c","d","e","j","g","h");
        Person person4 = new Person("j","b","c","d","e","k","g","h");
        Event event1 = new Event("a","a","a",3.0,2.0,"a","a","birth",1219);
        Event event2 = new Event("b","b","a",3.0,2.0,"b","b","marriage",1500);
        Event event3 = new Event("c","b","a",3.0,2.0,"b","b","death",1600);
        Model model = Model.getInstance();
        ArrayList<Person> people = new ArrayList<Person>();
        ArrayList<Event> events = new ArrayList<Event>();
        people.add(person);
        people.add(person2);
        people.add(person3);
        people.add(person4);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        model.setPeople(people);
        model.setEvents(events);
        model.mapEventIDtoLife();
        TreeMap<String,ArrayList<Event>> life = model.getLifeEventsMap();
        assertTrue(life.get("a").get(0).getEventType().equals("birth"));
        assertTrue(life.get("a").get(1).getEventType().equals("marriage"));
        assertTrue(life.get("a").get(2).getEventType().equals("death"));

    }

}