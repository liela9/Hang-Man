package sample;

import java.util.ArrayList;

public class HangMan {

    private String wordToGuess;
    private String wrongChars;
    private ArrayList<Character> correctChars;

    //Constructor
    public HangMan(String word){
        this.wordToGuess = word;
        this.correctChars = new ArrayList<Character>();
        this.wrongChars = "";

        for (int i = 0; i < word.length(); i++)
            this.correctChars.add('_');
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public String getCorrectChars() {
        return correctChars +"";
    }

    //Sets specific char in 'display'
    public void setDisplay(int i, Character ch) {
        this.correctChars.set(i, ch);
    }

    public String getWrongChars() {
        return wrongChars;
    }

    public void setWrongChars(String wrongChars) {
        this.wrongChars = wrongChars;
    }
}
