package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public int port;
    public String ip;
    public Socket serverSocket;
    public PrintWriter writer;
    public BufferedReader reader;

    public Client(String ip,int port) {
        this.port = port;
        this.ip = ip;
        serverSocket = null;
        writer = null;
        reader = null;
    }

    public void runClient(){
        try{
            serverSocket = new Socket(ip,port);
            reader = new  BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            writer = new PrintWriter(serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeClient(String data) {
        writer.write(data + "\r\n");
        writer.flush();
    }

    public void stopClient() throws IOException {
        serverSocket.close();
    }
}