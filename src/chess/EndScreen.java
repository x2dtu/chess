package chess;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EndScreen {

    private VBox vbox;
    private GUIBoard guiBoard;
    private String font;
    private Pane background;
    
    public EndScreen(String title, String message, GUIBoard guiBoard) {
        this.guiBoard = guiBoard;
        initBackground();
        font = "Arial";
        initVBox();
        createTitle(title);
        createMessage(message);
        createButton();
    }

    public VBox getVBox() {
        return vbox;
    }

    private void initVBox() {
        vbox = new VBox(30);
        double size = Board.WIDTH * GUIBoard.CELL_SIZE / 4;
        vbox.prefHeight(size);
        vbox.prefWidth(size);
        // vbox.setStyle("-fx-background-color: #e6e6e6;");
        // vbox.setStyle("-fx-background-radius: 5px;");
        vbox.setAlignment(Pos.CENTER);
    }

    private void createTitle(String title) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font(font, 60));
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        vbox.getChildren().add(titleLabel);
    }

    private void createMessage(String message) {
        Label messageLabel = new Label(message);
        messageLabel.setFont(new Font(font, 48));
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        vbox.getChildren().add(messageLabel);
    }

    private void createButton() {
        Button button = new Button("Play Again");
        button.setOnAction(e -> guiBoard.resetBoard());
        button.setAlignment(Pos.CENTER);
        button.setFont(new Font(font, 24));
        vbox.getChildren().add(button);
    }

    private void initBackground() {
        background = new Pane();
        double size = GUIBoard.CELL_SIZE * Board.WIDTH;
        background.setPrefHeight(size);
        background.setPrefWidth(size);
        background.setStyle("-fx-background-color: #000000;");
        background.setOpacity(0.5);
    }

    public Pane getBackground() {
        return background;
    }


}
