package com.mycompany.prj;

import java.io.File;
import java.io.IOException;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class SignUpController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String Username;
    private String Password;
   public static final String xmlFilePath = "src/main/resources/com/mycompany/prj/data.xml";


    @FXML
    private PasswordField password;

    @FXML
    private TextField userName;

    @FXML
    private Label text;

    @FXML
    public void ChangetoLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/mycompany/prj/Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    @FXML
    public void Register(ActionEvent event) {
        try {
            Username = userName.getText();
            Password = password.getText();

            
            if (Username.isEmpty() || Password.isEmpty()) {
                text.setText("Please enter a username and password!");
                return;
            }

            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document;

            
            if (xmlFile.exists()) {
                document = documentBuilder.parse(xmlFile);
                document.getDocumentElement().normalize();
            } else {
                document = documentBuilder.newDocument();
                Element root = document.createElement("data");
                document.appendChild(root);
            }

            
            Element root = document.getDocumentElement();
            Element user = document.createElement("user");
            root.appendChild(user);

            Element data_username = document.createElement("name");
            data_username.appendChild(document.createTextNode(Username));
            user.appendChild(data_username);

            Element data_password = document.createElement("password");
            data_password.appendChild(document.createTextNode(Password));
            user.appendChild(data_password);

            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.transform(domSource, streamResult);

            text.setText("Registration successful!");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            text.setText("Error occurred during registration!");
            e.printStackTrace();
        }
    }
}
