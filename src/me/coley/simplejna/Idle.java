package me.coley.simplejna;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;

/**
 * Utility for retrieving the idle time on Windows.
 * 
 * @author ochafik
 */
public class Idle {

	/**
	 * Get the time elapsed since the last input event <i>((mouse or keyboard))</i>
	 * in milliseconds.
	 * 
	 * @return Time in milliseconds
	 */
	public static int dxInputEventTime() {
		User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
		User32.INSTANCE.GetLastInputInfo(lastInputInfo);
		return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;
	}

	public static enum State {
		UNKNOWN, ONLINE, IDLE, AWAY
	};

	//@formatter:off
	/*
	public static void main(String[] args) {
		if (!System.getProperty("os.name").contains("Windows")) {
			System.err.println("ERROR: Only implemented on Windows");
			System.exit(1);
		}
		State state = State.UNKNOWN;
		DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		for (;;) {
			int idleSec = dxInputEventTime() / 1000;
			State newState = idleSec < 30 ? State.ONLINE : idleSec > 5 * 60 ? State.AWAY : State.IDLE;
			if (newState != state) {
				state = newState;
				System.out.println(dateFormat.format(new Date()) + " # " + state);
			}
			try {
				Thread.sleep(100);
			} catch (Exception ex) {}
		}
	}
	*/
}