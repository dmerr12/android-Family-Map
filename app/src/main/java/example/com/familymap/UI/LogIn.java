package example.com.familymap.UI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import example.com.familymap.Client;
import example.com.familymap.R;
import example.com.familymap.data.Model;
import model.Person;
import request.LoginRequest;
import request.RegisterRequest;


public class LogIn extends Fragment {
    private EditText serverHostEditText;
    private EditText serverPortEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private Button loginButton;
    private Button registerButton;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private RadioGroup genderGroup;


    public LogIn() {
        // Required empty public constructor
    }


    public static LogIn newInstance() {
        LogIn fragment = new LogIn();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        MenuItem item = menu.findItem(R.id.miFilter);
        item.setVisible(false);
        item = menu.findItem(R.id.miSearch);
        item.setVisible(false);
        item = menu.findItem(R.id.miSettings);
        item.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_in, container, false);
        serverHostEditText = (EditText) v.findViewById(R.id.serverHostEditText);
        serverPortEditText = (EditText) v.findViewById(R.id.serverPortEditText);
        usernameEditText = (EditText) v.findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) v.findViewById(R.id.passwordEditText);
        firstNameEditText = (EditText) v.findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) v.findViewById(R.id.lastNameEditText);
        emailEditText = (EditText) v.findViewById(R.id.emailEditText);
        loginButton = (Button) v.findViewById(R.id.loginButton);
        registerButton = (Button) v.findViewById(R.id.registerButton);
        genderGroup = (RadioGroup) v.findViewById(R.id.genderGroup);
        maleButton = (RadioButton) v.findViewById(R.id.maleButton);
        femaleButton = (RadioButton) v.findViewById(R.id.femaleButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogInButtonClicked();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClicked();
            }
        });
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleButton.setChecked(true);
            }
        });
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {femaleButton.setChecked(true);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    private String serverHost;
    private String serverPort;
    private String authToken;
    private Model model = Model.getInstance();

    private void onLogInButtonClicked() {
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(usernameEditText.getText().toString());
        loginRequest.setPassword(passwordEditText.getText().toString());
        serverPort = serverPortEditText.getText().toString();
        serverHost = serverHostEditText.getText().toString();
        System.out.println(usernameEditText.getText().toString());
        System.out.println(passwordEditText.getText().toString());
        LoginTask t = new LoginTask();
        t.execute(loginRequest);




    }

    public void onRegisterButtonClicked() {
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);
        serverPort = serverPortEditText.getText().toString();
        serverHost = serverHostEditText.getText().toString();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName(usernameEditText.getText().toString());
        registerRequest.setPassword(passwordEditText.getText().toString());
        registerRequest.setFirstName(firstNameEditText.getText().toString());
        registerRequest.setLastName(lastNameEditText.getText().toString());
        registerRequest.setEmail(emailEditText.getText().toString());
        if (femaleButton.isChecked()) {
            registerRequest.setGender("f");
        } else {
            registerRequest.setGender("m");
        }
        System.out.println(registerRequest.getGender());
        RegisterTask t = new RegisterTask();
        t.execute(registerRequest);


    }


    public class LoginTask extends AsyncTask<LoginRequest, Integer, Long> {

        public LoginTask() {

        }

        protected Long doInBackground(LoginRequest... r) {

            Client httpClient = new Client();
            for (int i = 0; i < r.length; i++) {
                authToken = httpClient.logIn(serverHost, serverPort, r[i]);
            }
            long totalSize = 0;
            int progress = 0;
            publishProgress(progress);


            return totalSize;
        }


        protected void onProgressUpdate(Integer... progress) {
            int percent = progress[0];

        }

        protected void onPostExecute(Long result) {
            long totalBytes = result;
            if (!authToken.equals("")) {
                Toast.makeText(getActivity(), "Login Successful",
                        Toast.LENGTH_SHORT).show();
                PersonTask p = new PersonTask();
                p.execute(authToken);
            } else {
                Toast.makeText(getActivity(), "Login Unsuccessful",
                        Toast.LENGTH_SHORT).show();
                loginButton.setEnabled(true);
                registerButton.setEnabled(true);
            }


        }
    }

    public class RegisterTask extends AsyncTask<RegisterRequest, Integer, Long> {
        Boolean success = false;


        public RegisterTask() {

        }

        protected Long doInBackground(RegisterRequest... r) {

            Client httpClient = new Client();
            for (int i = 0; i < r.length; i++) {
                authToken = httpClient.register(serverHost, serverPort, r[i]);
            }
            long totalSize = 0;
            int progress = 0;
            publishProgress(progress);


            return totalSize;
        }


        protected void onProgressUpdate(Integer... progress) {
            int percent = progress[0];
            //context.onProgressUpdate(percent);
        }

        protected void onPostExecute(Long result) {
            long totalBytes = result;
            System.out.println("Finished Executing");
            if (!authToken.equals("")) {
                Toast.makeText(getActivity(), "Register Successful",
                        Toast.LENGTH_SHORT).show();
                PersonTask p = new PersonTask();
                p.execute(authToken);
            } else {
                Toast.makeText(getActivity(), "Register Unsuccessful",
                        Toast.LENGTH_SHORT).show();
                loginButton.setEnabled(true);
                registerButton.setEnabled(true);
            }



        }
    }

    public class PersonTask extends AsyncTask<String, Integer, Long> {

        public PersonTask() {

        }

        protected Long doInBackground(String... r) {
            ArrayList<Person> peron = new ArrayList<Person>();
            Client httpClient = new Client();
            for (int i = 0; i < r.length; i++) {
                peron = httpClient.getPersons(serverHost,serverPort,r[i]);
                model.setPeople(peron);
            }
            long totalSize = 0;
            int progress = 0;
            publishProgress(progress);


            return totalSize;
        }


        protected void onProgressUpdate(Integer... progress) {
            int percent = progress[0];
            //context.onProgressUpdate(percent);
        }

        protected void onPostExecute(Long result) {
            long totalBytes = result;
            EventTask e = new EventTask();
            e.execute(authToken);


        }


    }

    public class EventTask extends AsyncTask<String, Integer, Long> {

        public EventTask() {

        }

        protected Long doInBackground(String... r) {

            Client httpClient = new Client();
            for (int i = 0; i < r.length; i++) {
                model.setEvents(httpClient.getEvents(serverHost, serverPort, r[i]));
            }
            long totalSize = 0;
            int progress = 0;
            publishProgress(progress);


            return totalSize;
        }


        protected void onProgressUpdate(Integer... progress) {
            int percent = progress[0];
        }

        protected void onPostExecute(Long result) {
            long totalBytes = result;
            if (model.getPeople() != null) {
                Toast.makeText(getActivity(), "Hello "+model.getPeople().get(0).getFirstName()+" "+model.getPeople().get(0).getLastName(),
                        Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).onLogin();
            } else {
                Toast.makeText(getActivity(), "People Unsuccessful",
                        Toast.LENGTH_SHORT).show();
            }
            loginButton.setEnabled(true);
            registerButton.setEnabled(true);

        }


    }
}
