/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import WebService.TestWS;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CGlobals 
{
    
    /********************************************************
                    Network Variables
    ********************************************************/
    /*< ID of the group to which the multicast will be sent. */
    public static String m_strGroupId   = "224.0.0.5";
    /*< ID of the leader of the group. */
    public static String m_strLeaderId  = "";
    /*< Local host IP address. */
    public static String m_strLocalHost = "";  
    
    public static boolean m_bDebugCS = true;
    
    /********************************************************
                    Port Variables
    ********************************************************/
    /*< Port to find leader and leader listener to attend new nodes. */
    public static int m_iPortLeaderListener     = 10000;
    /*< Time in miliseconds to wait for leader reply. */
    public static int m_iRemoteObjectPort 	= 1500;
    
    public static boolean checkUser( String in_username, String in_password )
    {
        try
        {
            Map<String, Object> params = new LinkedHashMap();
            params.put( "in_username", in_username );
            params.put( "in_password", in_password );
            /*the root must specify whre's the CI_Controller with the name of the class
             and the last root is the function wanted to call, in case is blank, the 
            default function is index (example: http://localhost/webservice/Serverws/testFunction ).
            */
            String result = HttpConnection.post( "http://localhost/webservice/Serverws/", params );
            
            if( result.equals( "true" ) )
                return true;
        }
        catch ( UnsupportedEncodingException ex )
        {
            Logger.getLogger(TestWS.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( IOException ex )
        {
            Logger.getLogger(TestWS.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return false;
    }
}
