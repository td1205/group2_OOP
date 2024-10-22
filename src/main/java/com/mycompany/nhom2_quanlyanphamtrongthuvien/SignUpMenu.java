/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nhom2_quanlyanphamtrongthuvien;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author Tire
 */
public class SignUpMenu {
@FXML
private Button SignUp;
@FXML
private Button SignIn;

    @FXML
    private void switchToLoginMenu(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }
}
