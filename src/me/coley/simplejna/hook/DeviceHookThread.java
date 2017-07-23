package me.coley.simplejna.hook;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.MSG;

public abstract class DeviceHookThread<H extends DeviceEventReceiver<?>> extends Thread {
	private final H eventReceiver;
	private final MSG msg = new MSG();
	private final int hookType;
	/**
	 * <b>Note</b>: This is not declared in the constructor for performance
	 * reasons. Doing so causes MASSIVE input lag on hooked devices. This is due
	 * to the fact it's being instantiated on the main thread, whereas
	 * instantiation in the {@link #run()} method is done so on another thread.
	 * Because of reasons, this doesn't cause massive input delays.
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

	private int getMessage() {
		return User32.INSTANCE.GetMessage(msg, null, 0, 0);
	}

	private void dispatchEvent() {
		User32.INSTANCE.TranslateMessage(msg);
		User32.INSTANCE.DispatchMessage(msg);
	}

	protected abstract void onFail();

	public void unhook() {
		User32.INSTANCE.UnhookWindowsHookEx(hhk);
	}

	public H getEventReceiver() {
		return eventReceiver;
	}

	public HHOOK getHHK() {
		return hhk;
	}
}
