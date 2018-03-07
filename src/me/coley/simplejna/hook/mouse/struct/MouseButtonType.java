package me.coley.simplejna.hook.mouse.struct;

import java.util.HashMap;
import java.util.Map;

public enum MouseButtonType {
	// @formatter:off
	LEFT_DOWN(513), LEFT_UP(514), RIGHT_DOWN(516), RIGHT_UP(517), MIDDLE_DOWN(519), MIDDLE_UP(520);
	// @formatter:on
	private static Map<Integer, MouseButtonType> types;

	MouseButtonType(int value) {
		register(value, this);
	}

	private void register(int value, MouseButtonType type) {
		if (types == null) {
			types = new HashMap<Integer, MouseButtonType>();
		}
		types.put(value, type);
	}

	public static MouseButtonType fromWParam(int value) {
		return types.get(value);
	}
}
