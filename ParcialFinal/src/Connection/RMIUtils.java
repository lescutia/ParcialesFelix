/**************************************************************************
	file:	 	NetworkUtils.java
	date:		2017/04/08 16:17	
	author:		Luis Eduardo Villela Zavala, Xitlali Moran Soltero
	Contact:    	luisedo21@gmail.com

	brief: This will update the checker of tyhe files through an RMI Naming only if there is a leader online. 
        If not, the system will show the LeaderAlert user interface and is going to change the 
        LeaderId to empty. 
        @Return the thread created.
**************************************************************************/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Global.CGUIManager;
import ResourceUpdate.ResourceUpdate;
import Global.CGlobals;
import Global.CThreadManager;
import Main.MainManager;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

/**
 *
 * @author gamaa
 */
public class RMIUtils
{

    /**
     *
     * @return the thread created. 
     * @throws RemoteException
    */
    public Thread ResourceUpdateChecker() throws RemoteException
    {
        Thread thread = new Thread( new Runnable()
        {
            Registry r;
            ResourceUpdate ru;

            @Override
            public void run()
            {
                boolean bExecute = true;
                try
                {
                    while ( bExecute )
                    {
                        if ( System.getSecurityManager() == null )
                        {
                            System.setProperty( "java.security.policy", "security.policy" );
                        }
                        //This variable is for the registry and it needs the LeaderID to initialize it. 
                        r = LocateRegistry.getRegistry( CGlobals.m_strLeaderId );
                        //This variable saves the RMI URL to do the connection. 
                        ru = (ResourceUpdate) Naming.lookup( "//" + CGlobals.m_strLeaderId + ":" + CGlobals.m_iRemoteObjectPort + "/UpdateServer" );
                        Thread.sleep( 500 );
                    }
                }
                catch ( RemoteException e )
                {
                    
                    bExecute = false;
                    System.out.println( "[ResourceUpdateChecker]: Exception " );
                    CThreadManager.stopAllThreads();
                    CGUIManager.hideAllGUIs();
                    CGUIManager.display("LeaderAlert");
                    CGlobals.m_strLeaderId = "";
                    
                    //MainManager.startApplication( false );
                    //e.printStackTrace();
                }
                catch ( MalformedURLException e )
                {
                    e.printStackTrace();
                }
                catch ( NotBoundException e )
                {
                    e.printStackTrace();
                }
                catch ( InterruptedException e )
                {
                    bExecute = false;
                    e.printStackTrace();
                }
            }

        });
        return thread;
    }
}
