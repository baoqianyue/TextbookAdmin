package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.app.MainApp;
import main.java.bean.Clazz;
import main.java.bean.Teacher;
import main.java.daoimpl.ClassImpl;
import main.java.daoimpl.TeacherImpl;
import main.java.utils.Statics;
import main.java.utils.TextUtils;
import main.java.utils.Toast;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

/**
 * 个人信息修改
 */
@ViewController(value = "../../resources/layout/layout_person.fxml")
public class PersonController {

    @FXML
    private StackPane root;
    @FXML
    private JFXDialogLayout content;
    @FXML
    private VBox dialogGroup;
    @FXML
    private JFXTextField textfieldOne;
    @FXML
    private JFXTextField textfieldTwo;
    @FXML
    private JFXTextField textfieldThree;
    @FXML
    private JFXTextField textfieldFour;
    @FXML
    private JFXTextField textfieldFive;
    @FXML
    private JFXTextField textfieldSix;
    @FXML
    private JFXTextField textfieldSeven;

    @FXML
    private JFXButton commitBtn;


    private TeacherImpl teacherDB;
    private ClassImpl classDB;

    private Teacher teacher;
    private Clazz clazz;


    @PostConstruct
    public void init() throws SQLException {
        teacherDB = new TeacherImpl();
        classDB = new ClassImpl();
        if (Statics.TYPE_CURR.equals(Statics.TYPE_TEACHRE)) {
            teacher = teacherDB.queryTeacherById(Statics.CURR_USERNAME);
            textfieldOne.setText(teacher.getId());
            textfieldOne.setDisable(true);
            textfieldTwo.setPromptText("姓名： " + teacher.getName());
            textfieldThree.setPromptText("性别: " + teacher.getSex());
            textfieldFour.setPromptText("权限： " + teacher.getRight());
            textfieldFour.setDisable(true);
            textfieldFive.setPromptText("电话: " + teacher.getTel());
            textfieldSix.setPromptText("邮箱： " + teacher.getEmail());
            textfieldSeven.setPromptText("密码: " + teacher.getPassword());
            commitBtn.setOnAction(event -> {
                String newName = textfieldTwo.getText();
                String newSex = textfieldThree.getText();
                String newTel = textfieldFive.getText();
                String newEmail = textfieldSix.getText();
                String newPassword = textfieldSeven.getText();
                if (!TextUtils.isEmpty(newName) && !TextUtils.isEmpty(newSex) &&
                        !TextUtils.isEmpty(newTel) && !TextUtils.isEmpty(newEmail)
                        && !TextUtils.isEmpty(newPassword)) {
                    try {
                        teacherDB.updateTeacher(new Teacher(
                                teacher.getId(), newName, newSex, teacher.getRight(), newTel, newEmail, teacher.getPassword()
                        ));
                        if (!newPassword.equals(teacher.getPassword())) {
                            Stage stage = (Stage) commitBtn.getScene().getWindow();
                            stage.close();
                            MainApp.getPrimaryStage().show();
                        }
                        Toast.show(root, "修改信息成功");
                    } catch (SQLException e) {
                        Toast.show(root, "修改信息失败");
                        e.printStackTrace();
                        return;
                    }
                } else {
                    Toast.show(root, "输入框不能为空");
                    return;
                }
            });
        } else {
            clazz = classDB.queryClassById(Statics.CURR_USERNAME);
            textfieldOne.setText(clazz.getClassId());
            textfieldOne.setDisable(true);
            textfieldTwo.setPromptText(clazz.getTeacherId());
            textfieldThree.setPromptText(String.valueOf(clazz.getClassNum()));
            textfieldFour.setPromptText(clazz.getClassTel());
            textfieldFive.setPromptText(clazz.getLeaderId());
            textfieldSix.setPromptText(clazz.getLeaderName());
            textfieldSeven.setPromptText(clazz.getClassPassword());
            commitBtn.setOnAction(event -> {
                String newTeacherId = textfieldTwo.getText();
                String newClassNum = textfieldThree.getText();
                String newClassTel = textfieldFour.getText();
                String newLeaderId = textfieldFive.getText();
                String newLeaderName = textfieldSix.getText();
                String newPassword = textfieldSeven.getText();
                if (!TextUtils.isEmpty(newTeacherId) && !TextUtils.isEmpty(String.valueOf(newClassNum))
                        && !TextUtils.isEmpty(newClassTel) && !TextUtils.isEmpty(newLeaderId)
                        && !TextUtils.isEmpty(newLeaderName)) {
                    Clazz clayy = new Clazz(clazz.getClassId(), newTeacherId, Integer.parseInt(newClassNum), newClassTel,
                            newLeaderId, newLeaderName, newPassword);
                    try {
                        classDB.updateClass(clayy);
                        if (!newPassword.equals(clazz.getClassPassword())) {
                            Stage stage = (Stage) commitBtn.getScene().getWindow();
                            stage.close();
                            MainApp.getPrimaryStage().show();
                        }
                        Toast.show(root, "修改信息成功");
                    } catch (SQLException e) {
                        Toast.show(root, "修改信息失败");
                        e.printStackTrace();
                        return;
                    }
                } else {
                    Toast.show(root, "输入框不能为空");
                    return;
                }
            });

        }

    }
}
