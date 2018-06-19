package main.java.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.IntegerTextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.bean.TextBook;
import main.java.daoimpl.SubBookImpl;
import main.java.daoimpl.TextbookImpl;
import main.java.utils.TextUtils;
import main.java.utils.Toast;

import javax.annotation.PostConstruct;
import javax.xml.soap.Text;
import java.awt.print.Book;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Function;

import static javafx.scene.control.TreeTableColumn.*;

@ViewController(value = "../../resources/layout/layout_sub_textbook.fxml")
public class TextbookSubController {

    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    @FXML
    public StackPane root;
    @FXML
    private JFXTreeTableView<BookSub> treeTableView;
    @FXML
    private JFXTreeTableColumn<BookSub, String> bnoColumn;
    @FXML
    private JFXTreeTableColumn<BookSub, String> bNameColumn;
    @FXML
    private JFXTreeTableColumn<BookSub, String> tnoColumn;
    @FXML
    private JFXTreeTableColumn<BookSub, Integer> bSubNumColumn;
    @FXML
    private JFXButton treeTableViewAdd;
    @FXML
    private JFXButton treeTableViewRemove;
    @FXML
    private Label treeTableViewCount;

    private JFXDialogLayout dialogLayout;

    private SubBookImpl subBookDB;

    private ArrayList firstDataList;

    @PostConstruct
    public void init() {
        subBookDB = new SubBookImpl();
        initTextbookSubTable();
    }

