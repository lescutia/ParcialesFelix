package middlewere;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author luise
 */
public interface IAutenticacion extends Remote
{
    public int autenticarRepositorio(String Nombre) throws RemoteException;
    public int autenticarCliente(String Nombre, int ID) throws RemoteException;
}
