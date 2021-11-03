import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;
    private boolean toDown = false;

    public Client() {
        try {
            this.socket = new Socket("127.0.0.1", 8189);
            this.scanner = new Scanner(System.in);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            while (!toDown) {
                try {
                    String inboundMessage = in.readUTF();
                    System.out.printf("\nServer: %s\nEnter message: ", inboundMessage);
                } catch (IOException ex) {
                    System.out.println("Connection closed. Press Enter.");
                    toDown = true;
                    break;
                }
            }
        })
                .start();


        while (!toDown) {
            try {
                System.out.print("Enter message: ");
                String outboundMessage = scanner.nextLine();
                out.writeUTF(outboundMessage);
            } catch (IOException ex) {
                System.out.println("Connection closed.");
                break;
            }
        }
        System.out.println("Client down.");
    }
}
