package me.coley.jnathread.hook.key;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;

/**
 * A simplified representation of JNA's LowLevelKeyboardProc.
 * 
 * @author Matt
 *
 */
public abstract class KeyEventReceiver implements LowLevelKeyboardProc {
    public static final int WM_KEYDOWN = 256, WM_KEYUP = 257;
    public static final int WM_SYSKEYDOWN = 260, WM_SYSKEYUP = 261;

    @Override
    public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
        int code = wParam.intValue();
        boolean cancel = false;
        boolean alt = code == WM_SYSKEYDOWN || code == WM_SYSKEYUP;
        boolean up = code == WM_SYSKEYUP || code == WM_KEYUP;
        boolean down = code == WM_SYSKEYDOWN || code == WM_KEYDOWN;
        if (down) {
            cancel = onKeyPress(alt, info);
        } else if (up) {
            cancel = onKeyRelease(alt, info);
        }
        if (cancel) { return new LRESULT(1); }
        Pointer ptr = info.getPointer();
        long peer = Pointer.nativeValue(ptr);
        return User32.INSTANCE.CallNextHookEx(KeyHook.getHhk(this), nCode, wParam, new LPARAM(peer));
    }

    /**
     * Called when a key is released. Returning true will cancel the event.
     * 
     * @param sys
     *            Alt key is pressed.
     * @param info
     *            Key information.
     * @return Event cancellation
     */
    public abstract boolean onKeyRelease(boolean sys, KBDLLHOOKSTRUCT info);

    /**
     * Called when a key is pressed. Returning true will cancel the event.
     * 
     * @param sys
     *            Alt key is pressed.
     * @param info
     *            Key information.
     * @return Event cancellation
     */
    public abstract boolean onKeyPress(boolean sys, KBDLLHOOKSTRUCT info);
}
