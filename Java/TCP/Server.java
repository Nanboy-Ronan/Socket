import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2000);

        System.out.println("Server Started");
        System.out.println("Server Information: " + server.getInetAddress() + "Port: " + server.getLocalPort());

        // Listen to client
        for (;;) {
            // Get client
            Socket client = server.accept();
            // Asychronized Thread
            ClientHandler clientHandler = new ClientHandler(client);
            // Start Thread
            clientHandler.start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("New client: " + socket.getInetAddress() +
                    "Port: " + socket.getPort());

            try {
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (flag) {
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)) {
                        flag = false;
                        socketOutput.println("bye");
                    } else {
                        System.out.println(str);
                        socketOutput.println("Send: " + str.length());
                    }
                }

                socketInput.close();
                socketOutput.close();

            } catch (Exception e) {
                System.out.println("Exception Caught");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Client exited: " + socket.getInetAddress() +
                        "Port: " + socket.getPort());
            }
        }
    }
}