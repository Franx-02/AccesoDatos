
import java.util.*;
import java.io.*;

//PATH -> C:\Users\aliva\OneDrive\Escritorio\acceso a datos\file.csv

public class CRUDcsv {
	
	public static Scanner entrada = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Bienvenido al menu para gestionar ficheros");
		
		//PEDIR PATH PARA LEER FICHERO
		System.out.println("Introduce la ruta a leer");
		String ruta = entrada.nextLine();
		
		//ARCHIVO CON EL PATH -> C:\\Users\\aliva\\OneDrive\\Escritorio\\acceso a datos\\file.csv
		File f = new File(ruta);
		
		//ARCHIVO TEMPORAL CON EL PATH -> C:\Users\aliva\OneDrive\Escritorio\acceso a datos\temporal.csv
		String rutatemporal = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf("\\")) + "\\tmp.csv";
		File fnew = new File(rutatemporal);
		System.out.println(fnew.getAbsolutePath());
		
		//COMPROBAR QUE LA RUTA Y EL ARCHIVO SON CORRECTOS SI NO, VUELVE A PEDIRLO
		while (!comprobarRuta(f)) {
			System.out.println("No es una ruta VALIDA, introduce ruta CORRECTA");
			ruta = entrada.nextLine();
			f = new File(ruta);
		}
		
		//MENU
		int menu = 0;
		do {
			System.out.println("Bienvenido al menu para gestionar ficheros");
			System.out.println("Que desea hacer?");
			System.out.println("Consular  |INTRODUCE 1|");
			System.out.println("Modificar |INTRODUCE 2|");
			System.out.println("Agregar   |INTRODUCE 3|");
			System.out.println("Borrar    |INTRODUCE 4|");
			System.out.println("Salir     |INTRODUCE 0|");
			menu = entrada.nextInt();
			switch(menu) {
			case 0:
				System.out.println("Has elegido |Salir|");
				break;
				
			//CONSULTAR REGISTROS DEL FICHERO
			case 1:
				Consultarf(f);
				break;
				
			//CONSULTAR Y MODIFICAR REGISTROS DEL FICHERO
			case 2:
				//LE PASAMOS LA POSICION A MODIFICAR = ARCHIVO ORIGINAL = f, ARCHIVO COPIA TEMPORAL = fnew
				Modificarf(f, fnew);
				break;
				
			//CONSULTAR Y AGREGAR UN REGISTRO AL FICHERO
			case 3:
				//LE PASAMOS LA POSICION ARCHIVO ORIGINAL = f, ARCHIVO TEMPORAL = fnew
				funcionAgregar(f, fnew);
				break;
				
			case 4:
				//POSICION A BORRAR = numDel, ARCHIVO ORIGINAL = f, ARCHIVO TEMPORAL = fnew
				Borrarf(f, fnew);
				break;
			}
		}while (menu != 0);
	}
	
	//FUNCION PARA COMPROBAR LA VALIDEZ DE UNA RUTA DE UN FICHERO
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
				check = true;
				//COMPRUEBA SI SE PUEDE LEER
				if(f.canRead()) {
					System.out.println("Sí se puede LEER");
				}else {
					System.out.println("No se puede LEER");
				}	
			}
		}
		else {
			try {
				f.createNewFile();
				System.out.println("Fichero creado");
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("NOMBRE;DNI;APELLIDO1;APELLIDO2;NACIMIENTO");
				bw.close();
				check = true;
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return check;
	}
	
	//FUNCION PARA CONSULTAR TODOS LOS REGISTROS EN UN FICHERO
	public static void Consultarf(File f) throws FileNotFoundException {
		System.out.print("El fichero contiene: \n");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String linea;
		String datos[];
		
		//HACE UNA LECTURA DE CADA LINEA
		try {
			int contador = 1;
			System.out.println(br.readLine());
			while((linea = br.readLine()) != null) {

				datos = linea.split(";");
				System.out.println("");
				System.out.println("Persona: "+(contador));
				System.out.println("DNI: "+datos[0]);
				System.out.println("Nombre: "+datos[1]);
				System.out.println("Apellido 1: "+datos[2]);
				System.out.println("Apellido 2: "+datos[3]);
				System.out.println("Fecha de Nacimiento: "+datos[4]);
				System.out.println("");
				contador++;
			}
			fr.close();
			System.out.println("Cierre del fichero");
			
		//CONTROL DE ERRORES
		}catch(Exception e){
			e.printStackTrace();

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
	
	//FUNCION PARA MODIFICAR REGISTRO EN UN FICHERO
	public static void Modificarf(File f,File fnew) {
		
		try {
			Consultarf(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		System.out.println("");
		System.out.println("POSICIÓN de PERSONA para MODIFICAR");
		int reg = entrada.nextInt();
		entrada.nextLine();
		System.out.println("Editar entrada");
		String texto = escribirLinea();

		try {
			
			//ABRIR FICHERO ORIGINAL PARA LEER
			FileReader fold = new FileReader(f);
			BufferedReader br = new BufferedReader(fold);
			String linea;
			
			//ABRIR TEMPORAL PARA ESCRIBIR
			FileWriter fnewer = new FileWriter(fnew);
			BufferedWriter bw = new BufferedWriter(fnewer);
			int contador = 1;
			
			//MIENTRAS HAYA LINEAS ESCRITAS LEEREMOS
			while((linea = br.readLine()) != null)  {
				
				if(contador == (reg+1)) {
					bw.write(texto);
					bw.newLine();
				}
				else {
					bw.write(linea);
					bw.newLine();
				}
				contador++;
			}
			
			br.close();
			bw.close();
			
			//COPIAMOS EL CONTENIDO DEL ARCHIVO TEMPORAL A EL ORIGINAL Y BORRAMOS EL ARCHIVO TEMPORAL
			Copiarf(fnew,f);
			System.out.println("hola1");
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	//FUNCION PARA AGREGAR REGISTRO EN UN FICHERO
	public static void funcionAgregar(File f, File fnew) {
		
		try {
			Consultarf(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		System.out.println("");
		System.out.println("POSICIÓN del REGISTRO para AGREGAR");
		int reg = entrada.nextInt();
		entrada.nextLine();
		System.out.println("Agregar entrada");
		String texto = escribirLinea();
		
		try {
			
			//ABRIR ORIGINAL PARA LEER
			FileReader fold = new FileReader(f);
			BufferedReader br = new BufferedReader(fold);
			String linea;
			
			//ABRIR PUENTE PARA ESCRIBIR
			FileWriter fnewer = new FileWriter(fnew);
			BufferedWriter bw = new BufferedWriter(fnewer);
			
			int contador = 1;
			int totalLineas = contarlineas(f) - 1;
			System.out.println(totalLineas);
			if(reg > totalLineas) {
				while((linea = br.readLine()) != null)  {
					bw.write(linea);
					bw.newLine();
				}
				bw.append(texto);
				bw.newLine();
			}
			else {
				//MIENTRAS HAYA LINEAS ESCRITAS LEEREMOS
				while((linea = br.readLine()) != null)  {
					if(contador == (reg+1)) {
						bw.write(texto);
						bw.newLine();
						bw.write(linea);
						bw.newLine();
					}else {
						bw.write(linea);
						bw.newLine();
					}
					contador++;
				}
			}
			br.close();
			bw.close();
			
			//COPIAMOS EL CONTENIDO DEL ARCHIVO TEMPORAL A EL ORIGINAL Y BORRAMOS EL ARCHIVO TEMPORAL
			Copiarf(fnew,f);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//FUNCION PARA BORRAR UN REGISTRO EN UN FICHERO
	public static void Borrarf(File f, File fnew) {
		
		try {
			Consultarf(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("");
		System.out.println("POSICIÓN de PERSONA para BORRAR");
		int reg = entrada.nextInt();
		entrada.nextLine();
		
		
		try {
			//ABRIR ORIGINAL PARA LEER
			FileReader fold = new FileReader(f);
			BufferedReader br = new BufferedReader(fold);
			String linea;
			//ABRIR TEMPORAL PARA ESCRIBIR
			FileWriter fnewer = new FileWriter(fnew);
			BufferedWriter bw = new BufferedWriter(fnewer);
			int i=1;
			//MIENTRAS HAYA LINEAS ESCRITAS LEEREMOS
			while((linea = br.readLine()) != null)  {
				//SI LA LINEA QUE LEEMOS COINCIDE CON LA LINEA A BORRAR DEJAMOS EL ESPACIO EN BLANCO 
				if(i == (reg+1)) {
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
			Copiarf(fnew,f);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//FUNCION PARA ESCRIBIR UN REGISTRO EN UN FICHERO
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
	
	//FUNCION PARA COPIAR LOS DATOS DE UN FICHERO A OTRO
	public static void Copiarf(File origen, File copia) {
		try {
			FileReader fold = new FileReader(origen);
			BufferedReader br = new BufferedReader(fold);
			String linea = "";
			FileWriter fnewer = new FileWriter(copia);
			BufferedWriter bw = new BufferedWriter(fnewer);
			
			while((linea = br.readLine()) != null)  {
				bw.write(linea);
				bw.newLine();
			}
			br.close();
			bw.close();
			//origen.delete();
		}
		catch(IOException e) {
			
		}
	}
	
	//FUNCION PARA CONTAR LOS REGISTROS DEL ARCHIVO
	public static int contarlineas(File f) {
		int resultado=0;
		FileReader fr;
		try {
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			while((br.readLine())!=null) {
				resultado++;
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}

}
