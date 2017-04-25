/** ************************************************************************
    * file:	 	CFileService.java
    * date:		2017/04/08 17:38
    * author:		Luis Eduardo Villela Zavala; Xitlali Moran Soltero
    * Contact:          luisedo21@gmail.com
    *
    * brief: This class initializes the File Service using RMI. 
    * Then we can use a method to download the file for the client from 
    * another client (The client who received the request).
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

/**
 *
 * @author luise
 */
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

    /**
     *
     * @param sFileName is the name of the file that is going to be downloaded. 
     * @param m_sHostName is the name of the current host. 
     */
    public void downloadFile( String sFileName, String m_sHostName )
    {
        //This ArrayList make a copy of the current File List of every connected users. 
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
