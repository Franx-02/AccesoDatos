package pokeapi;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConsultaApi {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Scanner scan = new Scanner(System.in);
        int eleccion = -1;
        String pokechosen;

        System.out.println("Bienvenid@ a la pokedex");
        System.out.println("Que pokemon desea consultar? (Nombre o ID)");
            
        pokechosen = scan.nextLine();
        pokechosen = pokechosen.toLowerCase();

        //CONEXIÓN A LA API DE POKEMON
        try {
            URL url1=new URL("https://pokeapi.co/api/v2/pokemon/"+ pokechosen);
            HttpURLConnection con1=(HttpURLConnection)url1.openConnection(); 
            con1.setRequestMethod("GET");
            
            int responsecode=con1.getResponseCode();
            
            if(responsecode!=200) {
                System.out.println("Error"+ responsecode);
            } else {
                StringBuilder informationstring= new StringBuilder();
                Scanner sc1=new Scanner(url1.openStream());
                
                while(sc1.hasNext()) {
                    informationstring.append(sc1.nextLine());
                }
                
                sc1.close();
        //FIN DE LA CONEXIÓN CON LA API DE POKEMON
                
                Pokemon pokemon;
                pokemon = gson.fromJson(String.valueOf(informationstring), Pokemon.class);
                
                System.out.println("Id: " + pokemon.getId());
                System.out.println("Nombre: " + pokemon.getName());
                System.out.println("Experiencia base: " + pokemon.getBase_experience());
                System.out.println("Altura (m): " + pokemon.getHeight()/10);
                System.out.println("Peso (Kg): " + pokemon.getWeight()/10);
                System.out.println("Tipo/s: ");
                
                for (Types types : pokemon.getTypes()) {
                    System.out.println("\t" + types.getType().getName());
/* 
                    //CONEXIÓN CON LA API DE TIPOS DE POKEMON
                    URL url2=new URL(types.getType().getUrl());
                    HttpURLConnection con2=(HttpURLConnection)url2.openConnection(); 
                    con2.setRequestMethod("GET");

                    StringBuilder informationstring2= new StringBuilder();
                    Scanner sc2=new Scanner(url2.openStream());
                
                    while(sc2.hasNext()) {
                        informationstring2.append(sc2.nextLine());
                    }
                
                    sc2.close();

                    System.out.println(types.getType().getUrl());
                    //FIN DE CONEXIÓN CON LA API DE TIPOS DE POKEMON
                
                    Damage_relations typestrweak;
                    typestrweak = gson.fromJson(String.valueOf(informationstring2), Damage_relations.class);

                    System.out.println("\tFuerte contra: ");
                    for (Double_damage_to double_damage_to:typestrweak.getDouble_damage_to()) {
                        System.out.println("\t\t" +double_damage_to.getName());
                    }
                    System.out.println("\tDébil contra: ");
                    for (Double_damage_from double_damage_from:typestrweak.getDouble_damage_from()) {
                        System.out.println("\t\t" +double_damage_from.getName());
                    }

                    //System.out.println(gson.toJson(pokemon));
                    //System.out.println("---------------");
                    //System.out.println(gson.toJson(typestrweak)); 
*/
                }

                System.out.println("Stats: ");

                for (Stats stats : pokemon.getStats()) {
                    System.out.println("\t" + stats.getStat().getName()+": "+stats.getBase_stat());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

