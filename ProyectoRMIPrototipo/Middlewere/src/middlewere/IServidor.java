package middlewere;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author luise
 */
public interface IServidor extends Remote
{
    public int IniciarSesion(String Nombre) throws RemoteException;
    public String CompartirCarpeta() throws RemoteException;
    public String ObtenerListaDeArchivos() throws RemoteException;
    public void ListaDeRepositorios() throws RemoteException;
    public boolean AgregarRepositorio(String Nombre, int ID) throws RemoteException;
    //public int BuscarRepositorio(int Usuario) throws RemoteException;
}
