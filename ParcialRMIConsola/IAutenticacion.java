import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IAutenticacion extends Remote
{
    public String autenticarRepositorio(String Nombre, List Repositorio) throws RemoteException;
    public Message DescargarArchivo(String NombreArchivo) throws RemoteException;
    //public int autenticarCliente(String Nombre, int ID) throws RemoteException;
}
