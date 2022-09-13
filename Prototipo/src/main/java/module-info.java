module com.vaxreact {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires org.postgresql.jdbc;


    opens com.vaxreact to javafx.fxml;
    exports com.vaxreact;
}