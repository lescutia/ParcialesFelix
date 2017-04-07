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
public class RCObj extends RemoteClientPOA
{

    @Override
    public void WriteFile( String fileName, byte[] data, int length, double fileLength )
    {

	try
	{

	    File file = new File(Globals.m_strDownloadPath + fileName);
	    file.createNewFile();
	    double actualFileLength = file.length();
	    FileOutputStream fos = new FileOutputStream(file, true);
	    fos.write(data, 0, length);
	    fos.flush();
	    fos.close();
	    double progress = (actualFileLength/fileLength)*100;
	    System.out.println("[RCObj]: Writing data "+"("+Math.round(progress-100)+"%)");

	    if ( data.length > length )
	    {
                System.out.println("[RCObj]: Writing data (100%)");
		System.out.println("[RCObj]: FIN DEL ARCHIVO");
	    }

	}
	catch ( Exception e )
	{
	    e.getStackTrace();
	}
    }
}
