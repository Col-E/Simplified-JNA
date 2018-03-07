package me.coley.simplejna.hook;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.MSG;

/**
 * Abstract head for listening to windows hooks.
 * 
 * @author Matt
 *
 * @param <H>
 *            Type of event receiver.
 */
public abstract class DeviceHookThread<H extends DeviceEventReceiver<?>> extends Thread {
	/**
	 * Hook receiver. Events are passed to the receiver.
	 */
	private final H eventReceiver;
	/**
	 * Event structure.
	 */
	private final MSG msg = new MSG();
	/**
	 * Type of hook. For example:
	 * <ul>
	 * <li>WinUser.WH_KEYBOARD_LL</li>
	 * <li>WinUser.WH_MOUSE_LL</li>
	 * </ul>
	 */
	private final int hookType;
	/**
	 * <b>Note</b>: Declared in {@link #run()} for thread synchronization. If
	 * declared on the main thread, expect massive input lag.
	 */
	private HHOOK hhk;

	public DeviceHookThread(H eventReceiver, int type) {
		this.eventReceiver = eventReceiver;
		this.hookType = type;

	}

	@Override
	public void run() {
		WinDef.HMODULE handle = Kernel32.INSTANCE.GetModuleHandle(null);
		this.hhk = User32.INSTANCE.SetWindowsHookEx(hookType, eventReceiver, handle, 0);
		int result;
		while ((result = getMessage()) != 0) {
			if (result == -1) {
				onFail();
				break;
			} else {
				dispatchEvent();
			}
		}
		unhook();
	}

	/**
	 * Event message value.
	 * 
	 * @return {@code 0} for failure, otherwise indicated some other sort of update.
	 */
	private int getMessage() {
		return User32.INSTANCE.GetMessage(msg, null, 0, 0);
	}

	/**
	 * Update event structure.
	 */
	private void dispatchEvent() {
		User32.INSTANCE.TranslateMessage(msg);
		User32.INSTANCE.DispatchMessage(msg);
	}

	/**
	 * Called when event structure could not be updated.
	 */
	protected abstract void onFail();

	/**
	 * Unregister hook from manager.
	 * 
	 * @return Successful.
	 */
	public boolean unhook() {
		return User32.INSTANCE.UnhookWindowsHookEx(hhk);
	}

	/**
	 * @return Receiver to handle hooked event data.
	 */
	public H getEventReceiver() {
		return eventReceiver;
	}

	/**
	 * @return Windows hook instance.
	 */
	public HHOOK getHHK() {
		return hhk;
	}
}
