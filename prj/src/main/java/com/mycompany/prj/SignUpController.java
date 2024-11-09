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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class SignUpController {

    private Scene scene;
    private Stage stage;
    private Parent root;
    private String Username;
    private String Password;
    public static final String xmlFilePath = "C:\\Users\\Tire\\Documents\\NetBeansProjects\\prj\\src\\main\\java\\com\\mycompany\\prj\\data.xml"; // Kiểm tra lại đường dẫn này nếu cần

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
    public void Register(ActionEvent event) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        Username = userName.getText();
        Password = password.getText();

        // Kiểm tra nếu Username và Password đã được nhập
        if (Username.isEmpty() || Password.isEmpty()) {
            text.setText("Vui lòng nhập tên đăng nhập và mật khẩu!");
            return;
        }

        File xmlFile = new File(xmlFilePath);

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document;

        // Đọc tài liệu XML hiện có hoặc tạo tài liệu mới
        if (xmlFile.exists()) {
            document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            System.out.println("Da doc file hien co");
        } else {
            // Tạo tài liệu XML mới với phần tử gốc <data>
            document = documentBuilder.newDocument();
            Element root = document.createElement("data");
            document.appendChild(root);
            System.out.println("file moi se duoc tao");
        }

        // Thêm phần tử "user" mới vào tài liệu XML
        Element root = document.getDocumentElement();
        Element user = document.createElement("user");
        root.appendChild(user);

        Element data_username = document.createElement("name");
        data_username.appendChild(document.createTextNode(Username));
        user.appendChild(data_username);

        Element data_password = document.createElement("password");
        data_password.appendChild(document.createTextNode(Password));
        user.appendChild(data_password);

        // Ghi lại tài liệu XML vào tệp
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(xmlFile);
        transformer.transform(domSource, streamResult);

        System.out.println("Luu thanh cong " + xmlFile.getAbsolutePath());

        text.setText("Đăng ký thành công!");
    }
}
