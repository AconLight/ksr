package runner;

public abstract class Configurable {
    protected int range;

    public Configurable(int range) {
        this.range = range;
    }

    public Configurable() {

    }
    public abstract void perform(int i);

    public void performAll() {
        for(int i = 0; i < range; i++) {
            perform(i);
        }
    }
}
