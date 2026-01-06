# Contributing to CoolPlanner

Dette dokument beskriver, hvad et nyt teammedlem skal vide for at kunne bidrage til
udviklingen af CoolPlanner på en struktureret og ensartet måde.

## 🔧 Forudsætninger

Inden du bidrager til projektet, forventes det, at du har opsat projektet lokalt.
De fulde softwaremæssige forudsætninger og opsætningsinstruktioner findes i README.md.

## 📁 Projektstruktur

Projektet er opbygget som en klassisk Spring Boot-applikation med følgende hovedpakker:

- **controller** – Håndtering af HTTP requests (GET/POST)
- **service** – Forretningslogik (opdelt i read- og write-services)
- **repository** – Databaseadgang (JDBC, opdelt i read- og write-repositories)
- **rowmapper** – Mapping fra SQL-resultater til Java-objekter
- **model** – Domæneklasser og enums
- **resources/templates** – Thymeleaf HTML-sider
- **test** – Unit tests, web slice tests og integrationstests

## 😌 -> 😧 -> 🫠 -> 🤯 -> 😮‍💨 -> 😌  Git workflow

Vi arbejder efter følgende principper:

- `main` indeholder stabil og færdig kode
- Nye features udvikles i separate feature branches
- Branch-navne skal være beskrivende
- Alle ændringer merges via Pull Requests
- Pull Requests skal godkendes af mindst ét andet teammedlem før merge

## 👀 Tests og kvalitet

- Nye features bør ledsages af relevante tests
- Projektet anvender unit tests, web slice tests og integrationstests
- Kodekvalitet overvåges via CI pipeline og Qodana-analyse
- Pull Requests bør kun oprettes, hvis projektet bygger og tester korrekt

## ✔️ Sådan bidrager du til CoolPlanner

1. Fork projektet eller opret en ny feature branch
2. Implementér ændringer i overensstemmelse med projektets struktur og principper
3. Kør tests lokalt
4. Opret en Pull Request med en kort beskrivelse af ændringerne
5. Afvent review og evt. feedback

### Vi ser frem til dit bidrag til CoolPlanner! 

