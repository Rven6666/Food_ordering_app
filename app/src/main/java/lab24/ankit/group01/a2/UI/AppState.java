package lab24.ankit.group01.a2.UI;

import lab24.ankit.group01.a2.Miscellaneous.State;

public interface AppState {
    public default State getNextState() {
        return State.MENU;
    }
}
