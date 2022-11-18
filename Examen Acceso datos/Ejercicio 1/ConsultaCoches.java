import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConsultaCoches{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
		String path;
		File f;
		
		//Pedimos PATH por teclado
		do {
			System.out.println("Introduzca el path del archivo XML");
			path = scan.nextLine();
			
			f = new File(path);
			if(!compruebaPath(f)) {
				System.out.println("Path no válido");
			}
		}while(!compruebaPath(f));
		
		Document document = leerXML(path);

        int eleccion = -1;

        do{
            System.out.println("\n\n¿Qué operación desea realizar?: ");
            System.out.println("1. Mostrar coches");
            System.out.println("2. Modificar un coche");
            System.out.println("3. Salir");
            eleccion = scan.nextInt();

			switch(eleccion) {
			case 1:
				System.out.println("Operación: Mostrar coches");
                mostrarPersonas(document);
				break;
			case 2:
                System.out.println("Operación: Modificar coche");
                document = modificarCoche(document);
                escribirXML(path, document);
                break;
			case 3:
				System.out.println("Saliendo del programa...");
				break;	
			default:
				System.out.println("Operación no contemplada...");	
			}

        }while( eleccion != 3 );
    }

    //Función que pide el path de un archivo para guardarlo en un buffer "Document"
	public static Document leerXML(String path) {
		DocumentBuilder builder = null;
		DocumentBuilderFactory factory = null;
		Document document = null;
		File f = new File(path);
		
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(f);
			document.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
			document = null;
		}
		
		return document;
		
	}

    //Función para comprobar si la ruta dada existe y es apta para ser leida
    public static boolean compruebaPath(File f) {
        boolean result = false;

        if(f.exists()) {
            System.out.print("EL path EXISTE");

            if(f.isDirectory()) {
                //ES UN DIRECTORIO, PINTO SU NOMBRE
                System.out.println(f.getName()+" es una carpeta");
            } else {
                //SI NO ES DIRECTORIO, ES FICHERO, LO PINTO
                System.out.println(f.getName()+" es un fichero");

                if(f.canRead()) {
                    System.out.println(" Y SE PUEDE LEER");
                    result = true;
                } else {
                    System.out.println(" PERO NO SE PUEDE LEER");
                }
            }
        }
        return result;
    }

    //Función que pide el buffer para mostrar los datos del XML
    public static void mostrarPersonas (Document document) {
        
        NodeList nodosCoche = document.getElementsByTagName("coche");

        for (int i = 0; i < nodosCoche.getLength(); i++) {
            Node coche = nodosCoche.item(i);
            if(coche.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) coche; // OBTENEMOS LOS ELEMENTS DEL NODO
                System.out.print("\n");
                System.out.print("\n");
                System.out.println("Coche "+(i+1)+"\n");
                System.out.print(elemento.getAttributeNode("id").getName() + ": " + elemento.getAttributeNode("id").getValue());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("marca").item(0).getNodeName()+": "+elemento.getElementsByTagName("marca").item(0).getTextContent());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("modelo").item(0).getNodeName()+": "+elemento.getElementsByTagName("modelo").item(0).getTextContent());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("matricula").item(0).getNodeName()+": "+elemento.getElementsByTagName("matricula").item(0).getTextContent());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("numPuertas").item(0).getNodeName()+": "+elemento.getElementsByTagName("numPuertas").item(0).getTextContent());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("numPlazas").item(0).getNodeName()+": "+elemento.getElementsByTagName("numPlazas").item(0).getTextContent());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("potencia").item(0).getNodeName()+": "+elemento.getElementsByTagName("potencia").item(0).getTextContent());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("color").item(0).getNodeName()+": "+elemento.getElementsByTagName("color").item(0).getTextContent());
                System.out.print("\n");
            }
        }
    }

    //Función que permite la modificación de un coche dado
    public static Document modificarCoche(Document document) {
        Scanner sca = new Scanner(System.in);
        Document documentAux = document;
        
        NodeList nodosCoche = documentAux.getElementsByTagName("coche");
        
        System.out.println("Introduce el número del coche a modificar: ");
        int numCoche = sca.nextInt();

        Element coche;
        String marca, modelo, matricula, numPuertas, numPlazas, potencia, color;

        coche = (Element) nodosCoche.item(numCoche-1);

        sca.nextLine();
        System.out.println("Introduce los nuevos datos del coche " + numCoche + ": ");
        System.out.println("Introduce la marca del coche: ");
        marca = sca.nextLine();
         System.out.println("Introduce el modelo del coche: ");
        modelo = sca.nextLine();
         System.out.println("Introduce la matricula del coche: ");
        matricula = sca.nextLine();
         System.out.println("Introduce el numero de puertas del coche: ");
        numPuertas = sca.nextLine();
         System.out.println("Introduce el numero de plazas  del coche: ");
        numPlazas = sca.nextLine();
         System.out.println("Introduce la potencia del coche: ");
        potencia = sca.nextLine();
         System.out.println("Introduce el color del coche: ");
        color = sca.nextLine();
        
        coche.getElementsByTagName("marca").item(0).setTextContent(marca);
        coche.getElementsByTagName("modelo").item(0).setTextContent(modelo);
        coche.getElementsByTagName("matricula").item(0).setTextContent(matricula);
        coche.getElementsByTagName("numPuertas").item(0).setTextContent(numPuertas);
        coche.getElementsByTagName("numPlazas").item(0).setTextContent(numPlazas);
        coche.getElementsByTagName("potencia").item(0).setTextContent(potencia);
        coche.getElementsByTagName("color").item(0).setTextContent(color);
 
        return documentAux;
    }

    //Función que pide el path del archivo y el buffer para escribir los datos nuevos
    public static void escribirXML(String path, Document document){
        try {
            //Se escribe el contenido del XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

            System.out.println("Se terminó de escribir en el fichero");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}