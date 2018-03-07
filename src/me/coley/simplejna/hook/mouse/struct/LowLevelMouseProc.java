package me.coley.simplejna.hook.mouse.struct;

import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;

/**
 * I couldn't find a LowLevelMouseProc in the provided JNA jars.
 * 
 * @author Matt
 *
 */
public interface LowLevelMouseProc extends HOOKPROC {
	public LRESULT callback(int nCode, WPARAM wParam, MOUSEHOOKSTRUCT info);
}
