import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.net.UnknownHostException;
import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

/*
	Esta clase contiene una implementación del hilo que tendrá toda la interacción
	con los clientes que se conecten. 
	Habrá un menú con opciones, mismos que no serán necesarios en el proyecto
	con interfaz gráfica, ya que se pueden colocar las implementaciones 
	por medio de botones sobre la interfaz. 
*/

public class Hilos extends Thread
{
	private IServidor Serv;
	private Resource RecursoAux;
	private String IPUsuario;
	private Map ListaDeUsuarios;
	Scanner leer;

	//Se realiza conexión con el servidor en ejecución. 
	public Hilos(){
		leer = new Scanner(System.in);
		try
		{
			this.Serv = (IServidor) Naming.lookup("rmi://172.16.62.247:1492/ImplServ");
		}catch(Exception e){}
		this.setName("LeaderRunner-Thread");
	}

	//Método que obtiene la dirección IP del cliente recién conectado. 
	public String ObtenerSesion() 
    {
        try
        {
            IPUsuario = InetAddress.getLocalHost().getHostAddress();
            return IPUsuario;
        }catch(Exception e){
            return "";
        }
    }

    //Método que imprime la lista de recursos y además la obtiene. 
	public void ImprimirRecursos(List ListaRecursos)
    {
        List ListaAux;
        System.out.println("=== RECURSOS DISPONIBLES ===");
        for(int i=0; i<ListaRecursos.size(); i++)
        {
            RecursoAux = (Resource) ListaRecursos.get(i);
            System.out.println("--- Ubicacion de recursos: " + RecursoAux.GetResourcesOwner() + " ---");
            ListaAux = RecursoAux.GetElements();
            for(int j=0; j<ListaAux.size(); j++)
                System.out.println(ListaAux.get(j));
        }
    }

    /*
    	Este método es el encargado de realizar la conexión con un tercer cliente
    	el cual servirá para poder solicitarle archivos remotos. 
    	Recibe el nombre del usuario y se declara una instancia a IAutenticacion
    	para poder conectarse directamente con el cliente al cual desea solicitarle
    	un archivo. Manda ejecutar el método DescargarArchivo desde el otro cliente,
    	mismo que obtendrá de regreso el archivo solicitado. 
    */
    public void ConectarConOtroUsuario(String NomUsuario)
    {
    	byte[] ArchAux;
    	Message fileMsg;
    	System.out.println(NomUsuario);
    	String NombreArchivo;
    	String strAux = "rmi://172.16.62.247:1492/" + NomUsuario;
    	IAutenticacion Aute;
    	System.out.println(strAux);
    	try
    	{
    		Aute = (IAutenticacion) Naming.lookup(strAux);
    		System.out.println("Se va a realizar peticion al usuario: " + NomUsuario);
    		System.out.println("Cual archivo de " + NomUsuario + " desea descargar?");
    		NombreArchivo = leer.next();
    		//fileMsg tendrá guardado el archivo proveniente del cliente al que se le solicitó
    		fileMsg = Aute.DescargarArchivo(NombreArchivo);
    		ArchAux = fileMsg.GetDataBytes();
    		if(fileMsg != null)
    		{
    			//Aqui entra cuando ya se tiene el archivo en el cliente destino. 
    			//Ahora hay que escribirlo en el directorio...
    			try
    			{
	    			FileOutputStream WriteArch = new FileOutputStream(Globals.URLAGuardar + NombreArchivo);
	    			WriteArch.write(ArchAux);
	    			WriteArch.close();
	    			System.out.println("Descarga exitosa");
    			}catch(Exception e)
    			{
    				System.out.println("No es posible escribir el archivo obtenido");
    			}
    		}
    		else
    			System.out.println("Descarga fallida");
    	}catch(Exception e)
    	{
    		System.out.println("No se pudo realizar la conexion con " + NomUsuario);
    	}
    }

    //Ejecución del hilo
	public void run()
	{
		//Declaración de variables para su uso dentro del hilo
		Message MsgR;
		List ListaAux;
		int OpcionMenu, Bandera = 0;
		String DuenoArchivo;
		IPUsuario = ObtenerSesion();

		//Siempre se ejecutará esto, a menos que se seleccione la opción 3
		while(true)
		{
			System.out.println("=== MENU DE OPCIONES PRACTIFILES ===");
			System.out.println("1- Ver archivos disponibles");
			System.out.println("2- Descargar archivo");
			System.out.println("3- Salir del sistema");
			OpcionMenu = leer.nextInt();

			if(OpcionMenu >= 1 && OpcionMenu <= 3)
			{
				//Switch case que gestiona las opciones del menú. 
				switch(OpcionMenu)
				{
					/*
						La opción 1 nos permitirá obtener una lista de todos los
						archivos disponibles de acuerdo a las sesiones abiertas 
						de Cliente. 
						Se imprimirá dicha lista para que el usuario pueda seleccionar
						un archivo. 
						En la interfaz gráfica esto se realizará de una manera similar
						a como se realiza en Sockets. 
					*/
					case 1:
						try
						{
							MsgR = Serv.ObtenerRepositorio();
							ListaAux = (ArrayList)MsgR.GetResources();
							ImprimirRecursos(ListaAux);
						}catch(Exception e){}
						break;
					/*
						La opción 2 nos permite obtener un archivo proveniente de
						otro cliente. Aun no se ha probado dicha opción. 
						Puede cambiar su implementación dependiendo del contexto. 
					*/
					case 2:
						try
						{
							ListaDeUsuarios = Serv.ObtenerListaDeUsuarios();
							System.out.println("A que usuario desea solicitarle un archivo?");
							DuenoArchivo = leer.next();
							ConectarConOtroUsuario(DuenoArchivo.toUpperCase());
						}catch(Exception e)
						{
							System.out.println("No se pudo obtener la lista de usuarios");
						}
						break;
					/*
						La opción 3 permite al cliente actual salir del sistema
						y, con ello, dar por finalizada la sesión. 
					*/
					case 3:
						try
						{
							Bandera = 1;
							Serv.EliminarUsuario(IPUsuario);
							System.out.println("Salio del sistema");
						}catch(Exception e){
							System.out.println("No se pudo salir del sistema");
							Bandera = 0;
						}
						break;
				}
			}
			if(Bandera == 1)
				break;
		}
	}
}