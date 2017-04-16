/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceUpdate;

/**
 *
 * @author gamaa
 */
import Global.CGlobals;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Scanner;
import java.lang.InterruptedException;
import java.io.IOException;

public class FileListener
{

    public Thread fileListenerThread( )
    {
        String path = CGlobals.m_strSharedDirPath;
        Thread thread = new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    listen( path );
                }
                catch ( InterruptedException e )
                {
                    System.out.println( "Error: " + e.toString() );
                }
            }
        } );
        return thread;
    }

    public void listen( String path ) throws InterruptedException
    {
        Path myDir = Paths.get( path );
        boolean flag = true;
        Updater u = new Updater();
        
        while ( flag )
        {
            try
            {
                WatchService watcher = myDir.getFileSystem().newWatchService();
                myDir.register( watcher, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY );

                WatchKey watckKey = watcher.take();

                List<WatchEvent<?>> events = watckKey.pollEvents();
                for ( WatchEvent event : events )
                {
                    if( event.kind() == StandardWatchEventKinds.ENTRY_CREATE || 
                            event.kind() == StandardWatchEventKinds.ENTRY_DELETE || 
                            event.kind() == StandardWatchEventKinds.ENTRY_MODIFY )
                    {
                        u.sendTable();
                    }
                }

            }
            catch ( InterruptedException e )
            {
                System.out.println( "[Main/listen]: Interrupted" );
                flag = false;
            }
            catch ( IOException e )
            {
                System.out.println( "IOException in Listen: " + e.toString() );
            }
        }
    }

}
