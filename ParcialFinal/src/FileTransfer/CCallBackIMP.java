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
import Global.CDownloadManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import Global.CGlobals;

/**
 *
 * @author luise
 */
public class CCallBackIMP extends UnicastRemoteObject implements CCallback
{
    public CCallBackIMP() throws RemoteException{};
    
    /**
     *
     * @param inFileName
     * @param data
     * @param fileLength
     * @throws RemoteException
    */
    @Override
    public void writeData( String inFileName , byte[] data, int dataLength,long fileLength ) throws RemoteException
    {
        
        try{
            System.out.println("File size ["+inFileName+"]:"+fileLength);
            writeLog(inFileName,CGlobals.m_strLocalHost);
            File dir = new File(Global.CGlobals.m_strDownloadPath);
            File file = new File(dir,inFileName);
            long progress = file.length()/fileLength;
            CDownloadManager.setProgress( inFileName, progress );
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write( data , 0 , dataLength );
            fos.flush();
            fos.close();
        }
        catch(Exception e){
            e.getStackTrace();
        }
        
    }
    
    static void writeLog(String fileName, String requester){
        try {
         // APPEND MODE SET HERE
         BufferedWriter bw = new BufferedWriter(new FileWriter("transfers.log", true));
         String line = fileName + "to" + requester;  
	 bw.write(line);
	 bw.newLine();
	 bw.flush();
        } catch (IOException ioe) {
           ioe.printStackTrace();
        }
    }
    
}
