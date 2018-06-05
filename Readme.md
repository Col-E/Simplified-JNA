# About
Simplified JNA is a library that allows for the quick creation of mouse and keyboard hooks in a multithreaded environment. Additionally it provides easy to use methods for sending inputs to window, mouse, and keyboard objects. 
# Usage
JNA and JNA-platform must be imported into a project in order to use Simplified JNA. Currently only windows is supported.
#### Examples

In these samples, returing `false` allows the event to be parsed by the system. Chaning the return value to `true` will cancel the event.

> Keyboard Hook
```java
KeyEventReceiver keyHook = new KeyEventReceiver() {
    @Override
    public boolean onKeyUpdate(SystemState sysState, PressState pressState, int time, int vkCode) {
        System.out.println("Is pressed:" + (pressState == PressState.DOWN));
        System.out.println("Alt down:" + (sysState == SystemState.SYSTEM));
        System.out.println("Timestamp:" + time);
        System.out.println("KeyCode:" + vkCode);
        return false;
    }
};
KeyHook.hook(keyHook);
```
> Mouse Hook
```java
MouseEventReceiver mer = new MouseEventReceiver() {
    @Override
    public boolean  boolean onMousePress(MouseButtonType type, HWND hwnd, POINT info) {
        boolean isLeft = type == MouseButtonType.LEFT_DOWN;
        if (isLeft) {
            System.out.println("Left mouse button has been pressed!")
        }
        return false;
    }
    @Override public boolean onMouseRelease(MouseButtonType type, HWND hwnd, POINT info) { return false; }
    @Override public boolean onMouseScroll(boolean down, HWND hwnd, POINT info) { return false;  }
    @Override public boolean boolean onMouseMove(HWND hwnd, POINT info) { return false; }
};
MouseHook.hook(mer);
```