/**************************************************************************
	file:	 	ResourceUpdate.java
	date:		2017/04/08 16:17	
	author:		Luis Eduardo Villela Zavala; Xitlali Moran Soltero
	Contact:    	luisedo21@gmail.com

	brief: Class to declare the RMI interface for the resource updater service.
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
public interface ResourceUpdate extends Remote
{    
    /*
    *   @Param fileList is the list of available resources.
    */

    /**
     *
     * @param owner of the fileList
     * @param fileList contains the full list of the owner's files. 
     * @throws RemoteException
     */

    public void update(String owner, ArrayList<String> fileList) throws RemoteException;

    /**
     *
     * @return the ArrayList with the file list. 
     * @throws RemoteException
     */
    public ArrayList<ArrayList<String>> getTable() throws RemoteException;

    /**
     * This method permits to detect the authenticity of an user. 
     * @param in_user is the username. 
     * @param in_pass is the password of that user. 
     * @return true or false, depending of the result. 
     * @throws RemoteException
     */
    public boolean checkUser( String in_user, String in_pass ) throws RemoteException;
}
