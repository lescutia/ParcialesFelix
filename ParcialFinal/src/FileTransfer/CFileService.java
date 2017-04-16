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
                System.setProperty( "java.security.policy", "security.policy" );
            
            LocateRegistry.createRegistry( CGlobals.m_iRemoteObjectPort );
            CRemoteServiceIMP rsIMPObj = new CRemoteServiceIMP();
            Naming.rebind( "//"+CGlobals.m_strLocalHost+ ":" + CGlobals.m_iRemoteObjectPort + "/FileServer", rsIMPObj );
            System.out.println( "[CFileService]: FileService ready." );

        } catch ( RemoteException e )
        {
            e.printStackTrace();
        }
        catch(MalformedURLException e )
        {
            e.getStackTrace();
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
            
        } catch ( RemoteException e )
        {
            e.printStackTrace();
        }
        catch(MalformedURLException e ){
            e.printStackTrace();
        }
        catch(NotBoundException e){
            e.printStackTrace();
        }

    }

}
