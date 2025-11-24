import java.util.Scanner;
public class MaquinaExp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean logged = false;
        char menuUsuaris;
        do {
            System.out.println("Config usuaris\n\n1. Registrar usuaris\n2. Login\n");
            menuUsuaris = sc.next().charAt(0);
            sc.nextLine();
            boolean contraValida = false;
            boolean contraAdmValida = false;
            switch (menuUsuaris) {
                case '1':
                    System.out.print("\nPosa-li un nom al teu usuari: ");
                    String usuari = sc.nextLine();
                    char ch;
                    boolean digit;
                    boolean simbol;
                    while (!contraValida) {
                        System.out.print("Assigna una contrasenya: ");
                        String contrasenya = sc.next();
                        if (contrasenya.length() < 4) { // controla llargària de la contrasenya i reinicia el bucle si és curta
                            System.out.println("Ha de contenir com a mínim 4 caràcters."); continue; }
                        digit = simbol = false;
                        for (int i = 0 ; i < contrasenya.length() ; i++) {
                            ch = contrasenya.charAt(i);
                            if (ch >= '!' && ch <= '~') { // controla que siguin símbols, nombres o lletres i es fan subfiltres a dins
                                if (ch >= '0' && ch <= '9' && !digit) { digit = true; } // comprova el dígit. si ja ho ha fet, no es repeteix per el !digit
                                else if (!Character.isLetterOrDigit(ch) && !simbol ) { simbol = true; }  // comprova el símbol amb la funció isLetterOrDigit,
                                // el qual només deixa símbols tinguent en compte el rang del primer if
                            } else { // caràcters invàlids per a la contrasenya
                                System.out.println("La contrasenya té caràcters invàlids."); break;
                            }
                        }
                        if (!digit || !simbol) {
                            System.out.println("Contrasenya invàlida, ha de tenir 1 nombre i 1 símbol. Torna a provar.\n");
                            digit = simbol = false;
                        } else {
                            contraValida = true;
                        }
                    }
                    final String ADMIN = "admin";
                    while (!contraAdmValida) {
                        System.out.print("Contrasenya registrada per a l'usuari " + usuari + ".\n\nÉs necessari crear un usuari admin addicionalment. Quina contrasenya li vols assignar? ");
                        String contraAdmin = sc.next();
                        digit = simbol = false;
                        for (int i = 0 ; i < contraAdmin.length() ; i++) {
                            ch = contraAdmin.charAt(i);
                            if (ch >= '!' && ch <= '~') {
                                if (ch >= '0' && ch <= '9' && !digit) { digit = true; }
                                else if (!Character.isLetterOrDigit(ch) && !simbol ) { simbol = true; }
                            } else {
                                System.out.println("La contrasenya té caràcters invàlids."); break;
                            }
                        }
                        if (!digit || !simbol) {
                            System.out.println("Contrasenya invàlida, ha de tenir 1 nombre i 1 símbol. Torna a provar.\n");
                            digit = simbol = false;
                        } else {
                            contraAdmValida = true;
                        }
                    }
                    break;
                case '2': // login
                    if (!contraAdmValida) { System.out.println("Abans de fer el login has de crear els usuaris.\n"); continue; }
                    else { System.out.println("ola"); }
                default:
                    System.out.println("Opció invàlida. Tria una de les del menú.\n"); break;
            }
        } while (!logged);



        sc.close();
    }
}
