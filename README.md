# 🧾 Evidencia Fitnes Klientov – Verzia 2.0.0 (MySQL + Java Swing)

Moderná desktopová aplikácia určená pre fitness centrá na správu klientov.  
Projekt je postavený na **Java SE**, **JDBC**, **MySQL** a **Java Swing**, so zameraním na čistý kód, prehľadnú architektúru a komfortné UI.

---

## 🚀 Funkcionality

- **Registrácia nového klienta**
    - Validácia vstupov (meno, priezvisko, email, dátum narodenia…)
    - Uloženie do MySQL databázy
    - Kontrola veku (min. 15 rokov)
    - Zobrazenie potvrdenia o úspešnej registrácii
    - Automatické uloženie dátumu registrácie


- **Vyhľadávanie klientov**
    - Fulltextové vyhľadávanie podľa mena/priezviska
    - Normalizácia textu (bez diakritiky)
    - Možnosť výberu pri viacerých zhôd
  


- **Zoznam klientov**
    - Prehľadná tabuľka všetkých klientov
    - Zarovnané stĺpce (ID, dátumy, mená…)
    - Automatické načítanie dát z databázy


- **Detail klienta**
    - Prehľad údajov klienta
    - UX rozdelenie na režim zobrazenia a úpravy
    - Úprava údajov (edit mód)
    - Vymazanie klienta
    - Potvrdenie akcií (editácia, vymazanie)

---

## 🏗 Použité technológie

- **Java 24**
- **JDBC (Java Database Connectivity)**
- **MySQL 8+**
- **Java Swing (UI)**
- **FlatLaf – moderný vzhľad UI**
- **IntelliJ IDEA**

---

## 🎨 UI & UX vylepšenia vo verzii 2.0

### Verzia 2 prináša zlepšenie používateľského komfortu:

- decentnejší vzhľad aplikácie
- nové rozloženie prvkov vo všetkých oknách
- jednotná typografia a štýl komponentov
- zlepšenie odsadenia, zarovnania a rozostup
- prehľadná karta detailu klienta s dvoma režimami:
    - **zobrazenie**
    - **úprava**
- moderné farby a čistý svetlý dizajn FlatLaf
- zarovnané tabuľky v zozname klientov

---

## 📸 Ukážky aplikácie

Vizualizácia hlavných častí desktopovej aplikácie **Evidencia Fitnes Klientov**:

### 🏠 Hlavné menu
![Hlavné menu](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20084003.png)


### 🧍‍♂️ Registrácia klienta - Detail klienta - Úprava klienta
![Registrácia-Detail klietna-Úprava klienta](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20212039.png)

### ⚠️ Validácia vstupov

![Validácia vstupov](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20211746.png)

### 🔍 Vyhľadávanie klienta

![Vyhľadávanie](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20212102.png)

### 📋 Zoznam všetkých klientov (Swing - MySQL)

![Zoznam klintov](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20211904.png)

---

## 🗂 Štruktúra projektu

```src/
└── sk.patrikscerba
├── db/ # Práca s databázou (DAO + pripojenie)
├── model/ # Dátové objekty (Klient)
├── ui/ # Swing UI okná
├── utils/ # Validácie a pomocné triedy
└── EvidenciaFitnesKlientovApp.java
```

---

## 🛢 Databáza MySQL

Použitá tabuľka: **klienti**

```sql
CREATE TABLE klienti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    krstne_meno VARCHAR(50),
    priezvisko VARCHAR(50),
    datum_narodenia DATE,
    telefonne_cislo VARCHAR(15),
    email VARCHAR(100),
    adresa VARCHAR(255),
    datum_registracie DATE DEFAULT CURRENT_DATE
);
```

---

## 🧪 Testovanie

#### Projekt bol otestovaný ako **desktopová CRUD aplikácia**:
- overené čítanie a zápis do MySQL databázy,
- testované scenáre: registrácia, úprava, mazanie, vyhľadávanie,
- validácia vstupov pre všetky polia,
- funkčnosť GUI.

---

## 🚀 Spustenie aplikácie (vývojárske prostredie)

### 1. ✔️ Požiadavky
- **JDK 24** (projekt bol vyvíjaný a testovaný v JDK 24)
- Plne kompatibilné aj s **JDK 21+ (LTS)**
- IntelliJ IDEA (odporúčané)
- MySQL Server + MySQL Workbench
- JDBC driver (automaticky načítaný cez projekt)

---

### 2. 📦 Naklonovanie projektu
Projekt si stiahnete cez Git:
git clone https://github.com/PatrikScerba/EvidenciaFitnesKlientov_MySQL.git

---

## 📌 Plány do budúcna (Roadmap)

- QR kódy pre klientov (verzia 3.0 – Hybrid DB + XML)
- Obmedzenie jedného vstupu denne
- Funkcia predĺženia permanentky podľa dátumu
- Offline režim pre výpadok databázy
- Export do PDF/CSV
- REST API vo verzii 4.0 (Spring Boot)

---

🏆 Cieľ portfólia
Tento projekt je súčasťou môjho osobného portfólia.
Cieľom bolo ukázať zvládnutie:

- Java Swing GUI, 
- prácu s databázou MySQL cez JDBC,

---

👤 Autor

Patrik Ščerba
Java Developer (Java SE • JDBC • Swing • MySQL)

- [LinkedIn](https://www.linkedin.com/in/patrik-%C5%A1%C4%8Derba/)

- [GitHub](https://github.com/PatrikScerba)

---
## ⚖️ Licencia

Projekt **Evidencia Fitnes Klientov** je určený na študijné a nekomerčné účely.

© 2024 Patrik Ščerba. Všetky práva vyhradené.



