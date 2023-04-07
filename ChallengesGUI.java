

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ChallengesGUI {
    private Frame challenge;
    private JButton back,launch,refresh;
    private String name;
    private JTextField wordForChallenge;
    private JTextArea challengesProposed;
    private JLabel challengeInfo;
    private List<String> wordsAddedForChallenge;
    private int score;
    private String wordAdd;
    private String newScoreFileContent;


    public ChallengesGUI(String name,int score) throws IOException {

        this.name=name;
        this.score=score;
        createGUI();
        actionListeners();
        challengesGUI();
        refreshPage();


    }



    private void createGUI(){
        challenge=new Frame();
        challenge.setSize(1600,1600);
        back=new JButton("Back");
        back.setBounds(400,800,100,30);
        wordForChallenge=new JTextField();
        wordForChallenge.setBounds(400,700,250,35);
        challengesProposed=new JTextArea();
        challengesProposed.setBounds(50,100,1000,500);
        challengesProposed.setEditable(false);
        launch=new JButton("launch");
        launch.setBounds(500,800,100,30);
        refresh=new JButton("Refresh");
        refresh.setBounds(800,800,150,30);
        challengeInfo=new JLabel("Introdu cuvantul pe care-l lansezi pentru challenge: ");
        challengeInfo.setBounds(400,650,300,35);


        challenge.add(back);
        challenge.add(wordForChallenge);
        challenge.add(challengesProposed);
        challenge.add(launch);
        challenge.add(refresh);
        challenge.add(challengeInfo);
        challenge.setLayout(null);
        challenge.setVisible(true);
    }

    //metoda pentru afisare questuri din fisierul cu questuri in textArea
    private void challengesGUI() throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("challenges.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        String challengesFileContent = sb.toString();
        challengesProposed.setText(challengesFileContent);
    }


    //metoda pentru functionalitate butoane
    private void actionListeners(){

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==back){
                    try {
                        UserInterface ui=new UserInterface(name);
                        challenge.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        launch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==launch){
                    try {
                        customWordChallenge();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        challenge.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                challenge.dispose();
                System.exit(0);
            }
        });
    }

    //metoda care adauga cuvantul nou daca toate conditiile sunt indeplinite
    private void customWordChallenge() throws IOException {
        wordAdd=wordForChallenge.getText();
        checkIfEnoughtTokens();

    }

        //metoda care verifica daca cuvantul contine doar litere
        private void checkIfLetter() throws IOException {
        boolean ok=true;
            for(int i=0;i<wordAdd.length();i++){
                if(!Character.isLetter(wordAdd.charAt(i))){
                    JOptionPane.showMessageDialog(null, "Cuvantul nu poate contine litere sau caractere speciale !");
                    ok=false;
                }
            }
            if(ok) {
                checkIfLength();
            }
    }

        //metoda care verifica dimensiunea cuvantului
        private void checkIfLength() throws IOException {
            if(wordAdd.length()<2 || wordAdd.length()>12){
                JOptionPane.showMessageDialog(null, "Cuvantul este prea scurt sau prea lung!");
            }else {
                checkIfWordExists();
            }
        }


        //metoda care verifica daca cuvantul nu este deja existent
        private void checkIfWordExists() throws IOException {

        // in aceasta metoda, se citeste linie cu linie fisierul cu cuvinte
         BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
         List<String> cuvinte=new ArrayList<>();//lista care stocheaza toate cuvintele din fisier
         String line = reader.readLine();
         String text=wordForChallenge.getText();
         boolean ok=true;
         while(line !=null) {
            cuvinte.add(line);
            line = reader.readLine();
         }
         reader.close();
         String[]cuvinteCheck=cuvinte.toArray(new String[0]); // am preluat cuvintele din text file intr-un string, datorita posibilelor spatii goale din linii
            //parcurgem tot array string-ul cu toate cuvintele pentru a cauta similiaritatea
         for(int i=0;i<cuvinteCheck.length;i++){
            if (text.equals(cuvinteCheck[i])) {
                ok = false; // daca similaritatea a fost gasita, boolean ok se trece pe false si se opreste loop-ul printr-un break
                break;
            }
         }
         if(ok) {
             // daca ok=true, similaritatea nu exista, iar cuvantul poate fi scris in fisier
            writeWordInFile();
         }
         else{
            JOptionPane.showMessageDialog(null, "Cuvantul deja exista!");
         }
    }

       //verificare daca jucatorul are destule tokens pentru a lansa challenge-ul
    private void checkIfEnoughtTokens() throws IOException {
            if(score<10000){
                JOptionPane.showMessageDialog(null, "Nu ai destule tokens pentru a lansa un challenge");
            }
            else{
                checkIfLetter();
            }
        }

         //daca cuvantul nu a fost existent, iar player-ul are destule tokens, cuvantul se va adauga in fisier
        private void writeWordInFile() throws IOException {
            FileWriter writer = new FileWriter("words.txt", true);
            writer.write("\n"+wordAdd);
            writer.close();

            updateChallengeBoard();

        }

        // metoda pentru update la fisierul cu challenge-uri
        private void updateChallengeBoard() throws IOException {
            FileWriter writer = new FileWriter("challenges.txt", true);
            writer.write("\n"+"Get the word: "+wordAdd+" to get 2500 tokens was added by user: "+name);
            writer.close();
            JOptionPane.showMessageDialog(null, "Challenge adaugat, refresh la pagina pentru a-l vedea!");
            updateTokensBalance(name,score);
        }

        //buton pentru refresh pentru a putea vedea challenge-ul dupa adaugarea acestuia
        private void refreshPage(){
           refresh.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   if(e.getSource()==refresh){
                       try {
                           challengesGUI();
                       } catch (IOException ex) {
                           ex.printStackTrace();
                       }
                   }
               }
           });
        }

        //metoda care substrage cele 10000 de tokens necesare lansarii unui cuvant challenge
        private void updateTokensBalance(String name,int score) throws IOException {
            int newScore=score-10000;
            StringBuilder scoreFileContent=new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader("score.txt"));
            String line = reader.readLine();
            while (line != null) {
                scoreFileContent.append(line+"\n");
                line = reader.readLine();
            }
            String toReplaceString=name+":"+String.valueOf(score);
            String replacerForString=name+":"+newScore;
            newScoreFileContent=scoreFileContent.toString().replace(toReplaceString,replacerForString);
            FileWriter writer=new FileWriter("score.txt");
            writer.write(newScoreFileContent);
            writer.close();
    }
}