    private <T> void setUpColumnValue(JFXTreeTableColumn<BookSub, T> column,
                                      Function<BookSub, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<BookSub, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    /**
     * 初始化征订表
     */
    private void initTextbookSubTable() {

        //加载表格中数据
        try {
            firstDataList = (ArrayList) subBookDB.queryAllSubBook();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setUpColumnValue(bnoColumn, BookSub::getBno);
        setUpColumnValue(bNameColumn, BookSub::getBname);
        setUpColumnValue(tnoColumn, BookSub::getTno);
        setUpColumnValue(bSubNumColumn, p -> p.bSubNum.asObject());

        ObservableList<BookSub> data = firstData2ResData(firstDataList);


        bSubNumColumn.setCellFactory((TreeTableColumn<BookSub, Integer> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new IntegerTextFieldEditorBuilder());
        });
        bSubNumColumn.setOnEditCommit((CellEditEvent<BookSub, Integer> t) -> {
            BookSub value;
            value = t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition().getRow())
                    .getValue();
            //更新表格数据
            value.bSubNum.set(t.getNewValue());

            // todo 将新数据更新到表BookSub
            try {
                subBookDB.updateSubBook(fxBookSub2BookSub(value));
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("数据更新失败");
            }
            //将新数据更新到表Textbook
            TextbookImpl textbookDB = new TextbookImpl();
            try {
                textbookDB.updateTextBook(value.bno.getValue(), value.bSubNum.getValue());
            } catch (SQLException e) {
                System.out.println("数据更新失败");
                e.printStackTrace();
            }

        });

        //增加征订
        ObservableList<BookSub> finalData = data;
        treeTableViewAdd.setOnMouseClicked(event -> {
            JFXAlert alert = new JFXAlert((Stage) treeTableViewAdd.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setOverlayClose(false);
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Label("增加征订一本教材"));
            JFXTextField bnoId = new JFXTextField();
            bnoId.setPromptText("输入教材编号");
            JFXTextField bname = new JFXTextField();
            bname.setPromptText("输入教材名");
            JFXTextField bsource = new JFXTextField();
            bsource.setPromptText("输入教材出版社");
            JFXTextField tnoId = new JFXTextField();
            tnoId.setPromptText("输入班主任教师号");
            JFXTextField subNum = new JFXTextField();
            subNum.setPromptText("输入征订数量");
            JFXTextField bdircetion = new JFXTextField();
            bdircetion.setPromptText("输入教材备注");
            VBox vBox = new VBox(bnoId, bname, bsource, tnoId, subNum, bdircetion);
            layout.setBody(vBox);
            JFXButton commitBtn = new JFXButton("提交");
            commitBtn.getStyleClass().add("dialog-accept");
            JFXButton cancelBtn = new JFXButton("取消");
            cancelBtn.getStyleClass().add("dialog-accept");
            cancelBtn.setOnAction(event1 -> alert.hideWithAnimation());
            commitBtn.setOnAction(event1 -> {
                if (!TextUtils.isEmpty(bnoId.getText()) && !TextUtils.isEmpty(bname.getText()) &&
                        !TextUtils.isEmpty(bsource.getText()) && !TextUtils.isEmpty(bdircetion.getText()) &&
                        !TextUtils.isEmpty(tnoId.getText()) && !TextUtils.isEmpty(subNum.getText())) {
                    String bnoStr = bnoId.getText();
                    String bnameStr = bname.getText();
                    String bsourceStr = bsource.getText();
                    String tnoStr = tnoId.getText();
                    int subNumint = Integer.parseInt(subNum.getText());
                    String bdircetionStr = bdircetion.getText();
                    BookSub sub = new BookSub(bnoStr, bnameStr, tnoStr, subNumint);
                    //向Textbook表中插入
                    TextbookImpl textbookDB = new TextbookImpl();
                    //向BookSub表中插入
                    try {
                        subBookDB.addSubBook(fxBookSub2BookSub(sub));
                        textbookDB.addTextBook(new TextBook(bnoStr, bnameStr, bsourceStr, subNumint, bdircetionStr));
                        finalData.add(sub);
                        final IntegerProperty currNum = treeTableView.currentItemsCountProperty();
                        currNum.set(currNum.get() + 1);
                        alert.hideWithAnimation();
                    } catch (SQLException e) {
                        Toast.show(root, "教材征订失败");
                        e.printStackTrace();
                    }

                } else {
                    Toast.show(root, "输入框不能为空!");
                    return;
                }

            });
            layout.setActions(commitBtn, cancelBtn);
            alert.setContent(layout);
            alert.show();
        });

        //减少征订
        treeTableViewRemove.setOnMouseClicked(event -> {
            BookSub sub = treeTableView.getSelectionModel().selectedItemProperty().get().getValue();
            //在Textbook表中删除
            TextbookImpl textbookDB = new TextbookImpl();
            //再BookSub表中删除
            try {
                subBookDB.deleteSubBook(sub.getBno().getValue());
                textbookDB.deleteTextBook(sub.getBno().getValue());
                data.remove(sub);
                final IntegerProperty currCount = treeTableView.currentItemsCountProperty();
                currCount.set(currCount.get() - 1);
            } catch (SQLException e) {
                Toast.show(root, "删除教材失败");
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

    /**
     * 将原生实体类对象BookSub转成属性类用于再表格上显示
     *
     * @param firstDataList
     * @return
     */
    private ObservableList<BookSub> firstData2ResData(ArrayList firstDataList) {
        final ObservableList<BookSub> data = FXCollections.observableArrayList();
        for (int i = 0; i < firstDataList.size(); i++) {
            main.java.bean.BookSub obj1 = (main.java.bean.BookSub) firstDataList.get(i);
            BookSub obj2 = new BookSub(obj1.getBookId(), obj1.getBookName(), obj1.getTeacherId(),
                    obj1.getSubBookNum());
            data.add(obj2);
        }
        return data;
    }

    /**
     * 将内部属性类BookSub对象转成数据库支持的类对象
     *
     * @param sub
     * @return
     */
    private main.java.bean.BookSub fxBookSub2BookSub(BookSub sub) {
        main.java.bean.BookSub obj = new main.java.bean.BookSub(sub.bno.getValue()
                , sub.bname.getValue(), sub.tno.getValue(), (int) sub.bSubNum.getValue());

        return obj;
    }

    static final class BookSub extends RecursiveTreeObject<BookSub> {

        final StringProperty bno;
        final StringProperty bname;
        final StringProperty tno;
        final SimpleIntegerProperty bSubNum;

        BookSub(String bno, String bname, String tno, int bnum) {
            this.bno = new SimpleStringProperty(bno);
            this.bname = new SimpleStringProperty(bname);
            this.tno = new SimpleStringProperty(tno);
            this.bSubNum = new SimpleIntegerProperty(bnum);
        }

        StringProperty getBno() {
            return bno;
        }

        StringProperty getBname() {
            return bname;
        }

        StringProperty getTno() {
            return tno;
        }

    }
}
