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
        do {
            System.out.println("Config usuaris\n\n1. Registrar usuaris\n2. Login\n");
            menuUsuaris = sc.next().charAt(0);
            sc.nextLine();
            switch (menuUsuaris) {
                case '1':
                    if (contraValida && contraAdmValida) {
                        System.out.println("Ja tens usuaris creats, es sobreescriuran sobre els anteriors. En vols crear de nous (si/no)? ");
                        String reescriure = sc.nextLine();
                        if (!reescriure.equalsIgnoreCase("si")) // si diu que si, deixa passar
                        // i reinicia les variables de contrasenyes vàlides perquè entri als bucles
                        {
                            contraValida = false;
                            contraAdmValida = false;
                        } else // només detecta si dius si per estalviar codi. si diu qualsevol
                        // altra cosa, mostra un missatge i surt del switch per tornar al menú principal
                        {
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
                            if (ch >= '0' && ch <= '9') {
                                digit = true;
                            }
                            else if (ch == '@' || ch == '#' || ch == '%' || ch == '&') {
                                simbol = true;
                            }
                            else {
                                if (!Character.isLetter(ch)) System.out.println("Caràcters invàlids detectats. Torna a provar.\n");
                            }
                        }
                        System.out.println((digit && simbol) // condició ternària per depenent de si és vàlida o no, mostrar un missatge
                                // d'èxit o error respectivament
                                ? "\nContrasenya registrada per a l'usuari " + usuari + ". És necessari crear un usuari administrador addicionalment."
                                // digit && simbol son true, èxit
                                : "Contrasenya invàlida, ha de tenir 1 nombre i 1 símbol. Torna a provar.\n");
                                // digit || simbol son false, error. no surt del bucle

                        if (digit && simbol) contraValida = true; // deixa sortir del bucle de creació de l'usuari corrent
                    }
                    // aquí enmig es podria demanar si es volen afegir més usuaris, potser afegint una nova condició al while que només
                    // s'activi quan l'usuari indiqui que no vol introduir més usuaris. així, el bucle comprovaria la validesa de les
                    // contrasenyes introduides, repetint-se fins que l'altra condició es deixi de complir, sortint del bucle i passant
                    // a la part on es configura l'usuari admin. la variable contraAdmValida seguiria sent la llum verda perquè el
                    // programa deixi entrar a l'opció 2 del menú (logear)
                    while (!contraAdmValida) { // nova bandera per la creació de l'usuari admin

                        System.out.print("Quina contrasenya vols assignar a l'usuari admin? ");
                        contraAdmin = sc.next();
                        if (contraAdmin.length() < 4) {
                            System.out.println("Ha de contenir com a mínim 4 caràcters."); continue;
                        }
                        digit = simbol = false;
                        for (int i = 0 ; i < contraAdmin.length() && (!digit || !simbol) ; i++) {
                            ch = contraAdmin.charAt(i);
                            if (ch >= '0' && ch <= '9') {
                                digit = true;
                            }
                            else if (ch == '@' || ch == '#' || ch == '%' || ch == '&') {
                                simbol = true;
                            }
                            else {
                                if (!Character.isLetter(ch)) System.out.println("Caràcters invàlids detectats. Torna a provar.\n");
                            }
                        }
                        System.out.println((digit && simbol)
                                ? "Contrasenya registrada per a l'usuari " + ADMIN + ". Ja pots fer login.\n"
                                : "Contrasenya invàlida, ha de tenir 1 nombre i 1 símbol. Torna a provar.\n");

                        if (digit && simbol) contraAdmValida = true; // deixa sortir del bucle de l'usuari admin i deixa entrar al login
                    }
                    break;
                case '2': // login
                    if (!contraAdmValida) { System.out.println("Abans de fer el login has de crear els usuaris.\n"); break; }
                    System.out.print("A quin usuari et vols logear? ");
                    String logUsuari = sc.nextLine();
                    System.out.print("Introdueix la contrasenya de " + usuari + ": ");
                    String logContrasenya = sc.nextLine();
                    if (logUsuari.equals(usuari)) { // enlloc d'aixo, es recorreria l'array d'usuaris
                        if (logContrasenya.equals(contrasenya)) { // igual per a la contrasenya
                            System.out.println("Login realitzat satisfactòriament!");
                            logged = true;
                        } else {
                            System.out.println("Contrasenya incorrecta.");
                        }
                    } else if (logUsuari.equals(ADMIN)) {
                        if (logContrasenya.equals(contraAdmin)) {
                            System.out.println("Login realitzar satisfactòriament!");
                            logged = true;
                        } else {
                            System.out.println("Contrasenya incorrecta.");
                        }
                    } else {
                        System.out.println("Usuari incorrecte.");
                    }
                    break;
                default:
                    System.out.println("Opció invàlida. Tria una de les del menú.\n"); break;
            }
        } while (!logged);


        
        sc.close();
    }
}
