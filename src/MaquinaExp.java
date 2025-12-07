import java.util.Scanner;
public class MaquinaExp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean logged = false;
        char menuUsuaris;
        String usuari = ""; // es podria fer un array en cas de voler enregistrar més usuaris addicionals
        String contrasenya = ""; // també es convertiria en un array comparant la posició dels usuaris dins
        // l'array anterior per treure les seves respectives contrasenyes dins aquest array
        final String ADMIN = "admin"; // es crea una constant pel nom de l'usuari admin
        String contraAdmin = "";
        boolean contraValida = false;
        boolean contraAdmValida = false;
        String login = "pere"; // borrar "pere", placeholder
        do {
            String disponible = (contraAdmValida ? "" : " (\u001B[31mno disponible\u001B[0m)");
            System.out.print("Configuració d'usuaris\n\n" +
                    "1. Registrar usuaris\n" +
                    "2. Login" + disponible + "\n\n" +
                    "Què vols fer? ");
            menuUsuaris = sc.next().charAt(0);
            sc.nextLine();
            switch (menuUsuaris) {
                case '1':
                    if (contraValida) { // no fa falta validar també contraAdmValida ja que una vegada entrat aquí no hi ha manera de tornar
                        // abans de crear tots els usuaris, pel que afegir l'altra variable és redundant
                        System.out.print("Ja tens usuaris creats, es sobreescriuran sobre els anteriors. En vols crear de nous? (si/no) ");
                        String reescriure = sc.nextLine();
                        if (reescriure.equalsIgnoreCase("si")) {
                            //si diu que si, deixa passar i reinicia les variables de contrasenyes vàlides perquè entri als bucles
                            contraValida = contraAdmValida = false; // reinicia variables
                        } else {
                              // només detecta si dius si per estalviar un bucle. si diu qualsevol altra cosa, mostra un missatge i surt
                              // del switch per tornar al menú principal
                            System.out.println("Tornant al menú.\n");
                            break;
                        }
                    }
                    System.out.print("\nPosa-li un nom al teu usuari: ");
                    usuari = sc.nextLine();
                    char ch;
                    boolean digit;
                    boolean simbol;
                    while (!contraValida) {
                        System.out.print("Assigna una contrasenya: ");
                        contrasenya = sc.next();
                        if (contrasenya.length() < 4) { // controla llargària de la contrasenya i reinicia el bucle si és curta
                            System.out.println("Ha de contenir com a mínim 4 caràcters."); continue;
                        }
                        digit = simbol = false;
                        for (int i = 0 ; i < contrasenya.length() && (!digit || !simbol) ; i++) {
                            ch = contrasenya.charAt(i);
                            if (ch >= '0' && ch <= '9') { // el caràcter comprovat és nombre
                                digit = true;
                            }
                            else if (ch == '@' || ch == '#' || ch == '%' || ch == '&') { // és símbol
                                simbol = true;
                            }
                            else { // no és nombre ni símbol
                                if (!Character.isLetter(ch)) { // però tampoc és lletra (tots els altres caràcters)
                                    System.out.println("Caràcters invàlids detectats. Torna a provar.\n");
                                    digit = simbol = false;
                                    break;
                                }
                            }
                        }
                        if (digit && simbol) {
                            // digit && simbol son true, segueix el programa
                            System.out.println("\nContrasenya registrada per a l'usuari \u001B[32m" + usuari + "\u001B[0m. És necessari crear un usuari administrador addicionalment.");
                            contraValida = true; // quan és true, el bucle de creació de l'usuari corrent acaba
                        } else {
                            // digit || simbol son false, error. no surt del bucle i repeteix fins rebre una contrasenya
                            // que compleixi els requisits
                            System.out.println("Contrasenya invàlida, ha de tenir 1 nombre i 1 símbol. Torna a provar.\n");
                        }
                    }
                    // aquí enmig es podria demanar si es volen afegir més usuaris, potser afegint una nova condició al while que només
                    // s'activi quan l'usuari indiqui que no vol introduir més usuaris. així, el bucle comprovaria la validesa de les
                    // contrasenyes introduides, repetint-se fins que l'altra condició es deixi de complir, sortint del bucle i passant
                    // a la part on es configura l'usuari admin. la variable contraAdmValida seguiria sent la llum verda perquè el
                    // programa deixi entrar a l'opció 2 del menú (logear)
                    while (!contraAdmValida) { // nova bandera per la creació de l'usuari admin
                        System.out.print("Quina contrasenya vols assignar a l'usuari \u001B[32madmin\u001B[0m? "); // usuari admin ja està creat, només es demana contrasenya
                        contraAdmin = sc.next();
                        if (contraAdmin.length() < 4) { // control de llargària
                            System.out.println("\u001B[31mHa de contenir com a mínim 4 caràcters.\u001B[0m");
                            continue;
                        }
                        digit = simbol = false;
                        for (int i = 0 ; i < contraAdmin.length() && (!digit || !simbol) ; i++) { // control de caràcters, exactament igual que a l'usuari corrent
                            ch = contraAdmin.charAt(i);
                            if (ch >= '0' && ch <= '9') {
                                digit = true;
                            }
                            else if (ch == '@' || ch == '#' || ch == '%' || ch == '&') {
                                simbol = true;
                            }
                            else {
                                if (!Character.isLetter(ch)) {
                                    System.out.println("\u001B[31mCaràcters invàlids detectats. Torna a provar.\u001B[0m\n");
                                    digit = simbol = false;
                                    break;
                                }
                            }
                        }
                        if (digit && simbol) { // creació exitosa. reafirma els usuaris creats i avisa de l'activació del login
                            System.out.println("Contrasenya registrada per a l'usuari administrador. Usuaris \u001B[32m" + usuari + "\u001B[0m i \u001B[32m" + ADMIN + "\u001B[0m creats, ja pots fer login.\n");
                            contraAdmValida = true; // deixa sortir del bucle de l'usuari admin per continuar naturalment al login
                        } else { // torna a repetir el procés de la contrasenya d'admin
                            System.out.println("\u001B[31mContrasenya invàlida, ha de tenir 1 nombre i 1 símbol. Torna a provar.\u001B[0m\n");
                        }
                    }
                    break;
                case '2': // login
                    if (!contraAdmValida) { // avisa de que s'han de configurar usuaris primer i surt del switch, tornant al bucle del menú d'usuaris
                        System.out.println("Abans de fer el login has de crear els usuaris.\n");
                        break;
                    }
                    while (!logged) { // bandera de logged que s'activa quan s'ingresa un usuari existent amb la seva contrasenya dins els 3 intents
                        System.out.print("A quin usuari vols loggear? ");
                        login = sc.nextLine(); // variable que emmagatzema l'usuari seleccionat o al que es fa l'intent de fer-ho (si no existeix)
                        if (login.equals(usuari) || login.equals(ADMIN)) { // agrupa comparacions per no repetir el codi on es demana contrasenya
                            for (int i = 3; i > 0; i++) { // iterador per comptar els intents restants (3-1, al 0 ja surt)
                                System.out.print("Introdueix la contrasenya: ");
                                String loginContra = sc.nextLine();
                                if (loginContra.equals(contrasenya) && login.equals(usuari)) {
                                    // comprova que la contrasenya sigui igual a la de l'usuari corrent, afegint que
                                    // login també sigui igual a l'usuari corresponent, per evitar que doni per vàlid
                                    // introduir l'usuari admin (entrarà a l'if pare) i després introdueixi la
                                    // contrasenya de l'altre usuari (entrarà a aquest if també) i viceversa.
                                    System.out.print("Loggeant a l'usuari \u001B[32m" +  login + "\u001B[0m...");
                                    logged = true; // dona valor true a logged per poder sortir del bucle general d'usuaris (no hi ha marxa anrere)
                                } else if (loginContra.equals(contraAdmin) && login.equals(ADMIN)) {
                                    // s'aplica el mateix per a aquest if
                                    System.out.print("Loggeant a l'usuari \u001B[32m" +  login + "\u001B[0m...");
                                    logged = true;
                                } else { // la contrasenya introduida no coincideix amb ninguna de les registrades
                                    i--; // es resta un intent
                                    // ja s'ha consumit l'intent 3 (es mostren 2 restants) i així consecutivament fins arribar a
                                    // mostrar el 0, on s'acaba el programa
                                    System.out.println("\u001B[31mContrasenya incorrecta. " + i + " intents restants.\u001B[0m");
                                    // quan s'acaben els intents (i), acaba el programa i mostra un missatge. seria més òptim
                                    // fer la condició fora del bucle, per no repetir-la a cada iteració, però la variable i
                                    // es declara a dins
                                    if (i == 0) { System.out.println("Sortint del programa..."); return; }
                                }
                            }
                        } else { // l'usuari introduit no està registrat. si falla aquí, no es resten intents ja que el
                            // bucle es troba dins el camí on l'usuari sí coincideix amb algun registrat
                            System.out.print("L'usuari no existeix. ");
                        }

                    }
                    break;
                default: // ha triat un nombre que no apareix al menú
                    System.out.println("Opció invàlida. Tria una de les del menú.\n"); break;
            }
        } while (!logged); //
        System.out.println("Sessió iniciada com a \u001B[32m" + login + "\u001B[0m. Benvingut!\n\n" +
                "Primer, cal configurar els productes.");
        int menuProductes = 1; // forçar l'entrada a la configuració de productes el primer pic
        boolean prodConfigurats = false; // per evitar el menú a la primera iteració
        String nomP1 = "", nomP2 = "", nomP3 = "", nomP4 = "";
        double preuP1 = 0, preuP2 = 0, preuP3 = 0, preuP4 = 0;
        int stockP1 = 0, stockP2 = 0, stockP3 = 0, stockP4 = 0;
        String siNo = ""; // variable reutilitzable
        boolean sortir = false;
        do {
            if (prodConfigurats) { // és false el primer pic, així que es bota
                System.out.print("Menú productes\n\n" +
                        "1. Configurar productes\n" +
                        "2. Comprar productes\n" +
                        "3. Reomplir stock\n" +
                        "4. Informe de vendes i recaptació\n" +
                        "5. Sortir del programa\n\n" +
                        "Què vols fer? ");
                menuProductes = sc.nextInt();
                sc.nextLine();
            }
            String producteIntroduit; // variable per reutilitzar
            int totalVentes = 0; // total productes venuts
            double totalRecaptat = 0; // total doblers recaptat amb ventes
            switch (menuProductes) {
                case 1:
                    if (prodConfigurats) {
                        System.out.print("Ja has configurat productes. Vols tornar a començar? (si/no) ");
                        siNo = sc.nextLine();
                        if (!siNo.equals("si"))  {
                            System.out.println("D'acord, tornant al menú.\n");
                            continue;
                        }
                    }
                    for (int i = 1 ; i <= 4 ; i++ ) {
                        switch (i) {
                            case 1:
                                System.out.print("P" + i +"\nNom: ");
                                nomP1 = sc.nextLine();
                                System.out.print("Preu: ");
                                preuP1 = sc.nextDouble();
                                System.out.print("Stock: ");
                                stockP1 = sc.nextInt();
                                sc.nextLine(); // neteja del buffer
                                break;
                            /*case 2:
                                System.out.print("P" + i +"\nNom: ");
                                nomP2 = sc.nextLine();
                                System.out.print("Preu: ");
                                preuP2 = sc.nextDouble();
                                System.out.print("Stock: ");
                                stockP2 = sc.nextInt();
                                sc.nextLine();
                                break;
                            case 3:
                                System.out.print("P" + i +"\nNom: ");
                                nomP3 = sc.nextLine();
                                System.out.print("Preu: ");
                                preuP3 = sc.nextDouble();
                                System.out.print("Stock: ");
                                stockP3 = sc.nextInt();
                                sc.nextLine();
                                break;
                            case 4:
                                System.out.print("P" + i +"\nNom: ");
                                nomP4 = sc.nextLine();
                                System.out.print("Preu: ");
                                preuP4 = sc.nextDouble();
                                System.out.print("Stock: ");
                                stockP4 = sc.nextInt();
                                sc.nextLine();
                                break;*/
                        }
                    }
                    System.out.println("Productes configurats correctament.\n");
                    prodConfigurats = true;
                    break;
                case 2: // OPCIO COMPRA
                    do {
                        System.out.print("Introdueix el codi (P1-P4) del producte que vols comprar: ");
                        producteIntroduit = sc.nextLine(); // producte a compar
                        if (producteIntroduit.length() != 2) {
                            System.out.print("\u001B[32mCodi invàlid.\u001B[0m ");
                            continue;
                        }
                        int quantCompra = 0;
                        switch (producteIntroduit) {
                            case "P1":
                                System.out.print("Producte: \u001B[93m" + nomP1 + "\u001B[0m Preu: " + preuP1 + "€\n" +
                                        "Quantitat? ");
                                quantCompra = sc.nextInt();
                                if (quantCompra > stockP1) {
                                    System.out.println("No pots comprar tant. Stock: " + stockP1);
                                } else {
                                    stockP1 -= quantCompra;
                                    totalVentes += quantCompra;
                                    totalRecaptat += preuP1*quantCompra;
                                }
                                break;
                            case "P2":
                                System.out.print("Producte: \u001B[93m" + nomP2 + "\u001B[0m Preu: " + preuP2 + "€\nQuantitat? ");
                                quantCompra = sc.nextInt();
                                if (quantCompra > stockP2) {
                                    System.out.println("No pots comprar tant. Stock: " + stockP2);
                                } else {
                                    stockP1 -= quantCompra;
                                    totalVentes += quantCompra;
                                    totalRecaptat += preuP1*quantCompra;
                                }
                                break;
                            case "P3":
                                System.out.print("Producte: \u001B[93m" + nomP3 + "\u001B[0m Preu: " + preuP3 + "€\nQuantitat? ");
                                quantCompra = sc.nextInt();
                                if (quantCompra > stockP3) {
                                    System.out.println("No pots comprar tant. Stock: " + stockP3);
                                } else {
                                    stockP1 -= quantCompra;
                                    totalVentes += quantCompra;
                                    totalRecaptat += preuP1*quantCompra;
                                }
                                break;
                            case "P4":
                                System.out.print("Producte: \u001B[93m" + nomP4 + "\u001B[0m Preu: " + preuP4 + "€\nQuantitat? ");
                                quantCompra = sc.nextInt();
                                if (quantCompra > stockP4) {
                                    System.out.println("No pots comprar tant. Stock: " + stockP4);
                                } else {
                                    stockP1 -= quantCompra;
                                    totalVentes += quantCompra;
                                    totalRecaptat += preuP1*quantCompra;
                                }
                                break;
                            default:
                                System.out.println("Codi no existent.");
                                continue;
                        }
                        System.out.print("Vols seguir comprant?");
                        siNo = sc.nextLine();
                    } while (!siNo.equalsIgnoreCase("si"));
                    break;
                case 3: // OPCIO AFEGIR STOCK
                    do {
                        System.out.print("Introdueix el codi (P1-P4) del producte del qual vols reomplir stock: ");
                        producteIntroduit = sc.nextLine(); // producte a reomplir
                        if (producteIntroduit.length() != 2) {
                            System.out.print("\u001B[32mCodi invàlid.\u001B[0m ");
                            continue;
                        }
                        int quantStock = 0;
                        switch (producteIntroduit) {
                            case "P1":
                                System.out.print("Producte: \u001B[93m" + nomP1 + "\u001B[0m Stock: " + stockP1 + "\nQuantitat? ");
                                quantStock = sc.nextInt();
                                stockP1 += quantStock;
                                System.out.println("S'ha actualitzat l'stock de \u001B[93m" + nomP1 + "\u001B[0m: " + stockP1);
                                break;
                            case "P2":
                                System.out.print("Producte: \u001B[93m" + nomP2 + "\u001B[0m Stock: " + stockP2 + "\nQuantitat? ");
                                quantStock = sc.nextInt();
                                stockP2 += quantStock;
                                System.out.println("S'ha actualitzat l'stock de \u001B[93m" + nomP2 + "\u001B[0m : " + stockP2);
                                break;
                            case "P3":
                                System.out.print("Producte: \u001B[93m" + nomP3 + "\u001B[0m Stock: " + stockP3 + "\nQuantitat? ");
                                quantStock = sc.nextInt();
                                stockP3 += quantStock;
                                System.out.println("S'ha actualitzat l'stock de \u001B[93m" + nomP3 + "\u001B[0m : " + stockP3);
                                break;
                            case "P4":
                                System.out.print("Producte: \u001B[93m" + nomP4 + "\u001B[0m Stock: " + stockP4 + "\nQuantitat? ");
                                quantStock = sc.nextInt();
                                stockP4 += quantStock;
                                System.out.println("S'ha actualitzat l'stock de \u001B[93m" + nomP4 + "\u001B[0m : " + stockP4);
                                break;
                            default:
                                System.out.println("Codi no existent.");
                                continue;
                        }
                        System.out.print("Vols seguir comprant?");
                        siNo = sc.nextLine();
                    } while (!siNo.equalsIgnoreCase("si"));
                    break;
                case 4:
                    System.out.print("Producte: \u001B[93m" + nomP1 + "\u001B[0m Stock: " + stockP1 +
                            "\nProducte: \u001B[93m" + nomP2 + "\u001B[0m Stock: " + stockP2 +
                            "\nProducte: \u001B[93m" + nomP3 + "\u001B[0m Stock: " + stockP3 +
                            "\nProducte: \u001B[93m" + nomP4 + "\u001B[0m Stock: " + stockP4 +
                            "Total");
                    break;
                case 5:
                    System.out.println("Estàs segur de que vols sortir? (si/no)");
                    siNo = sc.nextLine();
                    if (siNo.equals("si")) {
                        sortir = true;
                    } else {
                        System.out.println("D'acord, tornant al menú.\n");
                    }
                    break;
                default:
                    System.out.println("Opció invàlida. Tria una de les del menú.\n");
                    break;
            }
        } while (!sortir);
        sc.close();
    }
}
