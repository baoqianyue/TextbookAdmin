package main.java.utils;

import com.jfoenix.controls.JFXSnackbar;
import javafx.scene.layout.Pane;

public class Toast {
    public static void show(Pane pane, String msg) {
        JFXSnackbar toast = new JFXSnackbar(pane);
        toast.show(msg, 3000);
    }
}
