/**************************************************************************
	file:	 	CThreadManager.java
	date:		2017/04/08 17:38
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Contains tools to manage the threads.
        * Include functions to wait, stop, start and remove.
**************************************************************************/
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
    /**
     *
     * @param in_thread is the thread to be initialized. 
     * @param in_strThreadName is the name of the thread to be initiailized. 
     */
    
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
    
    /**
     *
     * @param in_strThreadName is the name of the thread to be stopped. 
     */
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
    
    /**
     *
     * @param in_strThreadName is the name of the thread to be gotten. 
     * @return the tread if it exists, null if the thread does not exist. 
     */
    public static Thread getThread( String in_strThreadName )
    {
        if( m_mapThreads.containsKey( in_strThreadName ) )
            return m_mapThreads.get( in_strThreadName );
        return null;
    }
    
    /**
     *
     * @param in_strThreadName is the name of the thread to be removed. 
     * @return true if the tread was deleted; false if not. 
     */
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
    
    /**
     *
     * @param in_strThreadName is the name of the thread to be waitted. 
     */
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
    
    /**
     This function will stop all the threads. 
     */
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

