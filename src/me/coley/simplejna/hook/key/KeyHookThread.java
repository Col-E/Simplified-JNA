package me.coley.simplejna.hook.key;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.MSG;

class KeyHookThread extends Thread {
	private HHOOK hhk;
	private KeyEventReceiver eventReceiver;

	public KeyHookThread(KeyEventReceiver eventReceiver) {
		this.eventReceiver = eventReceiver;
	}

	public void run() {
		hhk = User32.INSTANCE.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, eventReceiver, Kernel32.INSTANCE.GetModuleHandle(null), 0);
		int result;
		MSG msg = new MSG();
		while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
			if (result == -1) {
				System.err.println("error in get message");
				break;
			} else {
				System.err.println("got message");
				User32.INSTANCE.TranslateMessage(msg);
				User32.INSTANCE.DispatchMessage(msg);
			}
		}
		User32.INSTANCE.UnhookWindowsHookEx(hhk);
	}

	public HHOOK getHHK() {
		return hhk;
	}

	public void unhook() {
		User32.INSTANCE.UnhookWindowsHookEx(hhk);
	}
}
