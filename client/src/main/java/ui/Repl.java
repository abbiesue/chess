package ui;

import java.util.Scanner;


public class Repl {
    private final PreloginClient preloginClient;
    private String serverURL;

    public Repl(String serverURL) {
        this.serverURL = serverURL;
        preloginClient = new PreloginClient(serverURL);
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
