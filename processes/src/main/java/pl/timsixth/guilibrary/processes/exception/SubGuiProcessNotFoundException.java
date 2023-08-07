package pl.timsixth.guilibrary.processes.exception;

public class SubGuiProcessNotFoundException extends RuntimeException {

    public SubGuiProcessNotFoundException(String name) {
        super("SubGuiProcess with name " + name + " not found");
    }
}
