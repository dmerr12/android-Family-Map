package example.com.familymap.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import example.com.familymap.R;
import example.com.familymap.data.Settings;


public class SettingsActivity extends AppCompatActivity {
    private TextView reSync;
    private TextView logout;
    private Spinner lifeStory;
    private Spinner familyTree;
    private Spinner spouse;
    private Spinner mapType;
    private Switch  lifeStorySwitch;
    private Switch familyTreeSwitch;
    private Switch spouseSwitch;
    private Settings settings;
    private int lifeStoryPrompt;
    private int familyTreePrompt;
    private int spousePrompt;
    private int mapPrompt;
    Boolean firstPass = false;
    private Boolean ok = false;
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
        setContentView(R.layout.activity_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        settings = Settings.getInstance();
        reSync = (TextView)findViewById(R.id.textView5);
        logout = (TextView)findViewById(R.id.textView6);
        lifeStory = (Spinner)findViewById(R.id.spinner);
        familyTree = (Spinner)findViewById(R.id.spinner2);
        spouse = (Spinner)findViewById(R.id.spinner3);
        mapType = (Spinner)findViewById(R.id.spinner4);
        lifeStorySwitch = (Switch)findViewById(R.id.switch1);
        familyTreeSwitch = (Switch)findViewById(R.id.switch2);
        spouseSwitch = (Switch)findViewById(R.id.switch3);
        lifeStorySwitch.setChecked(settings.getLifeStoryLines());
        familyTreeSwitch.setChecked(settings.getFamilyTreeLines());
        spouseSwitch.setChecked(settings.getSpouseLines());
        lifeStory.setSelection(lifeStoryPrompt,false);
        lifeStorySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlifeStoryClicked();
            }
        });
        familyTreeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onfamilyTreeClicked();
            }
        });
        spouseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onspouseClicked();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlogout();
            }
        });
        reSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onresync();
            }
        });
        mapType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        mapPrompt = pos;
                        settings.setMapType(pos+1);
                        settings.setMapReset(true);
                        System.out.println(pos);     //prints the text in spinner item.

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        //.containssubstring
        familyTree.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        //familyTree.setSelection(pos);
                       System.out.println("WTF");
                        switch (pos) {
                            case 0:
                                settings.setFamilyTreeColor(Color.GREEN);
                                return;


                            case 1:
                                settings.setFamilyTreeColor(Color.BLUE);
                                return;


                            case 2:
                                settings.setFamilyTreeColor(Color.RED);
                                return;

                            case 3:
                                settings.setFamilyTreeColor(Color.MAGENTA);
                                return;

                            case 4:
                                settings.setFamilyTreeColor(Color.YELLOW);
                                return;

                            case 5:
                                settings.setFamilyTreeColor(Color.CYAN);
                                return;

                            case 6:
                                settings.setFamilyTreeColor(Color.BLACK);
                                return;

                            default:
                                settings.setFamilyTreeColor(Color.GREEN);
                        }
                        System.out.println(pos);     //prints the text in spinner item.

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        spouse.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        spousePrompt = pos;
                        switch (pos) {
                            case 0:
                                settings.setSpouseColor(Color.BLUE);
                                return;


                            case 1:
                                settings.setSpouseColor(Color.GREEN);
                                return;


                            case 2:
                                settings.setSpouseColor(Color.RED);
                                return;

                            case 3:
                                settings.setSpouseColor(Color.MAGENTA);
                                return;

                            case 4:
                                settings.setSpouseColor(Color.YELLOW);
                                return;

                            case 5:
                                settings.setSpouseColor(Color.CYAN);
                                return;

                            case 6:
                                settings.setSpouseColor(Color.BLACK);
                                return;

                            default:
                                settings.setSpouseColor(Color.BLUE);
                        }
                        System.out.println(pos);     //prints the text in spinner item.

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        lifeStory.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        // System.out.println("ITS OKAY!");

                        if (!firstPass) {
                            firstPass = true;
                        } else {
                            lifeStoryPrompt = pos;
                            switch (pos) {
                                case 0:
                                    settings.setLifeStoryColor(Color.RED);
                                    return;

                                case 1:
                                    settings.setLifeStoryColor(Color.GREEN);
                                    return;
                                case 2:
                                    settings.setLifeStoryColor(Color.BLUE);
                                case 3:
                                    settings.setLifeStoryColor(Color.MAGENTA);
                                    return;

                                case 4:
                                    settings.setLifeStoryColor(Color.YELLOW);
                                    return;

                                case 5:
                                    settings.setLifeStoryColor(Color.CYAN);
                                    return;

                                case 6:
                                    settings.setLifeStoryColor(Color.BLACK);
                                    return;

                                default:
                                    settings.setLifeStoryColor(Color.RED);

                            }
                            System.out.println(pos);     //prints the text in spinner item.

                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


    }

    private void onresync(){

    }
    private void onlogout(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void onlifeStoryClicked(){
        settings.setLifeStoryLines(lifeStorySwitch.isChecked());
    }
    private void onfamilyTreeClicked(){
        settings.setFamilyTreeLines(familyTreeSwitch.isChecked());

    }
    private void onspouseClicked(){
        settings.setSpouseLines(spouseSwitch.isChecked());
    }
}
