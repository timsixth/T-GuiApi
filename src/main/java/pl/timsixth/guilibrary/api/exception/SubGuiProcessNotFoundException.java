package pl.timsixth.guilibrary.api.exception;

public class SubGuiProcessNotFoundException extends RuntimeException {

    public SubGuiProcessNotFoundException(String name) {
        super("SubGuiProcess with name " + name + " not found");
    }
}
