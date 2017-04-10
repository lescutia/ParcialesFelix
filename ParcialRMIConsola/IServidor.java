import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IServidor extends Remote
{
	public int IniciarSesion(String Nombre) throws RemoteException;
    public void ImprimirRecursos(List ListaRecursos) throws RemoteException;
    public boolean AgregarRepositorio(String Nombre, String ID, List Repositorio) throws RemoteException;
    public Message ObtenerRepositorio() throws RemoteException;
    public void EliminarUsuario(String IPUsuario) throws RemoteException;
    public Map ObtenerListaDeUsuarios() throws RemoteException;
}
