package servidor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import middlewere.IServidor;

/**
 *
 * @author luise
 */
public class ImpServidor extends UnicastRemoteObject implements IServidor
{
    private static final long serialVersionUID = 1526643248021674437L;
    private static int NumSesion = new Random().nextInt();
    private static int SesionActual = 0;
    private static IServidor Funciones;
    private String nombreLog;
    
    private Map<Integer, String> sesion_nombre = new HashMap<Integer, String>();
    private Map<String, Integer> nombre_sesion = new HashMap<String, Integer>();
    private Map<Integer, List<Integer>> contactos = new HashMap<Integer, List<Integer>>();
    private Map<Integer, String> sesion_repositorio = new HashMap<Integer, String>();
     private Map<String, Integer> repositorio_sesion = new HashMap<String, Integer>();
    
    protected ImpServidor() throws RemoteException 
    {
        super();
    }
    
    protected ImpServidor(String nombreLog) throws RemoteException 
    {
        super();
        this.nombreLog = nombreLog;
    }
    
    public static int ObtenerSesion() 
    {
        return ++NumSesion;
    }
    
    public int IniciarSesion(String Nombre) throws RemoteException
    {
        //Cambiar esta parte por un registro de log en una estructura de datos...
        int SesionPorAsignar = ObtenerSesion();
        System.out.println(Nombre + " quiere iniciar sesión...");
        sesion_nombre.put(SesionPorAsignar, Nombre);
        nombre_sesion.put(Nombre, SesionPorAsignar);
        System.out.println(Nombre + " inició sesión con este numero de sesión: " + SesionPorAsignar);
        return SesionPorAsignar;
    }
    
    public String CompartirCarpeta() throws RemoteException
    {
        return "";
    }
    
    public String ObtenerListaDeArchivos() throws RemoteException
    {
        return "";
    }
    
    public boolean AgregarRepositorio(String Nombre, int ID) throws RemoteException
    {
        if(repositorio_sesion.containsKey(Nombre.toUpperCase()))
        {
            //Cambiar por funcionalidad de log. 
            System.out.println(Nombre + " ya se encuentra en la lista de repositorios...");
            return false;
        }
        else
        {
            sesion_repositorio.put(ID, Nombre.toUpperCase());
            repositorio_sesion.put(Nombre.toUpperCase(), ID);
            //Cambiar por log
            System.out.println("Se ha registrado el repositorio " + Nombre + " con el ID " + ID);
            return true;
        }
    }
    
    public void ListaDeRepositorios() throws RemoteException
    {
        
    }
}
