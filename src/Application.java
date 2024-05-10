import client.ChessClient;
import logic.ChessGameFactory;
import logic.ChessGameFactoryImpl;
import logic.enums.Colors;
import server.ChessServer;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    private final ChessGameFactory chessGameFactory;

    public Application(){
        this.chessGameFactory = new ChessGameFactoryImpl();
    }

    public static void main(String[] args){

        Application application = new Application();

        Scanner sc = new Scanner(System.in);

        int firstMenuChoice = 0;

        try {

            while (firstMenuChoice != 3) {

                System.out.println("1. Start a new game");
                System.out.println("2. About");
                System.out.println("3. Exit");

                firstMenuChoice = sc.nextInt();
                sc.nextLine();

                switch (firstMenuChoice) {
                    case 1: {
                        System.out.println("1. Local Multiplayer");
                        System.out.println("2. Server Multiplayer");
                        System.out.println("3. AI Single-player");
                        int startNewGameChoice = sc.nextInt();
                        sc.nextLine();
                        switch (startNewGameChoice) {
                            case 1: {
                                var chessGame = application.chessGameFactory.initLocal2Player();
                                chessGame.play();
                                continue;
                            }
                            case 2: {
                                System.out.println("1. Be the server");
                                System.out.println("2. Join to a server");

                                int multiplayerChoice = sc.nextInt();
                                sc.nextLine();
                                switch (multiplayerChoice) {
                                    case 1 -> {
                                        while (true) {
                                            System.out.println("Please enter an available port:");
                                            int port = sc.nextInt();
                                            sc.nextLine();
                                            try {
                                                ChessServer server = new ChessServer(port);
                                                System.out.println("Please select a color:");
                                                System.out.println("1. White");
                                                System.out.println("2. Black");
                                                int colorSelection = sc.nextInt();
                                                sc.nextLine();
                                                Colors player1Color, player2Color;
                                                if (colorSelection == 1) {
                                                    player1Color = Colors.WHITE;
                                                    player2Color = Colors.BLACK;
                                                } else {
                                                    player1Color = Colors.BLACK;
                                                    player2Color = Colors.WHITE;
                                                }
                                                server.setChessGameFactory(application.chessGameFactory);
                                                server.configure(player1Color, player2Color);
                                                server.run();
                                            } catch (BindException e) {
                                                System.out.println("Port not available!\n");
                                                continue;
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        }
                                        continue;
                                    }
                                    case 2 -> {
                                        try {
                                            System.out.println("Please enter server's IP address:");
                                            String ip = sc.nextLine();
                                            System.out.println("Please enter server's port:");
                                            int port = sc.nextInt();
                                            sc.nextLine();
                                            ChessClient client = new ChessClient(ip, port);
                                            client.play();

                                        } catch (ConnectException e) {
                                            System.out.println("Couldn't connect to server. Please try again.\n");
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (ClassNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                        continue;
                                    }
                                }

                            }
                            case 3: {
                                System.out.println("Coming soon...\n\n");
                                continue;
                            }
                        }
                    }
                    case 2: {
                        System.out.println("Just a fun project to practice OOP, design patterns and principles, java, SOLID etc...\n\nBtw just press enter for resign and have fun lol :)\n\n");
                        continue;
                    }
                    case 3:
                        break;
                }


            }
        }
        catch (InputMismatchException e){
            System.out.println("Next time, try entering a number man :/");
        }


    }
}
