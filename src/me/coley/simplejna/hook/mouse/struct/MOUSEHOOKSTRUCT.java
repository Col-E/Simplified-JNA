package me.coley.simplejna.hook.mouse.struct;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.POINT;

/**
 * Mouse hook data structure.
 * 
 * @see https://msdn.microsoft.com/en-us/library/windows/desktop/ms644968(v=vs.85).aspx
 * 
 * @author Matt
 */
public class MOUSEHOOKSTRUCT extends Structure {
	/**
	 * The x,y coordinates of the pointer.
	 */
	public POINT pt;
	/**
	 * Window handle receiving the event.
	 */
	public HWND hwnd;
	/**
	 * Hit-test-value. See <a href=
	 * "https://msdn.microsoft.com/en-us/library/windows/desktop/ms645618(v=vs.85).aspx">
	 * WM_NCHITTEST</a>
	 */
	public int wHitTestCode;
	/**
	 * Additional information.
	 */
	public ULONG_PTR dwExtraInfo;

	@Override
	protected List<String> getFieldOrder() {
		return Arrays.asList("pt", "hwnd", "wHitTestCode", "dwExtraInfo");
	}
}
