package com.mycompany.nhom2_quanlyanphamtrongthuvien;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("Login");
        scene = new Scene(root);
        
        try {
            scene.getStylesheets().add(getClass().getResource("/com/mycompany/nhom2_quanlyanphamtrongthuvien/design/design.css").toExternalForm());

        } catch (Exception e) {
            System.err.println(e);
        }
        stage.setTitle("QL");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
