package example.com.familymap;

import org.junit.Test;

import java.util.ArrayList;

import model.Event;
import model.Person;

import static org.junit.Assert.*;

/**
 * Created by dcmer on 12/13/2017.
 */
public class ClientTestEvents {
    @Test
    public void getEvents() throws Exception {
        String auth = "3ASQGJRIAH";
        String serverHost = "192.168.1.7";
        String serverPort = "9000";
        Client client = new Client();
        ArrayList<Event> events = client.getEvents(serverHost, serverPort,auth);
        assertTrue(events.get(0).getDescendant().equals("sheila"));
        assertTrue(events.size()==8);

    }

}