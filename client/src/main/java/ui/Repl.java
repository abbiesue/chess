package ui;

import server.ServerFacade;

import java.util.Scanner;


public class Repl {
    private final PreloginClient preloginClient;
    private PostloginClient postloginClient;
    private PlayerClient playerClient;
    private ObserverClient observerClient;
    ServerFacade server;

    public Repl(String serverURL) {
        server = new ServerFacade(serverURL);
        preloginClient = new PreloginClient(server);
    }

    public void run () {
        System.out.println("\u265B Welcome to 240 Chess! Type \"help\" to get started. \u265B");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();
            try {
                result = preloginClient.eval(line);
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
                if (result.contains("Logged in")) {
                    while(!result.equals("logged out") && !result.equals("quit")) {
                        printPrompt();
                        line = scanner.nextLine();
                        postloginClient = new PostloginClient(server, preloginClient.authToken);
                        result = postloginClient.eval(line);
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
                        if (result.contains("game!")) {
                            if (result.contains("joined")) {
                                while(!result.contains("left")) {
                                    printPrompt();
                                    line = scanner.nextLine();
                                    playerClient = new PlayerClient(server);
                                    result = playerClient.eval(line);
                                    System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
                                }
                            } else if (result.contains("observing")) {
                                while(!result.contains("left")) {
                                    printPrompt();
                                    line = scanner.nextLine();
                                    observerClient = new ObserverClient(server);
                                    result = observerClient.eval(line);
                                    System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
                                }
                            } else {
                                result = "entered game repl leak";
                            }
                        }
                    }
                }

            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
    }

    private void printPrompt() {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + "\n" + ">>> " + EscapeSequences.SET_TEXT_COLOR_GREEN);
    }
}
