import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.*;

public class ImplManejoDeArchivos extends UnicastRemoteObject implements IManejoDeArchivos 
{
	private String nombreLog;
	protected ImplManejoDeArchivos() throws RemoteException {
        super();
    }
    
    protected ImplManejoDeArchivos(String nombreLog) throws RemoteException {
        super();
        this.nombreLog = nombreLog;
    }

    public List obtenerListaArchivos(String dirURL) throws RemoteException
    {
    	File folder = new File(dirURL);
		File[] listOfFiles = folder.listFiles();
		List resources = new ArrayList();
		for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	        	resources.add(listOfFiles[i].getName());
	      	}
	    }
	    return resources;
    }
}