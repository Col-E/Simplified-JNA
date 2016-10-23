package me.coley.jnathread.hook.key;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;

/**
 * A registrar for keeping track of keyboard event receivers.
 * 
 * @author Matt
 */
public class KeyHook {
    private static Map<LowLevelKeyboardProc, KeyHookThread> hooks = new HashMap<LowLevelKeyboardProc, KeyHookThread>();

    /**
     * Registers a keyboard event receiver and starts it as a new thread.
     * 
     * @param eventReceiver
     */
    public static void hook(KeyEventReceiver eventReceiver) {
        KeyHookThread t = new KeyHookThread(eventReceiver);
        hooks.put(eventReceiver, t);
        t.start();
    }

    /**
     * Unhooks a given keyboard event receiver.
     * 
     * @param eventReceiver
     */
    public static void unhook(KeyEventReceiver eventReceiver) {
        hooks.get(eventReceiver).unhook();
    }

    /**
     * Retrieves the HHOOK of a given keyboard event receiver.
     * 
     * @param eventReceiver
     * @return
     */
    public static HHOOK getHhk(KeyEventReceiver eventReceiver) {
        return hooks.get(eventReceiver).getHHK();
    }
}