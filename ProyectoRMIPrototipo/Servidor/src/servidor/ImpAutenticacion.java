package servidor;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import middlewere.IAutenticacion;
import middlewere.IServidor;

/**
 *
 * @author luise
 */
public class ImpAutenticacion extends UnicastRemoteObject implements IAutenticacion
{
    private static int Puerto = 1495;
    private static final long serialVersionUID = 593676645816200166L;
    private static int NumSesion = new Random().nextInt();
    private static int SesionActual = 0;
    private String nombreLog;
    private static  IServidor Serv;
    
    protected ImpAutenticacion() throws RemoteException {
        super();
    }
    
    protected ImpAutenticacion(String nombreLog) throws RemoteException {
        super();
        this.nombreLog = nombreLog;
    }
    
    public static int ObtenerSesion() 
    {
        return ++NumSesion;
    }
    
    public int autenticarRepositorio(String Nombre) throws RemoteException{
        
        int SesionPorAsignar;
        //Cambiar esta parte por la implementación del log
        System.out.println(Nombre + " quiere iniciar la sesión...");
        SesionPorAsignar = ObtenerSesion();
        
        try
        {
            Serv = (IServidor) Naming.lookup("rmi://localhost:" + Puerto + "/ImplServ");
        }catch(Exception e)
        {
            System.out.println("Ocurrió un error: " + e);
        }
        
        if(Serv.AgregarRepositorio(Nombre, SesionPorAsignar))
            return SesionPorAsignar;
        return 0;
    }
    
    public int autenticarCliente(String Nombre, int ID) throws RemoteException
    {
        return 0;
    }
}
