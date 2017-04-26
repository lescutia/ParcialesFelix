/**************************************************************************
	file:	 	ResourceUpdateIMP.java
	date:		2017/04/08 17:38
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Implementation of ResourceUpdate interface. 
        * This class is going to initialize a thread for the resource update. 
**************************************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceUpdate;

import Global.CGlobals;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author gamaa
 */

/*
    This is the main class of the project. Here are the basic and initial specs of the system.
*/
public class ResourceUpdateIMP extends UnicastRemoteObject implements ResourceUpdate
{

    private ArrayList<ArrayList<String>> table;

    public ResourceUpdateIMP() throws RemoteException
    {
        //table = new ArrayList<ArrayList<String>>();
    }

    /**
     *
     * @param owner of the file. 
     * @param list is the file list of the owner. 
     * @throws RemoteException
     */
    @Override
    public void update( String owner, ArrayList<String> list ) throws RemoteException
    {    }

    @Override
    public ArrayList<ArrayList<String>> getTable() throws RemoteException
    {
        return null;//table;
    }
    
    /**
     *
     * @param in_user
     * @param in_pass
     * @return false
     * @throws RemoteException
     */
    @Override
    public boolean checkUser(String in_user, String in_pass ) throws RemoteException
    {
        return false;//CGlobals.checkUser( in_user, in_pass );
    }
    
    int findIndex( String owner )
    {
        for ( ArrayList<String> element : table )
        {
            if ( element.get( 0 ).equals( owner ) )
            {
                return table.indexOf( element );
            }
        }
        return -1;
    }

}
