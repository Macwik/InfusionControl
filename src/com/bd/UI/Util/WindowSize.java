package com.bd.UI.Util;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WindowSize {
	private int width;
	private int height;

	public WindowSize(Display display) {
		Rectangle area = Display.getDefault().getClientArea();
		width = area.width;
		height = area.height;
	}

	public WindowSize(Shell shell) {
		Rectangle area = shell.getClientArea();
		width = area.width;
		height = area.height;
	}

	public void setSize(Shell shell) {
		shell.setSize(width, height);
		// System.out.println(width + "" + height);
	}

	public int getScreenWidth() {
		return width;
	}

	public int getScreenHeight() {
		return height;
	}

}
