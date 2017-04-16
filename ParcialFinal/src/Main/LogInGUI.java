/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import FileTransfer.CFileService;
import Connection.ConnectionService;
import javax.swing.JFileChooser;
import Global.CGlobals;
import javax.swing.JButton;
import Connection.*;
import Global.CThreadManager;
import ResourceUpdate.ResourceUpdate;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import ResourceUpdate.*;

/**
 *
 * @author gamaa_000
 */
public class LogInGUI extends javax.swing.JFrame
{
    MainGUI m_cachedMainGUI;

    /**
     * Creates new form SettingsGUI
     */
    public LogInGUI( MainGUI in_mainGUI )
    {
        initComponents();
        m_cachedMainGUI = in_mainGUI;
        if ( !CGlobals.m_strSharedDirPath.equals( "" ) && !CGlobals.m_strDownloadPath.equals( "" ) )
        {
            login.setEnabled( true );
        }
    }

    public LogInGUI()
    {
        initComponents();
        if ( !CGlobals.m_strSharedDirPath.equals( "" ) && !CGlobals.m_strDownloadPath.equals( "" ) )
        {
            login.setEnabled( true );
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        login = new javax.swing.JButton();
        sharedDir = new javax.swing.JButton();
        downloadDir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        login.setText("Log In");
        login.setEnabled(false);
        login.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                loginActionPerformed(evt);
            }
        });

        sharedDir.setText("Shared Directory");
        sharedDir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                sharedDirActionPerformed(evt);
            }
        });

        downloadDir.setText("Download Directory");
        downloadDir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                downloadDirActionPerformed(evt);
            }
        });

        jLabel1.setText("Username");

        usernameField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                usernameFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sharedDir, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(downloadDir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1))
                            .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(passwordField)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sharedDir)
                    .addComponent(downloadDir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(login)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_loginActionPerformed
    {//GEN-HEADEREND:event_loginActionPerformed
        CGlobals.saveConfig();
        //Here is where the login process takes place, 
        //if the login is successful then next lines are executed
        //m_cachedDownloadBtn.setEnabled( true );
        //m_cachedSharedBtn.setEnabled( true );
        String username = usernameField.getText()
                ,password = CGlobals.hashPassword( String.copyValueOf( passwordField.getPassword() ) ) ;
        
        try
        {

            if ( System.getSecurityManager() == null )
            {
                System.setProperty( "java.security.policy", "security.policy" );
            }

            Registry registry = LocateRegistry.getRegistry( CGlobals.m_strLeaderId );
            ResourceUpdate ru = null;
            ru = (ResourceUpdate) Naming.lookup( "//" + CGlobals.m_strLeaderId + ":" + CGlobals.m_iRemoteObjectPort + "/UpdateServer" );
            if( ru.checkUser( username, password ) )
            {
                m_cachedMainGUI.setVisible( true );
                CThreadManager.startThread( new FileListener().fileListenerThread(), "FileListener");
                CFileService fileService = new CFileService();
                fileService.startFileService();
                Updater u = new Updater();
                u.sendTable();
                this.dispose();
            }
            else
            {
                System.out.println( "[TODO]: Error window" );
            }
        }
        catch ( RemoteException e )
        {
            System.out.println( "[Updater/sendTable]: RemoteException" );
            e.printStackTrace();
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch ( NotBoundException e )
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_loginActionPerformed

    private void sharedDirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_sharedDirActionPerformed
    {//GEN-HEADEREND:event_sharedDirActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory( new java.io.File(".") );
        jfc.setDialogTitle( "Select Shared Directory" );
        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        
        if( jfc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
            CGlobals.m_strSharedDirPath = jfc.getSelectedFile().getPath();
        
        if( !CGlobals.m_strSharedDirPath.equals("") && !CGlobals.m_strDownloadPath.equals("") )
            login.setEnabled( true );
    }//GEN-LAST:event_sharedDirActionPerformed

    private void downloadDirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_downloadDirActionPerformed
    {//GEN-HEADEREND:event_downloadDirActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory( new java.io.File(".") );
        jfc.setDialogTitle( "Select Download Directory" );
        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        
        if( jfc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
            CGlobals.m_strDownloadPath = jfc.getSelectedFile().getPath();
        
        if( !CGlobals.m_strSharedDirPath.equals("") && !CGlobals.m_strDownloadPath.equals("") )
            login.setEnabled( true );
    }//GEN-LAST:event_downloadDirActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_usernameFieldActionPerformed
    {//GEN-HEADEREND:event_usernameFieldActionPerformed

    }//GEN-LAST:event_usernameFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] )
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() )
            {
                if ( "Nimbus".equals( info.getName() ) )
                {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch ( ClassNotFoundException ex )
        {
            java.util.logging.Logger.getLogger(LogInGUI.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( InstantiationException ex )
        {
            java.util.logging.Logger.getLogger(LogInGUI.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( IllegalAccessException ex )
        {
            java.util.logging.Logger.getLogger(LogInGUI.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( javax.swing.UnsupportedLookAndFeelException ex )
        {
            java.util.logging.Logger.getLogger(LogInGUI.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new LogInGUI().setVisible( true );
            }
        } );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downloadDir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton login;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton sharedDir;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}