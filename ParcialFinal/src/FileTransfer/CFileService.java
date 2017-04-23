/** ************************************************************************
 * file:	 	FileService.java
 * date:		2017/04/08 17:38
 * author:		Luis Escutia, Gamaliel Palomo
 * Contact:    	escutialuis93@gmail.com
 *
 * brief: Contains methods for managing the file transfers.
 ************************************************************************* */
package FileTransfer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import Global.*;
import ResourceUpdate.Updater;
import java.util.ArrayList;
//import java.rmi.server.ExportException;
//import java.util.logging.Level;
//import java.util.logging.Logger;


public class CFileService
{
    public CFileService()
    {
        
    }
    
    public void initialize( )
    {
        try
        {
            LocateRegistry.createRegistry( CGlobals.m_iRemoteObjectPort + 1 );
        }
        catch ( RemoteException ex )
        {
            System.out.println( "[CFileService]: Registry already exists." );
        }
    }
    
    public void startFileService()
    {
        try
        {
            if ( System.getSecurityManager() == null )
                System.setProperty( "java.security.policy", "security.policy" );
            
            CRemoteServiceIMP rsIMPObj = new CRemoteServiceIMP();
            Naming.rebind( "//"+CGlobals.m_strLocalHost+ ":" + (CGlobals.m_iRemoteObjectPort+1) + "/FileServer", rsIMPObj );
            System.out.println( "[CFileService]: FileService ready." );

        }
        catch ( RemoteException|MalformedURLException e ) { }
    }

    public void downloadFile( String sFileName, String m_sHostName )
    {
        
        ArrayList<String> fileList = Updater.getFileList();
        for(String element: fileList){
            if(element.equals( sFileName) )
                return;
        }
        
        try
        {

            if ( System.getSecurityManager() == null )
            {
                System.setProperty( "java.security.policy", "security.policy" );
            }

            //Registry registry = LocateRegistry.getRegistry( m_sHostName );
            CRemoteService roObj = null;
            roObj = (CRemoteService) Naming.lookup( "//" + m_sHostName + ":" + (CGlobals.m_iRemoteObjectPort+1) + "/FileServer" );
            CCallBackIMP rObj = new CCallBackIMP();

            roObj.requestFile( sFileName, rObj );
            
        } catch ( RemoteException|MalformedURLException|NotBoundException e ) {
            e.printStackTrace();
        }

    }

}
