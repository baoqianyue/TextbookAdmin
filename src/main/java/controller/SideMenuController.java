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
    private JFXListView<Label> sideList;

    private static String[] slideContentTitle;
    private static Class[] slideContentController;


    @PostConstruct
    public void init() {
        switch (Statics.TYPE_CURR) {
            case Statics.TYPE_TEACHRE:
                slideContentTitle = Statics.teacherSlideTitle;
                slideContentController = Statics.teacherSlideController;
                break;
        }
        labelOne.setText(slideContentTitle[0]);
        labelTwo.setText(slideContentTitle[1]);
        labelThree.setText(slideContentTitle[2]);
        labelFour.setText(slideContentTitle[3]);
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        sideList.propagateMouseEventsToParent();
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

        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
        bindNodeToController(labelOne, slideContentController[0], contentFlow, contentFlowHandler);
//        bindNodeToController(checkbox, CheckboxController.class, contentFlow, contentFlowHandler);
//        bindNodeToController(combobox, ComboBoxController.class, contentFlow, contentFlowHandler);
//        bindNodeToController(dialogs, DialogController.class, contentFlow, contentFlowHandler);

    }


    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }
}
