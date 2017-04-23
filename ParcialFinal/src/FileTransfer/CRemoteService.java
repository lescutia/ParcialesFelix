/**************************************************************************
	file:	 	CRemoteService.java
	date:		2017/04/08 17:38	
	author:		Luis Eduardo Villela Zavala; Xitlali Moran Soltero
	Contact:    	luisedo21@gmail.com

	brief: Contains the interface methods for remote service implementation.
**************************************************************************/
package FileTransfer;
import java.rmi.*;

public interface CRemoteService extends Remote
{
    /*
    *   @Param in_strFile is the name of the file wanted to download.
    *   @Return byte array with the data of the file.
    */
    public void requestFile( String in_strFile , FileTransfer.CCallback in_Ref) throws RemoteException;
    
    /*
    *   @Param in_strFile is the name of the file wanted to download.
    *   @Return the size of the file wanted to download.
    */
    public long getSize( String in_strFile ) throws RemoteException;
}
