package main.java.controller;


import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.app.MainApp;
import main.java.utils.Statics;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ViewController(value = "../../resources/layout/layout_slide_menu.fxml")
public class SideMenuController {


    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    @ActionTrigger("labelOne")
    private Label labelOne;
    @FXML
    @ActionTrigger("labelTwo")
    private Label labelTwo;
    @FXML
    @ActionTrigger("labelThree")
    private Label labelThree;
    @FXML
    @ActionTrigger("labelFour")
    private Label labelFour;
    @FXML
    @ActionTrigger("labelFive")
    private Label labelFive;

    @FXML
    private JFXListView<Label> sideList;

    private static String[] slideContentTitle;
    private static Class[] slideContentController;


    @PostConstruct
    public void init() {
        switch (Statics.TYPE_CURR) {
            case Statics.TYPE_TEACHRE:
                slideContentTitle = Statics.teacherSlideTitle;
                slideContentController = Statics.teacherSlideController;
                initTeacherSlide();
                break;
            case Statics.TYPE_CLASS:
                slideContentTitle = Statics.classSlideTitle;
                slideContentController = Statics.classSlideController;
                initClassSlide();
                break;
        }


//        bindNodeToController(labelFive, slideContentController[4], contentFlow, contentFlowHandler);

    }

    private void initClassSlide() {
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        sideList.propagateMouseEventsToParent();
        initFlow(contentFlowHandler);
        labelOne.setText(slideContentTitle[0]);
        labelTwo.setText(slideContentTitle[1]);
        labelThree.setText(slideContentTitle[2]);
        labelFour.setText(slideContentTitle[3]);
        labelFour.setOnMouseClicked(event -> {
            Stage stage = (Stage) labelFour.getScene().getWindow();
            stage.close();
            MainApp.getPrimaryStage().show();
        });
        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
        bindNodeToController(labelOne, slideContentController[0], contentFlow, contentFlowHandler);
        bindNodeToController(labelThree, slideContentController[2], contentFlow, contentFlowHandler);
    }

    public void initTeacherSlide() {
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        sideList.propagateMouseEventsToParent();
        initFlow(contentFlowHandler);

        labelOne.setText(slideContentTitle[0]);
        labelTwo.setText(slideContentTitle[1]);
        labelThree.setText(slideContentTitle[2]);
        labelFour.setText(slideContentTitle[3]);
        labelFive.setText(slideContentTitle[4]);
        labelFive.setOnMouseClicked(event -> {
            Stage stage = (Stage) labelFive.getScene().getWindow();
            stage.close();
            MainApp.getPrimaryStage().show();
        });

        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
        bindNodeToController(labelOne, slideContentController[0], contentFlow, contentFlowHandler);
        bindNodeToController(labelTwo, slideContentController[1], contentFlow, contentFlowHandler);
        bindNodeToController(labelThree, slideContentController[2], contentFlow, contentFlowHandler);
        bindNodeToController(labelFour, slideContentController[3], contentFlow, contentFlowHandler);

    }

    private void initFlow(FlowHandler contentFlowHandler) {
        sideList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            new Thread(() -> {
                Platform.runLater(() -> {
                    if (newVal != null) {
                        try {
                            contentFlowHandler.handle(newVal.getId());
                        } catch (VetoException exc) {
                            exc.printStackTrace();
                        } catch (FlowException exc) {
                            exc.printStackTrace();
                        }
                    }
                });
            }).start();
        });
    }


    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }
}
