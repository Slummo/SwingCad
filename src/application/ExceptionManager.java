package application;

import gui.dialogs.SimpleDialogCreator;

import java.awt.*;

public class ExceptionManager {
    public static void tryAndCatch(Component parent, Runnable actionToTry, Runnable actionIfException, String exceptionMessage) {
        try {
            actionToTry.run();
        } catch(Exception e) {
            SimpleDialogCreator.showErrorDialog(parent, exceptionMessage);
            actionIfException.run();
        }
    }
}
