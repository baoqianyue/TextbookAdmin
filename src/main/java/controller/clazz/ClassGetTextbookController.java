package main.java.controller.clazz;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.datafx.controller.ViewController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import main.java.bean.ClassGetBook;
import main.java.daoimpl.GrantBookImpl;
import main.java.daoimpl.TextbookImpl;
import main.java.utils.Statics;
import main.java.utils.Toast;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private GrantBookImpl grantBookDB;
    private TextbookImpl textbookDB;

    private ArrayList firstDataList;


    @PostConstruct
    public void init() {
        grantBookDB = new GrantBookImpl();
        textbookDB = new TextbookImpl();
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
        try {
            firstDataList = (ArrayList) grantBookDB.queryAllGetBook(Statics.CURR_USERNAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setUpColumnValue(bnoColumn, GetBook::bnoProperty);
        setUpColumnValue(bnameColumn, GetBook::bnameProperty);
        setUpColumnValue(leaderSnoColumn, GetBook::leaderSnoProperty);
        setUpColumnValue(leaderNameColumn, GetBook::leaderNameProperty);
        setUpColumnValue(getBookNumColumn, p -> p.getBookNum.asObject());
        ObservableList<GetBook> data = firstData2ResData(firstDataList);


        getTextbookBtn.setOnAction(event -> {
            GetBook getBook = treeTableView.getSelectionModel().selectedItemProperty().get().getValue();
            //领取教材，在发放表中将对应记录删除
            //在教材库中将对应教材的数量更新
            try {
                grantBookDB.deleteGrantBook(getBook.getBno(), Statics.CURR_USERNAME);
                textbookDB.getTextbookUpdate(getBook.getBno(), getBook.getBookNum.get());
                data.remove(getBook);
                final IntegerProperty currCount = treeTableView.currentItemsCountProperty();
                currCount.set(currCount.get() - 1);
            } catch (SQLException e) {
                Toast.show(root, "领取失败");
                e.printStackTrace();
            }
        });


        treeTableView.setRoot(new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);
        treeTableView.setEditable(true);
        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() +
                        POSTFIX, treeTableView.currentItemsCountProperty()));
    }

    private ObservableList<GetBook> firstData2ResData(ArrayList firstDataList) {
        final ObservableList<GetBook> data = FXCollections.observableArrayList();
        for (int i = 0; i < firstDataList.size(); i++) {
            ClassGetBook obj1 = (ClassGetBook) firstDataList.get(i);
            GetBook obj2 = new GetBook(obj1.getBookId(), obj1.getBookName(), obj1.getClassLeaderId(),
                    obj1.getClassLeaderName(), obj1.getBookGetNum());
            data.add(obj2);
        }
        return data;
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

        public String getBname() {
            return bname.get();
        }

        public StringProperty bnameProperty() {
            return bname;
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
