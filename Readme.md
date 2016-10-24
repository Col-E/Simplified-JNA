# About
Simplified JNA is a library that allows for the quick creation of mouse and keyboard hooks in a multithreaded environment. Additionally it provides easy to use methods for sending inputs to window, mouse, and keyboard objects. 
# Usage
JNA and JNA-platform must be imported into a project in order to use Simplified JNA. Currently only windows is supported.
#### Examples

> Keyboard Hook
```java
KeyEventReceiver keyHook = new KeyEventReceiver() {
    @Override
    public boolean onKeyRelease(boolean sys, KBDLLHOOKSTRUCT info) {
        System.out.println("Key Released:" + info.vkCode);
        return false;
    }
    @Override
    public boolean onKeyPress(boolean sys, KBDLLHOOKSTRUCT info) {
		System.out.println("Key Pressed:" + info.vkCode);
        return false;
    }
};
KeyHook.hook(keyHook);
```
> Mouse Hook
```java
MouseEventReceiver mer = new MouseEventReceiver() {
    @Override
    public boolean onMousePress(MouseButtonType type, MOUSEHOOKSTRUCT info) {
        boolean isLeft = type == MouseButtonType.LEFT_DOWN;
        if (isLeft) {
            System.out.println("Left mouse button has been pressed!")
        }
        return false;
    }
    @Override public boolean onMouseRelease(MouseButtonType type, MOUSEHOOKSTRUCT info) { return false; }
    @Override public boolean onMouseScroll(boolean down, MOUSEHOOKSTRUCT info) { return false;  }
    @Override public boolean onMouseMove(MOUSEHOOKSTRUCT info) { return false; }
};
MouseHook.hook(mer);
```