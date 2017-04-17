/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Connection.*;
import FileTransfer.*;
import Global.*;
import ResourceUpdate.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author gamaa
 */
public class MainManager
{
    
    public static void main( String args[] )
    {
        CGlobals.loadConfig();
        CGlobals.m_strLocalHost = NetworkUtils.getLocalIP();
        CGUIManager.addGUI( new LeaderSearchGUI(), "LeaderSearch" );
        CGUIManager.addGUI( new LeaderAlertGUI(), "LeaderAlert" );
        CGUIManager.addGUI( new MainGUI(), "Main" );
        CGUIManager.addGUI( new LogInGUI(), "LogIn" );
        
        startApplication( true );
    }
    
    static Thread m_RMIUtilsThread;
    
    public static void startApplication( boolean in_bFirstTime )
    {
        CGUIManager.display( "LeaderSearch" );
        
        CThreadManager.startThread( new ConnectionService().findLeaderThread(), "findLeader" );
        CThreadManager.waitForThread( "findLeader" );
        
        CGUIManager.dispose( "LeaderSearch" );
        
        if ( CGlobals.m_strLeaderId.equals( "" ) )
        {
            System.out.println( "[Manager]: No leader found" );
            CGUIManager.display( "LeaderAlert" );
        }
        else
        {
            try
            {
                if( m_RMIUtilsThread == null )
                    m_RMIUtilsThread = new RMIUtils().ResourceUpdateChecker();
                if( !m_RMIUtilsThread.isAlive() )
                    m_RMIUtilsThread.start();
            }
            catch ( RemoteException e )
            {
                System.out.println( "RemoteException!!" );
            }
            
            if( in_bFirstTime )
                CGUIManager.display( "LogIn" );
            else
                CGlobals.login();
        }
    }
}
