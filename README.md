# Malabe Tuk Tuk Depot - Spare Parts Inventory System

A JavaFX desktop application for managing spare parts inventory, dealers, and point-of-sale
checkout for a tuk-tuk parts depot.

## Requirements

- **JDK:** 21 or later (developed and tested with JDK 26)
- **JavaFX:** 21.0.6 (`javafx-controls`, `javafx-fxml`, `javafx-swing`) - downloaded
  automatically by Maven, no separate install needed
- **Maven:** 3.9+ (or use IntelliJ's bundled Maven support)
- **JUnit:** 5.12.1 (Jupiter) - for running the automated test suite

## How to Run the Application

**Option 1 - From the command line (Maven):**
```bash
mvn clean javafx:run
```

**Option 2 - From IntelliJ IDEA:**
1. Open this folder as a Maven project (IntelliJ will detect `pom.xml` automatically).
2. Wait for Maven to finish downloading dependencies.
3. Run the `Launcher` class (or `MainApp`), found at
   `src/main/java/org/example/coursework/Launcher.java`.

The application window titled **"Malabe TukTuk Depot"** should open, showing the main menu.

## How to Run the Tests

**From the command line:**
```bash
mvn test
```

**From IntelliJ:** right-click the `src/test/java` folder → **Run 'All Tests'**, or right-click
any individual test class (e.g. `ValidatorTest`) to run just that one.

Test results appear in the Run panel, showing how many tests passed/failed per class.

## Project Structure

```
courseWork/
├── pom.xml
├── inventory_legacy.txt      # Raw/dirty inventory data provided for the coursework
├── dealers_legacy.txt        # Raw/dirty dealer data provided for the coursework
├── images/                   # Part images referenced by the inventory data
├── src/main/java/org/example/coursework/
│   ├── Controllers/          # JavaFX FXML controllers (one per screen)
│   └── ...                   # Model and service classes (Part, Validator, CommContext, etc.)
├── src/main/resources/org/example/coursework/
│   └── *.fxml                # JavaFX screen layouts
└── src/test/java/org/example/coursework/
    └── *Test.java             # JUnit test classes
```

## Git Repository

https://github.com/ChamodiMadurasinghe/CM1601_CW.git

## Assumptions Needed to Run the Project

Low-stock thresholds are per-part, not per-category or store-wide. Each Part stores its own lowStockThreshold, defaulting to Part.defaultThreshold if one isn't
set - there is no single screen to review every part's threshold at once.
Duplicate parts are detected by ID only (case-insensitive). Two records with the same ID in different case (e.g. p001 vs P001) are treated as the same part and 
cannot both exist.
Missing/blank fields become "NULL" after cleaning (for text fields), while a missing price or quantity becomes 0 - meaning a genuinely-zero value and a missing 
value look identical once cleaned.
Dirty legacy dates are parsed leniently by InventoryDataCleaner (multiple formats accepted, unparseable dates default to NULL rather than stopping the clean). 
Dates typed through the GUI, however, must be in yyyy-MM-dd (or yyyy/MM/dd) format - other formats are rejected there, even if the legacy cleaner would have 
accepted them.
Image files are only checked by extension before use. isValidImageFile() checks the file name, not the actual encoding; loadImage() relies on ImageIO plus the
TwelveMonkeys library to decode the real bytes (including WebP). A file with a valid-looking extension but an undecodable format will simply show "No image" in
the table, with no specific error shown to the user.
