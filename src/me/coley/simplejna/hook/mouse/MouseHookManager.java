package me.coley.simplejna.hook.mouse;

import me.coley.simplejna.hook.DeviceHookManager;

/**
 * A registrar for keeping track of mouse event receivers.
 * 
 * @author Matt
 */
public class MouseHookManager extends DeviceHookManager<MouseEventReceiver, MouseHookThread> {
	@Override
	public MouseHookThread createHookThread(MouseEventReceiver eventReceiver) {
		return new MouseHookThread(eventReceiver);
	}
}