package ui;

import server.ServerFacade;
import websocket.ServerMessageObserver;
import websocket.messages.*;

import java.util.Scanner;

public class Repl implements ServerMessageObserver {
    private final PreloginClient preloginClient;
    private PostloginClient postloginClient;
    private PlayerClient playerClient;
    private ObserverClient observerClient;
    ServerFacade server;
    String serverURL;

    BoardPrinter printer = new BoardPrinter();

    public Repl(String serverURL) {
        server = new ServerFacade(serverURL);
        this.serverURL = serverURL;
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
                        if (result.contains("joining")) {
                            playerClient = new PlayerClient(postloginClient.authToken, server, serverURL, this);
                            result = playerClient.eval(line);
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
                            while(!result.contains("left")) {
                                printPrompt();
                                line = scanner.nextLine();
                                result = playerClient.eval(line);
                                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
                            }
                        }
                        if (result.contains("observing")) {
                            // fix later
                            observerClient = new ObserverClient(postloginClient.authToken,server, serverURL, this);
                            result = observerClient.eval(line);
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
                            while(!result.contains("left")) {
                                printPrompt();
                                line = scanner.nextLine();
                                result = observerClient.eval(line);
                                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
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

    @Override
    public void notify(ServerMessage notification) {
        //call different private classes based on message type
        System.out.println("entered notify");
        switch (notification.getServerMessageType()) {
            case LOAD_GAME -> {
                System.out.println("Load Game received");
            }
            case NOTIFICATION -> {
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + ((NotificationMessage)notification).getMessage());
            }
            case ERROR -> {
                System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + ((ErrorMessage)notification).getMessage());
            }
        }
        printPrompt();
    }
}
