import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class listarCarpetas {
	
	private static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		
		System.out.println("Introduce el PATH que vayamos a leer: ");
		String path = entrada.nextLine();
		File f = new File(path);
		float total = f.length();
		System.out.println(total);
		
		while(!compruebafichero(f)) {
			System.out.println("No es un path valido, introduzca otro path: ");
			path = entrada.nextLine();
			f = new File(path);
		}
		listarsubcarpetas(f,1);

	}
	
	public static boolean compruebafichero(File f) {
		boolean flag = false;
		
		if(f.exists()) {
			flag = true;
			System.out.println("El path existe\n");
			
			if(f.canRead()) {
				System.out.println("Tambien se puede leer\n");
			}
			else {
				System.out.println("Pero no se puede leer\n");
			}
		}
		return flag;
	}
	
	public static void listarsubcarpetas(File f,int contador) {
		if(f.isDirectory()) {
			for (int i = 0;i<contador;i++) {
				System.out.print("\t");
			}
			contador++;
			System.out.println("------contenido------");
			File childFiles[] = f.listFiles();
			List<File> listFiles= new ArrayList<>();
			listFiles = Arrays.asList(childFiles);
			for (File file : listFiles) {
				for (int i = 0;i<contador;i++) {
					System.out.print("\t");
				}
				//SI ALGUNO DE LOS HIJOS DE LA LISTA ES UN DIRECTORIO, otros ficheros
				if(file.isDirectory()) {
					//ES UN DIRECTORIO, PINTO SU NOMBRE
					System.out.println("El archivo es un carpeta y es "+file.getName());
					String path = file.getPath();
					File a = new File(path);
					
					listarsubcarpetas(a,contador);
					
				}else {
					//SI NO ES DIRECTORIO, ES FICHERO, LO PINTO
					System.out.println(file.getName()+"es un fichero");
				}
			}
		}
		else {
			System.out.println("El archivo es un fichero y es "+f.getName());
		}
	}

}
