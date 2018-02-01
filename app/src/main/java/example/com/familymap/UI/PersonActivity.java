package example.com.familymap.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import example.com.familymap.data.Model;
import example.com.familymap.R;
import model.Event;
import model.Person;

public class PersonActivity extends AppCompatActivity {
    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private LinearLayout lifeEvents;
    private LinearLayout family;
    private ImageView lifeEventCollapse;
    private ImageView familyCollapse;
    private Model model;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person, menu);
        return true;
    }

    public static void startTopActivity(Context context, boolean newInstance) {
        Intent intent = new Intent(context, MainActivity.class);
        if (newInstance) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        Bundle b = new Bundle();
        b.putInt("key", 1); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Up:
                finish();
                return true;
            case R.id.Top:
                startTopActivity(this,true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar3);
        setSupportActionBar(myToolbar);
        Bundle b = getIntent().getExtras();
        String personID = b.getString("personID");
        model = Model.getInstance();
        Person person = model.getPersonIDPersonMap().get(personID);
        final ArrayList<Event> lifeEventsArray = model.getLifeEventsMap().get(personID);
        firstName = (TextView) findViewById(R.id.firstDefine);
        lastName = (TextView) findViewById(R.id.lastDefine);
        gender = (TextView) findViewById(R.id.genderDefine);
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if(person.getGender().equals("f")){
            gender.setText("Female");
        }
        else{
            gender.setText("Male");
        }
        lifeEvents = (LinearLayout) findViewById(R.id.lifeeventslist);
        family = (LinearLayout) findViewById(R.id.familylist);
        lifeEventCollapse = (ImageView) findViewById(R.id.imageView2);
        familyCollapse = (ImageView) findViewById(R.id.imageView3);
        for(int i =0;i<lifeEventsArray.size();i++) {
            LinearLayout lin = new LinearLayout(this);
            TextView test = new TextView(this);
            ImageView marker = new ImageView(this);
            Drawable genderIcon = new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.colorPrimary).sizeDp(40);
            marker.setImageDrawable(genderIcon);
            lin.addView(marker);
            test.setText(lifeEventsArray.get(i).getEventType()+": "+lifeEventsArray.get(i).getCity()+", "
                    + lifeEventsArray.get(i).getCountry()+" ("+lifeEventsArray.get(i).getYear()+")\n"+person.getFirstName()+" "+person.getLastName());
            lin.addView(test);
            final String eventID = lifeEventsArray.get(i).getEventID();
            lin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(PersonActivity.this, MapActivity.class);
                    Bundle b = new Bundle();
                    b.putString("eventID",eventID);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
            lifeEvents.addView(lin);
        }
        lifeEvents.setVisibility(View.VISIBLE);
        lifeEventCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lifeEvents.getVisibility()==View.VISIBLE){
                    lifeEvents.setVisibility(View.GONE);
                }
                else{
                    lifeEvents.setVisibility(View.VISIBLE);
                }
            }
        });
        if(person.getFather()!=null) {
            if(!person.getFather().equals("")) {
                LinearLayout lin = new LinearLayout(this);
                TextView test = new TextView(this);
                ImageView marker = new ImageView(this);
                Drawable genderIcon = new IconDrawable(this, FontAwesomeIcons.fa_male).colorRes(R.color.colorPrimary).sizeDp(40);
                marker.setImageDrawable(genderIcon);
                lin.addView(marker);
                final Person father = model.getPersonIDPersonMap().get(person.getFather());
                test.setText(father.getFirstName() + " " + father.getLastName() + "\nFather");
                lin.addView(test);
                family.addView(lin);
                lin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                        Bundle b = new Bundle();
                        b.putString("personID", father.getPersonID());
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
            }
        }
        if(person.getMother()!=null) {
            if (!person.getMother().equals("")) {
                LinearLayout lin2 = new LinearLayout(this);
                TextView test2 = new TextView(this);
                ImageView marker2 = new ImageView(this);
                Drawable genderIcon2 = new IconDrawable(this, FontAwesomeIcons.fa_female).colorRes(R.color.colorAccent).sizeDp(40);
                marker2.setImageDrawable(genderIcon2);
                lin2.addView(marker2);
                final Person mother = model.getPersonIDPersonMap().get(person.getMother());
                test2.setText(mother.getFirstName() + " " + mother.getLastName() + "\nMother");
                lin2.addView(test2);
                family.addView(lin2);
                lin2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                        Bundle b = new Bundle();
                        b.putString("personID", mother.getPersonID());
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
            }
        }
        if(person.getSpouse()!=null){
            if(!person.getSpouse().equals("")){
                LinearLayout lin2 = new LinearLayout(this);
                TextView test2 = new TextView(this);
                ImageView marker2 = new ImageView(this);
                final Person spouse = model.getPersonIDPersonMap().get(person.getSpouse());
                Drawable genderIcon2;
                if(spouse.getGender().equals("f")) {
                     genderIcon2 = new IconDrawable(this, FontAwesomeIcons.fa_female).colorRes(R.color.colorAccent).sizeDp(40);
                }
                else{
                    genderIcon2 = new IconDrawable(this, FontAwesomeIcons.fa_male).colorRes(R.color.colorPrimary).sizeDp(40);
                }
                marker2.setImageDrawable(genderIcon2);
                lin2.addView(marker2);
                test2.setText(spouse.getFirstName() + " " + spouse.getLastName() + "\nSpouse");
                lin2.addView(test2);
                family.addView(lin2);
                lin2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                        Bundle b = new Bundle();
                        b.putString("personID", spouse.getPersonID());
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
            }
        }
        family.setVisibility(View.VISIBLE);
        familyCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(family.getVisibility()==View.VISIBLE){
                    family.setVisibility(View.GONE);
                }
                else{
                    family.setVisibility(View.VISIBLE);
                }
            }
        });


    }
}
