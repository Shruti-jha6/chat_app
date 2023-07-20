
import java.net.*;///networking library
import java.io.*;

public class server1 {

    ServerSocket server;
    Socket socket;

    BufferedReader br;//to read
    PrintWriter out;//to write

    ////constructor...
    public server1() {
        try {
            System.out.println("server is ready to accept connection");
            System.out.println("waiting...");
            server = new ServerSocket(7777);
            //System.out.println("server is ready to accept connection");
            //System.out.println("waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));//new input stream converts 
            //the byte data into characters and then it is read inside the bufferedreader class

            out = new PrintWriter(socket.getOutputStream());

            startReading();//
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    ///we want to read and write simultaniously so we use multithreading here
    public void startReading() {
        //this would read the thread
        Runnable r1 = () -> {
            System.out.println("reader started...");
            try {
                while (true) {

                    String msg = br.readLine();///it would read the msg
                    if (msg == "stop") {
                        System.out.println("client has stopped reading");
                        break;
                    }
                    System.out.println("client :" + msg);

                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("connection is closed now because you entered stop");

            }

        };
        new Thread(r1).start();//to start this thread

    }

    public void startWriting() {
        //this thread will take user input and send it to the client
        Runnable r2 = () -> {
            System.out.println("writer started writing");
            try {
                while (true && !socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();//if it doesn't work then just forcefully flushes that

                }
            } catch (Exception e) {
                System.out.println("connection is closed now because you entered stop");

            }

        };
        new Thread(r2).start();//to start this thread

    }

    public static void main(String[] args) {
        System.out.println("this is server ");
        new server1();

    }
}
