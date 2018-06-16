package main.java.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    //主布局(舞台)
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //加载布局
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/layout/Login.fxml"));
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


    public static void main(String[] args) {
        launch(args);
    }

    //向外提供获取主舞台方法
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
