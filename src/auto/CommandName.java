package auto;

public enum CommandName {
	STOP(0), DRIVE(1), TURN(2), SHOOT(3);
	
	private int value;
	private CommandName(int value) {
        this.value = value;
    }

	public int getValue() {
		return value;
	}
}
