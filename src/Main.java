import purejavahidapi.HidDevice;
import purejavahidapi.HidDeviceInfo;
import purejavahidapi.InputReportListener;
import purejavahidapi.PureJavaHidApi;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public class Main {
    private static boolean mouseMode = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        List<HidDeviceInfo> devList = PureJavaHidApi.enumerateDevices();
        HidDeviceInfo devInfo = null;
        for (HidDeviceInfo info : devList) {
            if (info.getVendorId() == (short)0x06e7 && info.getProductId() == (short)0x8073) {
                devInfo = info;

                HidDevice dev=PureJavaHidApi.openDevice(devInfo);

                System.out.println(devInfo.getProductId());

                dev.setInputReportListener(new InputReportListener() {
                    @Override
                    public void onInputReport(HidDevice source, byte Id, byte[] data, int len) {
                        System.out.printf("onInputReport: id %d len %d data ", Id, len);
                        for (int i = 0; i < len; i++)
                            System.out.printf("%02X ", data[i]);
                        System.out.println();

                        int keyCode = -1;
                        switch (data[0]) {
                            case 0x42:
                                keyCode = KeyEvent.VK_UP;
                                break;
                            case 0x43:
                                keyCode = KeyEvent.VK_DOWN;
                                break;
                            case 0x44:
                            case (byte) 0xB4:
                                keyCode = KeyEvent.VK_LEFT;
                                break;
                            case 0x45:
                            case (byte) 0xB3:
                                keyCode = KeyEvent.VK_RIGHT;
                                break;
                            case 0x41:
                                keyCode = KeyEvent.VK_ENTER;
                                break;
                            case 0x40:
                                keyCode = KeyEvent.VK_WINDOWS;
                                break;
                            case (byte) 0x9C:
                                keyCode = KeyEvent.VK_F19;
                                break;
                            case (byte) 0x9D:
                                keyCode = KeyEvent.VK_F18;
                                break;
                            case (byte) 0xB2:
                                mouseMode = true;
                                break;
                        }

                        if (keyCode != -1) {
                            try {
                                Robot robot = new Robot();
                                robot.keyPress(keyCode);
                                robot.keyRelease(keyCode);
                            } catch (AWTException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        if (data[0] == 0x04) {
                            try {
                                Robot robot = new Robot();
                                robot.keyPress(KeyEvent.VK_ALT);
                                robot.keyPress(KeyEvent.VK_SPACE);
                                robot.keyPress(KeyEvent.VK_C);
                                robot.keyRelease(KeyEvent.VK_ALT);
                                robot.keyRelease(KeyEvent.VK_SPACE);
                                robot.keyRelease(KeyEvent.VK_C);
                            } catch (AWTException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        if (data[0] == 0x30) {
                            try {
                                Runtime.getRuntime().exec("shutdown.exe -s -t 0");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        }

        while (true) {

        }
    }

    private void pressKey(int keyCode) {

    }
}
