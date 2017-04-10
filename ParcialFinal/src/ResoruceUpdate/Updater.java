/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResoruceUpdate;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
        catch(Exception e){
            e.getStackTrace();
        }
    }
    
}
