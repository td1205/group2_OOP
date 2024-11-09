module com.mycompany.prj {
    requires javafx.controls;
    requires javafx.fxml;
requires java.sql;
    opens com.mycompany.prj to javafx.fxml;
    exports com.mycompany.prj;
}
