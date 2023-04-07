

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginGUI {
    private Frame frame;
    private JTextField username,password;
    private JLabel user,pass,welcome,registerInfo;
    private JButton register,login;

    public LoginGUI(){
        createGUI();
        actionListeners();
    }

    private void createGUI(){
        frame=new Frame("MyCreation");
        frame.setSize(1000,1000);
        frame.setBackground(Color.CYAN);
        welcome=new JLabel("Welcome!");
        welcome.setFont(new Font("Arial",Font.PLAIN,30));
        welcome.setBounds(350,350,140,30);
        user=new JLabel("Username: ");
        user.setBounds(400,400,70,20);
        pass=new JLabel("Password: ");
        pass.setBounds(400,480,70,20);
        username=new JTextField();
        username.setBounds(400,440,200,35);
        password=new JTextField();
        password.setBounds(400,520,200,35);
        login=new JButton("Login");
        login.setBounds(450,560,75,25);
        registerInfo=new JLabel("Don't have an account?");
        registerInfo.setBounds(500,600,200,30);
        register=new JButton("Register");
        register.setBounds(500,650,100,20);



        frame.add(register);
        frame.add(login);
        frame.add(username);
        frame.add(password);
        frame.add(welcome);
        frame.add(user);
        frame.add(pass);
        frame.add(registerInfo);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    //metoda functionalitate butoane din interfata
    public void actionListeners(){
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==register){
                    RegisterGUI registerGUI=new RegisterGUI();
                    frame.dispose();
                }
            }
        });

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLoginData();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               frame.dispose();
                System.exit(0);
            }
        });

    }

    //metoda verificare date de logare
    private void checkLoginData(){
        try {
            String data=username.getText()+":"+password.getText(); // formatez datele sa fie identice cu cele din fisier pentru o comparare usoara
            BufferedReader read=new BufferedReader(new FileReader("accounts.txt"));
            String lineReader= read.readLine();
            boolean loginOK=false; // setez un boolean pe false , in cazul in care contul introdus este corect, acesta se va trece in true
            while(lineReader!=null){

                if(lineReader.equals(data)){
                    loginOK=true; // daca datele exista in fisier, loginOK=true
                    frame.dispose();
                    UserInterface interf=new UserInterface(username.getText()); //pasez numele utilizatorului la noua interfata deschisa pentru a putea stii cine este cel logat
                    break;
                }

                lineReader=read.readLine();

            }
            // daca datele de logare nu exista in fisier, dar acestea corespund contului de administrator, se deschide interfata administratorului
            if(!loginOK && data.equals("administrator:accesachallenge")){
                AdminUI ui=new AdminUI();
                frame.dispose();
            }
            else if(!loginOK){ // daca loginOK ramane in false, afisam mesaj de date incorecte
                JOptionPane.showMessageDialog(null,"Username sau parola incorecte");
                username.setText("");
                password.setText("");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
