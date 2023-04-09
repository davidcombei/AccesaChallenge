# AccesaChallenge
conturi: 

         davidcombei, parola :12345678 -cont cu multe token score pentru testabilitate challenges panel + score updates

         administrator,	parola :accesachallenge -cont administrator care perminte modificare fisierelor direct din aplicatie

         pentru un cont nou + update score al acestuia, puteti folosi functia register, cu ocazia aceasta puteti testa si functionalitatea register


Notes: fisierul words este completat de pe un site cu "top 10000 used words in english", iar in azure VM nu stiu din ce motiv, fiecare cont+score din fisier nu mai este pe un new line, toate sunt pe o singura linie, insa programul le citeste ca fiind fiecare cu new line, deci functioneaza corect. (cred ca este o problema a afisajului in azure pentru fisierele txt prelucrate de executabilul jar)  


Ce reprezinta aplicatia ? 
Este un joc de tip type-racer in care poti acumula 1 tokens pentru fiecare cuvant corect introdus, dar mult mai multe prin complentarea challenge-urilor prestabilite , dar si a challenge-urilor (cuvintelor) propuse si lansate de catre ceilalti jucatori! Se pot vizualiza challenge-urile prestabilite si lansate prin apasarea butonului "Challenges panel" din interfata utilizatorului dupa conectare. Exista si un sistem de badge-uri in interfata utilizatorului care sunt primite la un anumit numar de quest-uri completate (momentam sunt badge-uri pentru 10, 100,1000 de questuri completate).
Pentru a putea lansa un nou challenge ai nevoie de o balanta de 10000 de tokens care va fi automat substrasa din cont in momentul lansarii unui challenge.
Avem si doua leader board-uri: unul pentru numarul de tokens si unul pentru numarul de quest-uri completate. In ambele leader board-uri se afiseaza primii 10 jucatori care au cele mai mari scoruri in categoria respectiva.


Functionalitatile proiectului:

 Interfata login: Clasa LoginGUI preia din text field-urile aferente datele si verifica existenta contului in fisierul txt 'accounts.txt', in cazul introducerii gresite a datelor, se afiseaza un mesaj de eroare aferent. Aceasta interfata are si un buton de register care ne trimite la urmatoarea functionalitate:

 Interfata register: Clasa RegisterGUI dispune de 3 text field-uri, primul pentru introducerea unui username, al doilea pentru introducerea unei parole si al treilea pentru verificarea parolei. Clasa dispune si aceasta de niste verificari pentru datele introduse: 
- verificare autenticitate username  : daca username-ul exista deja in baza, se afiseaza un mesaj care indica acest lucru
- verificare pentru parola : daca parolele din cele 2 field-uri pentru parola nu se potrivesc, se afiseaza un mesaj corespunzator
- verificare dimensiuni user si parola: acestea trebuie sa aiba minim 8 caractere , daca una din acestea nu au minim 8 caractere, se afiseaza un mesaj
 -verificare caracter ':' pentru user si parola: daca parola sau username-ul contine acest caracter, se afiseaza un mesaj, deoarece datele din fisierul cu user si parola sunt de forma user:parola pentru separarea acestora si prelucrarea mai usoara in program
In cazul in care toate verificarile sunt respectate, contul nou este introdus in fisierul accounts.txt + username-ul este introdus si in fisierele score.txt si questScore.txt cu scorul initial 0. Am facut acest lucru pentru a putea tine scorul + eventuala avansare in leader boards( am facut 2 leader boards unul pentru token score si unul pentru quest score) pentru fiecare utilizator nou!

 Interfata utilizatorului : Clasa UserInterface contine interfata utilizatorului cu numele + scorurile acestuia si butoanele de acces la cele doua leader board-uri + buton acces interfata quest-uri + buton Play care trimite utilizatorul la interfata jocului propriu-zis. Interfata mai contine si un textArea care afiseaza badge-urile obtinute. Practic, programul preia quest score-ul din text field-ul acestuia si in functie de acesta afiseaza badge-urile obtinute sau niciunul in cazul in care nu ai destul quest score.
Scorurile sunt preluate din fisierele corelate acestora, fisierele sunt scrise in formatul mentionat anterior la inregistrare user:scor, programul cauta utilizatorul conectat , ii preia continutul dupa ':' si il adauga in text field-urile cu scoruri din interfata pentru afisaj (de mentionat, text field-urile care nu ar trebui sa fie modificate, NU se pot modifica din interfata , sunt setate editable pe false!) .

 Interfata jocului : Clasa GameGUI contine jocul, adica un type-racer.Aici avem un text field care contine un timer de 60 de secunde (atat dureaza o runda), un text field care contine cuvantul generat si sub acesta este un text field in care trebuie scris cuvantul generat pentru punctare, un buton start care porneste timer-ul si genereaza primul cuvant. In cazul in care jucatorul nu introduce corect cuvantul, nu se va genera alt cuvant pana la introducerea corecta a cuvantului curent. La finalizarea timer-ului de 60 de secunde, implicit runda, din text field-urile care contin token si quest score se preiau scorurile obtinute in runda respectiva, apoi intervin clasele ScoreUpdate si QuestScoreBoard care dau update la fisierele cu scorurile, adaugand scorurile obtinute la scorul curent al jucatorului conectat.

 Cum se face update-ul la scoruri? Cum am mentionat anterior clasele ScoreUpdate si QuestScoreUpdate preiau numele si scorul din runda curenta obtinute de jucator. Prin intermediul unui string builder care contine continutul initial al fisierului ( scorurile actuale inainte de modificare), se cauta numele jucatorului conectat, se preia scorul initial, la care se adauga si scorul obtinut in runda respectiva. Stocam nume:scor vechi si nume:scor updatat in doua string-uri pe care le vom folosi ca parametri la replace in formarea noului string care va fi scris in fisier. definim noul string care va fi scris in fisier,la care vom apela string-ul format de string builder si vom da replace la string-ul cu scorul vechi cu string-ul cu scorul updatat. Scriem noul string in fisier astfel, am dat update la scoruri. Principiul este acelasi si pentru token score si pentru quest score file.

Functionalitate cont + fereastra de administrator: Am definit un cont de administrator, in cazul in care programul nu recunoaste contul introdus, se mai face o verificare suplimentara, aceea de a fi contul administratorului. In cazul in care contul este cel al administratorului, se va deschide interfata acestuia, care ofera acces la fisierele cu : token si quest score, fisierul cu conturi inregistrate si fisierul cu cuvintele posibile in joc pentru eventualele modificari necesare de catre un administrator!

Explicatia codului detaliata se afla in acesta, am lasat comentarii in cod.
