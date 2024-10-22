module com.mycompany.nhom2_quanlyanphamtrongthuvien {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.nhom2_quanlyanphamtrongthuvien to javafx.fxml;
    exports com.mycompany.nhom2_quanlyanphamtrongthuvien;
}
