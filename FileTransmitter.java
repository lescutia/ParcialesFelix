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

public class FileTransmitter extends Thread{
    public FileTransmitter(){}
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Globals.m_iPortFileTransmiter);
            while(true)
            {
                Socket clientConnection = serverSocket.accept();
                new FileTransmitterDispatcher(clientConnection).start();
            }
        } catch (Exception e) {
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
    public void run(){
        try
        {
            System.out.println("[FileTransmitter]: Received a resource request from: "+socket.getInetAddress().toString());
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
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static byte[] readBytesFromFile(String filePath) {

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