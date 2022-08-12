package ServerApplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ServerFormController implements Initializable {
    public TextArea txtAreaServerMsgWindow;
    public TextField txtServerMessage;
    public Button btnSend;

    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    public AnchorPane serverContext;

    String messageIn="";
    String newLine=System.lineSeparator();



    public void ServerMessageOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtServerMessage.getText().trim());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        serverContext.setStyle("-fx-color:rgb(239,242,255);" +
                "-fx-background-color:rgb(15,125,242);" +
                "-fx-background-radius:20px");
        btnSend.setStyle("-fx-background-color: darkblue;"+"-fx-background-radius:20");
        new Thread(()->{
            try {

                serverSocket = new ServerSocket(5000);
                System.out.println("Server Started");
                socket=serverSocket.accept();
                System.out.println("Client Accepted!");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while(!messageIn.equals("end")){
                    messageIn = dataInputStream.readUTF();
                    txtAreaServerMsgWindow.appendText(newLine+"Client :"+messageIn.trim());
                }
            } catch (IOException e) {

            }
        }).start();
    }
}
