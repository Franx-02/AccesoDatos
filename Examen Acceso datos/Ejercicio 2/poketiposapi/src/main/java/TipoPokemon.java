
public class TipoPokemon {
    private int id;
    private String name;
    private Damage_relations damage_relations = new Damage_relations();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Damage_relations getDamage_relations() {
        return damage_relations;
    }
    public void setDamage_relations(Damage_relations damage_relations) {
        this.damage_relations = damage_relations;
    }
    
}
