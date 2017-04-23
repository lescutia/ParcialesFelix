/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceUpdate;

import FileTransfer.CRemoteServiceIMP;
import Global.CGlobals;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.io.File;
import java.rmi.NotBoundException;


public class Updater
{
    public void startServerDaemon()
    {
        if ( System.getSecurityManager() == null )
        {
            System.setProperty( "java.security.policy", "security.policy" );
        }
        try
        {
            LocateRegistry.createRegistry( CGlobals.m_iRemoteObjectPort );
            CRemoteServiceIMP rsIMPObj = new CRemoteServiceIMP();
            Naming.rebind( "//" + CGlobals.m_strLocalHost + ":" + CGlobals.m_iRemoteObjectPort + "/UpdateServer", rsIMPObj );
        }
        catch ( RemoteException e )
        {
            e.getStackTrace();
        }
        catch ( MalformedURLException e )
        {
            e.getStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> getResourceTable()
    {
        try
        {

            if ( System.getSecurityManager() == null )
            {
                System.setProperty( "java.security.policy", "security.policy" );
            }

            Registry registry = LocateRegistry.getRegistry( CGlobals.m_strLeaderId );
            ResourceUpdate ru = null;
            ru = (ResourceUpdate) Naming.lookup( "//" + CGlobals.m_strLeaderId + ":" + CGlobals.m_iRemoteObjectPort + "/UpdateServer" );
            return ru.getTable();

        }
        catch ( RemoteException e )
        {
            System.out.println( "[Updater/getResourceTable]: RemoteException" );
            e.printStackTrace();
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch ( NotBoundException e )
        {
            e.printStackTrace();
        }
        return new ArrayList<ArrayList<String>>();
    }

    public void sendTable()
    {
        try
        {

            if ( System.getSecurityManager() == null )
            {
                System.setProperty( "java.security.policy", "security.policy" );
            }

            Registry registry = LocateRegistry.getRegistry( CGlobals.m_strLeaderId );
            ResourceUpdate ru = null;
            ru = (ResourceUpdate) Naming.lookup( "//" + CGlobals.m_strLeaderId + ":" + CGlobals.m_iRemoteObjectPort + "/UpdateServer" );
            ArrayList<String> fileList = getFileList();
            ru.update( CGlobals.m_strLocalHost, fileList );

        }
        catch ( RemoteException e )
        {
            System.out.println( "[Updater/sendTable]: RemoteException" );
            e.printStackTrace();
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch ( NotBoundException e )
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getFileList()
    {
        File folder = new File( CGlobals.m_strSharedDirPath );
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> resources = new ArrayList<String>();
        for ( File element : listOfFiles )
        {
            if ( element.isFile() )
            {
                resources.add( element.getName() );
            }
        }
        return resources;
    }

}
