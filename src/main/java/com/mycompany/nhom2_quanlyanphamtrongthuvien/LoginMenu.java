package com.mycompany.nhom2_quanlyanphamtrongthuvien;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginMenu {

    @FXML
    private Button SignIn;
    @FXML
    private Button SignUp;

    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
        App.setRoot("Main");
    }

    @FXML
    private void switchToSignUpMenu(ActionEvent event) throws IOException {
        App.setRoot("SignUp");
    }
}
