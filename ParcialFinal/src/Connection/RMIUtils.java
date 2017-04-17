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

    public Thread ResourceUpdateChecker() throws RemoteException
    {
        Thread thread = new Thread( new Runnable()
        {
            Registry r;
            ResourceUpdate ru;

            @Override
            public void run()
            {
                try
                {
                    while ( true )
                    {
                        if ( System.getSecurityManager() == null )
                        {
                            System.setProperty( "java.security.policy", "security.policy" );
                        }
                        r = LocateRegistry.getRegistry( CGlobals.m_strLeaderId );
                        ru = (ResourceUpdate) Naming.lookup( "//" + CGlobals.m_strLeaderId + ":" + CGlobals.m_iRemoteObjectPort + "/UpdateServer" );
                        Thread.sleep( 500 );
                    }

                }
                catch ( RemoteException e )
                {
                    System.out.println( "[ResourceUpdateChecker]: Exception " );
                    CThreadManager.stopAllThreads();
                    CGUIManager.disposeAll();
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
                catch ( InterruptedException e )
                {
                    e.printStackTrace();
                }

            }

        } );
        return thread;
    }
}
