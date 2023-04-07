

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QuestsScoreBoard {
    private Frame quest;
    private String name;
    private String score;
    private int scor;
    private String newScoreFileContent;
    public QuestsScoreBoard(String name,String score) throws IOException {
        this.name=name;
        this.score=score;
        updateScore();
        writeIntoFileTheNewScore(newScoreFileContent);


    }



    // metoda care da update la quest score leader board
    private void updateScore() throws IOException {

        //citim fisierul cu scoruri
        BufferedReader reader = new BufferedReader(new FileReader("questScore.txt"));
        String line = reader.readLine();
        StringBuilder scoreFileContent=new StringBuilder(); //stocam continutul intr-un stringBuilder
        scor=Integer.parseInt(score);
        int presentScore = 0;
        while (line != null) {
            scoreFileContent.append(line+"\n");
            String[] splits = line.split(":");
            if (splits.length == 2) { //verificare conditia linia sa nu fie null
                if (splits[0].equals(name)) { //dupa ce am gasit linia utilizatorului contectat din fisier, ii preluam scorul actual
                    presentScore = Integer.parseInt(splits[1]);
                }
            }
            line = reader.readLine();
        }

        scor += presentScore; //aici dam update la scor prin adaugarea scorului obtinut in timpul unei runde de joc la scorul actual aflat in fisier


        //aici cream un string care contine numele:scorul actual al jucatorului
        String toReplaceString=name+":"+String.valueOf(presentScore);
        //aici cream string-ul care contine numele:scorul acutalizat al jucatorului
        String replacerForString=name+":"+scor;
        //dam replace la nume:scor vechi cu nume:scor actualizat + restul utilizatorilor si scorul nou, practic acest string contine intreaga baza actualizata
        newScoreFileContent=scoreFileContent.toString().replace(toReplaceString,replacerForString);


    }

    private void writeIntoFileTheNewScore(String newScoreFileContent) throws IOException {
        //aici dam update la fisier cu noul string care contine fisierul cu scorul actualizat
        FileWriter writer = new FileWriter("questScore.txt");
        writer.write(newScoreFileContent);
        writer.close();

    }
}

