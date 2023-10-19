package lab24.ankit.group01.a2;

public interface AppState {
    public default State getNextState() {
        return State.MENU;
    }
}
