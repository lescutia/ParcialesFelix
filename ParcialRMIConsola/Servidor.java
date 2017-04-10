import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*
    Esta clase es la principal para el servidor. Dentro de esta clase se 
    inicializa el servcio remoto por medio de RMI, se coloca un nombrado
    principal, que es el que utilizarán los clientes para invocar los 
    métodos remotos del servidor. 
*/

public class Servidor
{
    //Se inicializa el puerto. Siempre es el mismo puerto el que se utilizará
	private static int Puerto = 1492;

	public static void main(String[]args) throws Exception
	{
		//Bat para ejecución de RMI
        Process proc = Runtime.getRuntime().exec("rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false");
        //Se inicializa el servicio 
        System.out.println("=== PRACTIFILES ===");
        Utils.setCodeBase(IServidor.class);
        startRegistry(Puerto);
        //Se declara la instancia de los métodos del Servidor. 
        IServidor Serv = new ImplServidor();
        //Se inicializa el nombrado para el servidor, mismo que será utilizado
        //por los clientes que se conecten. 
        Naming.rebind("rmi://172.16.62.247:1492/ImplServ", Serv);

        System.out.println("La funcionalidad ya se ha cargado");

        System.out.println("La aplicacion esta en ejecucion. Esperando usuarios... \nPara salir presione Enter");
        //Esperando usuarios
        System.in.read();
        
        UnicastRemoteObject.unexportObject(Serv, true);
        
        System.out.println("Gracias por utilizar este servicio.");
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