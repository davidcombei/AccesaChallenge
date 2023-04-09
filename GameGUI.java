

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameGUI {
    private Frame game;
    private JTextField ceas;
    private JButton startButton, back;
    private JTextField score,questScore,typedWord,generatedWord;
    private JLabel timeRemaining,givenWord,toTypeWord,currentScore,currentQuestScore;
    private int[] scores;
    private int[] quests;
    private List<String> words;
    private List<String> addedWords;
    private Random random;
    private String name;
    private int wordFileSize;
    private boolean gameInProgress=false; // daca jocul este oprit, acesta este false

    public GameGUI(String name) throws IOException {
        this.name = name;
        initUI();
        actionListeners();
        getTheNewWord();
        generateWord();
        wordFileSize = getNrOfWords();
        gameInProgress=false;


    }

    //creare interfata a jocului
    private void initUI() {
        game = new Frame();
        game.setSize(1000, 1000);

        timeRemaining=new JLabel("Remaining time: ");
        timeRemaining.setBounds(350,150,150,35);
        ceas = new JTextField("60");
        ceas.setBounds(400, 200, 100, 30);
        ceas.setEditable(false);

        startButton = new JButton("START");
        startButton.setBounds(400, 800, 150, 30);

        currentScore=new JLabel("Tokens acumulate runda aceasta:");
        currentScore.setBounds(570,600,200,35);
        score = new JTextField();
        score.setBounds(600, 640, 150, 30);
        score.setEditable(false);

        givenWord=new JLabel("Cuvant de scris: ");
        givenWord.setBounds(400,400,200,35);
        generatedWord = new JTextField();
        generatedWord.setBounds(400, 450, 250, 30);
        generatedWord.setEditable(false);

        toTypeWord=new JLabel("Scrie cuvantul:");
        toTypeWord.setBounds(400,500,200,35);
        typedWord = new JTextField();
        typedWord.setBounds(400, 550, 250, 30);

        back = new JButton("Back");
        back.setBounds(600, 800, 100, 30);

        currentQuestScore=new JLabel("Quest-uri completate runda aceasta:");
        currentQuestScore.setBounds(580,665,250,35);
        questScore=new JTextField();
        questScore.setBounds(600,700,150,30);
        questScore.setEditable(false);

        game.setBackground(Color.gray);
        game.add(startButton);
        game.add(ceas);
        game.add(score);
        game.add(generatedWord);
        game.add(typedWord);
        game.add(back);
        game.add(questScore);
        game.add(timeRemaining);
        game.add(givenWord);
        game.add(toTypeWord);
        game.add(currentScore);
        game.add(currentQuestScore);
        game.setLayout(null);
        game.setVisible(true);
    }


     //metoda functionalitate butoane
    private void actionListeners() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                gameInProgress=true; // in momentul in care jucatorul a apasat start pentru a incepe runda, gameInProgress se trece in true
                startButton.setEnabled(false);// pentru a evita apasarea multipla si instantierea mai multor runde consecutive, cat timp gameInProgress=true, dezactivam butonul start
            }
        });

        typedWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkWord();
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == back) {
                    try {
                        UserInterface in = new UserInterface(name);
                        game.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        game.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                game.dispose();
                System.exit(0);
            }
        });
    }



    //metoda care generaza cuvintele care trebuie scrise in textField
    private void generateWord() {
        random = new Random();
        scores = new int[]{0}; //aici am folosit un contor de tip array pentru a putea accesa si updata in mai multe metoda
        quests = new int[]{0};

        words = new ArrayList<>();
        //citire fisier cuvinte + stocare in arrayList words
        try {
            BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] splitter = line.split(":");
                if (splitter.length > 0) { //verificare sa nu fie linia goala
                    words.add(line); //adaugam cuvantul citit in arrayList
                }
                line = reader.readLine();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

        //metoda care porneste propriu-zis jocul si care genereaza aleator un index pentru extragere din arrayList-ul precedent
    private void startGame() {
        int rand = random.nextInt(wordFileSize + 1);
        generatedWord.setText(words.get(rand));// generarea cuvantului initial cand apasam start
        typedWord.setText("");// definit tot pe 0 si textfield de scris empty la inceputul rundei
        scores[0] = 0;
        quests[0] = 0;
        score.setText(String.valueOf(scores[0]));
        questScore.setText(String.valueOf(quests[0]));

        Timer timer = new Timer(1000, ev -> { // timerul de 60 de secunde incepe cand apasam start
            int count = Integer.parseInt(ceas.getText());

            if (count == 0) { // cand timerul ajunge la 0, oprim timer-ul si facem modificarile necesare
                ((Timer) ev.getSource()).stop();
                try {
                    ScoreUpdate score = new ScoreUpdate(String.valueOf(scores[0]), name); // cand timpul se scurge, scorul obtinut in runda se va adauga la scorul adunat si se v-a actualiza in fisier
                    QuestsScoreBoard questsScoreBoard=new QuestsScoreBoard(name,String.valueOf(quests[0])); // acelasi lucru si la quest score
                    gameInProgress=false;// trecem game in progress in false
                    startButton.setEnabled(true);// reactivam butonul start
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Game over!");
                ceas.setText("60"); // refresh la timer cand ajunge la 0
            } else {
                ceas.setText(String.valueOf(count - 1)); // daca timerul nu a ajuns la 0, decrementam in continuare
            }
        });
        timer.start();
    }

         //verificare daca cuvantul generat si cuvantul scris sunt identice+ update la token score + quest score
    private void checkWord() throws IOException, InterruptedException {

        if (generatedWord.getText().equals(typedWord.getText())) {
            if (Integer.parseInt(ceas.getText()) != 0 && Integer.parseInt(ceas.getText()) != 60) {
                // verificare daca cuvantul introdus nu face parte dintr-un challenge prestabilit sau propus + modificare scor in functie de reward-ul propus pentru challenge + incrementare quests completed
                if(generatedWord.getText().length()<3) { //quest dimensiune mai mica decat 3
                    scores[0]+=150;
                    quests[0]++;
                }
                else if(generatedWord.getText().length()>=12){ //quest dimensiune cuvant mai mare sau egala cu 12
                    scores[0]+=120;
                    quests[0]++;
                }
                else if(generatedWord.getText().charAt(0)=='z'){ // quest cuvantul sa inceapa cu litera z
                    scores[0]+=200;
                    quests[0]++;
                }
                else{
                    scores[0]+=1; // daca cuvantul introdus nu face parte din niciun challenge, adaugam 1 la scor

                }
                score.setText(String.valueOf(scores[0]));
                questScore.setText(String.valueOf(quests[0]));
                for(int i=0;i<addedWords.size();i++){ //verificare cuvantul introdus sa nu faca parte din cuvintele challege adaugate de jucatori
                    if(addedWords.get(i).equals(generatedWord.getText())){
                        scores[0]+=2500;
                        quests[0]++;
                        break;
                    }
                }

                //dupa introducerea unui cuvant , se genereaza un altul nou tot in mod aleator
                int rand1 = random.nextInt(wordFileSize + 1);
                generatedWord.setText(words.get(rand1));
                typedWord.setText("");
            }
        }
    }

    //metoda pentru a extrage numarul de cuvinte din fisier pentru generarea aleatoare
    // deoarce, cand dam update la fisier cu un cuvant challenge adaugat de un jucator, dimensiunea se modifica
    //iar daca aceasta nu ar fi modificata, cuvintele noi adaugate nu vor fi niciodata generate in joc
    private int getNrOfWords() {
        File file = new File("words.txt");
        int wordCount = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split("\\s+");
                wordCount += words.length;
            }
            scanner.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return wordCount;
    }

    // aceasta metoda preia toate cuvintele adaugate de jucatori, adica cuvintele incepand cu pozitia 10001, deoarece primele 10000 de cuvinte sunt prestabilite
    //aceasta metoda are rolul de a trimite la metoda care verifica cuvintele , cuvintele adaugate de jucatori, aflate in arrayList-ul addedWords
    private void getTheNewWord() throws IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader("words.txt"));
        addedWords=new ArrayList<>();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (reader.getLineNumber() >10000) { // incepe doar cu linia 10001, aici incep cuvintele adaugate de jucatori
                addedWords.add(line);//adaugam cuvintele cu pozitia >=10001 in arraylist-ul addedwords
            }
        }
    }
}

