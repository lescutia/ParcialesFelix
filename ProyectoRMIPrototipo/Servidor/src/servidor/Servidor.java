package servidor;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import middlewere.IServidor;
import middlewere.Utils;

/**
 *
 * @author luise
 */
public class Servidor 
{
    private static int Puerto = 1492;
    public static void main(String[] args) throws Exception
    {
        System.out.println("=== PRACTIFILES ===");
        Utils.setCodeBase(IServidor.class);
        startRegistry(Puerto);
        
        ImpServidor Serv = new ImpServidor();
        Naming.rebind("rmi://localhost:" + Puerto + "/ImplServ", Serv);
        System.out.println("La funcionalidad ya se ha cargado");
        
        ImpManejoArchivos MArc = new ImpManejoArchivos();
        Naming.rebind("rmi://localhost:" + Puerto + "/ImplManejoArch", MArc);
        System.out.println("Gestión de archivos... Listo!");
        //IServidor Remoto = (IServidor)UnicastRemoteObject.exportObject(Serv, 1492);
        
        //Registry Reg = LocateRegistry.getRegistry();
        //Reg.rebind("PractiFiles", Remoto);
        
        System.out.println("La aplicación está en ejecución. Esperando usuarios... \nPara salir presione Enter");
        
        System.in.read();
        
        //Reg.unbind("PractiFiles");
        UnicastRemoteObject.unexportObject(Serv, true);
        
        System.out.println("Gracias por utilizar este servicio.");
    }

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