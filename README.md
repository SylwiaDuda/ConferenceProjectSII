# ConferenceProjectSII
1. Aplikacja: Java 11, Maven, Spring Boot, Vaadin 8, PostgreSQL / H2

2. W celu uruchomienia projektu należy zaimportować projekt maven, zbudować przy użyciu mavena (clean install), 
  następnie skonfigutować baze danych (punkt 3). Uruchomić jako aplikację Spring Boot(run). 

3. Baza dany

3.1. Aplikacja korzysta z bazy danych PostgreSQL, która jest skonfiurowana w pliku application.properties.
   Należy utworzyć bazę w PostgreSQL oraz ustawić odpowiednie url/username/password w pliku application.properties.
   Zalżność w pliku pom.xml
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

3.2. Aplikacja może korzystać z bazy danych in-memory H2:
   Należy usunąć z pliku application.properties ustawienia dotyczące bazy PostgreSQL oraz dodać zależność w pliku pom.xml 
   oraz zaminić wyżej wynienioną zaleźność na:
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
        
 3.3. Baza jest automatycznie uzupełniana danymi generowanymi w pliku DataGenerator.java 
        
        
       
