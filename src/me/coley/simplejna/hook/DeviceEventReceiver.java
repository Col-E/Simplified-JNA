package me.coley.simplejna.hook;

import com.sun.jna.platform.win32.WinUser.HOOKPROC;

public class DeviceEventReceiver<D extends DeviceHookManager<?, ?>> implements HOOKPROC {
	private final D hookManager;

	public DeviceEventReceiver(D hookManager) {
		this.hookManager = hookManager;
	}

	public final D getHookManager() {
		return hookManager;
	}
}
