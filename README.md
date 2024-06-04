Dokumentacja Projektu
Dane autorów i ich wkład w poszczególne części projektu
Autor: Weronika Kolęda
Wkład: Implementacja serwisu bankowego (klasy BankService i BankServiceImpl),
integracja RMI.
Implementacja interfejsu użytkownika (klasy ClientGUI, AdminGUI, UserGUI), konfiguracja
FlatLaf, dokumentacja.
Tytuł programu
System bankowy z interfejsem graficznym i zdalnym wywoływaniem metod (RMI).
Krótki opis celu programu
Celem programu jest stworzenie systemu bankowego, który umożliwia administratorowi
zarządzanie kontami użytkowników, a użytkownikom wykonywanie operacji bankowych
takich jak przelewy, sprawdzanie salda i historii transakcji. System korzysta z RMI do
komunikacji między klientem a serwerem. Użytkownik może również usunąć swoje konto z
systemu oraz sprawdzić listę haseł potrzebnych do przelewów.
Opis i schemat struktury logicznej aplikacji
Serwer RMI:
 - Klasa Server uruchamia serwer RMI, który nasłuchuje na porcie 1099.
 - Klasa BankServiceImpl implementuje interfejs BankService i udostępnia metody zdalne.
Interfejs RMI:
 - Klasa BankService definiuje zdalne metody dostępne dla klientów.
Klient:
 - Klasa ClientGUI uruchamia graficzny interfejs użytkownika dla klientów banku.
 - Klasy AdminGUI i UserGUI obsługują operacje dostępne dla administratora i
użytkowników banku.
Informacje o wykorzystanych klasach niestandardowych
Account:
 - Reprezentuje konto bankowe użytkownika.
User:
 - Reprezentuje użytkownika banku, przechowuje informacje o koncie, hasłach
transferowych i historii transakcji.
BankService:
 - Interfejs zdalnego serwisu bankowego.
BankServiceImpl:
 - Implementacja interfejsu BankService, zawiera logikę biznesową.
ClientGUI, AdminGUI, UserGUI:
 - Klasy obsługujące graficzny interfejs użytkownika dla różnych ról (klient, administrator).
Opis specyficznych metod rozwiązania problemu
RMI:
 - Naming.lookup("rmi://localhost:1099/BankService"): Odnajduje zdalny obiekt serwisu
bankowego.
Metody zdalne:
 - createAccount(String username, String password, double initialBalance, List<String>
transferPasswords): Tworzy nowe konto użytkownika.
 - transfer(String fromUser, String toUser, double amount, int passwordIndex, String
password): Przelewa środki między kontami.
 - getBalance(String username): Zwraca saldo konta użytkownika.
 - verifyUser(String username, String password): Weryfikuje dane logowania użytkownika.
Krótka instrukcja obsługi
Uruchomienie serwera:
 - Uruchom klasę Server, aby uruchomić serwer RMI.
Uruchomienie klienta:
 - Uruchom klasę ClientGUI dla każdego klienta, który ma korzystać z systemu.
 - Zaloguj się jako administrator lub użytkownik.
 - Wykonuj dostępne operacje (tworzenie kont, przelewy, sprawdzanie salda itp.).
Ograniczenia programu
Maksymalna liczba obsługiwanych klientów:
 - Program nie ma określonego limitu klientów, ale wydajność zależy od zasobów serwera.
Inne istotne informacje związane z tematem projektu
FlatLaf:
 - Program korzysta z FlatLaf do stylizacji interfejsu użytkownika, co zapewnia nowoczesny
i estetyczny wygląd.
RMI:
 - Wykorzystanie RMI umożliwia rozdzielenie logiki biznesowej od interfejsu użytkownika,
co poprawia skalowalność i elastyczność systemu.
