package example.com.familymap;

import org.junit.Test;

import request.LoginRequest;
import request.RegisterRequest;

import static org.junit.Assert.*;

/**
 * Created by dcmer on 12/13/2017.
 */
public class ClientTestlogIn {

    @Test
    public void logIn() throws Exception {
        LoginRequest t = new LoginRequest();
        t.setUserName("susy");
        t.setPassword("pass");
        Client client = new Client();
        String serverHost = "192.168.1.7";
        String serverPort = "9000";
        String authToken;
        authToken = client.logIn(serverHost,serverPort,t);
        assertTrue(authToken.length()==10);

    }

}