package main.java.controller.clazz;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.datafx.controller.ViewController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import main.java.controller.teacher.TextbookSubController;

import javax.annotation.PostConstruct;
import java.util.function.Function;

@ViewController(value = "../../../resources/layout/layout_get_textbook.fxml")
public class ClassGetTextbookController {

    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    @FXML
    public StackPane root;
    @FXML
    private JFXTreeTableView<GetBook> treeTableView;
    @FXML
    private JFXTreeTableColumn<GetBook, String> bnoColumn;
    @FXML
    private JFXTreeTableColumn<GetBook, String> bnameColumn;
    @FXML
    private JFXTreeTableColumn<GetBook, String> leaderNameColumn;
    @FXML
    private JFXTreeTableColumn<GetBook, String> leaderSnoColumn;
    @FXML
    private JFXTreeTableColumn<GetBook, Integer> getBookNumColumn;
    @FXML
    private JFXButton getTextbookBtn;
    @FXML
    private Label treeTableViewCount;


    @PostConstruct
    public void init() {

        initGetBookTable();
    }

    private <T> void setUpColumnValue(JFXTreeTableColumn<GetBook, T> column,
                                      Function<GetBook, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<GetBook, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void initGetBookTable() {
    }

    static final class GetBook extends RecursiveTreeObject<GetBook> {
        final StringProperty bno;
        final StringProperty bname;
        final StringProperty leaderSno;
        final StringProperty leaderName;
        final SimpleIntegerProperty getBookNum;

        GetBook(String bno, String bname, String leaderSno, String leaderName, int getBookNum) {
            this.bno = new SimpleStringProperty(bno);
            this.bname = new SimpleStringProperty(bname);
            this.leaderSno = new SimpleStringProperty(leaderSno);
            this.leaderName = new SimpleStringProperty(leaderName);
            this.getBookNum = new SimpleIntegerProperty(getBookNum);
        }

        public String getBno() {
            return bno.get();
        }

        public StringProperty bnoProperty() {
            return bno;
        }

        public String getLeaderSno() {
            return leaderSno.get();
        }

        public StringProperty leaderSnoProperty() {
            return leaderSno;
        }

        public String getLeaderName() {
            return leaderName.get();
        }

        public StringProperty leaderNameProperty() {
            return leaderName;
        }

        public int getGetBookNum() {
            return getBookNum.get();
        }

        public SimpleIntegerProperty getBookNumProperty() {
            return getBookNum;
        }
    }

}
