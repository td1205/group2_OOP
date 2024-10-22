package com.mycompany.nhom2_quanlyanphamtrongthuvien;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainMenuController implements Initializable {

    @FXML
    private TextField textId;

    @FXML
    private TextField textName;

    @FXML
    private TextField textType;
    @FXML
    private TextField textCost;

    @FXML
    private TextField textQuantity;

    @FXML
    private TextField textNXB;

    @FXML
    private TextField textAuthor;

    @FXML
    private Button Add;
    @FXML
    private TableView<Danhsachanpham> table;

    @FXML
    private TableColumn<Danhsachanpham, String> Id;
    @FXML
    private TableColumn<Danhsachanpham, String> Name;
    @FXML
    private TableColumn<Danhsachanpham, String> Type;
    @FXML
    private TableColumn<Danhsachanpham, String> Cost;
    @FXML
    private TableColumn<Danhsachanpham, Integer> Quantity;
    @FXML
    private TableColumn<Danhsachanpham, String> Nxb;
    @FXML
    private TableColumn<Danhsachanpham, String> Author;

    ObservableList<Danhsachanpham> list = FXCollections.observableArrayList(
            new Danhsachanpham("222", "jack", "book", "2", "minh", "le", 1)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        Nxb.setCellValueFactory(new PropertyValueFactory<>("nxb"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));

        // Set items to the table
        table.setItems(list);
    }

    @FXML
    public void add(ActionEvent event) {
        // Lấy dữ liệu từ các trường văn bản
        String id = textId.getText();
        String name = textName.getText();
        String type = textType.getText();
        String cost = textCost.getText();
        int quantity = Integer.parseInt(textQuantity.getText());
        String nxb = textNXB.getText();
        String author = textAuthor.getText();

        // Tạo đối tượng Danhsachanpham mới
        Danhsachanpham newProduct = new Danhsachanpham(id, name, type, cost, nxb, author, quantity);

        // Thêm đối tượng mới vào danh sách
        list.add(newProduct);
        // Xóa nội dung trong các trường văn bản sau khi thêm
        
    }

}
