# RegexToNFA_Thomson-s_Algorithm
*This is a small java application which converts a regex in infix or prefix form to its equivalent NFA using Thomson's Algorithm.*

----------------------

> ## Explicatii Tema1(Ex3) - Tehnici de Compilare. 

In cadrul acestei aplicatii java am implimentat algoritmul de transformare a unui regex in forma infix sau postfix folosind regulile Algoritmului Thompson. Pentru detalii accesati [urmatorul link](https://en.wikipedia.org/wiki/Thompson%27s_construction)

In cadrul aplicatiei user-ul are un mic menu, in care este capabil sa aleaga forma dorita (*prefix/infix*) si respectiv sa introduca regex-ul. Am implementat si o mica parte de validare a regexului, care verifica ca simbolurile aparute in regex sa fie valide conform formei alese. Deasemenea, aplicatia tine cont si de partea sintactica a regexului, asftel incat in cazul unui input gresit ca sintaxa, va impune userul sa introduca un input valid.

In cazul unui input valid, algoritmul construieste AFN-ul si deschide automat o fereastra in care prezinta graful rezultat. Mai mult ca atat, acesta va fi salvat intr-o poza de tip png. Aveti un exemplu mai jos :

![](Tema1-RegexToNFA/src/main/resources/NFA-Visualization.png)

