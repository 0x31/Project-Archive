package comp1140.ass2.Scenes;

import comp1140.ass2.Blokus;
import comp1140.ass2.Game.Colour;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * @author ***REMOVED*** on 12/10/2015.
 */
public class End extends Pane {
    public End(int[] score, Colour[] playerColours, Blokus parent) {
        int[] positions = new int[4];
        ArrayList<String> winners = new ArrayList<>();
        for(int i=0;i<4;i++) {
            positions[i] =
                    ((score[i]>=score[(i+1)%4]) ? 0 : 1) +
                            ((score[i]>=score[(i+2)%4]) ? 0 : 1) +
                            ((score[i]>=score[(i+3)%4]) ? 0 : 1);
            if(positions[i]==0)
                winners.add(playerColours[i].name());
        }

        Pane pane = new Pane();
        pane.setMinSize(700, 700);
        pane.setOpacity(0);
        this.getChildren().add(pane);

        Rectangle rect = new Rectangle(700, 700, Color.valueOf("rgba(0, 0, 0, 0.6)"));
        pane.getChildren().add(rect);

        FadeTransition ft = new FadeTransition(Duration.millis(1000), pane);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setAutoReverse(true);
        ft.play();


        TextFlow title = new TextFlow();
        title.setMinWidth(700);
        Text mainTitle = new Text();
        if (winners.size() > 1) {
            mainTitle.setText("It's a tie between\n");
        } else {
            mainTitle.setText("The winner is\n");
        }
        mainTitle.setFont(Font.font("Amble Cn", FontWeight.BOLD, 30));
        mainTitle.setFill(Color.WHITE);
        title.getChildren().add(mainTitle);
        for (String winner : winners) {
            Text playerText = new Text(((winner + "\n")));
            playerText.setFont(Font.font("Amble Cn", FontWeight.BOLD, 80));
            if (winner == "Blue")
                playerText.setFill(Color.valueOf("rgb(11, 66, 155)"));
            if (winner == "Yellow")
                playerText.setFill(Color.valueOf("rgb(237,157, 0)"));
            if (winner == "Red")
                playerText.setFill(Color.valueOf("rgb(155, 11, 66)"));
            if (winner == "Green")
                playerText.setFill(Color.valueOf("rgb(66,155, 11)"));
            title.getChildren().add(playerText);
        }
        title.setTextAlignment(TextAlignment.CENTER);

        Pane center = new Pane();
        center.getChildren().add(title);
        center.setMinSize(700, 300);
        center.setLayoutY(50);
        pane.getChildren().add(center);


        int barWindow = 300;
        int BAR_WIDTH = 50;
        int MAX_SCORE = 1 + 2 + 3 + 3 + 4 + 4 + 4 + 4 + 4 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 20;

        /* Show bars: */
        // 100 233 366 500

        Pane barPane = new Pane();
        barPane.setMinSize(700, 700);
        barPane.setLayoutX(0);
        barPane.setLayoutY(0);
        this.getChildren().add(barPane);

        Rectangle blueBar = new Rectangle(BAR_WIDTH, 1, Color.valueOf("rgb(11, 66, 155)"));
        blueBar.setLayoutX((700 - barWindow) / 2 - BAR_WIDTH / 2);
        blueBar.setLayoutY(620);
        Label blueScore = new Label(MAX_SCORE + score[0] + "");
        blueScore.setLayoutX((700 - barWindow) / 2 + 10 - BAR_WIDTH / 2);
        blueScore.setLayoutY(580);
        blueScore.setTextFill(Color.WHITE);
        blueScore.setStyle("-fx-font-size: 18;");

        Rectangle yellowBar = new Rectangle(BAR_WIDTH, 1, Color.valueOf("rgb(237,157, 0)"));
        yellowBar.setLayoutX((700 - barWindow) / 2 + barWindow / 3 - BAR_WIDTH / 2);
        yellowBar.setLayoutY(620);
        Label yellowScore = new Label(MAX_SCORE + score[1] + "");
        yellowScore.setLayoutX((700 - barWindow) / 2 + 1 * barWindow / 3 + 10 - BAR_WIDTH / 2);
        yellowScore.setLayoutY(580);
        yellowScore.setStyle("-fx-font-size: 18");
        yellowScore.setTextFill(Color.WHITE);

        Rectangle redBar = new Rectangle(BAR_WIDTH, 1, Color.valueOf("rgb(155, 11, 66)"));
        redBar.setLayoutX((700 - barWindow) / 2 + 2 * barWindow / 3 - BAR_WIDTH / 2);
        redBar.setLayoutY(620);
        Label redScore = new Label(MAX_SCORE + score[2] + "");
        redScore.setLayoutX((700 - barWindow) / 2 + 2 * barWindow / 3 + 10 - BAR_WIDTH / 2);
        redScore.setLayoutY(580);
        redScore.setStyle("-fx-font-size: 18;");
        redScore.setTextFill(Color.WHITE);
        redBar.setRotate(180);


        Rectangle greenBar = new Rectangle(BAR_WIDTH, 1, Color.valueOf("rgb(66,155, 11)"));
        greenBar.setLayoutX((700 - barWindow) / 2 + barWindow - BAR_WIDTH / 2);
        greenBar.setLayoutY(620);
        Label greenScore = new Label(MAX_SCORE + score[3] + "");
        greenScore.setLayoutX((700 - barWindow) / 2 + 3 * barWindow / 3 + 10 - BAR_WIDTH / 2);
        greenScore.setLayoutY(580);
        greenScore.setStyle("-fx-font-size: 18;");
        greenScore.setTextFill(Color.WHITE);


        barPane.getChildren().addAll(blueBar, yellowBar, redBar, greenBar);
        barPane.getChildren().addAll(blueScore, yellowScore, redScore, greenScore);


    /* animation */
        {
            /*
            Timer animTimer = new Timer(true);
            animTimer.scheduleAtFixedRate(new TimerTask() {
            */

            double barSize = 2.5;
            Duration duration = Duration.millis(1500);

            double blueL = barSize * MAX_SCORE + score[0];
            TranslateTransition blueT = new TranslateTransition(duration);
            blueT.setFromY(0f);
            blueT.setToY(-blueL / 2);
            TranslateTransition blueT1 = new TranslateTransition(duration);
            blueT1.setFromY(0f);
            blueT1.setToY(-blueL);
            ScaleTransition blueS = new ScaleTransition(duration);
            blueS.setByY(blueL);
            ParallelTransition blueP = new ParallelTransition(blueBar, blueS, blueT);
            ParallelTransition blueP1 = new ParallelTransition(blueScore, blueT1);
            blueP.play();
            blueP1.play();

            double yellowL = barSize * MAX_SCORE + score[1];
            TranslateTransition yellowT = new TranslateTransition(duration);
            yellowT.setFromY(0f);
            yellowT.setToY(-yellowL / 2);
            TranslateTransition yellowT1 = new TranslateTransition(duration);
            yellowT1.setFromY(0f);
            yellowT1.setToY(-yellowL);
            ScaleTransition yellowS = new ScaleTransition(duration);
            yellowS.setByY(yellowL);
            ParallelTransition yellowP = new ParallelTransition(yellowBar, yellowS, yellowT);
            ParallelTransition yellowP1 = new ParallelTransition(yellowScore, yellowT1);
            yellowP.play();
            yellowP1.play();

            double redL = barSize * MAX_SCORE + score[2];
            TranslateTransition redT = new TranslateTransition(duration);
            redT.setFromY(0f);
            redT.setToY(-redL / 2);
            TranslateTransition redT1 = new TranslateTransition(duration);
            redT1.setFromY(0f);
            redT1.setToY(-redL);
            ScaleTransition redS = new ScaleTransition(duration);
            redS.setByY(redL);
            ParallelTransition redP = new ParallelTransition(redBar, redS, redT);
            ParallelTransition redP1 = new ParallelTransition(redScore, redT1);
            redP.play();
            redP1.play();

            double greenL = barSize * MAX_SCORE + score[3];
            TranslateTransition greenT = new TranslateTransition(duration);
            greenT.setFromY(0f);
            greenT.setToY(-greenL / 2);
            TranslateTransition greenT1 = new TranslateTransition(duration);
            greenT1.setFromY(0f);
            greenT1.setToY(-greenL);
            ScaleTransition greenS = new ScaleTransition(duration);
            greenS.setByY(greenL);
            ParallelTransition greenP = new ParallelTransition(greenBar, greenS, greenT);
            ParallelTransition greenP1 = new ParallelTransition(greenScore, greenT1);
            greenP.play();
            greenP1.play();

        }

        Button button5 = new Button("<");
        button5.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                parent.toMenu();
            }
        });
        button5.setMinSize(40, 40);
        button5.setMaxSize(40, 40);
        button5.setLayoutX(30 - button5.getMinWidth() / 2);
        button5.setLayoutY(10);
        button5.getStyleClass().add("back");
        button5.getStyleClass().add("button1");
        this.getChildren().add(button5);
    }
}
