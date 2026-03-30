import java.io.*;
import java.net.*;
import java.util.*;

public class ChatApp {

    static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) throws Exception {

        // Start Server Thread
        Thread serverThread = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(1234);
                System.out.println("Server started...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");

                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                    ClientHandler client = new ClientHandler(socket, dis, dos);
                    clients.add(client);

                    Thread t = new Thread(client);
                    t.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.start();

        // Small delay to ensure server starts
        Thread.sleep(1000);

        // Start Client
        startClient();
    }

    // CLIENT METHOD
    public static void startClient() {
        try {
            Socket socket = new Socket("localhost", 1234);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Send thread
            Thread sendThread = new Thread(() -> {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String msg;

                    while (true) {
                        msg = br.readLine();
                        dos.writeUTF(msg);

                        if (msg.equals("exit")) {
                            socket.close();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Receive thread
            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while (true) {
                        msg = dis.readUTF();
                        System.out.println("Message: " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected");
                }
            });

            sendThread.start();
            receiveThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// CLIENT HANDLER CLASS
class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String msg;

        try {
            while (true) {
                msg = dis.readUTF();

                if (msg.equals("exit")) {
                    socket.close();
                    break;
                }

                // Broadcast message
                for (ClientHandler client : ChatApp.clients) {
                    if (client != this) {
                        client.dos.writeUTF(msg);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }
}