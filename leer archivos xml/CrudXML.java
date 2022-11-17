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

public class CrudXML {

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
		
		int eleccion = 6;
		
		do {
			System.out.println("\n\n¿Qué operación desea realizar?: ");
            System.out.println("1. Mostrar persona");
            System.out.println("2. Agregar un persona");
            System.out.println("3. Modificar un persona");
            System.out.println("4. Borrar un persona");
            System.out.println("5. Salir");
            eleccion = scan.nextInt();

			switch(eleccion) {
			case 1:
				System.out.println("Mostrar personas");
                mostrarPersonas(document);
				break;
			case 2:
				System.out.println("Agregar persona");
                document = agregarPersona(document);
                escribirXML(path, document);
				break;
			case 3:
				System.out.println("Modificar persona");
                document = modificarPersona(document);
                escribirXML(path, document);
				break;
			case 4: 
				System.out.println("Eliminar persona");
                document = borrarPersona(document);
                escribirXML(path, document);
				break;
			case 5:
				System.out.println("Saliendo del programa...");
				break;	
			default:
				System.out.println("Operación no contemplada...");	
			}

		}while(eleccion != 5);

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

    //Función que pide el buffer para mostrar los datos del XML
    public static void mostrarPersonas (Document document) {
        Scanner sca = new Scanner(System.in);
        
        NodeList nodosPersona = document.getElementsByTagName("persona");

        for (int i = 0; i < nodosPersona.getLength(); i++) {
            Node persona = nodosPersona.item(i);
            if(persona.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) persona; // OBTENEMOS LOS ELEMENTS DEL NODO
                System.out.print("\n");
                System.out.print("\n");
                System.out.print("\n");
                System.out.println("Persona "+(i+1)+"\n");
                System.out.print(elemento.getAttributeNode("id").getName() + ": " + elemento.getAttributeNode("id").getValue());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("nombre").item(0).getNodeName()+": "+elemento.getElementsByTagName("nombre").item(0).getTextContent());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("apellido1").item(0).getNodeName()+": "+elemento.getElementsByTagName("apellido1").item(0).getTextContent());
                System.out.print("\n");
                System.out.print(elemento.getElementsByTagName("apellido2").item(0).getNodeName()+": "+elemento.getElementsByTagName("apellido2").item(0).getTextContent());
                System.out.print("\n");
                System.out.println(elemento.getElementsByTagName("edad").item(0).getNodeName()+": "+elemento.getElementsByTagName("edad").item(0).getTextContent());
            }
        }
    }

    //Función que pide el buffer para devolver el buffer con la persona nueva introducida
    public static Document agregarPersona(Document document) {
        Scanner sca = new Scanner(System.in);
        Document documentAux = document;
                
        Element persona, itemNombre, itemApellido1, itemApellido2, itemEdad;
        String nombre, apellido1, apellido2, edad;
        Attr atributo;

        NodeList nodosPersona = documentAux.getElementsByTagName("persona");
        int numPersonasFichero = nodosPersona.getLength();

        Element personas = (Element) documentAux.getElementsByTagName("personas").item(0);
        persona = documentAux.createElement("persona");
        personas.appendChild(persona);
        
        Element ultPersona = (Element) nodosPersona.item(numPersonasFichero-1);
        int idNuevo = Integer.parseInt(ultPersona.getAttributeNode("id").getValue()) + 1;

        atributo = documentAux.createAttribute("id");
        atributo.setValue(String.valueOf(idNuevo));
        persona.setAttributeNode(atributo);

        System.out.println("Introduce los datos de la nueva persona: ");
        System.out.println("Introduce el nombre de la persona: ");
        nombre = sca.nextLine();
        System.out.println("Introduce el apellido 1 de la persona: ");
        apellido1 = sca.nextLine();
        System.out.println("Introduce el apellido 2 de la persona: ");
        apellido2 = sca.nextLine();
        System.out.println("Introduce la edad de la persona: ");
        edad = sca.nextLine();

        itemNombre = documentAux.createElement("nombre");
        itemNombre.setTextContent(nombre);
        persona.appendChild(itemNombre);
        
        itemApellido1 = documentAux.createElement("apellido1");
        itemApellido1.setTextContent(apellido1);
        persona.appendChild(itemApellido1);

        itemApellido2 = documentAux.createElement("apellido2");
        itemApellido2.setTextContent(apellido2);
        persona.appendChild(itemApellido2);

        itemEdad = documentAux.createElement("edad");
        itemEdad.setTextContent(edad);
        persona.appendChild(itemEdad);

        return documentAux;
    }

    //Función que pide el buffer para devolver el buffer con la modificación de la persona
    public static Document modificarPersona(Document document) {
        Scanner sca = new Scanner(System.in);
        Document documentAux = document;
        
        NodeList nodosPersona = documentAux.getElementsByTagName("persona");
        
        System.out.println("Introduce el número de la persona a modificar: ");
        int numPersona = sca.nextInt();

        Element persona;
        String nombre, apellido1, apellido2, edad;

        persona = (Element) nodosPersona.item(numPersona-1);

        sca.nextLine();
        System.out.println("Introduce los nuevos datos de la persona " + numPersona + ": ");
        System.out.println("Introduce el nombre de la persona: ");
        nombre = sca.nextLine();
        System.out.println("Introduce el apellido 1 de la persona: ");
        apellido1 = sca.nextLine();
        System.out.println("Introduce el apellido 2 de la persona: ");
        apellido2 = sca.nextLine();
        System.out.println("Introduce la edad de la persona: ");
        edad = sca.nextLine();

        persona.getElementsByTagName("nombre").item(0).setTextContent(nombre);
        persona.getElementsByTagName("apellido1").item(0).setTextContent(apellido1);
        persona.getElementsByTagName("apellido2").item(0).setTextContent(apellido2);
        persona.getElementsByTagName("edad").item(0).setTextContent(edad);

        return documentAux;
    }

    //Función que pide el buffer para devolver el buffer con la persona eliminada
    public static Document borrarPersona(Document document) {
        Scanner sca = new Scanner(System.in);
        Document documentAux = document;
                
        System.out.println("Introduce el número del persona a borrar: ");
        int numPersona = sca.nextInt();
        
        NodeList nodosPersona = documentAux.getElementsByTagName("persona");
        Node persona = nodosPersona.item(numPersona-1);
        
        Node personas = document.getElementsByTagName("personas").item(0);
        personas.removeChild(persona);
        //empleado.getParentNode().removeChild(empleado);
        
        return documentAux;
    }
    
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
}
