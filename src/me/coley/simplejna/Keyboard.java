package me.coley.simplejna;

import com.sun.jna.platform.win32.User32;

import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.WORD;
import com.sun.jna.platform.win32.WinUser.INPUT;

/**
 * Keyboard related methods and values.
 */
public class Keyboard {
	public static final int KEYEVENTF_KEYDOWN = 0;
	public static final int KEYEVENTF_KEYUP = 2;

	/**
	 * Check if a key is pressed.
	 * 
	 * @param vkCode
	 *            Key-code. For example: <i>KeyEvent.VK_SHIFT </i>
	 * 
	 * @return {@code true} if key is down. False otherwise.
	 */
	public static boolean isKeyDown(int vkCode) {
		short state = User32.INSTANCE.GetAsyncKeyState(vkCode);
		// check most-significant bit for non-zero.
		return (0x1 & (state >> (Short.SIZE - 1))) != 0;
	}

	/**
	 * Sends a key-down input followed by a key-up input for the given character
	 * value c.
	 * 
	 * @param c
	 */
	public static void pressKey(int c) {
		INPUT input = new INPUT();
		input.type = new DWORD(INPUT.INPUT_KEYBOARD);
		input.input.setType("ki");
		input.input.ki.wScan = new WORD(0);
		input.input.ki.time = new DWORD(0);
		input.input.ki.dwExtraInfo = new ULONG_PTR(0);
		input.input.ki.wVk = new WORD(c);
		input.input.ki.dwFlags = new DWORD(KEYEVENTF_KEYDOWN);
		User32.INSTANCE.SendInput(new DWORD(1), (INPUT[]) input.toArray(1), input.size());
		input.input.ki.wVk = new WORD(c);
		input.input.ki.dwFlags = new DWORD(KEYEVENTF_KEYUP);
		User32.INSTANCE.SendInput(new DWORD(1), (INPUT[]) input.toArray(1), input.size());
	}

	/**
	 * Sends a key-down input for the given character value c.
	 * 
	 * @param c
	 */
	public static void sendKeyDown(int c) {
		INPUT input = new INPUT();
		input.type = new DWORD(INPUT.INPUT_KEYBOARD);
		input.input.setType("ki");
		input.input.ki.wScan = new WORD(0);
		input.input.ki.time = new DWORD(0);
		input.input.ki.dwExtraInfo = new ULONG_PTR(0);
		input.input.ki.wVk = new WORD(c);
		input.input.ki.dwFlags = new DWORD(KEYEVENTF_KEYDOWN);
		User32.INSTANCE.SendInput(new DWORD(1), (INPUT[]) input.toArray(1), input.size());
	}

	/**
	 * Sends a key-up input for the given character value c.
	 * 
	 * @param c
	 */
	public static void sendKeyUp(int c) {
		INPUT input = new INPUT();
		input.type = new DWORD(INPUT.INPUT_KEYBOARD);
		input.input.setType("ki");
		input.input.ki.wScan = new WORD(0);
		input.input.ki.time = new DWORD(0);
		input.input.ki.dwExtraInfo = new ULONG_PTR(0);
		input.input.ki.wVk = new WORD(c);
		input.input.ki.dwFlags = new DWORD(KEYEVENTF_KEYUP);
		User32.INSTANCE.SendInput(new DWORD(1), (INPUT[]) input.toArray(1), input.size());
	}
}
