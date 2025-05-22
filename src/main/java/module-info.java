module com.ali.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.ali.javafx to javafx.fxml;
    exports com.ali.javafx;
}