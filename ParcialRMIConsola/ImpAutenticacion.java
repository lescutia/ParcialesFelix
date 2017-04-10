import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.List;

/*
    Esta clase contiene los métodos implementados pertenecientes a la
    autenticación de los usuarios. 
*/

public class ImpAutenticacion extends UnicastRemoteObject implements IAutenticacion
{
    //Declaración de variables
    private static int Puerto = 1492;
    private static final long serialVersionUID = 593676645816200166L;
    private static int NumSesion = new Random().nextInt();
    private static int SesionActual = 0;
    private static String IPUsuario;
    private String nombreLog;
    private static IServidor Serv;
    private Socket socket;
    //private static InetAddress group = InetAddress.getByName("224.0.0.3");
    
    //Constructores de la clase
    protected ImpAutenticacion() throws RemoteException {
        super();
    }
    
    protected ImpAutenticacion(String nombreLog) throws RemoteException {
        super();
        this.nombreLog = nombreLog;
    }
    
    //Método que se encarga de obtener la dirección IP del cliente recién conectado. 
    public static String ObtenerSesion() 
    {
        try
        {
            IPUsuario = InetAddress.getLocalHost().getHostAddress();
            return IPUsuario;
        }catch(Exception e){
            return "";
        }
    }
    
    /*
        Este método se encarga de conectar el cliente con el servidor actuialmente
        en ejecución, además manda llamar al método de AgregarRepositorio que 
        se encuentra dentro de la implementación de Servidor. 
    */
    public String autenticarRepositorio(String Nombre, List Repositorio) throws RemoteException{
        Resource Estructura;// = new Resource();
        String SesionPorAsignar;
        //Cambiar esta parte por la implementación del log
        System.out.println(Nombre + " quiere iniciar la sesion...");
        SesionPorAsignar = ObtenerSesion();
        
        //Try-Catch que conecta al cliente con el servidor. 
        try
        {
            Serv = (IServidor) Naming.lookup("rmi://172.16.62.247:1492/ImplServ");
            System.out.println("Se establecio la conexion con el servidor");
        }catch(Exception e)
        {
            System.out.println("Ocurrio un error: " + e);
        }
        
        //Se manda llamar al método AgregarRepositorio con el nombre, 
        //la IP y la lista de los archivos del cliente conectado. 
        if(Serv.AgregarRepositorio(Nombre, SesionPorAsignar, Repositorio))
        {
            System.out.println("=== CONECTADO CON EL SERVIDOR ===");
            return SesionPorAsignar;
        }
        return "";
    }

    /*
        Método que se encarga de realizar la obtención del archivo por medio
        de la URL que se encuentra establecida dentro de la clase Globals
        del cliente conectado.  
        Este método se encuentra en pruebas todavía. PENDIENTE!!!
        PUEDE CAMBIAR SU IMPLEMENTACIÓN EN CUALQUIER MOMENTO!!!
    */
    public Message DescargarArchivo(String NombreArchivo) throws RemoteException
    {
        Message fileMsg = null;
        //ObjectOutputStream dOut = null;
        //boolean Respuesta = false;
        try
        {
            String URLDelArchivo = Globals.URLACompartir + "\\" + NombreArchivo;
            System.out.println("Se descargara el archivo de " + URLDelArchivo);
            try
            {
                byte[] bFile = readBytesFromFile(URLDelArchivo);
                fileMsg = new Message(Message.EMessageType.DATA_REPLY, 20, bFile);
                /*dOut = new ObjectOutputStream(socket.getOutputStream());
                dOut.writeObject(fileMsg);*/
                //Respuesta = true;
            }catch(Exception e)
            {
                System.out.println("No se encuentra el archivo en el directorio");
            }
        }catch(Exception e)
        {}
        return fileMsg;
        //return Respuesta;
    }

    //Método que sirve para realizar la lectura del archivo y convertir 
    //el mismo a un arreglo de bytes. 
    private static byte[] readBytesFromFile(String filePath) 
    {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }

    
    /*public int autenticarCliente(String Nombre, int ID) throws RemoteException
    {
        return 0;
    }*/
}
