/**************************************************************************
	file:	 	CGuiManager.java
	date:		2017/04/08 17:38	
	author:		Luis Escutia; Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Contains the implementation of the GUI interfaces manager.
**************************************************************************/
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
    
    /**
     *
     * @param in_strNameGUI is the name of the graphic interface to be showed. 
     */
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
    
    /**
     *
     * @param in_strNameGUI is the name of the graphic interface to be hidden. 
     */
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
    
    //This function will hide all the graphic interfaces. 
    public static void hideAllGUIs()
    {
        m_mapGUIs.entrySet().forEach((cachedElement) -> {
            cachedElement.getValue().setVisible( false );
            if( CGlobals.m_bDebugGUIMngr )
                System.out.println( "[CGUIManager]: " + cachedElement.getKey() + " GUI hided." );
        });
    }
    
    /**
     *
     * @param in_strNameGUI
     */
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
