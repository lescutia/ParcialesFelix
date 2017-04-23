/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author gamaa_000
 */
public class CDownloadManager
{
    static Map<String, Long> dList ;
    public void CDownloader(){
        dList = new LinkedHashMap();
    }
    
    public static void setProgress(String dID, long progress){
        if(dList.containsKey( dID ))
            dList.put( dID , progress );
    }
    
    public static void finishDownload(String dID){
        if(dList.containsKey( dID ))
            dList.remove( dID );
    }
    
    public static long getProgress(String dID){
        if(dList.containsKey( dID ))
            return dList.get( dID );
        return -1;
    }
    
}
