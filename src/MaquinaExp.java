import java.util.Scanner;
public class MaquinaExp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean logged = false;
        int menuUsuaris;
        do {
            System.out.println("Config usuaris\n\n1. Registrar usuaris\n2. Login\n");
            menuUsuaris = sc.nextInt();
            boolean contraValida = false;
            boolean contraAdmValida = false;
            switch (menuUsuaris) {
                case 1:
                    System.out.print("\nPosa-li un nom al teu usuari: ");
                    String usuari = sc.nextLine();
                    sc.next();
                    char ch;
                    boolean digit = false;
                    boolean simbol = false;
                    while (!contraValida) {
                        System.out.print("Assigna una contrasenya: ");
                        String contrasenya = sc.next();
                        if (contrasenya.length() < 4) { // controla llargària de la contrasenya i reinicia el bucle si és curta
                            System.out.println("Ha de contenir com a mínim 4 caràcters."); continue; }

                        for (int i = 0 ; i < contrasenya.length() ; i++) {
                            ch = contrasenya.charAt(i);
                            if (ch >= '!' && ch <= '~') { // controla que siguin símbols, nombres o lletres i es fan subfiltres a dins
                                if (ch >= '0' && ch <= '9' && !digit) { digit = true; } // comprova el dígit. si ja ho ha fet, no es repeteix
                                else if (!Character.isLetter(ch) && !simbol) { simbol = true; } // comprova el símbol agafant tot el que està dins
                                // el rang de l'if pare (símbols, nombres i lletres), que no sigui lletra ni tampoc nombre, que es controla a l'if anterior
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
                        digit = false;
                        simbol = false;
                        for (int i = 0 ; i < contraAdmin.length() ; i++) {
                            ch = contraAdmin.charAt(i);
                            if (ch >= '!' && ch <= '~') {
                                if (ch >= '0' && ch <= '9' && !digit) { digit = true; }
                                else if (!Character.isLetter(ch) && !simbol) { simbol = true; }
                            } else { // caràcters invàlids per a la contrasenya
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
                case 2:
                    if (!contraAdmValida) { System.out.println("Abans de fer el login has de crear els usuaris.\n"); continue; }
                    else { System.out.println("ola"); }
                default:
                    System.out.println("Opció invàlida."); break;
            }
        } while (!logged);



        sc.close();
    }
}
