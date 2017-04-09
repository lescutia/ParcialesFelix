/**************************************************************************
	file:	 	Download.java
	date:		2017/04/08 17:17	
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Class to find in the network the actual leader in the LAN or the
        service provider.
**************************************************************************/
package FileTransfer;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class CCallBackIMP extends UnicastRemoteObject implements CCallback
{
    public CCallBackIMP() throws RemoteException{};
    
    @Override
    public void writeData( String inFileName , byte[] data, int fileLength ) throws RemoteException
    {
        
        try{
            File file = new File(inFileName);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write( data , 0 , data.length );
            fos.flush();
            fos.close();
        }
        catch(Exception e){
            e.getStackTrace();
        }
        
    }
    
}
