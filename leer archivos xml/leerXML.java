
import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class leerXML {
    public static void main(String[] args) {
        Scanner sca = new Scanner(System.in);
        //PIDO PATH POR TECLADO
        System.out.println("INTRODUCE UN PATH A LEER");
        String path = sca.nextLine();
        //DIRECTORIO DONDE ME POSICIONO
        File f = new File(path);
        Document document = null;
        DocumentBuilder builder = null;
        DocumentBuilderFactory factory = null;

        //MIENTRAS NO INTRODUZCA UN PATH VALIDO, LO SEGUIRÉ PIDIENDO
        while(!compruebaPath(f)) {
                System.out.println("NO ES UN PATH VÁLIDO, INTRODUCE UNO CORRECTO");
                path = sca.nextLine();
                f = new File(path);
        }

        try {
            factory = DocumentBuilderFactory.newInstance(); // INSTANCIAMOS UNA FABRICA DE CONSTRUCTORES DE DOCUMENTOS
            builder = factory.newDocumentBuilder(); // INSTANCIAMOS UN CONSTRUCTOR DE DOCUMENTOS
            document = builder.parse(f); // OBJETO QUE REPRESENTA TODO EL XML, PERO PARSEADO
            document.getDocumentElement().normalize();
            
            NodeList nodosEmpleado = document.getElementsByTagName("empleado");

            for (int i = 0; i < nodosEmpleado.getLength(); i++) {
                Node empleado = nodosEmpleado.item(i);
                if(empleado.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) empleado; // OBTENEMOS LOS ELEMENTS DEL NODO
                    System.out.print("\n");
                    System.out.print("\n");
                    System.out.print("\n");
                    System.out.print(elemento.getAttributeNode("id").getName() + ": " + elemento.getAttributeNode("id").getValue());
                    System.out.print("\n");
                    System.out.print(elemento.getElementsByTagName("nombre").item(0).getNodeName()+": "+elemento.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.print("\n");
                    System.out.print(elemento.getElementsByTagName("apellidos").item(0).getNodeName()+": "+elemento.getElementsByTagName("apellidos").item(0).getTextContent());
                    System.out.print("\n");
                    System.out.print(elemento.getElementsByTagName("edad").item(0).getNodeName()+": "+elemento.getElementsByTagName("edad").item(0).getTextContent());
                    System.out.print("\n");
                    System.out.println(elemento.getElementsByTagName("salario").item(0).getNodeName()+": "+elemento.getElementsByTagName("salario").item(0).getTextContent());
                }
            }
            
            System.out.println("Se terminó de escribir en el fichero");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        } else {
            try {
                f.createNewFile();
                System.out.println("FICHERO CREADO CORRECTAMENTE");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return result;
    }
}
