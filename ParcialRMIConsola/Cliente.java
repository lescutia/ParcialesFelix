import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.*;

/*
    Esta clase es la principal para el cliente. Se realiza la conexión con 
    el servidor para poder realizar la compartición de archivos. 
    Se tienen las direcciones URL de manera fija dentro de Globals.java. 
    Es necesario cambiar la dirección IP del servidor. 
*/

public class Cliente
{
    //Declaración de variables
	private static String SesionActual = "";
    private static IServidor Serv;
    private static IAutenticacion Aute;
    private static IManejoDeArchivos IMDArch;
    private static BufferedReader LeerArchivo = new BufferedReader(new InputStreamReader(System.in));
    private static int Puerto = 1492;
    private static List ListaDeArchivos;

    //Este método ejecuta un hilo multitarea para los clientes que se inicialicen. 
    public static void Runner()
    {
        Hilos newHilo = new Hilos();
        newHilo.start();
        try
        {
            newHilo.join();
        }
        catch(InterruptedException e){}
    }

	public static void main(String[] args) throws Exception
	{
		String NombreUsuario;//, URLACompartir, URLAGuardar;
        Process proc = Runtime.getRuntime().exec("rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false");
        Utils.setCodeBase(IAutenticacion.class);
        startRegistry(Puerto);

        //Se inicializan las interfaces nuevas para IAutenticacion e IManejoDeArchivos. 
		Aute = new ImpAutenticacion();
		IMDArch = new ImplManejoDeArchivos();
        //Se realiza la conexión con el servidor remoto. 
		Serv = (IServidor) Naming.lookup("rmi://172.16.62.247:1492/ImplServ");

		//URLACompartir = "C:\\Users\\luise\\Music\\BaladasEnEspanol\\PruebaProyecto";
		//URLAGuardar = "C:\\Users\\luise\\Music\\BaladasEnEspanol\\PruebaProyecto";

        //Lo siguiente realiza la inicialización del cliente solicitando un nombre. 
        Scanner leer = new Scanner(System.in);
        do
        {
            System.out.println("Por favor escribe tu nombre: ");
            NombreUsuario = leer.nextLine();

            System.out.println("URL origen: " + Globals.URLACompartir);
            System.out.println("URL destino: " + Globals.URLAGuardar);

            //Se manda llamar al método remoto que nos va a traer la lista de los archivos. 
            ListaDeArchivos = IMDArch.obtenerListaArchivos(Globals.URLACompartir);
            //Se establece la sesión actual dentro del servidor. 
            SesionActual = Aute.autenticarRepositorio(NombreUsuario, ListaDeArchivos);
            //Se realiza un nuevo nombrado para el cliente actual. 
            Naming.rebind("rmi://172.16.62.247:1492/" + NombreUsuario.toUpperCase(), Aute);
            Runner();
        }while(SesionActual == "");
	}

    //Método que inicializa el registro para RMI por medio del numero del puerto. 
    private static void startRegistry(int Puerto) throws RemoteException 
    {
        try 
        {
            Registry registry = LocateRegistry.getRegistry(Puerto);
            registry.list();
        }catch (RemoteException ex) 
        {
            Registry Reg = LocateRegistry.createRegistry(Puerto);
            System.out.println("Ha sido creado el registro para el puerto #" + Puerto);
        }
    }
}