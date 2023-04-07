

import javax.swing.*;

public class BadgeDisplaySystem {
    //badge-uri pentru numarul de challenge-uri completate de utilizator
    private JTextArea textArea;
    public BadgeDisplaySystem(JTextArea textArea){
        this.textArea=textArea;

    }
    //adaugare badge 10 questuri completate
    public void displayBadge10(){
        textArea.append("\n 10 quests completed\n");
    }
    //adaugare badge 100 questuri completate
    public void displayBadge100(){
        textArea.append("\n 100 quests completed\n");
    }
    //adaugare badge 1000 questuri completate
    public void displayBadge1000(){
        textArea.append("\n 1000 quests completed\n");
    }

    //se mai pot adauga eventuale badge-uri aici
}
