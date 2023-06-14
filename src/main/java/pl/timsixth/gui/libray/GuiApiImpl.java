package pl.timsixth.gui.libray;

import lombok.RequiredArgsConstructor;
import pl.timsixth.gui.libray.api.GuiApi;
import pl.timsixth.gui.libray.manager.registration.ActionRegistration;

@RequiredArgsConstructor
final class GuiApiImpl implements GuiApi {

    private final GuiApiPlugin guiApiPlugin;

    @Override
    public ActionRegistration getActionRegistration() {
        return guiApiPlugin.getActionRegistration();
    }
}
