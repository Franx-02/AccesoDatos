package pokeapi;

import java.util.ArrayList;

public class Pokemon {
    private int id;
    private String name;
    private int base_experience;
    private float height; //Nos lo da en decímetros (metro*10)
    private float weight; //Nos lo da en hectogramos (kg*10)
    private ArrayList<Types> types = new ArrayList<Types>();
    private ArrayList<Stats> stats = new ArrayList<Stats>();
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getBase_experience() {
        return this.base_experience;
    }
    
    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(float height) {
        this.height = height;
    }
    
    public float getWeight() {
        return this.weight;
    }
    
    public void setWeight(float weight) {
        this.weight = weight;
    }
    
    public ArrayList<Types> getTypes() {
        return this.types;
    }
    
    public void setTypes(ArrayList<Types> types) {
        this.types = types;
    }

    public ArrayList<Stats> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stats> stats) {
        this.stats = stats;
    }
}

