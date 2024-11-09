package com.mycompany.prj;

import java.io.File;
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

    // Relative path to the XML file in the resources folder
    public static final String xmlFilePath = "/com/mycompany/prj/data.xml"; 

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
    public void submit(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
        Username = userName.getText();
        Password = password.getText();

        // Load XML document from the resources folder
        InputStream inputStream = getClass().getResourceAsStream(xmlFilePath);

        if (inputStream == null) {
            System.out.println("XML file not found!");
            return;
        }

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(inputStream);

        document.getDocumentElement().normalize();

        // Get list of "user" elements in the XML
        NodeList nList = document.getElementsByTagName("user");

        boolean loginSuccessful = false;

        // Check each "user" element for login credentials
        for (int i = 0; i < nList.getLength(); i++) {
            org.w3c.dom.Node node = nList.item(i); // Use fully qualified name to avoid conflict

            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) { // Use fully qualified name here as well
                Element userElement = (Element) node;
                String storedUsername = userElement.getElementsByTagName("name").item(0).getTextContent();
                String storedPassword = userElement.getElementsByTagName("password").item(0).getTextContent();

                // Compare credentials
                if (Username.equals(storedUsername) && Password.equals(storedPassword)) {
                    loginSuccessful = true;
                    break;
                }
            }
        }

        if (loginSuccessful) {
            // Successful login, switch scene
            root = FXMLLoader.load(getClass().getResource("/com/mycompany/prj/Main.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();
        } else {
            // Display error message if login credentials are incorrect
            text.setText("Sai tên đăng nhập hoặc mật khẩu!");
        }
    }
}
