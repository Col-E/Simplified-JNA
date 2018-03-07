package me.coley.simplejna;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;

/**
 * Window related methods and values.
 * 
 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/ms632600(v=vs.85).aspx
 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/ms632610(v=vs.85).aspx
 */
public class Windows {
	public static final int MAX_TITLE_LENGTH = 1024;
	/**
	 * The window is initially minimized.
	 */
	public static final int WS_ICONIC = 0x20000000;
	/**
	 * The window is initially maximized.
	 */
	public static final int WS_MAXIMIZE = 0x01000000;
	/**
	 * The window has a maximize button.
	 */
	public static final int WS_MAXIMIZEBOX = 0x00010000;
	/**
	 * The window is initially minimized.
	 */
	public static final int WS_MINIMIZE = 0x20000000;
	/**
	 * The window has a minimize button.
	 */
	public static final int WS_MINIMIZEBOX = 0x00020000;
	/**
	 * The window is an overlapped window.
	 */
	public static final int WS_OVERLAPPED = 0x00000000;
	/**
	 * The window has a horizontal scroll bar.
	 */
	public static final int WS_HSCROLL = 0x00100000;
	/**
	 * The window has a horizontal scroll bar.
	 */
	public static final int WS_VSCROLL = 0x00200000;
	/**
	 * The window has a sizing border.
	 */
	public static final int WS_SIZEBOX = 0x80000000;
	/**
	 * The window is initially visible.
	 */
	public static final int WS_VISIBLE = 0x10000000;

	/**
	 * Minimizes a window, even if the thread that owns the window is not
	 * responding.
	 */
	public static final int SW_FORCEMINIMIZE = 11;
	/**
	 * Hides the window and activates another window.
	 */
	public static final int SW_HIDE = 0;
	/**
	 * Maximizes the specified window.
	 */
	public static final int SW_MAXIMIZE = 3;
	/**
	 * Minimizes the specified window and activates the next top-level window in the
	 * Z order.
	 */
	public static final int SW_MINIMIZE = 6;
	/**
	 * Activates and displays the window.
	 */
	public static final int SW_RESTORE = 9;
	/**
	 * Activates the window and displays it in its current size and position.
	 */
	public static final int SW_SHOW = 5;
	/**
	 * Sets the show state based on the SW_ value specified in the STARTUPINFO
	 * structure passed to the CreateProcess function by the program that started
	 * the application.
	 */
	public static final int SW_SHOWDEFAULT = 10;
	/**
	 * Activates the window and displays it as a maximized window.
	 */
	public static final int SW_SHOWMAXIMIZED = 3;
	/**
	 * Activates the window and displays it as a minimized window.
	 */
	public static final int SW_SHOWMINIMIZED = 2;
	/**
	 * Displays the window as a minimized window. This value is similar to
	 * SW_SHOWMINIMIZED, except the window is not activated.
	 */
	public static final int SW_SHOWMINNOACTIVE = 7;
	/**
	 * Displays the window in its current size and position. This value is similar
	 * to SW_SHOW, except that the window is not activated.
	 */
	public static final int SW_SHOWNA = 8;
	/**
	 * Displays a window in its most recent size and position. This value is similar
	 * to SW_SHOWNORMAL, except that the window is not activated.
	 */
	public static final int SW_SHOWNOACTIVATE = 4;
	/**
	 * Activates and displays a window. If the window is minimized or maximized, the
	 * system restores it to its original size and position. An application should
	 * specify this flag when displaying the window for the first time.
	 */
	public static final int SW_SHOWNORMAL = 1;
	/**
	 * Hides the window.
	 */
	public static final int SWP_HIDEWINDOW = 0x0080;
	/**
	 * Does not activate the window. If this flag is not set, the window is
	 * activated and moved to the top of either the topmost or non-topmost group.
	 */
	public static final int SWP_NOACTIVATE = 0x0010;
	/**
	 * Retains the current position (ignores X and Y parameters).
	 */
	public static final int SWP_NOMOVE = 0x0002;
	/**
	 * Does not change the owner window's position in the Z order.
	 */
	public static final int SWP_NOOWNERZORDER = 0x0200;
	/**
	 * Does not redraw changes.
	 */
	public static final int SWP_NOREDRAW = 0x0008;
	/**
	 * Retains the current size (ignores the cx and cy parameters).
	 */
	public static final int SWP_NOSIZE = 0x0001;
	/**
	 * Displays the window.
	 */
	public static final int SWP_SHOWWINDOW = 0x0040;

