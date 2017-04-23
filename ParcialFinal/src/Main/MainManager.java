/**************************************************************************
	file:	 	MainManager.java
	date:		2017/04/08 17:38
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: The main class of the project. Initializes the system and 
        * shows the graphic user interface (GUI). 
**************************************************************************/
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
        CGUIManager.addGUI( new LogInErrorGUI(), "LogInError" );
        CGUIManager.addGUI( new LeaderAlertGUI(), "LeaderAlert" );
        CGUIManager.addGUI( new MainGUI(), "Main" );
        CGUIManager.addGUI( new LogInGUI(), "LogIn" );
        
        
        startApp();
        //CGUIManager.disposeAll();
    }
    
    public static void startApp()
    {
        CGUIManager.display( "LeaderSearch" );
        CThreadManager.startThread( new ConnectionService().findLeaderThread(), "findLeader" );
        CThreadManager.waitForThread( "findLeader" );
        CGUIManager.hideGUI( "LeaderSearch" );
        
        if ( CGlobals.m_strLeaderId.equals( "" ) )
        {
            System.out.println( "[Manager]: No leader found" );
            CGUIManager.display( "LeaderAlert" );
        }
        else
        {
            try
            {
                //CThreadManager.startThread( in_thread, in_strThreadName );
                Thread checker = new RMIUtils().ResourceUpdateChecker();
                checker.start();
            }
            catch ( RemoteException e ) { }
            
            CGUIManager.display( "LogIn" );
        }
    }
}
