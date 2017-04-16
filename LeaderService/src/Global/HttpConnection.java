/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Luis
 */
public class HttpConnection
{

    private static byte[] getPostData( Map<String, Object> params ) throws UnsupportedEncodingException
    {

        StringBuilder postData = new StringBuilder();
        for ( Map.Entry<String, Object> param : params.entrySet() )
        {
            if ( postData.length() != 0 )
            {
                postData.append( '&' );
            }
            postData.append( URLEncoder.encode( param.getKey(), "UTF-8" ) );
            postData.append( '=' );
            postData.append( URLEncoder.encode( String.valueOf( param.getValue() ), "UTF-8" ) );
        }

        byte[] postDataBytes = postData.toString().getBytes( "UTF-8" );

        return postDataBytes;
    }

    public static String post( String host, Map<String, Object> params ) throws MalformedURLException, UnsupportedEncodingException, IOException
    {
        URL url = new URL( host );

        byte[] postDataBytes = getPostData( params );

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
        conn.setRequestProperty( "Content-Length", String.valueOf( postDataBytes.length ) );
        conn.setDoOutput( true );
        conn.getOutputStream().write( postDataBytes );

        Reader in = new BufferedReader( new InputStreamReader( conn.getInputStream(), "UTF-8" ) );
        StringBuilder sb = new StringBuilder();

        for ( int c; (c = in.read()) >= 0; )
        {
            sb.append( (char) c );
        }

        return sb.toString();
    }

    public static void get( String url ) throws IOException
    {

        URL obj = new URL( url );
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod( "GET" );
        con.setRequestProperty( "User-Agent", "Mozilla/5.0" );
        int responseCode = con.getResponseCode();

        System.out.println( "GET Response Code :: " + responseCode );

        if ( responseCode == HttpURLConnection.HTTP_OK )
        { // success
            BufferedReader in = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ( (inputLine = in.readLine()) != null )
            {
                response.append( inputLine );
            }
            in.close();

            // print result
            System.out.println( response.toString() );
        }
        else
        {
            System.out.println( "GET request not worked" );
        }

    }

    public static void downloadFile( String fileURL, String saveDir, Map<String, Object> params ) throws IOException
    {

        URL url = new URL( fileURL );

        byte[] postDataBytes = getPostData( params );

        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

        httpConn.setRequestMethod( "POST" );
        httpConn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
        httpConn.setRequestProperty( "Content-Length", String.valueOf( postDataBytes.length ) );
        httpConn.setDoOutput( true );
        httpConn.getOutputStream().write( postDataBytes );

        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if ( responseCode == HttpURLConnection.HTTP_OK )
        {
            String fileName = "";
            String disposition = httpConn.getHeaderField( "Content-Disposition" );
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if ( disposition != null )
            {
                // extracts file name from header field
                int index = disposition.indexOf( "filename=" );
                if ( index > 0 )
                {
                    fileName = disposition.substring( index + 10,
                            disposition.length() - 1 );
                }
            }
            else
            {
                // extracts file name from URL
                fileName = fileURL.substring( fileURL.lastIndexOf( "/" ) + 1,
                        fileURL.length() );
            }

            System.out.println( "Content-Type = " + contentType );
            System.out.println( "Content-Disposition = " + disposition );
            System.out.println( "Content-Length = " + contentLength );
            System.out.println( "fileName = " + fileName );

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream( saveFilePath );

            int bytesRead = -1;
            byte[] buffer = new byte[1024 * 1024];
            while ( (bytesRead = inputStream.read( buffer )) != -1 )
            {
                outputStream.write( buffer, 0, bytesRead );
            }

            outputStream.close();
            inputStream.close();

            System.out.println( "File downloaded" );
        }
        else
        {
            System.out.println( "No file to download. Server replied HTTP code: " + responseCode );
        }
        httpConn.disconnect();
    }

}
