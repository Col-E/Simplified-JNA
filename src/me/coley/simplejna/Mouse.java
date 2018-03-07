package me.coley.simplejna;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.LONG;
import com.sun.jna.platform.win32.WinUser.INPUT;

/**
 * Mouse related methods and values.
 */
public class Mouse {
	public static final int MOUSEEVENTF_MOVE = 1;
	public static final int MOUSEEVENTF_LEFTDOWN = 2;
	public static final int MOUSEEVENTF_LEFTUP = 4;
	public static final int MOUSEEVENTF_RIGHTDOWN = 8;
	public static final int MOUSEEVENTF_RIGHTUP = 16;
	public static final int MOUSEEVENTF_MIDDLEDOWN = 32;
	public static final int MOUSEEVENTF_MIDDLEUP = 64;
	public static final int MOUSEEVENTF_WHEEL = 2048;

	/**
	 * Moves the mouse relative to it's current position.
	 * 
	 * @param x
	 *            Horizontal movement
	 * @param y
	 *            Vertical movement
	 */
	public static void mouseMove(int x, int y) {
		mouseAction(x, y, MOUSEEVENTF_MOVE);
	}

	/**
	 * Sends a left-click input at the given x,y coordinates. If -1 is given for
	 * each of the inputs it will send the input to the current position of the
	 * mouse.
	 * 
	 * @param x
	 * @param y
	 */
	public static void mouseLeftClick(int x, int y) {
		mouseAction(x, y, MOUSEEVENTF_LEFTDOWN);
		mouseAction(x, y, MOUSEEVENTF_LEFTUP);
	}

	/**
	 * Sends a right-click input at the given x,y coordinates. If -1 is given for
	 * each of the inputs it will send the input to the current position of the
	 * mouse.
	 * 
	 * @param x
	 * @param y
	 */
	public static void mouseRightClick(int x, int y) {
		mouseAction(x, y, MOUSEEVENTF_RIGHTDOWN);
		mouseAction(x, y, MOUSEEVENTF_RIGHTUP);
	}

	/**
	 * Sends a middle-click input at the given x,y coordinates. If -1 is given for
	 * each of the inputs it will send the input to the current position of the
	 * mouse.
	 * 
	 * @param x
	 * @param y
	 */
	public static void mouseMiddleClick(int x, int y) {
		mouseAction(x, y, MOUSEEVENTF_MIDDLEDOWN);
		mouseAction(x, y, MOUSEEVENTF_MIDDLEUP);
	}

	/**
	 * Sends an action (flags) at the given x,y coordinates.
	 * 
	 * @param x
	 * @param y
	 * @param flags
	 */
	public static void mouseAction(int x, int y, int flags) {
		INPUT input = new INPUT();

		input.type = new DWORD(INPUT.INPUT_MOUSE);
		input.input.setType("mi");
		if (x != -1) {
			input.input.mi.dx = new LONG(x);
		}
		if (y != -1) {
			input.input.mi.dy = new LONG(y);
		}
		input.input.mi.time = new DWORD(0);
		input.input.mi.dwExtraInfo = new ULONG_PTR(0);
		input.input.mi.dwFlags = new DWORD(flags);
		User32.INSTANCE.SendInput(new DWORD(1), new INPUT[] { input }, input.size());
	}
}
