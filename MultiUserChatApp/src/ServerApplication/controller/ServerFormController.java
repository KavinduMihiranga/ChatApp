package ServerApplication.controller;

import ClientApplication.controller.ClientFormController;
import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ServerFormController implements Initializable {
    public TextArea txtAreaServerMsgWindow;
    public TextField txtServerMessage;
    public Button btnSend;
    public Label txtServerName;

    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    public AnchorPane serverContext;
    public JFXButton btnImg;


    String messageIn="";
    String newLine=System.lineSeparator();

    public VBox vbox_messages;
    private ClientFormController clientFormController;

    LoginFormController loginFormController=new LoginFormController();

    public void ServerMessageOnAction(ActionEvent actionEvent) throws IOException {


        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);

        hBox.setPadding(new Insets(5,5,5,10));
        hBox.setStyle("-fx-color:rgb(239,242,255);" +
                "-fx-background-color:rgb(15,125,242);" +
                "-fx-background-radius:20px");

        dataOutputStream.writeUTF(txtServerMessage.getText().trim());
        txtAreaServerMsgWindow.appendText(newLine+"Server"+" : "+txtServerMessage.getText().trim());
        txtServerMessage.clear();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new Thread(()->{
            try {
                serverContext.setStyle("-fx-color:rgb(239,242,255);" +
                        "-fx-background-color:rgb(15,125,242);" +
                        "-fx-background-radius:20px");
                btnSend.setStyle("-fx-background-color: darkblue;"+"-fx-background-radius:20");

                serverSocket = new ServerSocket(1200);
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

    public void imgButtonOnAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            HBox hBox=new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);

            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for (int readNum; (readNum = fin.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }

        } catch (IOException ex) {
            Logger.getLogger("ss");
        }
    }


}