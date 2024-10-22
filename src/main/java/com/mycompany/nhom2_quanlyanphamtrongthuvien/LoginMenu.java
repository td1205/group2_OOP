package com.mycompany.nhom2_quanlyanphamtrongthuvien;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginMenu {

    @FXML
    private Button SignIn;

    @FXML
private void switchToMainMenu(ActionEvent event) {
    try {
        App.setRoot("Main");
    } catch (IOException e) {
        System.out.println("Lỗi khi tải Main.fxml: " + e.getMessage());
        e.printStackTrace(); // Hiển thị lỗi chi tiết
    }
}
}
