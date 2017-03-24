package servidor;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import middlewere.IManejoDeArchivos;

/**
 *
 * @author luise
 */
public class ImpManejoArchivos extends UnicastRemoteObject implements IManejoDeArchivos
{
    protected ImpManejoArchivos() throws RemoteException {
          super();
     }
    
    public String ComunicacionCteServ(int id) throws RemoteException, NotBoundException, MalformedURLException
    {
        return "";
    }
    public String ComunicacionServCte(int id) throws RemoteException, NotBoundException, MalformedURLException
    {
        return "";
    }
}
