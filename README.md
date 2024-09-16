# 🌐 Prototipo Spring Boot - Backend

Questo progetto è il backend dell'applicazione sviluppata per la tesi di laurea triennale in Informatica presso l'Università di Padova. Il backend è stato realizzato con **Spring Boot** e utilizza un database **PostgreSQL**. La parte **frontend** dell'applicazione è disponibile a [questo link](link-frontend).

## 🚀 Come iniziare

### Prerequisiti

- 🛠️ **JDK** v17 o superiore
- 📦 **Maven** v3.6 o superiore
- 🐋 **Docker** (opzionale per l'uso del database PostgreSQL tramite Docker)

### Installazione

1. **Clona il repository**:
   ```
   git clone https://github.com/bandomatteo/Prototipo-SpringBoot.git
   ```

2. **Vai nella directory del progetto**:
   ```
   cd Prototipo-SpringBoot
   ```

3. **Installa le dipendenze Maven**:
   ```
   mvn install
   ```

4. **(Opzionale) Avvia il database PostgreSQL con Docker**:
   Assicurati di avere Docker installato, poi avvia il container:
   ```
   docker-compose up
   ```

   Questo avvierà un'istanza di PostgreSQL sulla porta `5433`.

### Configurazione

Aggiorna i dettagli del database nel file `application.properties` se necessario:

```
spring.datasource.url=jdbc:postgresql://localhost:5433/postgres
spring.datasource.username=postgres
spring.datasource.password=password!
spring.jpa.hibernate.ddl-auto=update
```

### File .env

Ricordati di creare un file `.env` nella root del progetto e di inserire la tua chiave API di OpenAI:

```
OPENAI_API_KEY=```YOUR_API_KEY_HERE```
```

### 🖥️ Avvio del progetto

Per avviare l'applicazione:

```
mvn spring-boot:run
```

L'applicazione sarà disponibile su [http://localhost:8080](http://localhost:8080).

## 🛠️ Comandi disponibili

- **Avvio locale**: `mvn spring-boot:run`
- **Build per la produzione**: `mvn clean package`
- **Test**: `mvn test`

## 🧰 Tecnologie usate

- **Spring Boot**: ^3.3.2
- **PostgreSQL**: ^13
- **JWT (JSON Web Token)**: ^0.12.6
- **LangChain4J**: ^0.34.0
- **Lombok**

## 🐋 Configurazione Docker

Il progetto include un file `docker-compose.yml` per avviare facilmente il database PostgreSQL in un container Docker. Per avviare il database:

```
docker-compose up
```

## 🏗️ Costruzione della build

Per creare una build ottimizzata per la produzione:

```
mvn clean package
```

I file verranno generati nella cartella `target/`.
