Honestly, this whole thing is a bit of a hack. Basically I don't know enough C/C++ to write a proper driver (and I'm not convinced that is the proper way to do it) and the Python version of HIDAPI didn't seem to work and was a bit too complicated, as I also don't really know how HID devices work.

Relies on PowerToys (or your favourite button remapper) to function properly. Also use the AutoHotKey script to enable T9 esque typing on the number buttons.

TODO:

Move to maven or gradle.

Dependencies:
jna, jna-platform, purejavahidapi