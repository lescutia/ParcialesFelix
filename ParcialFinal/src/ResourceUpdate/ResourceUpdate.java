/**************************************************************************
	file:	 	ResourceUpdate.java
	date:		2017/04/08 17:38
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Interface for the ResourceUpdateIMP.java class. 
**************************************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceUpdate;
import java.rmi.*;
import java.util.ArrayList;

/**
 *
 * @author gamaa
 */
public interface ResourceUpdate extends Remote{
    
    /*
    *   @param fileList is the list of available resources.
    */
    public void update(String owner, ArrayList<String> fileList) throws RemoteException;
    public ArrayList<ArrayList<String>> getTable() throws RemoteException;
    public boolean checkUser( String in_user, String in_pass ) throws RemoteException;
}
