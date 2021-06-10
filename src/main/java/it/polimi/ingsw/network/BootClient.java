package it.polimi.ingsw.network;

import it.polimi.ingsw.view.Cli;

import java.util.Locale;
import java.util.Scanner;

public class BootClient {


    public static void main(String[] args) {
        System.out.println("                                                                            ▄▄▄▄                                           ▄▄                                                  \n" +
                "▀████▄     ▄███▀                ██                                        ▄█▀ ▀▀   ▀███▀▀▀██▄                              ██                                                  \n" +
                "  ████    ████                  ██                                        ██▀        ██   ▀██▄                                                                                 \n" +
                "  █ ██   ▄█ ██  ▄█▀██▄  ▄██▀████████  ▄▄█▀██▀███▄███ ▄██▀███     ▄██▀██▄ █████       ██   ▄██   ▄▄█▀██▀████████▄  ▄█▀██▄ ▀███  ▄██▀███▄██▀███▄█▀██▄ ▀████████▄  ▄██▀██  ▄▄█▀██ \n" +
                "  █  ██  █▀ ██ ██   ██  ██   ▀▀ ██   ▄█▀   ██ ██▀ ▀▀ ██   ▀▀    ██▀   ▀██ ██         ███████   ▄█▀   ██ ██    ██ ██   ██   ██  ██   ▀▀██   ▀▀█   ██   ██    ██ ██▀  ██ ▄█▀   ██\n" +
                "  █  ██▄█▀  ██  ▄█████  ▀█████▄ ██   ██▀▀▀▀▀▀ ██     ▀█████▄    ██     ██ ██         ██  ██▄   ██▀▀▀▀▀▀ ██    ██  ▄█████   ██  ▀█████▄▀█████▄▄█████   ██    ██ ██      ██▀▀▀▀▀▀\n" +
                "  █  ▀██▀   ██ ██   ██  █▄   ██ ██   ██▄    ▄ ██     █▄   ██    ██▄   ▄██ ██         ██   ▀██▄ ██▄    ▄ ██    ██ ██   ██   ██  █▄   ███▄   ███   ██   ██    ██ ██▄    ▄██▄    ▄\n" +
                "▄███▄ ▀▀  ▄████▄████▀██▄██████▀ ▀████ ▀█████▀████▄   ██████▀     ▀█████▀▄████▄     ▄████▄ ▄███▄ ▀█████▀████  ████▄████▀██▄████▄██████▀██████▀████▀██▄████  ████▄█████▀  ▀█████▀" );
        System.out.println("                                                                    A Lorenzo Il Magnifico Game\n\n");

        System.out.println("Welcome! Type \"CLI\" to play a game with the command line, or \"GUI\" to play a game with a graphical interface (Without the quotation marks!)");
        Scanner in = new Scanner(System.in);
        String mode;
        do{
            mode=in.nextLine().toUpperCase(Locale.ROOT);
            if(!(mode.equals("CLI"))&&!(mode.equals("GUI"))){
                System.out.println("Mode not recognized. Please type \"CLI\" or \"GUI\" without quotation marks.");
            }
            else{
                if(mode.equals("CLI")){
                    Cli cli=new Cli();
                    ClientMessenger clientMessenger= new ClientMessenger(cli);
                    cli.addObserver(clientMessenger);
                    cli.init();
                }
                else{
                    //TODO: Open GUI
                }
            }
        }while(!(mode.equals("CLI"))&&!(mode.equals("GUI")));
    }

}