

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterGUI {
    private Frame register;
    private JTextField regUsername,regPassword,verifPassword;
    private JLabel us,pas,verifPas,info;
    private JButton reg,back;


    public RegisterGUI(){
    createGUI();
    actionListeners();
    }

    private void createGUI(){
        register=new Frame();
        register.setSize(1000,1000);
        info=new JLabel("Register your account:");
        info.setBounds(400,300,250,20);
        us=new JLabel("Username:");
        us.setBounds(400,400,120,20);
        pas=new JLabel("Password:");
        pas.setBounds(400,550,120,20);
        verifPas=new JLabel("Confirm your password:");
        verifPas.setBounds(400,650,200,20);
        regUsername=new JTextField();
        regUsername.setBounds(400,450,200,20);
        regPassword=new JTextField();
        regPassword.setBounds(400,600,200,20);
        verifPassword=new JTextField();
        verifPassword.setBounds(400,700,200,20);
        back=new JButton("Back");
        back.setBounds(100,850,90,30);
        reg=new JButton("Register");
        reg.setBounds(400,800,200,30);
        register.add(reg);
        register.add(us);
        register.add(pas);
        register.add(info);
        register.add(regUsername);
        register.add(regPassword);
        register.add(verifPas);
        register.add(verifPassword);
        register.add(back);
        register.setLayout(null);
        register.setVisible(true);
    }


    //metoda pentru butoane
    private void actionListeners(){
        reg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==back){
                    LoginGUI log=new LoginGUI();
                    register.dispose();

                }
            }
        });
        register.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                register.dispose();
                System.exit(0);
            }
        });
    }


    //metoda care inregistreaza un nou cont + scrierea acestuia in fisier
    private void createAccount(){
        try {
            //verificare pentru ca numele de utilizator sa nu fie deja existent in accounts
            BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] splitInfo = line.split(":");
                if (splitInfo.length > 0 && splitInfo[0].equals(regUsername.getText())) {

                    JOptionPane.showMessageDialog(null,"Username deja existent!");
                    reader.close();
                    return;

                }

                line = reader.readLine();
            }
            reader.close();

            //verificare conditie campuri + scriere in fisier scor initial 0
            if(regPassword.getText().equals(verifPassword.getText()) && regUsername.getText().length()>=8 && regPassword.getText().length()>=8 && !regUsername.getText().contains(":") && !verifPassword.getText().contains(":")) {
                FileWriter writer = new FileWriter("accounts.txt",true); //append true pentru a adauga si nu a rescrie intregul fisier
                FileWriter writer1=new FileWriter("score.txt",true); //aici adaugam utilizatorul nou si in fisierul cu tokens score cu scor initial 0
                FileWriter writer2=new FileWriter("questScore.txt",true);// aici adaugam jucatorul nou si in fisierul cu quest score cu scor initial 0
                writer.write(regUsername.getText()+":"+regPassword.getText()+"\n"); // scrierea user si parola noua +  separare cu ':'
                writer.flush();//folosesc flush-ul pentru a evita suprapunerea ultimului cont creat+ scorul initial al acestuia peste linia ultimul cont deja creat
                writer1.write(regUsername.getText()+":0"+"\n"); // odata cu creare user-ului, numele acestuia si scorul initial de 0 sunt adaugate in "baza de date" cu scorurile
                writer1.flush();
                writer2.write(regUsername.getText()+":0"+"\n");
                writer2.flush();
                writer.close();
                writer1.close();
                writer2.close();
                JOptionPane.showMessageDialog(null,"Inregistrare efectuata cu succes!");
                regUsername.setText("");
                regPassword.setText("");
                verifPassword.setText("");
            }
            //afisare mesaj de eroare in cazul nerespectarii conditiei pentru field-uri la crearea unui cont nou
            else if(!regPassword.getText().equals(verifPassword.getText())){
                JOptionPane.showMessageDialog(null,"parolele nu se potrivesc!");
            }
            else if(regUsername.getText().length()<8 || regPassword.getText().length()<8){
                JOptionPane.showMessageDialog(null,"campurile trebuie sa aiba minim 8 caractere !");
            }
            else if(regUsername.getText().contains(":") || regPassword.getText().contains(":")){
                JOptionPane.showMessageDialog(null,"Username si parola nu pot contine caracterul ':'");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
