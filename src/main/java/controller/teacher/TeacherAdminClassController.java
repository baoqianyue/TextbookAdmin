package main.java.controller.teacher;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.datafx.controller.ViewController;
import javafx.beans.binding.Bindings;
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
import main.java.bean.BookGrant;
import main.java.bean.BookSub;
import main.java.daoimpl.ClassImpl;
import main.java.daoimpl.GrantBookImpl;
import main.java.daoimpl.SubBookImpl;
import main.java.utils.TextUtils;
import main.java.utils.Toast;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Function;

@ViewController(value = "../../../resources/layout/layout_teacher_ad_class.fxml")
public class TeacherAdminClassController {

    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    @FXML
    public StackPane root;
    @FXML
    private Label treeTableViewCount;
    @FXML
    private JFXTreeTableView<Clazz> treeTableView;
    @FXML
    private JFXTreeTableColumn<Clazz, String> cnoColumn;
    @FXML
    private JFXTreeTableColumn<Clazz, Integer> cnumColumn;
    @FXML
    private JFXTreeTableColumn<Clazz, String> cnoTelColumn;
    @FXML
    private JFXTreeTableColumn<Clazz, String> leaderSnoColumn;
    @FXML
    private JFXTreeTableColumn<Clazz, String> leaderSnameColumn;
    @FXML
    private JFXTreeTableColumn<Clazz, String> spasswordColumn;
    @FXML
    private JFXButton grantTextbookBtn;

    private ArrayList firstDataList;

    private ClassImpl classDB;

    private SubBookImpl subBookDB;

    private GrantBookImpl grantBookDB;


    @PostConstruct
    public void init() {
        classDB = new ClassImpl();
        subBookDB = new SubBookImpl();
        grantBookDB = new GrantBookImpl();
        //初始化表数据
        initAdminClassData();
    }

    private <T> void setUpColumnValue(JFXTreeTableColumn<Clazz, T> column,
                                      Function<Clazz, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Clazz, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void initAdminClassData() {
        //在数据库中查询表格信息
        try {
            firstDataList = (ArrayList) classDB.queryALLClass();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setUpColumnValue(cnoColumn, Clazz::classIdProperty);
        setUpColumnValue(cnumColumn, p -> p.classNum.asObject());
        setUpColumnValue(cnoTelColumn, Clazz::classTelProperty);
        setUpColumnValue(leaderSnoColumn, Clazz::leaderIdProperty);
        setUpColumnValue(leaderSnameColumn, Clazz::leaderNameProperty);
        setUpColumnValue(spasswordColumn, Clazz::classPasswordProperty);

        ObservableList<Clazz> data = firstData2FxData(firstDataList);

        grantTextbookBtn.setOnMouseClicked(event -> {
            Clazz clazz = treeTableView.getSelectionModel().selectedItemProperty().get().getValue();
            //开启dialog进行发放登记
            //1.查询征订表中该教材的数量是否满足要求
            //2.向发放表中插入发放数据
            //3.在征订表中更新该教材的数量
            JFXAlert alert = new JFXAlert((Stage) grantTextbookBtn.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Label("向该班级发放教材"));
            JFXTextField bnoId = new JFXTextField();
            bnoId.setPromptText("输入要发放的教材编号");
            JFXTextField bname = new JFXTextField();
            bname.setPromptText("输入要发放的教材名");
            JFXTextField bissuenum = new JFXTextField();
            bissuenum.setPromptText("输入要发放的教材数量");
            VBox vBox = new VBox(bnoId, bname, bissuenum);
            layout.setBody(vBox);
            JFXButton commitBtn = new JFXButton("提交");
            commitBtn.getStyleClass().add("dialog-accept");
            commitBtn.setOnAction(event1 -> {
                if (!TextUtils.isEmpty(bnoId.getText()) && !TextUtils.isEmpty(bname.getText())
                        && !TextUtils.isEmpty(bissuenum.getText())) {
                    String bnoStr = bnoId.getText().trim();
                    String bnameStr = bname.getText().trim();
                    String clazzId = clazz.classId.get();
                    //从输入框中获取的发放数量
                    int bnum = Integer.parseInt(bissuenum.getText());
                    //
                    int preBnum = 0;
                    //查询该教材的征订数量，确定是否满足要求
                    try {
                        preBnum = subBookDB.querySubBookNumById(bnoStr);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (preBnum < bnum) {
                        Toast.show(root, "教材数量不足，无法发放");
                        return;
                    } else {
                        //向发放表中插入
                        //在征订表中更新该教材的数量
                        try {
                            grantBookDB.addGrantBook(new BookGrant(bnoStr, clazzId, bnameStr, bnum));
                            subBookDB.updateGrantBook(bnoStr, bnum);
                            alert.hideWithAnimation();
                            Toast.show(root, "发放成功");
                        } catch (SQLException e) {
                            Toast.show(root, "向发放表中插入失败");
                            e.printStackTrace();
                            return;
                        }
                    }

                } else {
                    Toast.show(root, "输入框不能为空");
                }
            });
            layout.setActions(commitBtn);
            alert.setContent(layout);
            alert.show();
        });


        treeTableView.setRoot(new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);
        treeTableView.setEditable(true);
        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() +
                        POSTFIX, treeTableView.currentItemsCountProperty()));
    }

    /**
     * 转换list类型
     *
     * @param firstDataList
     * @return
     */
    private ObservableList<Clazz> firstData2FxData(ArrayList firstDataList) {
        final ObservableList<Clazz> data = FXCollections.observableArrayList();
        for (int i = 0; i < firstDataList.size(); i++) {
            main.java.bean.Clazz srcClass = (main.java.bean.Clazz) firstDataList.get(i);
            Clazz clazz = new Clazz(srcClass.getClassId(), srcClass.getClassNum(), srcClass.getClassTel(),
                    srcClass.getLeaderId(), srcClass.getLeaderName(), srcClass.getClassPassword());
            data.add(clazz);
        }
        return data;
    }

    static final class Clazz extends RecursiveTreeObject<Clazz> {
        final StringProperty classId;
        final SimpleIntegerProperty classNum;
        final StringProperty classTel;
        final StringProperty leaderId;
        final StringProperty leaderName;
        final StringProperty classPassword;

        public Clazz(String classId, int classNum, String classTel,
                     String leaderId, String leaderName, String classPassword) {
            this.classId = new SimpleStringProperty(classId);
            this.classNum = new SimpleIntegerProperty(classNum);
            this.classTel = new SimpleStringProperty(classTel);
            this.leaderId = new SimpleStringProperty(leaderId);
            this.leaderName = new SimpleStringProperty(leaderName);
            this.classPassword = new SimpleStringProperty(classPassword);
        }


        public StringProperty classIdProperty() {
            return classId;
        }


        public SimpleIntegerProperty classNumProperty() {
            return classNum;
        }


        public StringProperty classTelProperty() {
            return classTel;
        }


        public StringProperty leaderIdProperty() {
            return leaderId;
        }


        public StringProperty leaderNameProperty() {
            return leaderName;
        }


        public StringProperty classPasswordProperty() {
            return classPassword;
        }
    }
}
