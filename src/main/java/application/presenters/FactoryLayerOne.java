package application.presenters;

import application.views.MainView;

public final class FactoryLayerOne {
    private FactoryLayerOne() {}

    public static MainPresenter getMainPresenter(MainView view) {
        return new Executor(view);
    }
}
