/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;
import dms.*;
import java.io.File;
import java.io.FileOutputStream;
/**
 *
 * @author gamaa_000
 */
public class RCObj extends RemoteClientPOA{
    
    @Override
    public void WriteFile( String fileName , byte[] data , int length ){
        
	try{
	    
	    File file = new File(fileName);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file,true);
            fos.write(data,0,length);
            fos.flush();
            fos.close();
	    
	    System.out.println("[RCObj]: Writing data");
	    
	    if(data.length > length)
                System.out.println("[RCObj]: FIN DEL ARCHIVO");
	    
	}
	catch(Exception e){
	    e.getStackTrace();
	}
	
    }
    
}
