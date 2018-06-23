package main.java.controller.admin;

import com.jfoenix.controls.JFXToggleButton;
import io.datafx.controller.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import main.java.db.JDBCHelper;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@ViewController(value = "../../../resources/layout/layout_admin_switch.fxml")
public class AdminSubGrantController {

    @FXML
    private StackPane root;
    @FXML
    private JFXToggleButton teacherSwitchTBtn;
    @FXML
    private JFXToggleButton classSwitchTBtn;

    private Connection conn = null;
    private Statement stat = null;

    @PostConstruct
    public void init() {

        ToggleGroup teacherGroup = new ToggleGroup();
        teacherSwitchTBtn.setToggleGroup(teacherGroup);
        teacherGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("关闭开关");
                try {
                    conn = JDBCHelper.getsInstance().getConnection();
                    stat = conn.createStatement();
                    stat.execute("ALTER DATABASE [TextbookAdmin] SET READ_ONLY WITH NO_WAIT");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    conn = JDBCHelper.getsInstance().getConnection();
                    stat = conn.createStatement();
                    stat.execute("ALTER DATABASE [TextbookAdmin] SET READ_WRITE WITH NO_WAIT");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("打开开关");
            }
        });

    }
}
