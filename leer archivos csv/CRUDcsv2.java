import java.util.*;
import java.io.*;

//C:\Users\aliva\OneDrive\Escritorio\acceso a datos\prueba.csv

public class CRUDcsv2{

	public static Scanner entrada = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		int menu = 0;
		//PEDIR PATH PARA LEER
		System.out.println("Introduce la ruta a leer");
		String ruta = entrada.nextLine();
		
		//String ruta="C:\Users\aliva\OneDrive\Escritorio\acceso a datos\prueba.csv";
		File f = new File(ruta);
		//File fnew =new File("C:\Users\aliva\OneDrive\Escritorio\acceso a datos\nuevo.txt");
		File fnew=new File("temporal.csv");
		
		
		//COMPRUEBA LA RUTA, SINO REPITE
		while (!comprobarRuta(f)) {
			System.out.println("No es una ruta VALIDA, introduce ruta CORRECTA");
			ruta = entrada.nextLine();
			f = new File(ruta);
		}
		//MENU
		do {
			System.out.println("Bienvenido al menu para gestionar ficheros");
			System.out.println("Que desea hacer?");
			System.out.println("Consular  INTRODUCE 1");
			System.out.println("Modificar INTRODUCE 2");
			System.out.println("Agregar   INTRODUCE 3");
			System.out.println("Borrar    INTRODUCE 4");
			System.out.println("Salir     INTRODUCE 0");
			menu = entrada.nextInt();
			switch (menu) {
			case 0:
				System.out.println("Salir");
				break;
			//CONSULTA DEL ARCHIVO
			case 1:
				funcionConsultar(f);
				break;
				
			//MODIFICAR EL ARCHIVO
			case 2:
				funcionConsultar(f);
				System.out.println("");
				System.out.println("POSICIÓN de PERSONA para MODIFICAR");
				int registro=entrada.nextInt();
				entrada.nextLine();
				System.out.println("Editar entrada");
				String linea=escribirLinea();
				//LE PASAMOS LA POSICION A MODIFICAR=registro, ARCHIVO ORIGINA=f,ARCHIVO PUENTE COPIA=fnew, TEXTO A INSERTAR=linea
				funcionModificar(registro,f, fnew,linea);
				break;
				
			//AGREGAR NUEVO REGISTRO AL FICHERO
			case 3:
				
				funcionConsultar(f);
				System.out.println("");
				System.out.println("POSICIÓN de PERSONA para AGREGAR");
				int numAdd=entrada.nextInt();
				entrada.nextLine();
				System.out.println("Agregar entrada");
				String lineAdd=escribirLinea();
				//POSICION A AGREGAR=numAdd, ARCHIVO ORIGINA=f,ARCHIVO PUENTE COPIA=fnew, TEXTO A INSERTAR=lineAdd
				funcionAgregar(numAdd,f, fnew,lineAdd);
				break;
				
			//BORRA REGISTRO EN CONCRETO DEL FICHERO
			case 4:
				funcionConsultar(f);
				System.out.println("");
				System.out.println("POSICIÓN de PERSONA para BORRAR");
				int numDel=entrada.nextInt();
				entrada.nextLine();
				//POSICION A BORRAR=numDel, ARCHIVO ORIGINA=f,ARCHIVO PUENTE COPIA=fnew
				funcionBorrar(numDel,f, fnew);
				break;
			}
		} while (menu != 0);

	}

	public static void funcionConsultar(File f) throws FileNotFoundException {
		System.out.print("El fichero contiene: \n");
		//CREA LOS OBJETOS DE LECTURA DEL FICHERO
		FileReader fr=new FileReader(f);
		BufferedReader br=new BufferedReader(fr);
		String linea;
		String datos[];
		//HACE UNA LECTURA DE CADA LINEA HASTA QUE NO HAYA MAS LINEAS Y LO CIERRO
		try {
			int i=1;
			//NECESITAMOS QUE LEA LA PRIMERA LINEA ANTES DE ENTRAR EN EL BUCLE PARA COMENZAR EN LA PERSONA 1
			System.out.println(br.readLine());
			while((linea=br.readLine())!=null) {

				
				datos=linea.split(";");
				System.out.println("");
				System.out.println("Persona: "+(i));
				System.out.println("DNI: "+datos[0]);
				System.out.println("Nombre: "+datos[1]);
				System.out.println("Apellido 1: "+datos[2]);
				System.out.println("Apellido 2: "+datos[0]);
				System.out.println("Fecha de Nacimiento: "+datos[3]);
				System.out.println("");
				i++;
			}
			fr.close();
			System.out.println("Fin del fichero");
			//SI ALGO FALLA MUESTRO EL ERROR POR PANTALLA
		}catch(Exception e){
			e.printStackTrace();
			//SI FALLA TERMINO CERRANDO EL ARCHIVO
		}finally {
			try {
				if (null!= fr) {
					fr.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}		
	}
	public static boolean comprobarRuta(File f) {
		
		boolean check = false;
		//COMPRUEBA SI LA RUTA EXISTE
		if (f.exists()) {
			System.out.println("La ruta EXISTE");
			//COMPRUEBA SI ES UN DIRECTORIO Y MUESTRA SU NOMBRE
			if (f.isDirectory()) {
				System.out.println(f.getName() + " es una carpeta");
			} else {
				//SI NO, ES UN FICHERO Y MUESTRA SU NOMBRE
				System.out.println(f.getName() + " es un fichero");
				check=true;
				//COMPRUEBA SI SE PUEDE LEER
				if(f.canRead()) {
					System.out.println("Sí se puede LEER");
				}else {
					System.out.println("No se puede LEER");
				}	
			}
		}
		return check;
	}
	
	public static void funcionModificar(int reg,File f,File fnew,String texto) {
//		int numlugar=contarlineas(f);
		try {//ABRIR ORIGINAL PARA LEER
			FileReader fold=new FileReader(f);
			BufferedReader br=new BufferedReader(fold);
			String linea;
			//ABRIR PUENTE PARA ESCRIBIR
			FileWriter fnewer =new FileWriter(fnew);
			BufferedWriter bw=new BufferedWriter(fnewer);
			int i=1;
			//MIENTRAS HAYA LINEAS ESCRITAS LEEREMOS
			while((linea=br.readLine())!=null)  {
				//SI LA LINEA QUE LEEMOS COINCIDE CON LA LINEA A MODIFICAR CAMBIAMOS EL TEXTO
				if(i==(reg+1)) {
					bw.write(texto);
					bw.newLine();
				}else {
					bw.write(linea);
					bw.newLine();
				}
				i++;
			}
			
			br.close();
			bw.close();
			//COPIAMOS EL CONTENIDO DEL ARCHIVO PUENTE A EL ORIGINAL Y BORRAMOS EL ARCHIVO PUENTE
			copiarArchivo(fnew,f);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void funcionAgregar(int reg,File f, File fnew,String texto) {
//		int numlugar=contarlineas(f);
		try {//ABRIR ORIGINAL PARA LEER
			FileReader fold=new FileReader(f);
			BufferedReader br=new BufferedReader(fold);
			String linea;
			//ABRIR PUENTE PARA ESCRIBIR
			FileWriter fnewer =new FileWriter(fnew);
			BufferedWriter bw=new BufferedWriter(fnewer);
			int i=1;
			//MIENTRAS HAYA LINEAS ESCRITAS LEEREMOS
			while((linea=br.readLine())!=null)  {
				//SI LA LINEA QUE LEEMOS COINCIDE CON LA LINEA A AGREGAR INTRODUCIMOS EL NUEVO TEXTO Y LA COPIAMOS LA SIGUIENTE LINEA DEL ORIGINAL
				if(i==(reg+1)) {
					bw.write(texto);
					bw.newLine();
					bw.write(linea);
					bw.newLine();
				}else {
					bw.write(linea);
					bw.newLine();
				}
				i++;
			}
			
			br.close();
			bw.close();
			//COPIAMOS EL CONTENIDO DEL ARCHIVO PUENTE A EL ORIGINAL Y BORRAMOS EL ARCHIVO PUENTE
			copiarArchivo(fnew,f);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void funcionBorrar(int reg,File f, File fnew) {
//		int numlugar=contarlineas(f);
		try {//ABRIR ORIGINAL PARA LEER
			FileReader fold=new FileReader(f);
			BufferedReader br=new BufferedReader(fold);
			String linea;
			//ABRIR PUENTE PARA ESCRIBIR
			FileWriter fnewer =new FileWriter(fnew);
			BufferedWriter bw=new BufferedWriter(fnewer);
			int i=1;
			//MIENTRAS HAYA LINEAS ESCRITAS LEEREMOS
			while((linea=br.readLine())!=null)  {
				//SI LA LINEA QUE LEEMOS COINCIDE CON LA LINEA A BORRAR DEJAMOS EL ESPACIO EN BLANCO 
				if(i==(reg+1)) {
					bw.write("");
					
				}else {
					bw.write(linea);
					bw.newLine();
				}
				i++;
			}
			
			br.close();
			bw.close();
			//COPIAMOS EL CONTENIDO DEL ARCHIVO PUENTE A EL ORIGINAL Y BORRAMOS EL ARCHIVO PUENTE
			copiarArchivo(fnew,f);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//DEVUELVE EL TEXTO PARA USARLO 
	public static String escribirLinea() {	
		System.out.println("Escribiendo...");
		System.out.println("DNI:");
		String texto = entrada.nextLine();
		System.out.println("Nombre:");
		texto = texto+";"+entrada.nextLine();
		System.out.println("Apellido1:");
		texto = texto+";"+entrada.nextLine();
		System.out.println("Apellido2:");
		texto = texto+";"+entrada.nextLine();
		System.out.println("Fecha de nacimiento:");
		texto = texto+";"+entrada.nextLine();
		return texto;
	}
	//CUENTA LINEAS(SIN IMPLEMENTACIÓN)
	public static int contarlineas(File f) {
		int resultado=0;
		FileReader fr;
		try {
			fr = new FileReader(f);
			BufferedReader br=new BufferedReader(fr);
			
			while((br.readLine())!=null) {
				resultado++;
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
	//COPIAMOS EL CONTENIDO DEL ARCHIVO PUENTE A EL ORIGINAL Y BORRAMOS EL ARCHIVO PUENTE
	public static void copiarArchivo(File origen, File copia) {
		try {
			FileReader fold=new FileReader(origen);
			BufferedReader br=new BufferedReader(fold);
			String linea="";
			FileWriter fnewer =new FileWriter(copia);
			BufferedWriter bw=new BufferedWriter(fnewer);
			
			while((linea=br.readLine())!=null)  {
				bw.write(linea);
				bw.newLine();
			}
			br.close();
			bw.close();
			origen.delete();
		}
		catch(IOException e) {
			
		}
	}
}