package me.coley.simplejna.hook.mouse;

import com.sun.jna.platform.win32.WinUser;

import me.coley.simplejna.hook.DeviceHookThread;

class MouseHookThread extends DeviceHookThread<MouseEventReceiver> {
	public MouseHookThread(MouseEventReceiver eventReceiver) {
		super(eventReceiver, WinUser.WH_MOUSE_LL);
	}

	@Override
	public void onFail() {
		System.err.println("Invalid message result for mouse hook, aborting");
	}
}
