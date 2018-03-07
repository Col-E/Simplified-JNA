package me.coley.simplejna.hook;

import com.sun.jna.platform.win32.WinUser.HOOKPROC;

/**
 * Abstract receiver for hooked event data.
 * 
 * @author Matt
 *
 * @param <D>
 *            Hook manager.
 */
public class DeviceEventReceiver<D extends DeviceHookManager<?, ?>> implements HOOKPROC {
	private final D hookManager;

	public DeviceEventReceiver(D hookManager) {
		this.hookManager = hookManager;
	}

	/**
	 * @return Registrar containing all hooks of this type.
	 */
	protected final D getHookManager() {
		return hookManager;
	}
}
