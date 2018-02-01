package example.com.familymap.data;

import java.util.ArrayList;
import java.util.TreeMap;

import model.Event;
import model.Person;

/**
 * Created by dcmer on 11/15/2017.
 */

public class Model {
    private static Model instance = null;
    private ArrayList<Person> people;
    private ArrayList<Event> events;
    private TreeMap<String,Person> eventPersonMap;
    private TreeMap<String, ArrayList<Event>> personToAncestorBirths;
    private ArrayList<String> eventTypes;
    private TreeMap<String, ArrayList<Event>> persontoAncestressBirths;
    private TreeMap<String, Event> eventIDEventMap;
    private TreeMap<String,ArrayList<Event>> lifeEventsMap;
    private TreeMap<String,Event> personToSpouseBirth;
    private TreeMap<String,Person> personIDPersonMap;
    private TreeMap<String, Person> personToChildrenPerson;




    protected Model(){
        eventTypes = new ArrayList<String>();
        eventPersonMap = new TreeMap<String,Person>();
        lifeEventsMap = new TreeMap<String,ArrayList<Event>>();
        eventIDEventMap = new TreeMap<String,Event>();
        personToSpouseBirth = new TreeMap<String,Event>();
        personToAncestorBirths = new TreeMap<String,ArrayList<Event>>();
        persontoAncestressBirths = new TreeMap<String,ArrayList<Event>>();
        personIDPersonMap = new TreeMap<String,Person>();
        personToChildrenPerson = new TreeMap<String,Person>();
    }
    public static Model getInstance(){
        if(instance==null){
            instance = new Model();
        }
        return instance;
    }

    public TreeMap<String, Person> getPersonToChildrenPerson() {
        return personToChildrenPerson;
    }

    public void setPersonToChildrenPerson(TreeMap<String, Person> personToChildrenPerson) {
        this.personToChildrenPerson = personToChildrenPerson;
    }


    public TreeMap<String, Person> getPersonIDPersonMap() {
        return personIDPersonMap;
    }

    public void setPersonIDPersonMap(TreeMap<String, Person> personIDPersonMap) {
        this.personIDPersonMap = personIDPersonMap;
    }

