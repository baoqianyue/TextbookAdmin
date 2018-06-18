package main.java.app;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.controller.MainController;


public class MainApp extends Application {

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    //主布局(舞台)
    private static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //加载布局
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/layout/layout_login.fxml"));
        //取消系统默认装饰
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("教材征订与发放系统");

        Scene mainScene = new Scene(root, 350, 420);
        mainScene.setRoot(root);
        final ObservableList<String> stylesheets = mainScene.getStylesheets();
        stylesheets.addAll(MainApp.class.getResource("../../resources/css/jfoenix-design.css").toExternalForm(),
                MainApp.class.getResource("../../resources/css/jfoenix-main-demo.css").toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        //退出
        primaryStage.setOnCloseRequest(event -> Platform.exit());
    }


    /*    public Initializable replaceSceneContent(String fxml) throws Exception {
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
        }*/
    public static void main(String[] args) {
        launch(args);
    }

    //向外提供获取主舞台方法
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
