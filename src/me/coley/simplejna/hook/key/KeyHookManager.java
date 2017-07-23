package me.coley.simplejna.hook.key;

import me.coley.simplejna.hook.DeviceHookManager;

/**
 * A registrar for keeping track of keyboard event receivers.
 * 
 * @author Matt
 */
public class KeyHookManager extends DeviceHookManager<KeyEventReceiver, KeyHookThread> {
	@Override
	public KeyHookThread createHookThread(KeyEventReceiver eventReceiver) {
		return new KeyHookThread(eventReceiver);
	}
}