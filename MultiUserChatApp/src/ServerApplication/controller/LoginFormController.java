package ServerApplication.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public JFXTextField txtUserName;

    public JFXTextField getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(JFXTextField txtUserName) {
        this.txtUserName = txtUserName;
    }

    public JFXButton btnLogin;
    public AnchorPane contextId;

    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) contextId.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ServerForm.fxml"))));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnLogin.setStyle("-fx-background-color: #5468F2;" + "-fx-background-radius:100");
        txtUserName.setStyle("-fx-text-fill: white");
    }
}
