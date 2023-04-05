# Documentation

## Data partition & Affinity

### Apache Ignite or GridGain

Il partizionamento è gestito interamente dal cluster, ogni dato viene assegnato ad una partizione 
e questa viene distribuita sul cluster. Il modo con il quale viene distribuita può essere personalizzato 
in termini di affinità e modalità di replicazione.

- Affinità: oggetti con stessa chiave di affinità finisco nella stessa partizione, 
  questo può essere utile per collocare dati che vengono spesso letti e/o scritti insieme sullo stessa partizione in modo che in fase di query questa venga presa in carico da un solo nodo che ha tutti i dati in “locale”. Utile per eseguire operazioni sul cluster sfruttando l’affinità del dato rispetto al nodo.
- Replicazione: esistono 2 modalità di replicazione: partitioned e replicated 
    * partitioned: le partizioni sono distribuite equamente tra tutti i nodi del cluster. 
      In questo modo la memoria totale utilizzabile è la somma della memoria dei singoli nodi (a meno di backup). Questo consente di aggiungere un nodo e di aumentare la memoria totale disponibile. Preferibile quando si tratta un set di dati molto grande
    * replicated: tutte le partizioni sono presenti in tutti i nodi. In questo modo la memoria totale 
     è data dalla memoria del singolo nodo. Preferibile quando il dataset è relativamente piccolo e ci sono pochi update.
