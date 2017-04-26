/**************************************************************************
	file:	 	ResourceUpdateIMP.java
	date:		2017/04/08 16:17	
	author:		Luis Eduardo Villela Zavala; Xitlali Moran Soltero
	Contact:    	luisedo21@gmail.com

	brief: Class to implement the methods of ResourceUpdate interface.
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
public class ResourceUpdateIMP extends UnicastRemoteObject implements ResourceUpdate
{
    private static ResourceUpdateIMP m_cachedResourceIMP = null;
    
    private ArrayList<ArrayList<String>> m_OpenTable;
    private ArrayList<ArrayList<String>> m_CloseTable;
    
    
    public static ResourceUpdateIMP getInstance() throws RemoteException
    {
        if( m_cachedResourceIMP == null )
            m_cachedResourceIMP = new ResourceUpdateIMP();
        return m_cachedResourceIMP;
    }
    
    /*
    public ResourceUpdateIMP() throws RemoteException
    {
        System.out.println( "!!!!!!!!!!!!!!!!!!" );
        m_OpenTable = new ArrayList<ArrayList<String>>();
    }
  */
    private ResourceUpdateIMP() throws RemoteException
    {
        m_OpenTable = new ArrayList<ArrayList<String>>();
        m_CloseTable = new ArrayList<ArrayList<String>>();
        
    }

    @Override
    public void update( String owner, ArrayList<String> list ) throws RemoteException
    {
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add( owner );
        System.out.println( "\n[ResourceUpdate]: Elements in " + owner );
        for ( String element : list )
        {
            System.out.println( "\t-> " + element );
            tmp.add( element );
        }
        int i = findIndex( owner );
        if ( i == -1 )
        {
            m_OpenTable.add( tmp );
        }
        else
        {
            m_OpenTable.remove( i );
            m_OpenTable.add( i, tmp );
        }
    }

    @Override
    public ArrayList<ArrayList<String>> getTable() throws RemoteException
    {
        System.out.println("Actual table: "+m_OpenTable);
        return m_OpenTable;
    }
    
    @Override
    public boolean checkUser(String in_user, String in_pass ) throws RemoteException
    {
        return CGlobals.checkUser( in_user, in_pass );
    }
            
    int findIndex( String owner )
    {
        for ( ArrayList<String> element : m_OpenTable )
        {
            if ( element.get( 0 ).equals( owner ) )
            {
                return m_OpenTable.indexOf( element );
            }
        }
        return -1;
    }
    
    public Thread ServerDaemon()
    {

        Thread thread = new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                if ( System.getSecurityManager() == null )
                {
                    System.setProperty( "java.security.policy", "security.policy" );
                }
                try
                {
                    LocateRegistry.createRegistry( CGlobals.m_iRemoteObjectPort );
                    //ResourceUpdateIMP rsIMPObj = new ResourceUpdateIMP();
                    Naming.rebind( "//" + CGlobals.m_strLocalHost + ":" + CGlobals.m_iRemoteObjectPort + "/UpdateServer", m_cachedResourceIMP );
                    System.out.println( "[ResourceUpdate]: UpdateServer ready" );
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
        } );

        return thread;
    }
    
    public void updateTable( ArrayList<String> in_aAliveNodes )
    {
        ArrayList<ArrayList<String>> tmpList = new ArrayList<>();
        for( ArrayList<String> element : m_OpenTable )
        {
            String owner = element.get( 0 );
            if( in_aAliveNodes.indexOf( owner ) > -1)
                tmpList.add(element);
            else
                m_CloseTable.add(element);
        }
        m_OpenTable.clear();
        if(tmpList.size()>0)
            m_OpenTable = new ArrayList(tmpList);
        System.out.println("[ResourceUpdateIMP]: OpenTable: "+m_OpenTable);
    }
}
