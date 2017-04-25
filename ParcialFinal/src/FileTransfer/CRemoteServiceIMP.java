/**************************************************************************
	file:	 	CRemoteServiceIMP.java
	date:		2017/04/08 17:38	
	author:		Luis Eduardo Villela Zavala; Xitlali Moran Soltero
	Contact:    	luisedo21@gmail.com

	brief: Contains the implementation of CRemoteService.java interface.
**************************************************************************/
package FileTransfer;

import Global.CGlobals;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.io.File;
import java.io.FileInputStream;

public class CRemoteServiceIMP extends UnicastRemoteObject implements CRemoteService
{

    public CRemoteServiceIMP() throws RemoteException
    {
    }
    
    /**
     *
     * This method obtains the file requested by the user from the owner. 
     * Uses FileInputStream to get the file and make a copy for the client who requested the file. 
     * @param in_strFileName is the name of the requested file. 
     * @param in_Ref is the callback to make the file transfer.
     * @throws RemoteException
     */
    @Override
    public void requestFile( String in_strFileName, FileTransfer.CCallback in_Ref ) throws RemoteException
    {

        new Thread( new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    System.out.println("Request received");
                    File dir = new File(CGlobals.m_strSharedDirPath);
                    File file = new File( dir ,in_strFileName );
                    FileInputStream fis = new FileInputStream(file);
                    long fileSize = file.length();
                    System.out.println("Size ["+in_strFileName+"]: "+fileSize);
                    byte []data = new byte[1024*1024];
                    int dataLength = fis.read(data);
                    while(dataLength>0){
                        in_Ref.writeData( in_strFileName, data, dataLength,fileSize);
                        dataLength = fis.read(data);
                    }
                    
                    
                } catch ( Exception e )
                {
                    e.getStackTrace();
                }
            }

        } ).start();

    }

    /**
     *
     * @param in_strFile is the name of the file and then we are aoing to get the file size. 
     * @return the file size
     * @throws RemoteException
     */
    @Override
    public long getSize( String in_strFile ) throws RemoteException
    {
        File f1 = new File( CGlobals.m_strSharedDirPath + in_strFile );
        return f1.length();
    }
}
