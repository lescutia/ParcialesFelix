import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IManejoDeArchivos extends Remote
{
	public List obtenerListaArchivos(String dirURL) throws RemoteException;
}