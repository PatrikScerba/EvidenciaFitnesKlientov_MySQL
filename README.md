# 🧾 Evidencia Fitnes Klientov – Verzia 2.0.0 (MySQL + Java Swing)

Moderná desktopová aplikácia určená pre fitness centrá na správu klientov.  
Projekt je postavený na **Java SE**, **JDBC**, **MySQL** a **Java Swing**, so zameraním na čistý kód, prehľadnú architektúru a komfortné UI.

---

## 🚀 Funkcionality

### ✔ Registrácia klienta
- Validácia vstupov (meno, priezvisko, email, dátum narodenia…)
- Uloženie do MySQL databázy
- Zobrazenie potvrdenia o úspešnej registrácii
- Kontrola veku (min. 15 rokov)
- Automatické uloženie dátumu registrácie

### ✔ Vyhľadávanie klientov
- Fulltextové vyhľadávanie podľa mena/priezviska
- Normalizácia textu (bez diakritiky)
- Možnosť výberu pri viacerých zhodách

### ✔ Zoznam klientov
- Prehľadná tabuľka všetkých klientov
- Zarovnané stĺpce (ID, meno, dátumy, email…)
- Automatické načítanie dát z databázy

### ✔ Detail klienta
- Prehľad údajov klienta
- UX rozdelenie na režim zobrazenia a úpravy
- Režim **zobrazenia**
- Režim **úpravy**
- Úprava údajov (edit mód)
- Vymazanie klienta
- Potvrdenie akcií (úprava, vymazanie)
- úprava, vymazanie, zrušenie úprav
- Registrácia klienta je fixná 

---

## 🏗 Použité technológie

- **Java 21**
- **JDBC (Java Database Connectivity)**
- **MySQL 8+**
- **Java Swing (UI)**
- **FlatLaf – moderný vzhľad UI**
- **IntelliJ IDEA**
- **Launch4j**

---

## 🎨 UI & UX vylepšenia vo verzii 2.0.0

### Verzia 2 prináša skvalitnenie používateľského komfortu:

- decentnejší vzhľad aplikácie
- nové rozloženie prvkov vo všetkých oknách
- jednotná typografia a štýl komponentov
- zlepšenie odsadenia, zarovnania a rozostup
- prehľadná karta detailu klienta s dvoma režimami
- moderné farby a čistý svetlý dizajn FlatLaf
- zarovnanie stĺpcov v zozname klientov

---

## 📸 Ukážky aplikácie

### Vizualizácia častí desktopovej aplikácie **Evidencia Fitnes Klientov**:

### 🏠 Hlavné menu
![Hlavné menu](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-07%20181254.png)


### 🧍‍♂️ Registrácia klienta - Detail klienta - Úprava klienta
![Registrácia-Detail klietna-Úprava klienta](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20212039.png)

### ⚠️ Validácia vstupov

![Validácia vstupov](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20211746.png)

### 🔍 Vyhľadávanie klienta

![Vyhľadávanie](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20080407.png)

### 📋 Zoznam všetkých klientov (Swing-MySQL)

![Zoznam klintov](screenshots/Sn%C3%ADmka%20obrazovky%202025-12-05%20211904.png)

---

## 🗂 Štruktúra projektu

```src/
└── sk.patrikscerba
├── db/ # Práca s databázou (DAO + pripojenie)
├── model/ # Dátové objekty (Klient)
├── ui/ # Swing UI okná
├── utils/ # Validácie
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

### Aplikácia bola testovaná ako desktopová CRUD aplikácia

### Testované scenáre:

- overené čítanie a zápis do MySQL databázy
- registrácia, úprava a vymazanie klienta 
- vyhľadávanie podľa mena/priezviska
- validácie pre všetky polia
- funkčnosť grafického rozhrania
- stabilita pri práci s MySQL

### Testované prostredie:

- Windows 11,
- Temurin JDK 21
- XAMPP (MySQL server)
- IntelliJ IDEA 
- Launch4j 

---

## 🚀Spustenie aplikácie 
### 1. vývojárske prostredie(zdrojový kód)

###  ✔️ Požiadavky
- IntelliJ IDEA JDK 21
- XAMPP (MySQL server)
- JDBC driver (automaticky načítaný cez projekt)

###  📦 klonovanie projektu
Projekt si stiahnete cez Git:
git clone

👉 https://github.com/PatrikScerba/EvidenciaFitnesKlientov_MySQL.git

---

### ✔ 2. Ako EXE (pre používateľov)

###  ✔️ Požiadavky
- JDK/JRE 21+
- XAMPP (MySQL server)


## 📥 Stiahnutie aplikácie

Novú stabilnú verziu aplikácie (EXE + schéma databázy + používateľský návod) si môžete stiahnuť tu:

👉 **[Stiahnuť EvidenciaFitnesApp – Verzia 2.0.0](https://github.com/PatrikScerba/EvidenciaFitnesKlientov_MySQL/releases/tag/v2.0.0)**

ZIP obsahuje:
- EXE aplikáciu
- Databázovú schému `evidencia_klientov_schema.sql`
- Používateľský manuál (PDF + MD)

---

## 📌 Plány do budúcna (Roadmap)

### 🔜 Verzia 3.0.0 (Hybrid – MySQL + XML)
- Generovanie a správa QR kódov pre klientov
- Obmedzenie jedného vstupu **1x denne**
- Funkcia predĺženia permanentky/kontrola 
- Offline režim pri výpadku databázy (iba čítanie údajov)
- Export údajov do PDF/CSV formátu 

### 🔜 Verzia 4.0.0 (Spring Boot Backend + React Frontend)

- REST API pre správu klientov 
- Moderný webový frontend v Reacte
- Pokročilé prihlasovanie (zamestnávateľ /zamestnanec)
- Riadenie prístupov podľa rolí
- Uchovávanie záznamov 
- Pokročilé logovanie, monitoring a audit zmien
- Oddelená architektúra backend-frontend

---

### 🏆 Cieľ portfólia

Tento projekt je súčasťou môjho osobného portfólia.
Cieľom bolo ukázať zvládnutie:

- Java Swing GUI, 
- prácu s databázou MySQL cez JDBC
- návrh desktopovej CRUD aplikácie

---

### 👤 Autor
**Patrik Ščerba**  
Java Developer |Java • Swing • MySQL | © 2025

- [LinkedIn](https://www.linkedin.com/in/patrik-%C5%A1%C4%8Derba/)

- [GitHub](https://github.com/PatrikScerba)




