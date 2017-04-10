/** ************************************************************************
 * file:	 	FileService.java
 * date:		2017/04/08 17:38
 * author:		Luis Escutia, Gamaliel Palomo
 * Contact:    	escutialuis93@gmail.com
 *
 * brief: Contains method implementations of the remote service for
 * managing the file transfers with RMI.
 ************************************************************************* */
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
                        in_Ref.writeData( in_strFileName, data, fileSize);
                        dataLength = fis.read(data);
                    }
                    
                    
                } catch ( Exception e )
                {
                    e.getStackTrace();
                }
            }

        } ).start();

    }

    @Override
    public long getSize( String in_strFile ) throws RemoteException
    {
        File f1 = new File( CGlobals.m_strSharedDirPath + in_strFile );
        return f1.length();
    }
}
