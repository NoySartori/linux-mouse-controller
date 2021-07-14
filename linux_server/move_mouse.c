#include <stdio.h>
#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <X11/Xatom.h>
#include <stdlib.h>
#include <string.h>

void move_mouse(int xPosition, int yPosition) {
	// Declare pointers that hold the default display and root window.
	Display *dpy;
	Window root_window;

	// Get the default (first) display.
	dpy = XOpenDisplay(0);

	// Get the root window (The "window" of all windows).
	root_window = XRootWindow(dpy, 0);

	// We tell x11 server to report all events of type key release.
	// This is so that from now on when we send commands,
	// the x11 server will report them to the OS.
	XSelectInput(dpy, root_window, KeyReleaseMask);

	// We move the pointer, to the main display, to the xPosition
	// and yPosition we collected.
	// We put zeroes and Nones in all "src" args, because we work
	// on absolute positions and not relative positions.
	// So if we tell the mouse to move to position (100, 100),
	// it will be absolute position of (100,100), relative to (0,0).
	XWarpPointer(dpy, None, root_window, 0, 0, 0, 0, xPosition, yPosition);

	// We flush the changes to the screen.
	XFlush(dpy);
}
