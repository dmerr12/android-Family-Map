package example.com.familymap.data;

import java.util.ArrayList;

/**
 * Created by dcmer on 12/4/2017.
 */

public class Settings {
    private static Settings instance = null;
   private Boolean LifeStoryLines;
    private Boolean FamilyTreeLines;
    private Boolean SpouseLines;
    private int LifeStoryColor;
    private int FamilyTreeColor;
    private int SpouseColor;
    private int MapType;
    private ArrayList<Boolean> filters;

    private Boolean MapReset;

    public static Settings getInstance(){
        if(instance==null){
            instance = new Settings();
        }
        return instance;
    }

    protected Settings(){
        LifeStoryLines = true;
        FamilyTreeLines = true;
        SpouseLines = true;
        LifeStoryColor =-65536;
        FamilyTreeColor= -16711936;
        SpouseColor = -16776961;
        MapType =1;
        MapReset = false;
    }

    public ArrayList<Boolean> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<Boolean> filters) {
        this.filters = filters;
    }
    public void setOneFilter(int i, Boolean set){
        filters.set(i,set);
    }
    public Boolean getOneFilter(int i){
        return filters.get(i);
    }

    public Boolean getMapReset() {
        return MapReset;
    }

    public void setMapReset(Boolean mapReset) {
        MapReset = mapReset;
    }

    public Boolean getLifeStoryLines() {
        return LifeStoryLines;
    }

    public void setLifeStoryLines(Boolean lifeStoryLines) {
        LifeStoryLines = lifeStoryLines;
    }

    public Boolean getFamilyTreeLines() {
        return FamilyTreeLines;
    }

    public void setFamilyTreeLines(Boolean familyTreeLines) {
        FamilyTreeLines = familyTreeLines;
    }

    public Boolean getSpouseLines() {
        return SpouseLines;
    }

    public void setSpouseLines(Boolean spouseLines) {
        SpouseLines = spouseLines;
    }

    public int getLifeStoryColor() {
        return LifeStoryColor;
    }

    public void setLifeStoryColor(int lifeStoryColor) {
        LifeStoryColor = lifeStoryColor;
    }

    public int getFamilyTreeColor() {
        return FamilyTreeColor;
    }

    public void setFamilyTreeColor(int familyTreeColor) {
        FamilyTreeColor = familyTreeColor;
    }

    public int getSpouseColor() {
        return SpouseColor;
    }

    public void setSpouseColor(int spouseColor) {
        SpouseColor = spouseColor;
    }

    public int getMapType() {
        return MapType;
    }

    public void setMapType(int mapType) {
        MapType = mapType;
    }

}
