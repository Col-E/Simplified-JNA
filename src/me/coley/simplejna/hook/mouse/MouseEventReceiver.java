package me.coley.simplejna.hook.mouse;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.POINT;
import com.sun.jna.platform.win32.WinDef.WPARAM;

import me.coley.simplejna.hook.DeviceEventReceiver;
import me.coley.simplejna.hook.mouse.struct.LowLevelMouseProc;
import me.coley.simplejna.hook.mouse.struct.MOUSEHOOKSTRUCT;
import me.coley.simplejna.hook.mouse.struct.MouseButtonType;

/**
 * A simplified representation of a LowLevelMouseProc.
 * 
 * @author Matt
 */
public abstract class MouseEventReceiver extends DeviceEventReceiver<MouseHookManager> implements LowLevelMouseProc {
	public static final int WM_MOUSEMOVE = 512;
	public static final int WM_MOUSESCROLL = 522;
	public static final int WM_MOUSELDOWN = 513, WM_MOUSELUP = 514;
	public static final int WM_MOUSEMDOWN = 519, WM_MOUSEMUP = 520;
	public static final int WM_MOUSERDOWN = 516, WM_MOUSERUP = 517;

	public MouseEventReceiver(MouseHookManager hookManager) {
		super(hookManager);
	}

	@Override
	public LRESULT callback(int nCode, WPARAM wParam, MOUSEHOOKSTRUCT info) {
		boolean cancel = false;
		int code = wParam.intValue();
		if (code == WM_MOUSEMOVE) {
			cancel = onMouseMove(info.hwnd, info.pt);
		} else if (code == WM_MOUSESCROLL) {
			boolean down = Pointer.nativeValue(info.hwnd.getPointer()) == 4287102976L;
			cancel = onMouseScroll(down, info.hwnd, info.pt);
		} else if (code == WM_MOUSELDOWN || code == WM_MOUSERDOWN || code == WM_MOUSEMDOWN) {
			onMousePress(MouseButtonType.fromWParam(code), info.hwnd, info.pt);
		} else if (code == WM_MOUSELUP || code == WM_MOUSERUP || code == WM_MOUSEMUP) {
			onMouseRelease(MouseButtonType.fromWParam(code), info.hwnd, info.pt);
		}
		if (cancel) {
			return new LRESULT(1);
		}
		Pointer ptr = info.getPointer();
		long peer = Pointer.nativeValue(ptr);
		return User32.INSTANCE.CallNextHookEx(getHookManager().getHhk(this), nCode, wParam, new LPARAM(peer));
	}

	/**
	 * Called when the mouse is pressed. Returning true will cancel the event.
	 * 
	 * @param type
	 *            Type is press <i>(Left, Right, Middle)</i>
	 * @param hwnd
	 *            Window handle that receives the event.
	 * @param info
	 *            Mouse information.
	 * @return Event cancellation
	 */
	public abstract boolean onMousePress(MouseButtonType type, HWND hwnd, POINT info);

	/**
	 * Called when the mouse is released. Returning true will cancel the event.
	 * 
	 * @param type
	 *            Type is press <i>(Left, Right, Middle)</i>
	 * @param info
	 *            Mouse information.
	 * @return Event cancellation
	 */
	public abstract boolean onMouseRelease(MouseButtonType type, HWND hwnd, POINT info);

	/**
	 * Called when the mouse wheel is moved. Returning true will cancel the event.
	 * 
	 * @param down
	 *            Scroll event is down <i>(Negative movement)</i>
	 * @param hwnd
	 *            Window handle that receives the event.
	 * @param info
	 *            Mouse information.
	 * @return Event cancellation
	 */
	public abstract boolean onMouseScroll(boolean down, HWND hwnd, POINT info);

	/**
	 * Called when the mouse wheel is moved. Returning true will cancel the event.
	 * 
	 * @param hwnd
	 *            Window handle that receives the event.
	 * @param info
	 *            Mouse information.
	 * @return Event cancellation
	 */
	public abstract boolean onMouseMove(HWND hwnd, POINT info);
}
