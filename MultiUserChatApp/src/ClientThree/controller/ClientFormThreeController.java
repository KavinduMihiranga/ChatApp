package ClientThree.controller;

import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientFormThreeController {
    public AnchorPane contextClientThree;
    public TextArea txtAreaClientThree;
    public TextField txtClientThreeMsg;
    public Button btnClientThreeMsg;
    public JFXButton btnImg;

    static Socket socket = null;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;

    public Image image;
    String newLine=System.lineSeparator();

    public void btnClientThreeOnAction(ActionEvent actionEvent) throws IOException {
        String reply="";
        reply=txtClientThreeMsg.getText();
        dataOutputStream.writeUTF(reply);
        txtClientThreeMsg.appendText(newLine+"ClientThree :"+reply.trim());
        txtClientThreeMsg.clear();
    }

    public void btnImgOnAction(ActionEvent actionEvent) {
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

            //my code-------------------
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());

            while (image!=null){

            }

            //---------------------------

        } catch (IOException ex) {
            Logger.getLogger("ss");
        }

    }
    public void initialize() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

//                    File path=new File("ServerApplication/assets/img_550783.png");
//                    BufferedImage image=ImageIO.read(path);


                    btnImg.setStyle("-fx-background-color: lightskyblue;"+"");


                    contextClientThree.setStyle("-fx-color:rgb(239,242,255);" +
                            "-fx-background-color:rgb(15,125,242);" +
                            "-fx-background-radius:20px");
                    btnClientThreeMsg.setStyle("-fx-background-color: darkblue;"+"-fx-background-radius:20");
                    socket = new Socket("localhost",1400);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    String messageIn= "";

                    while (!messageIn.equals("end")){
                        messageIn=dataInputStream.readUTF();
                        txtAreaClientThree.appendText(newLine+"Server : "+messageIn.trim());
                    }
                } catch (IOException e) {

                }
            }
        }).start();

    }
}
