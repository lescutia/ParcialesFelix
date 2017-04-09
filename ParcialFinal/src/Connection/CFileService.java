/** ************************************************************************
 * file:	 	FileService.java
 * date:		2017/04/08 17:38
 * author:		Luis Escutia, Gamaliel Palomo
 * Contact:    	escutialuis93@gmail.com
 *
 * brief: Contains methods for managing the file transfers.
 ************************************************************************* */
package Connection;

import FileTransfer.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import Global.CGlobals;


public class CFileService
{

    public CFileService()
    {

    }

    public void startFileService()
    {
        try
        {

            if ( System.getSecurityManager() == null )
            {
                System.setProperty( "java.security.policy", "security.policy" );
            }
            LocateRegistry.createRegistry( CGlobals.m_iRemoteObjectPort );
            CRemoteServiceIMP rsIMPObj = new CRemoteServiceIMP();
            Naming.rebind( "//localhost:" + CGlobals.m_iRemoteObjectPort + "/FileServer", rsIMPObj );
            System.out.println( "[CFileService]: FileService ready." );

        } catch ( Exception e )
        {
            System.out.println( "[CFileService]: Valio verga" );
            e.printStackTrace();
        }
    }

    public void downloadFile( String sFileName, String m_sHostName )
    {
        try
        {

            if ( System.getSecurityManager() == null )
            {
                System.setProperty( "java.security.policy", "security.policy" );
            }

            Registry registry = LocateRegistry.getRegistry( m_sHostName );
            CRemoteService roObj = null;
            roObj = (CRemoteService) Naming.lookup( "//" + m_sHostName + ":" + CGlobals.m_iRemoteObjectPort + "/FileServer" );
            CCallBackIMP rObj = new CCallBackIMP();

            roObj.requestFile( sFileName, rObj );
            
        } catch ( Exception e )
        {
            System.out.println( "[CFileService]: Valio verga" );
            e.printStackTrace();
        }

    }

}
