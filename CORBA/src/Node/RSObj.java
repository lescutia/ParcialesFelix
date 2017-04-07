/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;
import dms.*;
import org.omg.CORBA.*;
import java.io.File;
import java.io.FileInputStream;
/**
 *
 * @author gamaa_000
 */
public class RSObj extends RemoteServerPOA{
  
    private ORB orb;
 
    public void setORB(ORB orb_val) {
      orb = orb_val; 
    }
    
    @Override
    public void GetFile(String fileName, RemoteClient rCRef){
        new Thread (new Runnable() {
	    
            @Override
            public void run(){
                try{
		    
		    System.out.println("[RSObj]: Sending "+fileName);
                    File file = new File(Globals.m_strSharedDirPath+fileName);			 
                    FileInputStream fis = new FileInputStream(file);	
		    double fileLength = file.length();
                    byte [] data = new byte[1024*1024];						
                    int dataLength = fis.read(data);
		    System.out.println("[RSObj]: File length: "+dataLength);
		    while( dataLength>0 ){
			rCRef.WriteFile(fileName, data, dataLength, fileLength);
			dataLength = fis.read(data);
		    }
		    
                }
                catch(Exception e){
		    System.out.println("[RSObj]: Exception");
		    e.getStackTrace();
		}
            }
        }).start();
        
    }
}
