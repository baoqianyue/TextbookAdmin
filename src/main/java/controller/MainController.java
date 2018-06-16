package main.java.controller;


import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import io.datafx.controller.ViewController;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import main.java.app.MainApp;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

@ViewController(value = "../../resources/layout/layout_main.fxml")
public final class MainController {

    @FXML
    private StackPane root;

    @FXML
    private StackPane titleBurgerContainer;
    @FXML
    private JFXHamburger titleBurger;

    @FXML
    private JFXDrawer drawer;

    private JFXPopup toolbarPopup;
    private MainApp app;

    public void setApp(MainApp app) {
        this.app = app;
    }

    @PostConstruct
    public void init() throws Exception {
        //设置toolbar按钮
        drawer.setOnDrawerOpened(event -> {
            final Transition animation = titleBurger.getAnimation();
            animation.setRate(1);
            animation.play();
        });
        drawer.setOnDrawerClosed(event -> {
            final Transition animation = titleBurger.getAnimation();
            animation.setRate(-1);
            animation.play();
        });
        titleBurgerContainer.setOnMouseClicked(event -> {
            if (drawer.isClosed() || drawer.isClosing()) {
                drawer.open();
            } else {
                drawer.close();
            }
        });
    }

}
