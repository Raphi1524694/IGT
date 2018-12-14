![aufg](aufg6.JPG)

# Performance

Für die Schreibwerte wurden 5000 Flüge mit unterschiedlichen zufälligen Werten generiert und abgespeichert. 
Für die Lesewerte wurden diese Flüge wieder abgerufen. 
Alle Daten in Sekunden.

System:
MacOS High Sierra
2,2 GHz Intel i7
16 GB RAM 1600 MHz
 
## Docker

| Database 	| Write         | Read         |
|----------	|-------------- |------------  |
| PostgreSQL| 24,29        	| 8,2          |
| MongoDB  	| 35,7         	| 3,6          |
| Neo4j  	| 165,2         | 49,1         |
| Cassandra | 324         	| 495          |
| Redis    	| 281,4        	| 5,5          |

Unsere Versuche haben ergeben, dass PostgreSQL für die Schreib- und MongoDB für die Lesezugriffe die besten Ergebnisse erzielt hat. Es ist jedoch deutlich, dass insgesamt zwischen NoSQL und SQL Datenbanken insgesamt eine große Diskrepanz besteht. Dass MongoDB den schnellsten Lesezugriff hat, hat verschiedene Gründe. Zum einen besitzt MongoDB keine Joins, welche Performance normalerweise einschränken. Zum anderen existieren in MongoDB weniger Persistierungschecks, welche in Relationalen Datenbanken häufig ausgeführt werden. Die extrem hohen Zeiten bei dem Column Store Cassandra können bei uns durch die immense Anzahl von Konsolenausgaben der Datenbank erklärt werden. Die hohen Zeiten bei Neo4j können dadurch erklärt werden, dass Graphen als Datenstrukturen aufwändiger zu erzeugen sind als einfache Relationen. Ein Key-Value store wie Redis hingegen bietet sich nicht für das Speichern komplexer Strukturen, wie unseren Objekten, an.

## Virualisiert

| Database 	| Write         | Read         |
|----------	|-------------- |------------  |
| PostgeSQL	| 35,26         | 11,82        |
| MongoDB  	| 48,26       	| 5,2          |
| Neo4j  	| 254,7         | 81           |
| Cassandra | -          	| -            |
| Redis    	| -          	| -            |

Die höheren Zeiten der virtualisierten Datenbanken sind dadurch begründet, dass zwischen dem Hostsystem und der virtualisierten Datenbank noch eine zusätzliche Schicht, nämlich das virtuelle Betriebssystem, liegt.
