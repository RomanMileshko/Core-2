import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private boolean toDown = false;
    private Scanner scanner;
    public Server() {
        try {
            socket = new ServerSocket(8189);
            scanner = new Scanner(System.in);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {
        System.out.println("Server started and waiting for connection...");
        Socket acceptClient = socket.accept();
        System.out.printf("Client (%s) connected !\n", acceptClient.getInetAddress());

        inStream = new DataInputStream(acceptClient.getInputStream());
        outStream = new DataOutputStream(acceptClient.getOutputStream());

        new Thread(() -> {
            try {
                while (true) {
                    String message = readInboundMessage();
                    if (message.equals("/ENDC")) {
                        System.out.println("Server is down. Press Enter");
                        toDown = true;
                        break;
                    }
                    System.out.printf("\nClient: %s\nEnter message: ", message);
                }
            } catch (MyServerException ex) {
                System.out.println("Connection gracefully closed.");
                toDown = true;
            }
        }).start();

        while (!toDown) {
            System.out.print("Enter message: ");
            String outBoundMessage = scanner.nextLine();
            sendOutboundMessage(outBoundMessage);
        }
        System.out.println("Server down.");
    }

    private void sendOutboundMessage(String message) {
        try {
            outStream.writeUTF(message);
        } catch (IOException ex) {
            throw new MyServerException("Output operation error.", ex);
        }
    }

    private String readInboundMessage() {
        try {
            return inStream.readUTF();
        } catch (EOFException ex) {
            throw new MyServerException("End of stream. Client is shutdown !?", ex);
        } catch (IOException ex) {
            throw new MyServerException("Input operation error.", ex);
        }
    }
}
