package me.coley.simplejna.hook;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.platform.win32.WinUser.HHOOK;

/**
 * A registrar for keeping track of windows hook events.
 * 
 * @author Matt
 *
 * @param <H>
 *            Hook receiver type.
 * @param <T>
 *            Hook thread type. Sends data to the receiver.
 */
public abstract class DeviceHookManager<H extends DeviceEventReceiver<?>, T extends DeviceHookThread<?>> {
	private final Map<H, T> hooks = new HashMap<>();

	/**
	 * Hooks the device and registers a device event receiver and starts it as a new
	 * thread.
	 * 
	 * @param eventReceiver
	 */
	public void hook(H eventReceiver) {
		T t = createHookThread(eventReceiver);
		hooks.put(eventReceiver, t);
		t.start();
	}

	/**
	 * Unhooks the device and unregisters a given device event receiver.
	 * 
	 * @param eventReceiver
	 */
	public void unhook(H eventReceiver) {
		hooks.get(eventReceiver).unhook();
	}

	/**
	 * Retrieves the HHOOK of a given device event receiver.
	 * 
	 * @param eventReceiver
	 * @return
	 */
	public HHOOK getHhk(H eventReceiver) {
		return hooks.get(eventReceiver).getHHK();
	}

	/**
	 * Creates a thread that will send data to the given receiver.
	 * 
	 * @param eventReceiver
	 *            Receiver to handle hooked event data.
	 * @return Thread to update event data.
	 */
	public abstract T createHookThread(H eventReceiver);
}