    public ArrayList<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(ArrayList<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public TreeMap<String, ArrayList<Event>> getPersonToAncestorBirths() {
        return personToAncestorBirths;
    }
    public TreeMap<String, ArrayList<Event>> getPersontoAncestressBirths() {
        return persontoAncestressBirths;
    }

    public void setPersontoAncestressBirths(TreeMap<String, ArrayList<Event>> persontoAncestressBirths) {
        this.persontoAncestressBirths = persontoAncestressBirths;
    }

    public void setPersonToAncestorBirths(TreeMap<String, ArrayList<Event>> personToAncestorBirths) {
        this.personToAncestorBirths = personToAncestorBirths;
    }


    public TreeMap<String, Event> getEventIDEventMap() {
        return eventIDEventMap;
    }

    public void setEventIDEventMap(TreeMap<String, Event> eventIDEventMap) {
        this.eventIDEventMap = eventIDEventMap;
    }


    public TreeMap<String, ArrayList<Event>> getLifeEventsMap() {
        return lifeEventsMap;
    }

    public void setLifeEventsMap(TreeMap<String, ArrayList<Event>> lifeEventsMap) {
        this.lifeEventsMap = lifeEventsMap;
    }


    public TreeMap<String, Event> getPersonToSpouseBirth() {
        return personToSpouseBirth;
    }

    public void setPersonToSpouseBirth(TreeMap<String, Event> personToSpouseBirth) {
        this.personToSpouseBirth = personToSpouseBirth;
    }


    public TreeMap<String, Person> getEventPersonMap(){
        return eventPersonMap;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
    public void mapPersonIDtoPerson(){
        for(int i =0;i<people.size();i++){
            String personID = people.get(i).getPersonID();
            personIDPersonMap.put(personID,people.get(i));
        }
    }
    public void mapPersonToChildren(){
        for(int i =0;i<people.size();i++){
            if(people.get(i).getFather()!=null){
                if(!people.get(i).getFather().equals("")){
                    personToChildrenPerson.put(people.get(i).getFather(),people.get(i));
                }
            }
            if(people.get(i).getMother()!=null){
                if(!people.get(i).getMother().equals("")){
                    personToChildrenPerson.put(people.get(i).getMother(),people.get(i));
                }
            }
        }
    }
    public void findEventTypes(){
        Settings settings = Settings.getInstance();
        ArrayList<Boolean> local = new ArrayList<>();
        eventTypes.add(events.get(0).getEventType().toLowerCase());
        local.add(true);
        for(int i =0; i<events.size();i++){
            if(!eventTypes.contains(events.get(i).getEventType().toLowerCase())){
                eventTypes.add(events.get(i).getEventType().toLowerCase());
                local.add(true);
            }
        }
        local.add(true);
        local.add(true);
        local.add(true);
        local.add(true);
        settings.setFilters(local);
    }
    public int getEventTypeIndex(String find){
        for(int i =0;i<eventTypes.size();i++){
            if(find.equals(eventTypes.get(i))){
                return i;
            }
        }
        return 0;
    }
    public void mapPeopleToAncestorBirths(){
        ArrayList<Event> births;
        System.out.println(people.size());
        for(int i =0;i<people.size();i++){
            births = new ArrayList<Event>();
            if(people.get(i).getFather()==null||people.get(i).getFather().equals("")){
                System.out.println("continue");
                continue;
            }
            System.out.println(people.get(i).getFather());
            recursiveHelperFather(people.get(i).getFather(),births);
            personToAncestorBirths.put(people.get(i).getPersonID(),births);
        }

    }
    public void recursiveHelperFather(String ID, ArrayList<Event> births){
        if(!lifeEventsMap.containsKey(ID)){
            System.out.println("i utilized this well");
            return;
        }
        else if(lifeEventsMap.get(ID)==null||lifeEventsMap.get(ID).equals("")){
            return;
        }
        else{
            ArrayList<Event> fatherEvents = lifeEventsMap.get(ID);
            Event firstEvent = fatherEvents.get(0);
            for(int k = 0; k<fatherEvents.size();k++){
                if(fatherEvents.get(k).getYear()<firstEvent.getYear()){
                    firstEvent = fatherEvents.get(k);
                }
            }
            births.add(firstEvent);
            if(personIDPersonMap.get(ID).getFather()==null){
                return;
            }
            recursiveHelperFather(personIDPersonMap.get(ID).getFather(),births);
        }
    }

    public void mapPeopleToAncestressBirths(){
        ArrayList<Event> births;
        for(int i =0;i<people.size();i++){
            births = new ArrayList<Event>();
            if(people.get(i).getMother()==null||people.get(i).getMother().equals("")){
                continue;
            }
            recursiveHelperMother(people.get(i).getMother(),births);
            persontoAncestressBirths.put(people.get(i).getPersonID(),births);
        }

    }
    public void recursiveHelperMother(String ID, ArrayList<Event> births){
        if(!lifeEventsMap.containsKey(ID)){
            System.out.println("i utilized this well");
            return;
        }
        else if(lifeEventsMap.get(ID)==null||lifeEventsMap.get(ID).equals("")){
            return;
        }
        else{
            ArrayList<Event> fatherEvents = lifeEventsMap.get(ID);
            Event firstEvent = fatherEvents.get(0);
            for(int k = 0; k<fatherEvents.size();k++){
                if(fatherEvents.get(k).getYear()<firstEvent.getYear()){
                    firstEvent = fatherEvents.get(k);
                }
            }
            births.add(firstEvent);
            if(personIDPersonMap.get(ID).getMother()==null){
                return;
            }
            recursiveHelperMother(personIDPersonMap.get(ID).getMother(),births);
        }
    }
    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
    public void mapEventIDtoPeople(){
        for(int i =0;i<events.size();i++){
            for(int j = 0;j<people.size();j++){
                if(events.get(i).getPerson().equals(people.get(j).getPersonID())){
                    //System.out.println("Mapped babby");
                    eventPersonMap.put(events.get(i).getEventID(),people.get(j));
                }
            }
        }
    }
    public void mapEventIDtoEvents(){
        for(int i = 0;i<events.size();i++){
            eventIDEventMap.put(events.get(i).getEventID(),events.get(i));
        }
    }
    public void mapPersonIDtoSpouse(){
        ArrayList<Event> spouseEvents = new ArrayList<Event>();
        for(int i =0;i<people.size();i++){
           spouseEvents= lifeEventsMap.get(people.get(i).getSpouse());
           if(spouseEvents==null){

           }
           else{
               Event firstEvent = spouseEvents.get(0);
               for(int k = 0; k<spouseEvents.size();k++){
                   if(spouseEvents.get(k).getYear()<firstEvent.getYear()){
                       firstEvent = spouseEvents.get(k);
                   }
               }
               personToSpouseBirth.put(people.get(i).getPersonID(),firstEvent);
           }
        }
    }
    public void mapEventIDtoLife(){
        ArrayList<Event> temp;
        for(int i =0;i<people.size();i++){
            temp = new ArrayList<Event>();
            for(int j =0;j<events.size();j++){
                if(people.get(i).getPersonID().equals(events.get(j).getPerson())){
                    temp.add(events.get(j));
                }
            }
            lifeEventsMap.put(people.get(i).getPersonID(),temp);
        }
    }
    public static void main(String args[]){
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
        ArrayList<Event> backout = life.get("a");
        for(int i = 0; i<backout.size();i++){
            System.out.println(backout.get(i).getDescendant());
        }
        model.mapEventIDtoEvents();
        Event eventback = model.getEventIDEventMap().get("a");
        System.out.println(eventback.getCity());
    }
}

