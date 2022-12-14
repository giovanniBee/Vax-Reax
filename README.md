# Vax-Reax
## Software per la gestione delle reazioni avverse ai vaccini

Si vuole progettare un sistema software per gestire le segnalazioni di reazioni avverse (ad esempio, asma, dermatiti,
insufficienza renale, miocardiopatia, …) da vaccini anti-Covid.
Ogni segnalazione è caratterizzata da un codice univoco, dall’indicazione del paziente a cui fa riferimento,
dall’indicazione della reazione avversa, dalla data della reazione avversa, dalla data di segnalazione, e dalle
vaccinazioni ricevute nei due mesi precedenti il momento della reazione avversa.
Per ogni paziente sono memorizzati: un codice univoco, l’anno di nascita, la provincia di residenza e la professione.
Per ogni paziente è possibile memorizzare gli eventuali fattori di rischio presenti (paziente fumatore, iperteso,
sovrappeso, paziente fragile per precedenti patologie cardiovascolari/oncologiche), anche più d’uno. Ogni fattore di
rischio è caratterizzato da un nome univoco, una descrizione e il livello di rischio associato. Per ogni paziente è,
inoltre, memorizzata l’intera storia delle sue vaccinazioni precedenti, anti-Covid-19 e antinfluenzali.
Ogni vaccinazione è caratterizzata da: paziente a cui si riferisce, segnalazioni a cui è legata, vaccino somministrato
(AstraZeneca, Pfizer, Moderna, Sputnik, Sinovac, antinfluenzale, …), tipo della somministrazione (I, II, III o IV dose,
dose unica), sede presso la quale è avvenuta la vaccinazione e data di vaccinazione. Per ogni reazione avversa
sono memorizzati un nome univoco, un livello di gravità (da 1 a 5) e una descrizione generale, espressa in linguaggio
naturale. Una reazione avversa può essere legata a molte segnalazioni. Per ogni paziente sono memorizzati il
numero di reazioni avverse segnalate ed il numero di vaccinazioni ricevute.
Il sistema deve supportare i medici che effettuano la segnalazione. Dopo opportuna autenticazione, il medico viene
introdotto ad una interfaccia che permette l’inserimento dei dati delle reazioni avverse e dei pazienti. Il codice univoco
dei pazienti è gestito dal sistema, che tiene traccia dei pazienti indicati da ogni medico. Ogni medico vede solo i
codici identificativi dei pazienti, dei quali ha già segnalato qualche reazione avversa, e le relative informazioni.
Ad ogni fine settimana o quando il numero di segnalazioni raggiunge la soglia di 50, il sistema manda un avviso ad
uno dei farmacologi responsabili della gestione delle segnalazioni di reazioni avverse. Il farmacologo, dopo
autenticazione, accede alle segnalazioni (tutte, con l’indicazione del medico che le ha fatte) e può effettuare alcune
analisi di base (quante segnalazioni per vaccino, quante segnalazioni gravi in settimana, quante segnalazioni per
provincia e quante segnalazioni per sede di vaccinazione). Il sistema, inoltre, avvisa il farmacologo quando un
vaccino ha accumulato in un mese oltre 5 segnalazioni di gravità superiore a 3.
In base alle segnalazioni e agli avvisi del sistema, il farmacologo può proporre di attivare una fase di controllo del
vaccino. Tale proposta viene registrata dal sistema, che tiene traccia di tutte le proposte relative ai vaccini segnalati.
