#SingleInstance force
Mods = ^!+#

Numpad1::KeyBD("1.;")
Numpad2::KeyBD("2ABC")
Numpad3::KeyBD("3DEF")
Numpad4::KeyBD("4GHI")
Numpad5::KeyBD("5JKL")
Numpad6::KeyBD("6MNO")
Numpad7::KeyBD("7PQRS")
Numpad8::KeyBD("8TUV")
Numpad9::KeyBD("9WXYZ")
Numpad0::KeyBD(" 0")

KeyBD(list)
{
   Static i          ; index of the character in the list, to be sent
   Global Caps, Mod, c
   If (A_ThisHotKey = A_PriorHotKey and A_TimeSincePriorHotkey < 1000)
   {
      IfEqual Mod,,Send {BS} ; erase the char this key has inserted
      i++            ; index of next char, wrapped around
      IfGreater i,% StrLen(list), SetEnv i,1
   }
   Else i = 1        ; at new key send first char from list
   StringMid c, list, i, 1
   IfEqual Mod,, {
      IfEqual Caps,1, StringUpper c,c
      Else            StringLower c,c
      SendRaw %c%    ; send selected
   }
   Else {
      StringLower c,c
      ModTip(Mod,c)
   }
}

ModTip(s,c)
{
   StringReplace s, s, ^, Ctrl-, All
   StringReplace s, s, !, Alt-,  All
   StringReplace s, s, +, Shift-,All
   StringReplace s, s, #, Win-,  All
   TrayTip,,Cmd = %s%%c%
}