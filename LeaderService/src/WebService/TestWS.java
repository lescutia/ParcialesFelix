/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

//import ClientWS.WSTest;
//import ClientWS.WSTest_Service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class TestWS
{
    private static int workload = 12;
    /*
    static String m_strUrl = "jdbc:mysql://localhost:3306/SystemDB";
    static String m_strUsername = "root";
    static String m_strPassword = "";
    */
    
    
    public static String hashPassword( String password_plaintext )
    {
        String salt = BCrypt.gensalt( workload );
        String hashed_password = BCrypt.hashpw( password_plaintext, salt );

        return (hashed_password);
    }
    
    public static void main( String[] args ) throws Exception
    {
        testHash();
    }
    
    public static void testHash()
    {
        try
        {
            Map<String, Object> params = new LinkedHashMap();
            params.put( "in_username", "root" );
            params.put( "in_password", hashPassword("root") );
            /*the root must specify whre's the CI_Controller with the name of the class
             and the last root is the function wanted to call, in case is blank, the 
            default function is index (example: http://localhost/webservice/Serverws/testFunction ).
            */
            String result = HttpConnection.post( "http://localhost/webservice/Serverws/", params );
            System.out.println( result );

        }
        catch ( UnsupportedEncodingException ex )
        {
            Logger.getLogger(TestWS.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( IOException ex )
        {
            Logger.getLogger(TestWS.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }
}
