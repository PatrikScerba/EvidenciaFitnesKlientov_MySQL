## 📘 Inštalačný a používateľský návod k aplikácii
### Evidencia klientov – verzia 2.0.0 (MySQL + JDBC)

---

## 1. Systémové požiadavky

Pred spustením aplikácie je potrebné mať nainštalované:

### ✔ Java JDK 21 (LTS)

Stiahnite z oficiálnej stránky:
https://adoptium.net/temurin/releases/?version=21

Odporúčaná verzia:

#### - Windows x64 – MSI Installer

---

### ✔ XAMPP (MySQL databáza)

Aplikácia komunikuje s MySQL databázou.


Stiahnuť XAMPP pre Windows môžete tu:
https://www.apachefriends.org/download.html

Po inštalácii spustite XAMPP Control Panel a aktivujte:

- Apache → Start
- MySQL → Start

#### Oba moduly musia svietiť na zeleno (Running)!

---

## 2. Otvorenie phpMyAdmin

### Možnosť 1 - cez XAMPP Control Panel
Kliknite na **Admin** pri MySQL.

### Možnosť 2 cez prehliadač
Zadajte do prehliadača URL adresu:

```
http://localhost/phpmyadmin
```
---
## 3. Import databázy
1. V hornom menu vyberte **Import**
2. Kliknite **Choose File**
3. Vyberte súbor:

```
evidencia_klientov_schema.sql
```
4. kliknite **GO/Import**

Ak sa zobrazí hlásenie Import *was successful-databáza bola úspešne vytvorená.*

**Odporúčanie: Po importe stlačte F5 (obnoviť stránku).**

---

## 4. Spustenie aplikácie

Aplikáciu spustíte dvojklikom na EXE súbor:
```
EvidenciaFitnesApp_MySQL.exe
```
---

## 5. Dokončenie inštalácie
Aplikácia aj databáza sú pripravené na používanie.

---

## 6. Možné problémy a riešenia

#### 1. Aplikácia nefunguje – chyba databázy

- Skontrolujte, či v XAMPP svieti **MySQL** na zeleno (Running).
- Ak MySQL nejde spustiť, môže port 3306 používať iná aplikácia.

#### 2. Import databázy zlyhal

- Uistite sa, že importujete súbor evidencia_klientov_schema.sql.
- Ak sa zobrazí chyba porovnania, skúste import zopakovať po obnovení (F5).

#### 3. EXE súbor sa nespustí


- Uistite sa, že máte nainštalované JDK 21.
- Na Windows môže byť potrebné spustiť EXE, ako správca.


Prajem pohodlné používanie.

#### Autor:
#### Patrik Ščerba

Java Developer | Swing & MySQL | © 2025


