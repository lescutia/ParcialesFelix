/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;
//import Main.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JFrame;
//import javax.swing.SwingUtilities;

/**
 *
 * @author gamaa
 */
public class CGUIManager 
{
    static Map<String, JFrame> m_mapGUIs = new LinkedHashMap<>();
    
    public static boolean addGUI( JFrame in_newGUI, String in_strNameGUI )
    {
        if( !m_mapGUIs.containsKey( in_strNameGUI ) )
        {
            m_mapGUIs.put( in_strNameGUI, in_newGUI );
            if( CGlobals.m_bDebugGUIMngr )
                System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI added." );
        }
        return false;
    }
    
    public static void display( String in_strNameGUI )
    {
        if( m_mapGUIs.containsKey( in_strNameGUI ) )
        {
            m_mapGUIs.get( in_strNameGUI ).setVisible( true );
            //SwingUtilities.updateComponentTreeUI( m_mapGUIs.get( in_strNameGUI ) );
            if( CGlobals.m_bDebugGUIMngr )
                System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI displayed." );
        }
        else if( CGlobals.m_bDebugGUIMngr )
            System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI doesn't exist." );
    }
    
    public static void hideGUI( String in_strNameGUI )
    {
        if( m_mapGUIs.containsKey( in_strNameGUI ) )
        {
            m_mapGUIs.get( in_strNameGUI ).setVisible( false );
            if( CGlobals.m_bDebugGUIMngr )
                System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI hided." );
        }
        else if( CGlobals.m_bDebugGUIMngr )
            System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI doesn't exist." );
    }
    
    public static void hideAllGUIs()
    {
        m_mapGUIs.entrySet().forEach((cachedElement) -> {
            cachedElement.getValue().setVisible( false );
            if( CGlobals.m_bDebugGUIMngr )
                System.out.println( "[CGUIManager]: " + cachedElement.getKey() + " GUI hided." );
        });
    }
    
    public static void dispose( String in_strNameGUI )
    {
        if( m_mapGUIs.containsKey( in_strNameGUI ) )
        {
            m_mapGUIs.get( in_strNameGUI ).dispose();
            m_mapGUIs.remove( in_strNameGUI );
            if( CGlobals.m_bDebugGUIMngr )
                System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI disposed." );
        }
        else if( CGlobals.m_bDebugGUIMngr )
            System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI doesn't exist." );
    }
    
    public static void disposeAll()
    {
        m_mapGUIs.entrySet().forEach((cachedElement) -> {
            cachedElement.getValue().dispose();
            if( CGlobals.m_bDebugGUIMngr )
                System.out.println( "[CGUIManager]: " + cachedElement.getKey() + " GUI disposed." );
        });
        
        m_mapGUIs.clear();
    }
    
    public static JFrame getGUI( String in_strNameGUI )
    {
        JFrame result = null;
        if( m_mapGUIs.containsKey( in_strNameGUI ) )
            result = m_mapGUIs.get( in_strNameGUI );
        return result;
    }
}
