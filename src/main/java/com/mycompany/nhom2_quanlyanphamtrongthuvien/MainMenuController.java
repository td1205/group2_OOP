package com.mycompany.nhom2_quanlyanphamtrongthuvien;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainMenuController implements Initializable{

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
}
