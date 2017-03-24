package middlewere;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author luise
 */
public interface IManejoDeArchivos 
{
    public String ComunicacionCteServ(int id) throws RemoteException, NotBoundException, MalformedURLException;
    public String ComunicacionServCte(int id) throws RemoteException, NotBoundException, MalformedURLException;
}
