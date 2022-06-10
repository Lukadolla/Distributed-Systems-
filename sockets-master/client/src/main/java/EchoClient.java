import java.io.*;
import java.net.*;

public class EchoClient {
    /**
     * This program accepts a single command line argument: the host name for the servev
     */
    public static void main(String[] args) {
        // Choose the hostname
        String host = "localhost";
        if (args.length > 0) {
            host = args[0];
        }
        
        try {
            Socket mySocket = new Socket(host, 7788);
            InputStream is = mySocket.getInputStream();
            OutputStream os = mySocket.getOutputStream();

            BufferedReader in = 
                new BufferedReader(new InputStreamReader(is));
            PrintWriter out = new PrintWriter(os, true);

            // send a string to the server
            out.println("Hello World");

            // Print out the response
            String line = in.readLine();
            System.out.println(line);

            in.close(); out.close(); mySocket.close();
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }
}