	/**
	 * Returns a map of process HWND's to their window titles.
	 * 
	 * @return
	 */
	public static Map<HWND, String> getWindows() {
		Map<HWND, String> map = new HashMap<HWND, String>();
		User32.INSTANCE.EnumWindows(new WNDENUMPROC() {
			@Override
			public boolean callback(HWND hWnd, Pointer arg1) {
				String wText = getWindowTitle(hWnd);
				map.put(hWnd, wText);
				return true;
			}
		}, null);
		return map;
	}

	/**
	 * 
	 * @return
	 */
	public static HWND getDesktop() {
		return User32.INSTANCE.GetDesktopWindow();
	}

	/**
	 * @return Title of the active window.
	 */
	public static String getCurrentWindowTitle() {
		return getWindowTitle(User32.INSTANCE.GetForegroundWindow());
	}

	/**
	 * @param hWnd
	 * @return Title of a given process HWND.
	 */
	public static String getWindowTitle(HWND hWnd) {
		char[] buffer = new char[MAX_TITLE_LENGTH * 2];
		User32.INSTANCE.GetWindowText(hWnd, buffer, MAX_TITLE_LENGTH);
		return Native.toString(buffer);
	}

	/**
	 * @param hWnd
	 * @return Window information of a given HWND.
	 */
	public static WINDOWINFO getInfo(HWND hWnd) {
		WINDOWINFO info = new WINDOWINFO();
		User32.INSTANCE.GetWindowInfo(hWnd, info);
		return info;
	}

	/**
	 * Checks if a given window HWND's properties contains a given flag.
	 * 
	 * @param hWnd
	 * @param flag
	 * @return
	 */
	public static boolean checkWindowTag(HWND hWnd, int flag) {
		return checkWindowTag(getInfo(hWnd), flag);
	}

	/**
	 * Checks if a given window properties contains a given flag.
	 * 
	 * @param info
	 * @param flag
	 * @return
	 */
	public static boolean checkWindowTag(WINDOWINFO info, int flag) {
		return (info.dwStyle & flag) == flag;
	}

	/**
	 * Set window's state.
	 * 
	 * @param hWnd
	 * @param flags
	 *            State flags.
	 */
	public static void showWindow(HWND hWnd, int flags) {
		User32.INSTANCE.ShowWindow(hWnd, flags);
	}

	/**
	 * Destroys window by the given HWND.
	 * 
	 * @param hWnd
	 */
	public static void destroyWindow(HWND hWnd) {
		User32.INSTANCE.DestroyWindow(hWnd);
	}

	/**
	 * Set the position and size of a given window.
	 * 
	 * @param hWnd
	 *            Window handle.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param flags
	 */
	public static void setWindowPos(HWND hWnd, int x, int y, int width, int height, int flags) {
		User32.INSTANCE.SetWindowPos(hWnd, null, x, y, width, height, flags);
	}

	//@formatter:off
	/* TODO
	 * 
	public static void putAbove(HWND hWnd, HWND hWndOther) {
		WINDOWINFO info = getInfo(hWnd);
		int flags = info.dwWindowStatus;
		int x = info.rcWindow.left;
		int y = info.rcWindow.top;
		int width = info.rcWindow.right - x;
		int height = info.rcWindow.bottom - y;
		User32.INSTANCE.SetWindowPos(hWnd, hWndOther, x, y, width, height, flags);
	}
	*/
}