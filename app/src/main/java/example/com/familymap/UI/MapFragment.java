package example.com.familymap.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.TreeMap;

import example.com.familymap.R;
import example.com.familymap.data.Model;
import example.com.familymap.data.Settings;
import model.Event;
import model.Person;


public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private TextView eventText;
    private ImageView genderView;
    private Model model;
    private TreeMap<String, Person> localMap;
    private TreeMap<String, ArrayList<Event>> localLifeMap;
    private TreeMap<String, Event> spouseBirthMap;
    private ArrayList<Polyline> past;
    private int pastSize;
    private Settings settings;
    private TreeMap<String, Event> localEventMap;
    private String eventID;
    private Event event;
    private ArrayList<String> eventTypes;
    private boolean resumeHasRun = false;

    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance(String eventID) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("eventID",eventID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventID = getArguments().getString("eventID");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        genderView = (ImageView)v.findViewById(R.id.imageView);
        settings = Settings.getInstance();
        model = Model.getInstance();
       model.mapEventIDtoPeople();
       localMap = model.getEventPersonMap();
        model.mapEventIDtoLife();
        //next
       localLifeMap = model.getLifeEventsMap();
        model.mapEventIDtoEvents();
        localEventMap=model.getEventIDEventMap();
        model.mapPersonIDtoSpouse();
        model.mapPersonIDtoPerson();
        model.mapPeopleToAncestorBirths();
        model.mapPeopleToAncestressBirths();
        model.mapPersonToChildren();
        model.findEventTypes();
       eventTypes = model.getEventTypes();
        spouseBirthMap = model.getPersonToSpouseBirth();
        past = new ArrayList<Polyline>();
        pastSize = 0;
        eventText = (TextView) v.findViewById(R.id.event);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventText.setEnabled(false);
        if(!eventID.equals("first")){
            event = localEventMap.get(eventID);
            eventText.setText(localMap.get(eventID).getFirstName()+" "+ localMap.get(eventID).getLastName()+"\n"+localEventMap.get(eventID).getEventType()+": "+localEventMap.get(eventID).getCity()+", "+localEventMap.get(eventID).getCountry()+" ("+localEventMap.get(eventID).getYear()+")");
            if(localMap.get(eventID).getGender().equals("f")) {
                    Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.colorAccent).sizeDp(40);
                    genderView.setImageDrawable(genderIcon);

            }
            else{
                Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.colorAccent).sizeDp(40);
                genderView.setImageDrawable(genderIcon);
            }
            eventText.setEnabled(true);

        }
        eventText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                Bundle b = new Bundle();
                b.putString("personID",event.getPerson());
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }


    float colorMarkers(int i){
        if(i==0){
            return BitmapDescriptorFactory.HUE_ORANGE;
        }
        else if(i==1){
            return BitmapDescriptorFactory.HUE_GREEN;
        }
        else if(i==2){
            return BitmapDescriptorFactory.HUE_BLUE;
        }
        else if(i==3){
            return BitmapDescriptorFactory.HUE_AZURE;
        }
        else if(i==4) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        else if(i==5){
            return BitmapDescriptorFactory.HUE_VIOLET;
        }
        else if(i==6){
            return BitmapDescriptorFactory.HUE_ROSE;
        }
        else{
            return BitmapDescriptorFactory.HUE_YELLOW;
        }
    }

    ArrayList<Marker> markers;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (!eventID.equals("first")) {
            LatLng origin = new LatLng(localEventMap.get(eventID).getLatitude(), localEventMap.get(eventID).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
        }
        Model model = Model.getInstance();
        ArrayList<Event> events = model.getEvents();
        LatLng origin = new LatLng(0, 0);
        markers = new ArrayList<Marker>();
        for (int i = 0; i < model.getEventTypes().size(); i++) {
            System.out.println(model.getEventTypes().get(i));
        }
        for (int j = 0; j < eventTypes.size(); j++) {
            for (int i = 0; i < events.size(); i++) {
                origin = new LatLng(events.get(i).getLatitude(), events.get(i).getLongitude());
                if (events.get(i).getEventType().equals(eventTypes.get(j))) {
                    if(settings.getFilters().get(j)) {
                        if(localMap.get(events.get(i).getEventID()).getGender().equals("f")&&!settings.getFilters().get(settings.getFilters().size()-1)) {

                        }
                        else if(localMap.get(events.get(i).getEventID()).getGender().equals("m")&&!settings.getFilters().get(settings.getFilters().size()-2)){

                        }
                        else {
                            Marker marker = mMap.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.defaultMarker(colorMarkers(j))));
                            marker.setTag(events.get(i));
                            markers.add(marker);
                        }
                    }
                }

            }
            mMap.setOnMarkerClickListener(this);
        }
    }

    public void updateMarkers(){
        mMap.clear();
        Model model = Model.getInstance();
        ArrayList<Event> events = model.getEvents();
        LatLng origin = new LatLng(0, 0);
        markers = new ArrayList<Marker>();
        for (int i = 0; i < model.getEventTypes().size(); i++) {
            System.out.println(model.getEventTypes().get(i));
        }
        for (int j = 0; j < eventTypes.size(); j++) {
            for (int i = 0; i < events.size(); i++) {
                origin = new LatLng(events.get(i).getLatitude(), events.get(i).getLongitude());
                if (events.get(i).getEventType().toLowerCase().equals(eventTypes.get(j))) {
                    if(settings.getFilters().get(j)) {
                        if(localMap.get(events.get(i).getEventID()).getGender().equals("f")&&!settings.getFilters().get(settings.getFilters().size()-1)) {

                        }
                        else if(localMap.get(events.get(i).getEventID()).getGender().equals("m")&&!settings.getFilters().get(settings.getFilters().size()-2)){

                        }
                        else {
                            Marker marker = mMap.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.defaultMarker(colorMarkers(j))));
                            marker.setTag(events.get(i));
                            markers.add(marker);
                        }
                    }
                }

            }
            mMap.setOnMarkerClickListener(this);
        }

    }
    @Override
    public void onResume(){
        super.onResume();
        if (!resumeHasRun) {
            resumeHasRun = true;
            return;
        }
        updateMarkers();
        addLines(event);
    }
    @Override
    public boolean onMarkerClick(final Marker marker) {
        eventText.setEnabled(true);
        Model model = Model.getInstance();
        // Retrieve the data from the marker..
        event = (Event) marker.getTag();
        eventText.setText(localMap.get(event.getEventID()).getFirstName()+" "+ localMap.get(event.getEventID()).getLastName()+"\n"+event.getEventType()+": "+event.getCity()+", "+event.getCountry()+" ("+event.getYear()+")");

        addLines(event);

        return false;
    }


    public void addLines(Event e){
        if(settings.getMapReset()) {
            mMap.setMapType(settings.getMapType());
            settings.setMapReset(false);
        }
        if(localMap.get(e.getEventID()).getGender().equals("m")) {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.colorPrimary).sizeDp(40);
            genderView.setImageDrawable(genderIcon);
        }
        else
        {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.colorAccent).sizeDp(40);
            genderView.setImageDrawable(genderIcon);
        }

        ArrayList<Event> life = localLifeMap.get(e.getPerson());
        LatLng origin = new LatLng(e.getLatitude(),e.getLongitude());
        if(pastSize>0){
            for(int i =0;i<past.size();i++){
                past.get(i).remove();
            }
            pastSize=0;
        }
        if(localMap.get(e.getEventID()).getGender().equals("f")&&!settings.getOneFilter(settings.getFilters().size()-1)){

        }
        else if(localMap.get(e.getEventID()).getGender().equals("m")&&!settings.getOneFilter(settings.getFilters().size()-2)) {

        }
        else {
            if (settings.getLifeStoryLines()) {
                for (int i = 1; i < life.size(); i++) {
                    if(!settings.getFilters().get((model.getEventTypeIndex(life.get(i).getEventType())))){

                    }
                    else if(!settings.getFilters().get((model.getEventTypeIndex(life.get(i-1).getEventType())))){

                    }
                    else {
                        Polyline line2 = mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(life.get(i).getLatitude(), life.get(i).getLongitude()), new LatLng(life.get(i - 1).getLatitude(), life.get(i - 1).getLongitude())).width(6).color(settings.getLifeStoryColor()));
                        past.add(line2);
                        pastSize++;
                    }
                }
            }
            Event spouse = spouseBirthMap.get(e.getPerson());
            if(localMap.get(spouse.getEventID()).getGender().equals("f")&&!settings.getOneFilter(settings.getFilters().size()-1)){

            }
            else if(localMap.get(spouse.getEventID()).getGender().equals("m")&&!settings.getOneFilter(settings.getFilters().size()-2)){

            }
               else {
                if (settings.getSpouseLines()) {
                    if(!settings.getFilters().get(model.getEventTypeIndex(spouse.getEventType()))){

                    }
                    else {
                        Polyline line3 = mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(spouse.getLatitude(), spouse.getLongitude()), origin).width(6).color(settings.getSpouseColor()));
                        past.add(line3);
                        pastSize++;
                    }
                }
            }

            ArrayList<Event> fatherEvents = model.getPersonToAncestorBirths().get(e.getPerson());
            if (fatherEvents == null) {

            }
            else if(!settings.getOneFilter(settings.getFilters().size()-2)){

            }

            else {
                if (settings.getFamilyTreeLines()) {
                    if(!settings.getFilters().get((model.getEventTypeIndex(fatherEvents.get(0).getEventType())))){

                    }
                    else {

                        Polyline line2 = mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(fatherEvents.get(0).getLatitude(), fatherEvents.get(0).getLongitude()), origin).width(10).color(settings.getFamilyTreeColor()));
                        pastSize++;
                        past.add(line2);
                    }

                    for (int i = 1; i < fatherEvents.size(); i++) {
                        if(!settings.getFilters().get((model.getEventTypeIndex(fatherEvents.get(i).getEventType())))){

                        }
                        else {
                            Polyline line4 = mMap.addPolyline(new PolylineOptions()
                                    .add(new LatLng(fatherEvents.get(i).getLatitude(), fatherEvents.get(i).getLongitude()), new LatLng(fatherEvents.get(i - 1).getLatitude(), fatherEvents.get(i - 1).getLongitude())).width(10 - 2 * i).color(settings.getFamilyTreeColor()));
                            past.add(line4);
                            pastSize++;
                        }

                    }
                }

            }
            ArrayList<Event> motherEvents = model.getPersontoAncestressBirths().get(e.getPerson());
            if (motherEvents == null) {

            }
            else if(!settings.getOneFilter(settings.getFilters().size()-1)){

            }

            else {
                if (settings.getFamilyTreeLines()) {
                    if(!settings.getFilters().get((model.getEventTypeIndex(motherEvents.get(0).getEventType())))){

                    }
                    else {
                        Polyline line2 = mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(motherEvents.get(0).getLatitude(), motherEvents.get(0).getLongitude()), origin).width(10).color(settings.getFamilyTreeColor()));
                        past.add(line2);
                        pastSize++;
                    }
                    for (int i = 1; i < motherEvents.size(); i++) {
                        if(!settings.getFilters().get((model.getEventTypeIndex(motherEvents.get(i).getEventType())))){

                        }
                        else {
                            Polyline line4 = mMap.addPolyline(new PolylineOptions()
                                    .add(new LatLng(motherEvents.get(i).getLatitude(), motherEvents.get(i).getLongitude()), new LatLng(motherEvents.get(i - 1).getLatitude(), motherEvents.get(i - 1).getLongitude())).width(10 - 2 * i).color(settings.getFamilyTreeColor()));
                            past.add(line4);
                            pastSize++;
                        }

                    }
                }
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
    }

}
