

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuestsScoreBoardGUI {
    private Frame quests;
    private JTextArea leaderBoard;
    private JButton back;
    private String name;
    private JLabel info;
    private List<String> playersScore;
    private List<String> top10Scores;

    public QuestsScoreBoardGUI(String name) throws IOException {
        this.name=name;
        createGUI();
        actionListener();
        getPlayers();
        sortareDupaScor(playersScore);
        top10(playersScore, top10Scores);
        afisareLeaderBoard(top10Scores);
    }

    private void createGUI(){
        quests=new Frame();
        quests.setSize(1000,1000);
        back=new JButton("Back");
        back.setBounds(400,800,120,30);
        leaderBoard=new JTextArea();
        leaderBoard.setBounds(300,400,300,300);
        leaderBoard.setEditable(false);
        info=new JLabel("QUESTS LEADER BOARD:");
        info.setBounds(400,200,200,35);


        quests.setBackground(Color.yellow);
        quests.add(leaderBoard);
        quests.add(back);
        quests.add(info);
        quests.setLayout(null);
        quests.setVisible(true);
    }

    //aici preluam toti jucatorii
    private void getPlayers() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("questScore.txt"));
        String line = reader.readLine();
        playersScore = new ArrayList<>();
        top10Scores = new ArrayList<>();
        while (line != null) {
            playersScore.add(line + "\n");
            line = reader.readLine();
        }


    }

    private void actionListener(){
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == back) {
                    try {
                        UserInterface in = new UserInterface(name);
                        quests.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        quests.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                quests.dispose();
                System.exit(0);
            }
        });
    }

    //aici sortam toata lista jucatorilor in functie de scorul acestora, descrescator
    private void sortareDupaScor(List<String> jucatori){
        jucatori.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                double d1 = Double.parseDouble(s1.substring(s1.lastIndexOf(":") + 1));
                double d2 = Double.parseDouble(s2.substring(s2.lastIndexOf(":") + 1));
                return Double.compare(d2, d1);
            }
        });
    }
    //aici preluam primii 10 cu scorul cel mai mare
    private void top10(List<String> scores, List<String>top10){
        for(int i=0;i<10;i++){
            top10.add(scores.get(i));
        }
    }
    //afisam primii 10 jucatori  in textArea
    private void afisareLeaderBoard(List<String> top10){
        String[]strArray=top10.toArray(new String[0]);
        for(int i=0;i<strArray.length;i++){
            leaderBoard.append(i+1+". "+strArray[i]);
        }
    }
}
