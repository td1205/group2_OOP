package com.mycompany.prj;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MainController implements Initializable {

    public static final String xmlFilePath = "src/main/resources/com/mycompany/prj/table_data.xml";

    private ObservableList<Product> productList;
    private final String[] productType = {"sách", "tiểu thuyết", "giáo trình", "tạp chí", "báo", "tập san", "sách tranh"};
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField amount;
    @FXML
    private TextField nxb;
    @FXML
    private TextField author;
    @FXML
    private Label text;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> typeColumn;
    @FXML
    private TableColumn<Product, Integer> priceColumn;
    @FXML
    private TableColumn<Product, Integer> amountColumn;
    @FXML
    private TableColumn<Product, String> nxbColumn;
    @FXML
    private TableColumn<Product, String> authorColumn;

    private int idCounter = 0; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.getItems().addAll(productType);
        productList = FXCollections.observableArrayList();
        loadProductData();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        nxbColumn.setCellValueFactory(new PropertyValueFactory<>("nxb"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        tableView.setItems(productList);
    }

    private void loadProductData() {
        try {
            File xmlFile = new File(xmlFilePath);
            if (!xmlFile.exists()) {
                return;
            }

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList bookNodes = document.getElementsByTagName("book");

            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element bookElement = (Element) bookNodes.item(i);

                int id = Integer.parseInt(bookElement.getElementsByTagName("id").item(0).getTextContent());
                String name = bookElement.getElementsByTagName("name").item(0).getTextContent();
                String type = bookElement.getElementsByTagName("type").item(0).getTextContent();
                int price = Integer.parseInt(bookElement.getElementsByTagName("price").item(0).getTextContent());
                int amount = Integer.parseInt(bookElement.getElementsByTagName("amount").item(0).getTextContent());
                String nxb = bookElement.getElementsByTagName("nxb").item(0).getTextContent();
                String author = bookElement.getElementsByTagName("author").item(0).getTextContent();

                productList.add(new Product(id, name, type, price, amount, nxb, author));

                if (id > idCounter) {
                    idCounter = id;
                }
            }
        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
        }
    }

    @FXML
    @SuppressWarnings("empty-statement")
    public void Add(ActionEvent event) throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException {
        int Price, Amount;
        String Name = name.getText();
        String NXB = nxb.getText();
        String Author = author.getText();
        String Type = type.getValue();

        if (Name.isEmpty() || NXB.isEmpty() || Author.isEmpty()) {
            text.setText("Vui lòng nhập đầy đủ thông tin!!!");
            return;
        }
        try {
            Price = Integer.parseInt(price.getText().replaceAll("\\.", ""));
            Amount = Integer.parseInt(amount.getText());
        } catch (NumberFormatException e) {
            text.setText("Vui lòng nhập số nguyên!!!");
            return;
        }

        if (Type == null) {
            text.setText("Vui lòng chọn loại sản phẩm!");
            return;
        }

        int Id = ++idCounter;
        boolean duplicateFound = false;

        File xmlFile = new File(xmlFilePath);
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document;

        if (xmlFile.exists()) {
            document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList bookNodes = document.getElementsByTagName("book");

            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element bookElement = (Element) bookNodes.item(i);

                String xmlName = bookElement.getElementsByTagName("name").item(0).getTextContent().toLowerCase();
                String xmlType = bookElement.getElementsByTagName("type").item(0).getTextContent().toLowerCase();
                int xmlPrice = Integer.parseInt(bookElement.getElementsByTagName("price").item(0).getTextContent());
                int xmlAmount = Integer.parseInt(bookElement.getElementsByTagName("amount").item(0).getTextContent());
                String xmlNxb = bookElement.getElementsByTagName("nxb").item(0).getTextContent().toLowerCase();
                String xmlAuthor = bookElement.getElementsByTagName("author").item(0).getTextContent().toLowerCase();

                if (Name.equalsIgnoreCase(xmlName) && Type.equalsIgnoreCase(xmlType)
                        && Price == xmlPrice && Amount == xmlAmount
                        && NXB.equalsIgnoreCase(xmlNxb) && Author.equalsIgnoreCase(xmlAuthor)) {
                    duplicateFound = true;
                    break;
                }
            }
        } else {
            document = documentBuilder.newDocument();
            Element root = document.createElement("data");
            document.appendChild(root);
        }

        if (duplicateFound) {
            text.setText("Đã trùng lặp, vui lòng thử lại!!!");
        } else {
            Element root = document.getDocumentElement();
            Element book = document.createElement("book");
            root.appendChild(book);

            Element data_id = document.createElement("id");
            data_id.appendChild(document.createTextNode(String.valueOf(Id)));
            book.appendChild(data_id);

            Element data_name = document.createElement("name");
            data_name.appendChild(document.createTextNode(Name));
            book.appendChild(data_name);

            Element data_type = document.createElement("type");
            data_type.appendChild(document.createTextNode(Type));
            book.appendChild(data_type);

            Element data_price = document.createElement("price");
            data_price.appendChild(document.createTextNode(String.valueOf(Price)));
            book.appendChild(data_price);

            Element data_amount = document.createElement("amount");
            data_amount.appendChild(document.createTextNode(String.valueOf(Amount)));
            book.appendChild(data_amount);

            Element data_nxb = document.createElement("nxb");
            data_nxb.appendChild(document.createTextNode(NXB));
            book.appendChild(data_nxb);

            Element data_author = document.createElement("author");
            data_author.appendChild(document.createTextNode(Author));
            book.appendChild(data_author);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.transform(domSource, streamResult);

            text.setText("Thêm thành công!!!");
            Product newProduct = new Product(Id, Name, Type, Price, Amount, NXB, Author);
            productList.add(newProduct);
        }
    }

    @FXML
    public void Delete(ActionEvent event) {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            text.setText("Vui lòng chọn một sản phẩm để xóa.");
            return;
        }

        productList.remove(selected);

        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            NodeList bookNodes = document.getElementsByTagName("book");
            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element bookElement = (Element) bookNodes.item(i);
                String idValue = bookElement.getElementsByTagName("id").item(0).getTextContent();

                
                if (Integer.parseInt(idValue) == selected.getId()) {
                    bookElement.getParentNode().removeChild(bookElement);
                    break;
                }
            }

            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.transform(domSource, streamResult);

            text.setText("Xóa thành công!!!");

        } catch (IOException | NumberFormatException | ParserConfigurationException | TransformerException | DOMException | SAXException e) {
            text.setText("Lỗi khi xóa!!!");
        }
    }

    @FXML
    public void Edit(ActionEvent event) {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        Product temp = selected;
        if (selected == null) {
            text.setText("Vui lòng chọn một sản phẩm để sửa.");
            return;
        }

        try {
            
            String updatedName = name.getText();
            String updatedType = type.getValue();
            int updatedPrice = Integer.parseInt(price.getText());
            int updatedAmount = Integer.parseInt(amount.getText());
            String updatedNxb = nxb.getText();
            String updatedAuthor = author.getText();

            
            if (updatedName.isEmpty() || updatedNxb.isEmpty() || updatedAuthor.isEmpty()) {
                text.setText("Vui lòng nhập đầy đủ thông tin!");
                return;
            }


            selected.setName(updatedName);
            selected.setType(updatedType);
            selected.setPrice(updatedPrice);
            selected.setAmount(updatedAmount);
            selected.setNxb(updatedNxb);
            selected.setAuthor(updatedAuthor);


            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();


            NodeList bookNodes = document.getElementsByTagName("book");
            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element bookElement = (Element) bookNodes.item(i);
                String idValue = bookElement.getElementsByTagName("id").item(0).getTextContent();


                if (Integer.parseInt(idValue) == selected.getId()) {
                    bookElement.getElementsByTagName("name").item(0).setTextContent(updatedName);
                    bookElement.getElementsByTagName("type").item(0).setTextContent(updatedType);
                    bookElement.getElementsByTagName("price").item(0).setTextContent(String.valueOf(updatedPrice));
                    bookElement.getElementsByTagName("amount").item(0).setTextContent(String.valueOf(updatedAmount));
                    bookElement.getElementsByTagName("nxb").item(0).setTextContent(updatedNxb);
                    bookElement.getElementsByTagName("author").item(0).setTextContent(updatedAuthor);
                    break;
                }
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.transform(domSource, streamResult);

            text.setText("Sửa thông tin thành công!!!");

        } catch (NumberFormatException e) {
            text.setText("Vui lòng nhập giá và số lượng hợp lệ!");
        } catch (IOException | ParserConfigurationException | TransformerException | DOMException | SAXException e) {
            text.setText("Lỗi khi cập nhật XML.");
        }


        tableView.refresh();
    }

    @FXML
    void Reset(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn có chắc chắn muốn đặt lại toàn bộ dữ liệu?");
        ButtonType buttonYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType buttonNo = new ButtonType("Hủy", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            productList.clear();
            tableView.setItems(productList); 
            idCounter = 0;

            try {
                File xmlFile = new File(xmlFilePath);
                if (xmlFile.exists()) {
                    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                    Document document = documentBuilder.newDocument();

                    Element root = document.createElement("data");
                    document.appendChild(root);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource domSource = new DOMSource(document);
                    StreamResult streamResult = new StreamResult(xmlFile);
                    transformer.transform(domSource, streamResult);

                    text.setText("Reset thành công!!!");
                }
            } catch (ParserConfigurationException | TransformerException e) {
                text.setText("Lỗi khi xóa dữ liệu");
            }
        }
    }

    @FXML
    void Search(ActionEvent event) {
        String nameInput = name.getText().trim().toLowerCase();
        String nxbInput = nxb.getText().trim().toLowerCase();
        String authorInput = author.getText().trim().toLowerCase();
        String typeInput = type.getValue() != null ? type.getValue().toLowerCase() : "";
        int priceInput;
        int amountInput;

        
        try {
            priceInput = Integer.parseInt(price.getText().trim());
            amountInput = Integer.parseInt(amount.getText().trim());
        } catch (NumberFormatException e) {
            text.setText("Vui lòng nhập giá và số lượng hợp lệ!");
            return;
        }

        ObservableList<Product> filteredList = FXCollections.observableArrayList();

        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList bookNodes = document.getElementsByTagName("book");

            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element bookElement = (Element) bookNodes.item(i);

                int id = Integer.parseInt(bookElement.getElementsByTagName("id").item(0).getTextContent());
                String xmlName = bookElement.getElementsByTagName("name").item(0).getTextContent().toLowerCase();
                String xmlType = bookElement.getElementsByTagName("type").item(0).getTextContent().toLowerCase();
                int xmlPrice = Integer.parseInt(bookElement.getElementsByTagName("price").item(0).getTextContent());
                int xmlAmount = Integer.parseInt(bookElement.getElementsByTagName("amount").item(0).getTextContent());
                String xmlNxb = bookElement.getElementsByTagName("nxb").item(0).getTextContent().toLowerCase();
                String xmlAuthor = bookElement.getElementsByTagName("author").item(0).getTextContent().toLowerCase();

                boolean match = true;


                if (!nameInput.isEmpty() && !xmlName.contains(nameInput)) {
                    match = false;
                }
                if (!typeInput.isEmpty() && !xmlType.contains(typeInput)) {
                    match = false;
                }
                if (!authorInput.isEmpty() && !xmlAuthor.contains(authorInput)) {
                    match = false;
                }
                if (!nxbInput.isEmpty() && !xmlNxb.contains(nxbInput)) {
                    match = false;
                }
                if (priceInput != 0 && xmlPrice != priceInput) {
                    match = false;
                }
                if (amountInput != 0 && xmlAmount != amountInput) {
                    match = false;
                }

                
                if (match) {
                    filteredList.add(new Product(id, xmlName, xmlType, xmlPrice, xmlAmount, xmlNxb, xmlAuthor));
                }
            }
        } catch (Exception e) {
            text.setText("Lỗi khi đọc dữ liệu từ XML.");
            return;
        }


        if (filteredList.isEmpty()) {
            text.setText("Không tìm thấy sản phẩm nào!");
        } else {
            tableView.setItems(filteredList);
            text.setText("Tìm thấy " + filteredList.size() + " kết quả!");
        }
    }

    @FXML
    void Show(ActionEvent event) {
        tableView.setItems(productList);
        text.setText("Hiển thị tất cả sản phẩm.");
    }

}
