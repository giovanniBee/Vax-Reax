module com.vaxreact {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires org.postgresql.jdbc;
    requires org.apache.commons.lang3;


    opens com.vaxreact to javafx.fxml;
    exports com.vaxreact;
}