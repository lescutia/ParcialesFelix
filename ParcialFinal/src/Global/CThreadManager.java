/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author gamaa
 */
public class CThreadManager 
{
    static Map<String, Thread> m_mapThreads = new LinkedHashMap();
    
    public static void startThread( Thread in_thread, String in_strThreadName )
    {
        if( !m_mapThreads.containsKey( in_strThreadName ) )
        {
            in_thread.setName( in_strThreadName );
            m_mapThreads.put(in_strThreadName, in_thread);
            in_thread.start();
            if( CGlobals.m_bDebugThreadMngr )
                System.out.println( "[CThreadManager]: " + in_strThreadName + " Thread started." );
        }
        else if( CGlobals.m_bDebugThreadMngr )
            System.out.println( "[CThreadManager]: Thread " + in_strThreadName + " already exists." );
    }
    

    public static void stopThread( String in_strThreadName )
    {
        if( m_mapThreads.containsKey( in_strThreadName ) )
        {
            Thread thread = m_mapThreads.get( in_strThreadName );
            thread.interrupt();
            m_mapThreads.remove( in_strThreadName );
            if( CGlobals.m_bDebugThreadMngr )
                System.out.println( "[CThreadManager]: " + in_strThreadName + " Thread stoped." );
        }
        else if( CGlobals.m_bDebugThreadMngr )
            System.out.println( "[CThreadManager]: Thread " + in_strThreadName + " doesn't exists." );
    }
    
    public static Thread getThread( String in_strThreadName )
    {
        if( m_mapThreads.containsKey( in_strThreadName ) )
            return m_mapThreads.get( in_strThreadName );
        return null;
    }
    
    public static boolean removeThread( String in_strThreadName )
    {
        if( m_mapThreads.containsKey( in_strThreadName ) )
        {
            m_mapThreads.remove( in_strThreadName );
            if( CGlobals.m_bDebugThreadMngr )
                System.out.println( "[CThreadManager]: " + in_strThreadName + " Thread removed." );
            return true;
        }
        return false;
    }
    
    public static void waitForThread( String in_strThreadName )
    {
        if( m_mapThreads.containsKey( in_strThreadName ) )
        {
            Thread cachedThread = m_mapThreads.get( in_strThreadName );
            if( CGlobals.m_bDebugThreadMngr )
                System.out.println( "[CThreadManager]: Waiting for " + in_strThreadName + " Thread." );
            try{ cachedThread.join(); } catch( InterruptedException ie ){ }
            removeThread( in_strThreadName );
        }
    }
    
    public static void stopAllThreads()
    {
        m_mapThreads.entrySet().forEach((cachedElement) -> {
            cachedElement.getValue().interrupt();
            if( CGlobals.m_bDebugThreadMngr )
                System.out.println( "[CThreadManager]: " + cachedElement.getKey() + " Thread interrupted." );
        });
        m_mapThreads.clear();
    }
}

