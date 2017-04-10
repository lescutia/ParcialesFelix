/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResoruceUpdate;

import FileTransfer.CRemoteServiceIMP;
import Global.CGlobals;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

/**
 *
 * @author gamaa
 */
public class Updater {
    
    public void startClientDaemon()
    {
        try{
            if ( System.getSecurityManager() == null )
                System.setProperty( "java.security.policy", "security.policy" );
            Registry registry = LocateRegistry.getRegistry( "" );
        }
        catch(RemoteException e){
            e.getStackTrace();
        }
    }
    
    public void startServerDaemon(){
        if ( System.getSecurityManager() == null )
            System.setProperty( "java.security.policy", "security.policy" );
        try{
            LocateRegistry.createRegistry( CGlobals.m_iRemoteObjectPort );
            CRemoteServiceIMP rsIMPObj = new CRemoteServiceIMP();
            Naming.rebind( "//"+CGlobals.m_strLocalHost+ ":" + CGlobals.m_iRemoteObjectPort + "/UpdateServer", rsIMPObj );
        }
        catch(RemoteException e){
            e.getStackTrace();
        }
        catch(MalformedURLException e){
            e.getStackTrace();
        }
    }
    
}
