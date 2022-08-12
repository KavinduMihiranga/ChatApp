package ClientApplication.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController {
    public TextField txtClientMessage;
    public TextArea txtAreaClientMsgWindow;
    public Button btnSend;

    static Socket socket = null;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;

    String newLine=System.lineSeparator();
    public void initialize(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost",5000);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    String messageIn= "";

                    while (!messageIn.equals("end")){
                        messageIn=dataInputStream.readUTF();
                        txtAreaClientMsgWindow.appendText(newLine+"Serve : "+messageIn.trim());
                    }
                } catch (IOException e) {

                }
            }
        }).start();
    }
    public void clientSendMessageOnAction(ActionEvent actionEvent) throws IOException {
        String reply="";
        reply=txtClientMessage.getText();
        dataOutputStream.writeUTF(reply);
    }
}
