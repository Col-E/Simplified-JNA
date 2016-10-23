package me.coley.jnathread.hook.mouse;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.platform.win32.WinUser.HHOOK;

import me.coley.jnathread.hook.mouse.struct.LowLevelMouseProc;

/**
 * A registrar for keeping track of mouse event receivers.
 * 
 * @author Matt
 */
public class MouseHook {
    private static Map<LowLevelMouseProc, MouseHookThread> hooks = new HashMap<LowLevelMouseProc, MouseHookThread>();

    /**
     * Registers a mouse event receiver and starts it as a new thread.
     * 
     * @param eventReceiver
     */
    public static void hook(MouseEventReceiver eventReceiver) {
        MouseHookThread t = new MouseHookThread(eventReceiver);
        hooks.put(eventReceiver, t);
        t.start();
    }

    /**
     * Unhooks a given mouse event receiver.
     * 
     * @param eventReceiver
     */
    public static void unhook(MouseEventReceiver eventReceiver) {
        hooks.get(eventReceiver).unhook();
    }

    /**
     * Retrieves the HHOOK of a given mouse event receiver.
     * 
     * @param eventReceiver
     * @return
     */
    public static HHOOK getHhk(MouseEventReceiver eventReceiver) {
        return hooks.get(eventReceiver).getHHK();
    }

}