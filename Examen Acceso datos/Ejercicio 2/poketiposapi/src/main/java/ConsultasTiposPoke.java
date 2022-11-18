import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;

public class ConsultasTiposPoke{
    public static void main(String[] args) {
        Gson gson = new Gson();
        Scanner scan = new Scanner(System.in);
        int eleccion = -1;
        String poketypechosen;

        System.out.println("Bienvenid@ a la pokedex");

        //CONEXIÓN CON LA API DE TODOS LOS TIPOS POKEMON
        
        try {
            URL url = new URL("https://pokeapi.co/api/v2/type/");
            HttpURLConnection con = (HttpURLConnection)url.openConnection(); 
            con.setRequestMethod("GET");
            
            int responsecode=con.getResponseCode();

            if(responsecode != 200){
                System.out.println("Error"+ responsecode);
                
            }else{
                StringBuilder informationstring = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());
                
                while(sc.hasNext()) {
                    informationstring.append(sc.nextLine());
                }
                
                sc.close();

                BibliotecaTipos bibliotecaTipos;
                bibliotecaTipos = gson.fromJson(String.valueOf(informationstring), BibliotecaTipos.class);

                String tiposBaneados[] = {"unknown", "shadow"};
                int contador = 0;
                
                //MOSTRAMOS TODOS LOS TIPOS
                System.out.println("Los tipos de pokemon disponibles son: ");
                for(Results results : bibliotecaTipos.getResults()){
                    if(results.getName().equals(tiposBaneados[0]) || results.getName().equals(tiposBaneados[1])){ 
                    }else{
                        if(contador <=9){
                            System.out.print("ID: " + contador + " | ");
                        }
                        else{
                            System.out.print("ID: " + contador + "| ");
                        }
                        System.out.println(results.getName());
                    }
                    contador++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Que tipo de pokemon desea consultar? (Nombre o ID)");
            
        poketypechosen = scan.nextLine();
        poketypechosen = poketypechosen.toLowerCase();

        //CONEXIÓN CON LA API DE RELACIÓN DE DAÑOS

        try{
            URL url = new URL("https://pokeapi.co/api/v2/type/" + poketypechosen);
            HttpURLConnection con = (HttpURLConnection)url.openConnection(); 
            con.setRequestMethod("GET");
            
            int responsecode=con.getResponseCode();

            if(responsecode!=200) {
                System.out.println("Error "+ responsecode);
                System.out.println("No se puede realizar la busqueda, error de datos introducidos");
            } else {
                StringBuilder informationstring = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());
                
                while(sc.hasNext()) {
                    informationstring.append(sc.nextLine());
                }
                
                sc.close();
                TipoPokemon tipoPokemon;
                tipoPokemon = gson.fromJson(String.valueOf(informationstring), TipoPokemon.class);

                //MOSTRAMOS LAS RELACIONES DE DAÑO DEL TIPO ELEGIDO

                System.out.println("Id: " + tipoPokemon.getId());
                System.out.println("Tipo: " + tipoPokemon.getName());
                System.out.println("Doble de daño recibido de: ");
                for (Double_damage_from double_damage_from : tipoPokemon.getDamage_relations().getDouble_damage_from()) {
                    System.out.println("\t" + double_damage_from.getName());
                }
                System.out.println("Doble de daño a: ");
                for (Double_damage_to double_damage_to : tipoPokemon.getDamage_relations().getDouble_damage_to()) {
                    System.out.println("\t" + double_damage_to.getName());
                }
                System.out.println("Mitad de daño recibido de: ");
                for (Half_damage_from half_damage_from : tipoPokemon.getDamage_relations().getHalf_damage_from()) {
                    System.out.println("\t" + half_damage_from.getName());
                }
                System.out.println("Mitad de daño a: ");
                for (Half_damage_to half_damage_to : tipoPokemon.getDamage_relations().getHalf_damage_to()) {
                    System.out.println("\t" + half_damage_to.getName());
                }
                System.out.println("No recibe daño de: ");
                for (No_damage_from no_damage_from : tipoPokemon.getDamage_relations().getNo_damage_from()) {
                    System.out.println("\t" + no_damage_from.getName());
                }
                System.out.println("No afecta a: ");
                for (No_damage_to no_damage_to : tipoPokemon.getDamage_relations().getNo_damage_to()) {
                    System.out.println("\t" + no_damage_to.getName());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}