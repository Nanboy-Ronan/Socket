import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        // Time out
        socket.setSoTimeout(3000);

        socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 2000), 3000);

        System.out.println("Client Started");
        System.out.println("Client Information: " + socket.getLocalAddress() + "Port: " + socket.getLocalPort());
        System.out.println("Server Information: " + socket.getInetAddress() + "Port: " + socket.getPort());

        try {
            render(socket);
        } catch (Exception e) {
            System.out.println("Exception Caught");
        }

        socket.close();
        System.out.println("Client Exit");

    }

    private static void render(Socket client) throws IOException {
        // KeyBoard In
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        // Socket output -> PrintSteam
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean flag = true;
        while (flag) {
            // Read from keybord
            String str = input.readLine();
            // Send to server
            socketPrintStream.println(str);

            // Read from server
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        }

        socketPrintStream.close();
        socketBufferedReader.close();

    }
}
