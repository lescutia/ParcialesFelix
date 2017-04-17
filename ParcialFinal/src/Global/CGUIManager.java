/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;
import Main.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JFrame;
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
            if( CGlobals.m_bDebugGUIMngr )
                System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI displayed." );
        }
        else if( CGlobals.m_bDebugGUIMngr )
            System.out.println( "[CGUIManager]: " + in_strNameGUI + " GUI doesn't exist." );
    }
    
    public static void dispose( String in_strNameGUI )
    {
        if( m_mapGUIs.containsKey( in_strNameGUI ) )
        {
            m_mapGUIs.get( in_strNameGUI ).dispose();
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
    }
    
}
