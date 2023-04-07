

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.Timer;
import java.util.TimerTask;

public class ScoreUpdate {
    private String score;
    private String name;
    private int scor;
    private String  newScoreFileContent;

    public ScoreUpdate(String score, String name) throws IOException{
        this.score=score;
        this.name=name;
        updateScore();
        writeIntoFileTheNewScore(newScoreFileContent);
    }


    //exact acelasi principiu ca la quest leader board, preluare date, modificare linia cu jucatorul conctat, modificarea scorului , rescrierea fisierului cu continutul updatat
    private void updateScore() throws IOException {

        scor = Integer.parseInt(score);
        BufferedReader reader = new BufferedReader(new FileReader("score.txt"));
        String line = reader.readLine();
        StringBuilder scoreFileContent=new StringBuilder();

        int presentScore = 0;
        while (line != null) {
            scoreFileContent.append(line+"\n");
            String[] splits = line.split(":");
            if (splits.length == 2) {
                if (splits[0].equals(name)) {
                    presentScore = Integer.parseInt(splits[1]);
                }
            }
            line = reader.readLine();
        }
        scor += presentScore;


        String toReplaceString=name+":"+String.valueOf(presentScore);
        String replacerForString=name+":"+scor;
        newScoreFileContent=scoreFileContent.toString().replace(toReplaceString,replacerForString);


    }

    private void writeIntoFileTheNewScore(String newScoreFileContent) throws IOException {
        FileWriter writer = new FileWriter("score.txt");
        writer.write(newScoreFileContent);
        writer.close();

    }
}
