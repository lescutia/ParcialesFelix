/**************************************************************************
	file:	 	NetworkUtils.java
	date:		2017/04/08 16:17	
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: This class if going to get the local IP address (The user 
        * IP address) based on the local IP address machine.
**************************************************************************/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
/**
 *
 * @author gamaa
 */
public class NetworkUtils {
    public static String getLocalIP(){
        try {
                for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = (NetworkInterface) en.nextElement();
                    for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                            String ipAddress=inetAddress.getHostAddress();
                            return ipAddress;
                        }
                    }
                }
            } catch (SocketException ex) {
                ex.getStackTrace();
            }
        return null; 
    }
}
