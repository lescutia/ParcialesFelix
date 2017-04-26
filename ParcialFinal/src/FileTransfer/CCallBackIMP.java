/**************************************************************************
	file:	 	CCallBackIMP.java
	date:		2017/04/08 17:17
	author:		Luis Eduardo Villela Zavala, Xitlali Moran Soltero
	Contact:    	luisedo21@gmail.com

	brief: This class is going to receive the requested files for the 
        * client who has requested the specified file. 
        * Implementation of CCallback Interface.
**************************************************************************/
package FileTransfer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import Global.CGlobals;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author luise
 */
public class CCallBackIMP extends UnicastRemoteObject implements CCallback
{
    public CCallBackIMP() throws RemoteException{};
    
    /**
     *
     * @param inFileName is the name of the file that is going to be written.
     * @param data the file content to be written
     * @param dataLength the length of the byte array incoming
     * @param fileLength is the length of the new file
     * @throws RemoteException
    */
    @Override
    public void writeData( String inFileName , byte[] data, int dataLength, long fileLength ) throws RemoteException
    {
        
        try{
            File dir = new File(Global.CGlobals.m_strDownloadPath);
            File file = new File(dir,inFileName);
            
            int progress = (int)(((float)file.length()/fileLength)*100);
            file.createNewFile();
            System.out.println( "[Download]: "+ inFileName+" " + progress +"%");
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write( data , 0 , dataLength );
            fos.flush();
            fos.close();
        }
        catch(Exception e){
            e.getStackTrace();
        }
        
    }
    /*
        This method is the responsible to carry a register into an specified log. 
    */
    @Override
    public void writeLog(String owner, String fileName) throws RemoteException
    {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
            BufferedWriter bw = new BufferedWriter(new FileWriter("transfers.log", true));
            String line = fileName + " from " + CGlobals.m_strLocalHost + " to "  + owner + " at " + dateFormat.format( date ) ;  
            bw.write(line);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ioe) {
           ioe.printStackTrace();
        }
    }
    
    
}
