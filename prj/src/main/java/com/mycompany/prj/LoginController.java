package com.mycompany.prj;

import java.io.IOException;
import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LoginController {

    private Scene scene;
    private Stage stage;
    private Parent root;
    private String Username;
    private String Password;

    @FXML
    private PasswordField password;

    @FXML
    private TextField userName;

    @FXML
    private Label text;

    @FXML
    public void ChangetoSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/mycompany/prj/SignUp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }

    @FXML
    public void submit(ActionEvent event) {
        Username = userName.getText();
        Password = password.getText();

        try (InputStream inputStream = getClass().getResourceAsStream("/com/mycompany/prj/data.xml")) {
            if (inputStream == null) {
                System.out.println("XML file not found!");  
                return;
            }

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("user");
            boolean loginSuccessful = false;
            for (int i = 0; i < nList.getLength(); i++) {
                org.w3c.dom.Node node = nList.item(i);

                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element userElement = (Element) node;
                    String storedUsername = userElement.getElementsByTagName("name").item(0).getTextContent();
                    String storedPassword = userElement.getElementsByTagName("password").item(0).getTextContent();

                    if (Username.equals(storedUsername) && Password.equals(storedPassword)) {
                        loginSuccessful = true;
                        break;
                    }
                }
            }

            if (loginSuccessful) {
                root = FXMLLoader.load(getClass().getResource("/com/mycompany/prj/Main.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Welcome");
                stage.show();
            } else {
                text.setText("Incorrect username or password!!!");
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            text.setText("An error occurred while processing the login!!!");
        }
    }

}
