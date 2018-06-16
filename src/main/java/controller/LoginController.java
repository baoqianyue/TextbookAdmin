package main.java.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import main.java.app.Main;
import main.java.bean.Teacher;
import main.java.daoimpl.TeacherImpl;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    private Scene scene;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView studentView;
    @FXML
    private ImageView teacherView;
    @FXML
    private ImageView adminView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ChoiceBox imagePicker;

    private double xOffset;
    private double yOffset;

    //初始化回调
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //默认显示班级登录
        imagePicker.getSelectionModel().selectFirst();

        //设置窗口拖放
        borderPane.setOnMousePressed(event -> {
            xOffset = Main.getPrimaryStage().getX() - event.getSceneX();
            yOffset = Main.getPrimaryStage().getY() - event.getSceneY();
            borderPane.setCursor(Cursor.CLOSED_HAND);
        });

        borderPane.setOnMouseDragged(event -> {
            Main.getPrimaryStage().setX(event.getSceneX() + xOffset);
            Main.getPrimaryStage().setY(event.getSceneY() + yOffset);
        });

        borderPane.setOnMouseReleased(event -> {
            borderPane.setCursor(Cursor.DEFAULT);
        });

        //选择不同的用户类型切换头像
        imagePicker.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldImg, newImg) -> {
                    if (oldImg != null) {
                        switch (oldImg) {
                            case "班级登录":
                                studentView.setVisible(false);
                                break;
                            case "教师登录":
                                teacherView.setVisible(false);
                                break;
                            case "管理员登录":
                                adminView.setVisible(false);
                        }
                    }
                    if (newImg != null) {
                        switch (newImg) {
                            case "班级登录":
                                studentView.setVisible(true);
                                break;
                            case "教师登录":
                                teacherView.setVisible(true);
                                break;
                            case "管理员登录":
                                adminView.setVisible(true);
                        }
                    }
                });
        //设置登录界面背景动画
        int numberOfSquares = 30;
        while (numberOfSquares > 0) {
            maintainAnimation();
            numberOfSquares--;
        }
    }

    /**
     * 生成背景动画
     */
    private void maintainAnimation() {
        Random random = new Random();
        int sizeOfSqaure = random.nextInt(50) + 1;
        int speedOfSqaure = random.nextInt(10) + 5;
        int startXPoint = random.nextInt(420);
        int startYPoint = random.nextInt(350);
        int direction = random.nextInt(5) + 1;

        KeyValue moveXAxis = null;
        KeyValue moveYAxis = null;
        Rectangle r1 = null;

        switch (direction) {
            case 1:
                // MOVE LEFT TO RIGHT
                r1 = new Rectangle(0, startYPoint, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 350 - sizeOfSqaure);
                break;
            case 2:
                // MOVE TOP TO BOTTOM
                r1 = new Rectangle(startXPoint, 0, sizeOfSqaure, sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSqaure);
                break;
            case 3:
                // MOVE LEFT TO RIGHT, TOP TO BOTTOM
                r1 = new Rectangle(startXPoint, 0, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 350 - sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSqaure);
                break;
            case 4:
                // MOVE BOTTOM TO TOP
                r1 = new Rectangle(startXPoint, 420 - sizeOfSqaure, sizeOfSqaure, sizeOfSqaure);
                moveYAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 5:
                // MOVE RIGHT TO LEFT
                r1 = new Rectangle(420 - sizeOfSqaure, startYPoint, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 6:
                //MOVE RIGHT TO LEFT, BOTTOM TO TOP
                r1 = new Rectangle(startXPoint, 0, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 350 - sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSqaure);
                break;

            default:
                System.out.println("default");
        }

        r1.setFill(Color.web("#669966"));
        r1.setOpacity(0.1);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(speedOfSqaure * 1000), moveXAxis, moveYAxis);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        borderPane.getChildren().add(borderPane.getChildren().size() - 1, r1);

    }


    /**
     * 登录逻辑
     *
     * @param actionEvent
     * @throws Exception
     */
    public void loginButtonAction(ActionEvent actionEvent) throws Exception {

    }

    /**
     * 最小化窗口
     *
     * @param actionEvent
     * @throws Exception
     */
    public void minimizeWindow(ActionEvent actionEvent) throws Exception {
        Main.getPrimaryStage().setIconified(true);
    }


    /**
     * 关闭系统
     *
     * @param actionEvent
     * @throws Exception
     */
    public void closeSystem(ActionEvent actionEvent) throws Exception {
        Platform.exit();
        System.exit(0);
    }
}
