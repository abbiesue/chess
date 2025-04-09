package ui;

import Exceptions.ResponseException;
import server.ServerFacade;

import java.util.Scanner;

public class Repl {
    private final PreloginClient preloginClient;
    private PostloginClient postloginClient;
    private PlayerClient playerClient;
    private ObserverClient observerClient;
    ServerFacade server;
    String serverURL;

    public Repl(String serverURL) {
        server = new ServerFacade(serverURL);
        this.serverURL = serverURL;
        preloginClient = new PreloginClient(server);
    }

    public void run() {
        System.out.println("\u265B Welcome to 240 Chess! Type \"help\" to get started. \u265B");

        Scanner scanner = new Scanner(System.in);
        String result = "";

        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = preloginClient.eval(line);
                printResult(result);

                if (result.contains("Logged in")) {
                    handlePostLogin(scanner);
                }

            } catch (Throwable e) {
                System.out.print(e.toString());
            }
        }
    }


    private void handlePostLogin(Scanner scanner) {
        postloginClient = new PostloginClient(server, preloginClient.authToken);
        String result = "";

        while (!result.equals("logged out") && !result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = postloginClient.eval(line);
                printResult(result);

                if (result.contains("joining")) {
                    handleGameAsPlayer(scanner, line);
                } else if (result.contains("observing")) {
                    handleGameAsObserver(scanner, line);
                }

            } catch (Throwable e) {
                System.out.print(e.toString());
            }
        }
    }

    private void handleGameAsObserver(Scanner scanner, String line) throws ResponseException {
        observerClient = new ObserverClient(postloginClient.authToken, server, serverURL);
        String result = observerClient.eval(line);
        printResult(result);

        while (!result.contains("left")) {
            printPrompt();
            line = scanner.nextLine();
            result = observerClient.eval(line);
            printResult(result);
        }
    }

    private void handleGameAsPlayer(Scanner scanner, String line) throws ResponseException {
        playerClient = new PlayerClient(postloginClient.authToken, server, serverURL);
        String result = playerClient.eval(line);
        printResult(result);

        while (!result.contains("left")) {
            printPrompt();
            line = scanner.nextLine();
            result = playerClient.eval(line);
            printResult(result);
        }
    }

    private void printResult(String result) {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
    }

    private void printPrompt() {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + "\n" + ">>> " + EscapeSequences.SET_TEXT_COLOR_GREEN);
    }
}
