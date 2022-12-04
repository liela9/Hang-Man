package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class HangManController {

    @FXML
    private Circle head;
    @FXML
    private Line body;
    @FXML
    private Line leftHand;
    @FXML
    private Line rightHand;
    @FXML
    private Line leftLeg;
    @FXML
    private Line rightLeg;
    @FXML
    private Button btn;
    @FXML
    private ComboBox<Character> cbox;
    @FXML
    private Label rightGuessesLabel;
    @FXML
    private Label wrongGuessesLabel;

    private GraphicsContext gc;
    private HangMan game;
    private int count;

    public void initialize() {
        initComboBox();
        newGame();
    }

    /**
     * This method starts new game.
     */
    private void newGame() {
        //initialize the man as invisible at first
        head.setVisible(false);
        body.setVisible(false);
        leftHand.setVisible(false);
        rightHand.setVisible(false);
        leftLeg.setVisible(false);
        rightLeg.setVisible(false);

        count = 0;
        game = new HangMan(readWord());
        rightGuessesLabel.setText(game.getCorrectChars().toString().replace('[', ' ').replace(']', ' '));
        wrongGuessesLabel.setText(game.getWrongChars());
    }

    /**
     * This method reads a word from the words file.
     * and sets it as the 'wordToGuess' of the current game.
     */
    private String readWord() {
        ArrayList<String> arr = new ArrayList<String>();

        Scanner scan = null;
        try {
            scan = new Scanner(new File("words.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (scan != null) {
            while (scan.hasNext())
                arr.add(scan.next());
        }

        scan.close();

        Random rand = new Random();
        return arr.get(rand.nextInt(arr.size()));
    }

    /**
     * This method initializes the comboBox.
     */
    private void initComboBox() {
        final Character START_CHAR = 'a', END_CHAR = 'z';

        for (Character i = START_CHAR; i <= END_CHAR; ++i)
            cbox.getItems().add(i);
        cbox.setValue('a');
    }

    /**
     * This method executes when the user presses 'Ok' after choosing a letter.
     */
    @FXML
    void okPressed(ActionEvent event) {
        Character ch = cbox.getValue();

        // right guess - the word contains the char ch
        if (game.getWordToGuess().contains("" + ch)) {
            for (int i = 0; i < game.getWordToGuess().length(); i++) {
                if (game.getWordToGuess().charAt(i) == ch){
                    if(game.getCorrectChars().contains("" + ch)){
                        usedChar();
                    }
                    else{
                        game.setDisplay(i, ch);// set the char at index i as ch
                        count += 1;
                    }
                }
            }
        }
        // wrong guess
        else if (game.getWrongChars().contains("" + ch))
                usedChar();
        else {
            game.setWrongChars(game.getWrongChars() + " " + ch);
            drawOrgan();
        }

        presentResults();
        //win
        if(count == game.getWordToGuess().length())
            failOrWinAlert("You Won!"); //winning alert
    }

    /**
     * This method draws an organ when the user guesses wrong letter.
     * an organ at a time.
     */
    private void drawOrgan() {
        if (!head.isVisible())
            head.setVisible(true);

        else if (!body.isVisible())
            body.setVisible(true);

        else if (!leftHand.isVisible())
            leftHand.setVisible(true);

        else if (!rightHand.isVisible())
            rightHand.setVisible(true);

        else if (!leftLeg.isVisible())
            leftLeg.setVisible(true);

        else if (!rightLeg.isVisible())
            rightLeg.setVisible(true);

        else failOrWinAlert("Game Over");//failure alert
    }

    /**
     * This method presents right and wrong guesses.
     */
    private void presentResults() {
        rightGuessesLabel.setText(game.getCorrectChars().toString().replace('[',
                ' ').replace(']', ' '));
        wrongGuessesLabel.setText(game.getWrongChars());
    }

    /**
     * This method alerts that the user failed or won the game.
     */
    private void failOrWinAlert(String failOrWin) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(failOrWin);
        alert.setHeaderText(failOrWin);
        alert.setContentText("Try again?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK)
            newGame();
        else if (option.get() == ButtonType.CANCEL)
            System.exit(0);
    }

    /**
     * This method alerts that the user used twice (or more) some letter.
     */
    private void usedChar(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText("You tried this letter before..");
        alert.showAndWait();
    }
}
