import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.lang.Exception;
import java.net.Socket;
import java.net.ServerSocket;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.net.SocketTimeoutException;

public class FileTransmitter extends Thread
{
    public boolean m_bStopFL = false;
    public FileTransmitter(){}
    public void run() 
    {
        try 
        {
            ServerSocket serverSocket = new ServerSocket(Globals.m_iPortFileTransmiter);
            serverSocket.setSoTimeout(1000);
            while(!m_bStopFL)
            {
                try
                {
                    Socket clientConnection = serverSocket.accept();
                    new FileTransmitterDispatcher(clientConnection).start();
                }
                catch(SocketTimeoutException s){}
            }

            Globals.PrintMessage("[FileTransmitter Interrupted]", Globals.m_bDebugFT );
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}

class FileTransmitterDispatcher extends Thread
{
    private Socket socket;
    public FileTransmitterDispatcher(Socket clientConnection)
    {   
        this.socket = clientConnection;
    }
    
    public void run()
    {
        try
        {
            Globals.PrintMessage("[FileTransmitter]: Received a resource request from: "+socket.getInetAddress().toString(), Globals.m_bDebugFT );
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Message inputMsg = (Message)inputStream.readObject();
            String file = inputMsg.GetFileName();
            if(inputMsg.GetMsgType() == Message.EMessageType.DATA_REQUEST)
            {
                String tmpPath = Globals.m_strSharedDirPath + file;
                byte[] bFile = readBytesFromFile(tmpPath);    
                Message fileMsg = new Message(Message.EMessageType.DATA_REPLY, 20, bFile);
                ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
                dOut.writeObject(fileMsg);
            }
        }
        catch (ClassNotFoundException e){}
        catch (IOException e) 
        {
            Globals.PrintMessage("[FileTransmitterIOException]", Globals.m_bDebugFT );
        }
    }
    
    private static byte[] readBytesFromFile(String filePath) 
    {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }
}