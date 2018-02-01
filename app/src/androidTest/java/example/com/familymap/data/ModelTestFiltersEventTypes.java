package example.com.familymap.data;

import org.junit.Test;

import java.util.ArrayList;

import model.Event;

import static org.junit.Assert.*;

/**
 * Created by dcmer on 12/13/2017.
 */
public class ModelTestFiltersEventTypes {
    @Test
    public void findEventTypes() throws Exception {
        Event event1 = new Event("a","a","f",3.0,2.0,"a","a","birth",1219);
        Event event2 = new Event("b","b","f",3.0,2.0,"b","b","marriage",1500);
        Event event3 = new Event("c","b","f",3.0,2.0,"b","b","death",1600);
        Event event4 = new Event("a","a","a",3.0,2.0,"a","a","BIRTH",1455);
        ArrayList<Event> events = new ArrayList<Event>();
        Model model = Model.getInstance();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        model.setEvents(events);
        Settings settings = Settings.getInstance();
        model.findEventTypes();
        assertTrue(model.getEventTypes().size()==3);
        assertTrue(settings.getOneFilter(0));
        assertTrue(settings.getOneFilter(1));
        assertTrue(settings.getOneFilter(2));

    }

}