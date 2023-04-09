

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UserInterface {
     private Frame user;
     private JTextField tokens,quests;
     private JButton logout,play,leaderBoard,challenges,questLeaderBoard;
     private String name;
     private JLabel label,token, questPoints,badgesEarned;
     private JTextArea questBadgeArea;

    public UserInterface(String name) throws IOException {
        this.name=name;
        createGUI();
        preluareScorUtilizator();
        preluareQuestScoreUtilizator();
        setUsersBadges();
        actionListeners();
    }


    //creare interfata utilizator
    private void createGUI(){
        user=new Frame();
        user.setSize(1000,1000);
        String welcome="Welcome, "+name;//string pentru mesaj cu numele de utilizator

        label=new JLabel(welcome);
        label.setBounds(100,100,500,30);
        label.setFont(new Font("Arial",Font.PLAIN,25));

        //textfield uneditable cu numarul de token-uri pentru afisaj+ preluare nr token-uri
        tokens=new JTextField();
        tokens.setBounds(700,100,100,30);
        tokens.setEditable(false);
        quests=new JTextField();
        quests.setBounds(850,100,100,30);
        quests.setEditable(false);
        token=new JLabel("Tokens:");
        token.setBounds(700,60,100,30);
        logout=new JButton("Logout");
        logout.setBounds(400,750,150,30);
        play=new JButton("Play");
        play.setBounds(400,500,150,30);
        leaderBoard=new JButton("Leader Board");
        leaderBoard.setBounds(700,700,250,35);
        challenges=new JButton("Challenges panel");
        challenges.setBounds(700,800,250,35);
        questLeaderBoard=new JButton("Quest Leader Board");
        questLeaderBoard.setBounds(700,750,250,35);
        questBadgeArea=new JTextArea();
        questBadgeArea.setBounds(700,200,300,500);
        questBadgeArea.setEditable(false);
        badgesEarned=new JLabel("Earned Badges:");
        badgesEarned.setBounds(700,150,200,35);
        questPoints=new JLabel("Quest Points: ");
        questPoints.setBounds(830,60,150,35);


        user.setBackground(Color.LIGHT_GRAY);
        user.add(tokens);
        user.add(logout);
        user.add(play);
        user.add(leaderBoard);
        user.add(challenges);
        user.add(label);
        user.add(token);
        user.add(questLeaderBoard);
        user.add(quests);
        user.add(questBadgeArea);
        user.add(questPoints);
        user.add(badgesEarned);
        user.setLayout(null);
        user.setVisible(true);

    }

    //preia token scorul actual al utilizatorului din fisierul cu scoruri si il afiseaza in interfata utilizatorului (in text field-ul corespunzator)
    private void preluareScorUtilizator() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("score.txt"));
        String line = reader.readLine();
        while (line != null) {
            String[] splitInfo = line.split(":");//split la date primul element este username, al doilea scorul

            if (splitInfo[0].equals(name)) {
                tokens.setText(splitInfo[1]);
            }
            line = reader.readLine();
        }
        reader.close();
    }
    //preia quest scorul actual al utilizatorului din fisierul cu scoruri si il afiseaza in interfata utilizatorului (in text field-ul corespunzator)
    private void preluareQuestScoreUtilizator() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("questScore.txt"));
        String line = reader.readLine();
        while (line != null) {
            String[] splitInfo = line.split(":");

            if (splitInfo[0].equals(name)) {
                quests.setText(splitInfo[1]);
            }
            line = reader.readLine();
        }
        reader.close();
    }


    //metoda functionalitate butoane
    private void actionListeners(){
        //buton logout+ functionabilitatea acestuia sa revina la fereastra de login

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==logout){
                    LoginGUI login=new LoginGUI();
                    user.dispose();
                }
            }
        });


        //buton play+ functionabilitate de a te trimite in mini-game

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==play){

                    try {
                        GameGUI game=new GameGUI(name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    user.dispose();
                }
            }
        });

        //buton pentru a trimite utilizatorul la token leader board
        leaderBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==leaderBoard){
                    try {
                        ScoreBoardGUI scoreBoardGUI=new ScoreBoardGUI(name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    user.dispose();
                }
            }
        });
        //buton pentru trimitere utilizator la quest leader board
        questLeaderBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==questLeaderBoard){
                    try {
                        QuestsScoreBoardGUI questsScoreBoardGUI=new QuestsScoreBoardGUI(name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    user.dispose();
                }
            }
        });
        //buton pentru a trimite utlizatorul la challenges panel
        challenges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==challenges){
                    try {
                        ChallengesGUI challengesGUI=new ChallengesGUI(name, Integer.parseInt(tokens.getText()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    user.dispose();
                }
            }
        });
        user.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                user.dispose();
                System.exit(0);
            }
        });
    }

    //verificare numar de quest-uri completate + adaugarea badge-urilor in functie de nr de quest-uri in interfata utilizatorului
    private void setUsersBadges(){
        int questsNumber= Integer.parseInt(quests.getText());
         if(questsNumber>=10){
            BadgeDisplaySystem badge3=new BadgeDisplaySystem(questBadgeArea);//adaugam badge-ul la textArea din interfata utilizatorului
            badge3.displayBadge10();
             if(questsNumber>=100){
                 BadgeDisplaySystem badge2=new BadgeDisplaySystem(questBadgeArea);
                 badge2.displayBadge100();
                 if(questsNumber>=1000){
                     BadgeDisplaySystem badge1=new BadgeDisplaySystem(questBadgeArea);
                     badge1.displayBadge1000();
                 }
             }
         }
    }

}
