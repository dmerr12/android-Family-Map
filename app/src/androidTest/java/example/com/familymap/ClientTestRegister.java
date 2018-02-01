package example.com.familymap;

import org.junit.Test;

import request.RegisterRequest;

import static org.junit.Assert.*;

/**
 * Created by dcmer on 12/13/2017.
 */
public class ClientTestRegister {
    @Test
    public void register() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("susy");
        registerRequest.setPassword("pass");
        registerRequest.setFirstName("sus");
        registerRequest.setLastName("y");
        registerRequest.setEmail("something");
        registerRequest.setGender("f");
        Client client = new Client();
        String serverHost = "192.168.1.7";
        String serverPort = "9000";
        String authToken;
        authToken = client.register(serverHost,serverPort,registerRequest);
        assertTrue(authToken.length()==10);

    }

}