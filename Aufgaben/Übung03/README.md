![aufg](aufg3.JPG)

# Datenmodelle
| Database 	| Type            	|
|----------	|-----------------	|
| MySQL    	| SQL Database    	|
| MongoDB  	| Document Store  	|
| Ehcache  	| Cache           	|
| Neo4j    	| Graph Database  	|
| Redis    	| Key-value Store 	|


# Impedance Mismatch

|                     	| Classes                               	| Tables                             	| Document Store                          	| Graph based Database                          	| Key Value Store                    	| Wide Column Database                                        	|
|---------------------	|---------------------------------------	|------------------------------------	|-----------------------------------------	|-----------------------------------------------	|------------------------------------	|-------------------------------------------------------------	|
| Struktur            	| attributes, methods, classes          	| Columns and Rows                   	| documents (flexible)                    	| nodes (id and data) and edges (relationships) 	| key and value                      	| Columns and Rows, but columns can be different for each row 	|
| Instanz             	| simulates behavior, mutatable         	| fixed State                        	| fixed State                             	| fixed State                                   	| fixed State                        	| fixed State                                                 	|
| Kapselung           	| information hiding, getter und setter 	| x                                  	| x                                       	| x                                             	| x                                  	| x                                                           	|
| Identität           	| object ID                             	| Primary Key                        	| unique id usually auto generated        	| nodeID                                        	| Key                                	| Primary Key                                                 	|
| Verarbeitungsmodell 	| discrete access (singular)            	| quantity based access              	| object value based acess returns object 	| node and relation based access                	| access through key returns value   	| quantity based access                                       	|
| Wartung und Pflege  	| software developer                    	| DB admin without programming skill 	| DB admin without programming skill      	| DB admin without programming skill            	| DB admin without programming skill 	| DB admin without programming skill                          	|

# OR-Mapper
REST-Server