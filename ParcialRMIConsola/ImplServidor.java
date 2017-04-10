import java.io.Serializable;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.*;

/*
    Esta clase contiene todos los métodos implementados pertenecientes 
    al servidor; mismos a los cuales los clientes accederán por medio
    de la interfaz IServidor. 
*/
public class ImplServidor extends UnicastRemoteObject implements IServidor
{
    //Declaración de variables. 
	private static final long serialVersionUID = 1526643248021674437L;
    private static int NumSesion = new Random().nextInt();
    private static int SesionActual = 0;
    private static IServidor Funciones;
    private String nombreLog;
    private Resource RecursoAux;
    List ListaDeRecursos = new ArrayList();
    Message Msg;
    
    //Maps correspondientes a los registros de la sesión y del nombre del usuario. 
    //Esto con la finalidad de llevar un control de los usuarios que entran y salen.
    private Map<Integer, String> sesion_nombre = new HashMap<Integer, String>();
    private Map<String, Integer> nombre_sesion = new HashMap<String, Integer>();
    private Map<Integer, List<Integer>> contactos = new HashMap<Integer, List<Integer>>();
    private Map<String, String> sesion_repositorio = new HashMap<String, String>();
    private Map<String, String> repositorio_sesion = new HashMap<String, String>();

    protected ImplServidor() throws RemoteException 
    {
        super();
        Globals.StrDePrueba = "Pase por aqui...";
        //ListaDeRecursos = Globals.ListaDeRecursos;
    }

    protected ImplServidor(String nombreLog) throws RemoteException 
    {
        super();
        this.nombreLog = nombreLog;
    }
    
    public static int ObtenerSesion() 
    {
        return ++NumSesion;
    }
    
    /*
        Este método se encarga de reconocer al cliente que desea iniciar sesión
        y le asigna un numero de sesión (Esto no es necesario, fue sólo
        para realizar pruebas por medio de consola). 
    */
    public int IniciarSesion(String Nombre) throws RemoteException
    {
        //Cambiar esta parte por un registro de log en una estructura de datos...
        int SesionPorAsignar = ObtenerSesion();
        System.out.println(Nombre + " quiere iniciar sesion...");
        sesion_nombre.put(SesionPorAsignar, Nombre);
        nombre_sesion.put(Nombre, SesionPorAsignar);
        System.out.println(Nombre + " inicio sesion con este numero de sesion: " + SesionPorAsignar);
        return SesionPorAsignar;
    }

    /*
        Método que se encarga de imprimir la lista de los recursos de parte
        del cliente para su uso posterior. 
        Además de imprimir, también obtiene la dirección IP del cliente. 
    */
    public void ImprimirRecursos(List ListaRecursos) throws RemoteException
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
        Método que agrega el repositorio del cliente recién conectado a un map de repositorios
        al que se podrá acceder una vez que otro cliente desee obtener un archivo
        de ese cliente. 
    */
    public boolean AgregarRepositorio(String Nombre, String ID, List Repositorio) throws RemoteException
    {
        //Este if verifica que no se encuentre el nombre del usuario en la lista.
        if(repositorio_sesion.containsKey(Nombre.toUpperCase()))
        {
            //Cambiar por funcionalidad de log. (No necesario en interfaz gráfica)
            System.out.println(Nombre + " ya se encuentra en la lista de repositorios...");
            return false;
        }
        else
        {
            //Almacena el usuario recién conectado. 
            sesion_repositorio.put(ID, Nombre.toUpperCase());
            repositorio_sesion.put(Nombre.toUpperCase(), ID);
            //Cambiar por log (No necesario en interfaz gráfica)

            System.out.println("Se ha registrado el repositorio " + Nombre + " con el ID " + ID + " y cuenta con los siguientes archivos: ");
            /*
                Crear un nuevo objeto de tipo Resource para almacenarlo a la lista
                global de recursos. De aquí se van a obtener los archivos una 
                vez solicitados por otros clientes. 
            */
            ListaDeRecursos.add(new Resource(ID, Repositorio));

            //Manda imprimir los recursos del cliente recién conectado. 
            ImprimirRecursos(ListaDeRecursos);

            //Se crea un nuevo objeto de tipo Message con los recursos. Se utilizará
            //para almacenar la estructura de una forma más práctica. 
            Msg = new Message(Message.EMessageType.DATA_REPLY, 1, ListaDeRecursos);

            return true;
        }
    }

    public Message ObtenerRepositorio() throws RemoteException
    {
        return Msg;
    }

    /*
        Método que elimina un cliente que acaba de cerrar su sesión
        (NO FUNCIONA AUN, SE PLANTEARA DE FORMA SIMILAR AL PROYECTO DE SOCKETS
        YA CON INTERFAZ GRAFICA)
    */
    public void EliminarUsuario(String IPUsuario) throws RemoteException
    {
        sesion_repositorio.remove(IPUsuario);
        repositorio_sesion.remove(IPUsuario);
        ListaDeRecursos.remove(IPUsuario);
    }

    public Map ObtenerListaDeUsuarios() throws RemoteException
    {
        return sesion_repositorio;
    }
}