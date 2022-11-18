import java.util.ArrayList;

public class Damage_relations {
    private ArrayList<Double_damage_from> double_damage_from = new ArrayList<Double_damage_from>();
    private ArrayList<Double_damage_to> double_damage_to = new ArrayList<Double_damage_to>();
    private ArrayList<Half_damage_from> half_damage_from = new ArrayList<Half_damage_from>();
    private ArrayList<Half_damage_to> half_damage_to = new ArrayList<Half_damage_to>();
    private ArrayList<No_damage_from> no_damage_from = new ArrayList<No_damage_from>();
    private ArrayList<No_damage_to> no_damage_to = new ArrayList<No_damage_to>();
    
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
    public ArrayList<Half_damage_from> getHalf_damage_from() {
        return half_damage_from;
    }
    public void setHalf_damage_from(ArrayList<Half_damage_from> half_damage_from) {
        this.half_damage_from = half_damage_from;
    }
    public ArrayList<Half_damage_to> getHalf_damage_to() {
        return half_damage_to;
    }
    public void setHalf_damage_to(ArrayList<Half_damage_to> half_damage_to) {
        this.half_damage_to = half_damage_to;
    }
    public ArrayList<No_damage_from> getNo_damage_from() {
        return no_damage_from;
    }
    public void setNo_damage_from(ArrayList<No_damage_from> no_damage_from) {
        this.no_damage_from = no_damage_from;
    }
    public ArrayList<No_damage_to> getNo_damage_to() {
        return no_damage_to;
    }
    public void setNo_damage_to(ArrayList<No_damage_to> no_damage_to) {
        this.no_damage_to = no_damage_to;
    }
}
