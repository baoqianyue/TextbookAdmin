package main.java.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.controller.LoginController;
import main.java.controller.MainController;
import sun.applet.Main;

import java.io.InputStream;

public class MainApp extends Application {

    //主布局(舞台)
    private static Stage primaryStage;
    private Group root = new Group();

    private static MainApp sInstance;



    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //加载布局
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/layout/layout_login.fxml"));
        //取消系统默认装饰
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("教材征订与发放系统");
//        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource()))
        Scene mainScene = new Scene(root, 350, 420);
        mainScene.setRoot(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        //退出
        primaryStage.setOnCloseRequest(event -> Platform.exit());
    }

    public Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MainApp.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainApp.class.getResource(fxml));
        Pane pane;
        try {
            pane = loader.load(in);
        } finally {
            in.close();
        }
        root.requestFocus();
        root.getChildren().removeAll();
        root.getChildren().clear();
        root.getChildren().addAll(pane);

        return (Initializable) loader.getController();
    }

//    public void goToMain() {
//        try {
//            MainController main = (MainController) replaceSceneContent("../../resources/layout/layout_main.fxml");
//            //设置主界面大小
//            primaryStage.setWidth(1200);
//            primaryStage.setHeight(700);
//            main.setApp(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        launch(args);
    }

    //向外提供获取主舞台方法
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
