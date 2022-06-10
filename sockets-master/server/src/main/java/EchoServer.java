import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7788);
            
            Socket socket = serverSocket.accept();

            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())); 
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String message = in.readLine();
            out.println(message);
            System.out.println("Received " + message);
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }
}