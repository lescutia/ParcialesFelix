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
    static ArrayList<Thread> m_lstThreads = new ArrayList<>();;
    
    public static void StartThread( Thread in_thread, String in_strThreadName )
    {
        in_thread.setName( in_strThreadName );
        m_lstThreads.add( in_thread );
        in_thread.start();
    }
    
    
    public static void main( String args[] )
    {
        CGlobals.loadConfig();
        CGlobals.m_strLocalHost = NetworkUtils.getLocalIP();
        LeaderSearchGUI lsGUI = new LeaderSearchGUI();
        lsGUI.setVisible( true );

        CThreadManager.startThread( new ConnectionService().findLeaderThread(), "findLeader" );
        CThreadManager.waitForThread( "findLeader" );
        lsGUI.dispose();
        if ( CGlobals.m_strLeaderId.equals( "" ) )
        {
            System.out.println( "[Manager]: No leader found" );
            LeaderAlertGUI alert = new LeaderAlertGUI();
            alert.setVisible( true );
        }
        else
        {
            try
            {
                Thread checker = new RMIUtils().ResourceUpdateChecker();
                checker.start();
            }
            catch ( RemoteException e )
            {
                System.out.println( "RemoteException!!" );
            }
            MainGUI mainGUI = new MainGUI();
            LogInGUI loginGUI = new LogInGUI( mainGUI );
            loginGUI.setVisible( true );
            //MainGUI mainGUI = new MainGUI();
            //mainGUI.setVisible(true);
        }
    }

}
