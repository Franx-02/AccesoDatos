package pokeapi;

import java.util.ArrayList;

public class Damage_relations {
    private ArrayList<Double_damage_from> double_damage_from = new ArrayList<Double_damage_from>();
    private ArrayList<Double_damage_to> double_damage_to = new ArrayList<Double_damage_to>();
    
    public ArrayList<Double_damage_from> getDouble_damage_from() {
        return double_damage_from;
    }
    public void setDouble_damage_from(ArrayList<Double_damage_from> double_damage_from) {
        this.double_damage_from = double_damage_from;
    }
    public ArrayList<Double_damage_to> getDouble_damage_to() {
        return double_damage_to;
    }
    public void setDouble_damage_to(ArrayList<Double_damage_to> double_damage_to) {
        this.double_damage_to = double_damage_to;
    }

    
}