import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.*;

/*
public class DynamicTreeDemo extends Thread
{
  BaseDynamicTree m_DynamicTree;
  public DynamicTreeDemo() { }

  public void run()
  {
    m_DynamicTree = new BaseDynamicTree();
    m_DynamicTree.createAndShowGUI();
  }

  public void finish()
  {
    m_DynamicTree.dispose();
  }
}
*/

class DynamicTreeDemo extends JPanel implements ActionListener 
{
  private int newNodeSuffix = 1;
  private static String DOWNLOAD_COMMAND = "download";
  private static String REFRESH_COMMAND = "refresh";
  private static String CLEAR_COMMAND = "clear";
  JFrame frame;
  private DynamicTree treePanel;
  
  JButton addButton, 
          removeButton,
          clearButton;

  public void EnabledButtons( boolean in_bEnable )
  {
    addButton.setEnabled( in_bEnable );
    removeButton.setEnabled( in_bEnable );
    clearButton.setEnabled( in_bEnable );
  }

  public DynamicTreeDemo() 
  {
    super(new BorderLayout());

    // Create the components.
    treePanel = new DynamicTree();
    //populateTree(treePanel);

    addButton = new JButton("Download");
    addButton.setActionCommand(DOWNLOAD_COMMAND);
    addButton.addActionListener(this);

    removeButton = new JButton("Refresh");
    removeButton.setActionCommand(REFRESH_COMMAND);
    removeButton.addActionListener(this);

    clearButton = new JButton("Clear");
    clearButton.setActionCommand(CLEAR_COMMAND);
    clearButton.addActionListener(this);

    // Lay everything out.
    treePanel.setPreferredSize(new Dimension(500, 250));
    add(treePanel, BorderLayout.CENTER);

    JPanel panel = new JPanel(new GridLayout(0, 3));
    panel.add(addButton);
    panel.add(removeButton);
    panel.add(clearButton);
    add(panel, BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    if (DOWNLOAD_COMMAND.equals(command)) {
      // Download the resource from the owner
      treePanel.download();
    } else if (REFRESH_COMMAND.equals(command)) {
      // Refresh the actual resources in the system
      treePanel.refreshTree(Globals.m_strLeaderId);
    } else if (CLEAR_COMMAND.equals(command)) {
      // Clear button clicked.
      treePanel.clear();
    }
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event-dispatching thread.
   */
  public void createAndShowGUI() 
  {
    // Create and set up the window.
    this.frame = new JFrame("Distributed File System");
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create and set up the content pane.
    //DynamicTreeDemo newContentPane = new DynamicTreeDemo();
    this.setOpaque(true); // content panes must be opaque
    this.frame.setContentPane(this);

    // Display the window.
    this.frame.pack();
    this.frame.setVisible(true);
  }

  public void dispose()
  {
    this.frame.dispose();
  }

  public void startGUI() {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() 
    {
      public void run() 
      {
        createAndShowGUI();
      }
    });
  }
}

class DynamicTree extends JPanel 
{
  protected DefaultMutableTreeNode rootNode;
  protected DefaultTreeModel treeModel;
  protected JTree tree;
  private Toolkit toolkit = Toolkit.getDefaultToolkit();

  public DynamicTree() {
    super(new GridLayout(1, 0));

    rootNode = new DefaultMutableTreeNode("DMS");
    treeModel = new DefaultTreeModel(rootNode);

    tree = new JTree(treeModel);
    tree.setEditable(false);
    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    tree.setShowsRootHandles(true);
    JScrollPane scrollPane = new JScrollPane(tree);
    add(scrollPane);
  }

  /*Refresh the tree obtaining from the leader the table of resources*/
  public void refreshTree(String leader)
  {
    try
    {
      Socket socket = new Socket(leader,Globals.m_iPortRefresh);
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      Message msg = new Message(Message.EMessageType.REQUEST,10);
      outputStream.writeObject(msg);
      outputStream.flush();
      ObjectInputStream inputStream;
      inputStream = new ObjectInputStream(socket.getInputStream());   
      Message inputMsg = (Message)inputStream.readObject();
      List inputList = (ArrayList)inputMsg.GetResources();
      System.out.println("[DynamicTree]: Received List ");
      if( Globals.m_bDebugTree )
      {
        for( int i = 0; i < inputList.size(); i++ )
        {
          Resource tmp = (Resource)inputList.get(i);
          System.out.println(tmp.GetElements());
        }
      }

      this.clear();
      for( int i = 0; i < inputList.size(); i++ )
      {
        Resource tmp = (Resource)inputList.get(i);
        if( tmp.GetResourcesOwner().equals(InetAddress.getLocalHost().getHostAddress()) )
          continue;
        List tmpResources = tmp.GetElements();
        DefaultMutableTreeNode p = this.addObject(null, tmp.GetResourcesOwner() );
        for( int j = 0; j < tmpResources.size(); j++ )
          this.addObject(p, tmpResources.get(j) );
      }
      socket.close();
      outputStream.close();
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /*This method creates a connection and request a resource*/
  public void download(){
    TreePath currentSelection = tree.getSelectionPath();
    if (currentSelection != null)
    {
      String owner    = currentSelection.getParentPath().getLastPathComponent().toString();
      String fileName = currentSelection.getLastPathComponent().toString();
      String path     = Globals.m_strDownloadPath;
      System.out.println("[DynamicTree]: Requesting "+fileName+" from "+owner);
      FileReceiver fr = new FileReceiver(owner, path, fileName);
      fr.start();
    }
  }

  /** Remove all nodes except the root node. */
  public void clear() {
    rootNode.removeAllChildren();
    treeModel.reload();
  }

  /** Add child to the currently selected node. */
  public DefaultMutableTreeNode addObject(Object child) {
    DefaultMutableTreeNode parentNode = null;
    TreePath parentPath = tree.getSelectionPath();

    if (parentPath == null) {
      parentNode = rootNode;
    } else {
      parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
    }

    return addObject(parentNode, child, true);
  }

  public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
      Object child) {
    return addObject(parent, child, false);
  }

  public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
      Object child, boolean shouldBeVisible) {
    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

    if (parent == null) {
      parent = rootNode;
    }

    // It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
    treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

    // Make sure the user can see the lovely new node.
    if (shouldBeVisible) {
      tree.scrollPathToVisible(new TreePath(childNode.getPath()));
    }
    return childNode;
  }
}