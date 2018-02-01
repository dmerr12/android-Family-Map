package example.com.familymap.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import example.com.familymap.data.Model;
import example.com.familymap.adapter.MySearchAdapter;
import example.com.familymap.R;
import model.Event;
import model.Person;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button addLineButton;
    private int lineCounter;
    private EditText search;
    private String searchText;
    private Model model;

    private List<String> lines;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Up:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        search = (EditText)findViewById(R.id.search);

        model = Model.getInstance();
        lineCounter = 3;

        persons = new ArrayList<Person>();
        events = new ArrayList<Event>();
        addLineButton = (Button)findViewById(R.id.addLine);
        addLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lines = new ArrayList<>();
                persons = new ArrayList<Person>();
                events = new ArrayList<Event>();
                searchText = search.getText().toString();
                for(int i = 0; i<model.getPeople().size();i++){
                    if(model.getPeople().get(i).getFirstName().toLowerCase().contains(searchText) || model.getPeople().get(i).getLastName().toLowerCase().contains(searchText) ){
                        lines.add(model.getPeople().get(i).getFirstName() + " " + model.getPeople().get(i).getLastName());
                        persons.add(model.getPeople().get(i));
                    }
                }
                for(int i = 0;i<model.getEvents().size();i++){
                    if(model.getEvents().get(i).getCity().toLowerCase().contains(searchText)||model.getEvents().get(i).getCountry().toLowerCase().contains(searchText)
                            || model.getEvents().get(i).getEventType().toLowerCase().contains(searchText)||model.getEvents().get(i).getYear().toString().toLowerCase().contains(searchText)){
                        lines.add(model.getEvents().get(i).getEventType()+": "+model.getEvents().get(i).getCity()+", "
                                + model.getEvents().get(i).getCountry()+" ("+model.getEvents().get(i).getYear()+")\n"+model.getEventPersonMap().get(model.getEvents().get(i).getEventID()).getFirstName()+" "+model.getEventPersonMap().get(model.getEvents().get(i).getEventID()).getLastName());
                    events.add(model.getEvents().get(i));
                    }
                }
                mAdapter = new MySearchAdapter(lines,persons, events);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager)mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);


        lines = new ArrayList<>();
        Person whatever = model.getPeople().get(0);
        Event whatever2 = model.getEvents().get(0);
        events.add(whatever2);
        persons.add(whatever);
        mAdapter = new MySearchAdapter(lines,persons,events);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
