
import java.net.*;
import java.io.*;

public class client1 {

    Socket socket;
    BufferedReader br;//to read
    PrintWriter out;//to write

    public client1() {
        try {
            System.out.println("sendin request to server ");
            socket = new Socket("192.168.149.13", 7777);
            System.out.println("connection done ");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));//new input stream converts 
            //the byte data into characters and then it is read inside the bufferedreader class

            out = new PrintWriter(socket.getOutputStream());

            startReading();//
            startWriting();

        } catch (Exception e) {
        }
    }

    public void startReading() {
        //this would read the thread
        Runnable r1 = () -> {
            System.out.println("reader started...");
            try {
                while (true && !socket.isClosed()) {

                    String msg = br.readLine();///it would read the msg
                    if (msg == "stop") {
                        System.out.println("server has stopped reading");
                        socket.close();
                        break;
                    }
                    System.out.println("server :" + msg);

                }
            } catch (Exception e) {
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
                while (true) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    out.println(content);
                    out.flush();//if it doesn't work then just forcefully flushes that
                    if (content.equals("stop")) {
                        socket.close();
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        };
        new Thread(r2).start();//to start this thread

    }

    public static void main(String[] args) {
        System.out.println("this is client..");
        new client1();

    }

}
