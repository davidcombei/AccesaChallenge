

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class AdminUI {
    private Frame admin;
    private JButton accounts,tokenScore,questScore,words,logout;
    private JLabel label;

    public AdminUI(){
     createGUI();
     actionListeners();
    }
    //creare interfata cont administrator :
    private void createGUI(){
      admin=new Frame();
      admin.setSize(1000,1000);
      label=new JLabel("Welcome, administrator!");
      label.setBounds(200,100,300,35);
      accounts=new JButton("accounts");
      accounts.setBounds(300,500,150,35);
      tokenScore=new JButton("tokenLeaderBoard");
      tokenScore.setBounds(500,500,150,35);
      questScore=new JButton("questLeaderBoard");
      questScore.setBounds(300,600,150,35);
      words=new JButton("wordsFile");
      words.setBounds(500,600,200,35);
      logout=new JButton("logout");
      logout.setBounds(450,800,150,35);

      admin.add(accounts);
      admin.add(tokenScore);
      admin.add(questScore);
      admin.add(words);
      admin.add(label);
      admin.add(logout);
      admin.setLayout(null);
      admin.setVisible(true);
    }

    private void actionListeners(){
        // buton pentru accesarea fisierul cu conturi pentru eventuale modificari necesare de catre admin
        accounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openAccountsFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // buton pentru accesarea fisierul cu token score pentru eventuale modificari necesare de catre admin
        tokenScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    scoreFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // buton pentru accesarea fisierul cu quest score pentru eventuale modificari necesare de catre admin
        questScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    questScoreFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // buton pentru accesarea fisierul cu cuvinte pentru eventuale modificari necesare de catre admin
        words.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wordsFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //buton pentru logout si revenire la pagina login
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI gui=new LoginGUI();
                admin.dispose();
            }
        });

        admin.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                admin.dispose();
                System.exit(0);
            }
        });
    }

    //metoda pentru deschidere fisier conturi
    private void openAccountsFile() throws IOException {
        File file=new File("accounts.txt");
        Desktop d= Desktop.getDesktop();
        d.open(file);
    }

    //metoda pentru deschidere fisier token score
    private void scoreFile() throws IOException {
        File file=new File("score.txt");
        Desktop d= Desktop.getDesktop();
        d.open(file);
    }

    //metoda pentru deschidere fisier quest score
    private void questScoreFile() throws IOException {
        File file=new File("questScore.txt");
        Desktop d= Desktop.getDesktop();
        d.open(file);
    }

    //metoda pentru deschidere fisier cuvinte
    private void wordsFile() throws IOException {
        File file=new File("words.txt");
        Desktop d= Desktop.getDesktop();
        d.open(file);
    }

}
