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
| PostgeSQL	| 24,29        	| 8,2          |
| MongoDB  	| 35,7         	| 3,6          |
| Neo4j  	| 165,2         | 49,1         |
| Cassandra | -          	| -            |
| Redis    	| -          	| -            |


## Virualisiert

| Database 	| Write         | Read         |
|----------	|-------------- |------------  |
| PostgeSQL	| 35,26         | 11,82        |
| MongoDB  	| 48,26       	| 5,2          |
| Neo4j  	| 254,7         | 81           |
| Cassandra | -          	| -            |
| Redis    	| -          	| -            |

