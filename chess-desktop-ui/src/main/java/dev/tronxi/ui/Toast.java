package dev.tronxi.ui;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast {

    public static void show(Stage ownerStage, String message, int durationInMillis) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        Label toastLabel = new Label(message);
        toastLabel.setStyle("-fx-background-color: #d23b3b; -fx-text-fill: white; -fx-padding: 10px; " +
                "-fx-background-radius: 8; -fx-border-radius: 8;");
        toastLabel.setFont(new Font(14));
        toastLabel.setOpacity(0);

        StackPane content = new StackPane(toastLabel);
        content.setStyle("-fx-background-color: transparent;");
        popup.getContent().add(content);

        popup.show(ownerStage);
        popup.setX(ownerStage.getX() + ownerStage.getWidth() / 2 - toastLabel.getWidth() / 2);
        popup.setY(ownerStage.getY() + ownerStage.getHeight() - 100);

        FadeTransition fadeIn = getFadeTransition(durationInMillis, toastLabel, popup);

        fadeIn.play();
    }

    private static FadeTransition getFadeTransition(int durationInMillis, Label toastLabel, Popup popup) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toastLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setOnFinished(event -> new Thread(() -> {
            try {
                Thread.sleep(durationInMillis);
            } catch (InterruptedException ignored) {
            }
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toastLabel);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> popup.hide());
            fadeOut.play();
        }).start());
        return fadeIn;
    }
}
