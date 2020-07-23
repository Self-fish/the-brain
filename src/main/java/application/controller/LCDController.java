package application.controller;

import com.pi4j.io.i2c.I2CDevice;

public class LCDController {

    private I2CDevice device;

    public LCDController(I2CDevice device) {
        this.device = device;
    }

    private final byte LCD_CLEARDISPLAY = (byte) 0x01;
    private final byte LCD_RETURNHOME = (byte) 0x02;
    private final byte LCD_ENTRYMODESET = (byte) 0x04;
    private final byte LCD_DISPLAYCONTROL = (byte) 0x08;
    private final byte LCD_FUNCTIONSET = (byte) 0x20;

    private final byte LCD_ENTRYLEFT = (byte) 0x02;

    private final byte LCD_DISPLAYON = (byte) 0x04;

    private final byte LCD_4BITMODE = (byte) 0x00;
    private final byte LCD_2LINE = (byte) 0x08;
    private final byte LCD_5x8DOTS = (byte) 0x00;

    private final byte LCD_BACKLIGHT = (byte) 0x08;
    private final byte LCD_NOBACKLIGHT = (byte) 0x00;

    private final byte En = (byte) 0b00000100; // Enable bit

    public void init() {
        try {
            writeCommand((byte) 0x03);
            writeCommand((byte) 0x03);
            writeCommand((byte) 0x03);
            writeCommand((byte) 0x02);

            writeCommand((byte) (LCD_FUNCTIONSET | LCD_2LINE | LCD_5x8DOTS | LCD_4BITMODE));
            writeCommand((byte) (LCD_DISPLAYCONTROL | LCD_DISPLAYON));
            writeCommand((byte) (LCD_CLEARDISPLAY));
            writeCommand((byte) (LCD_ENTRYMODESET | LCD_ENTRYLEFT));
            Thread.sleep(0, 200000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void writeString(String string, int line, int pos) {
        setCursor(line, pos);
        for (int i = 0; i < string.length(); i++) {
            writeCharacter((byte) string.charAt(i));
        }
    }

    public void backLight(boolean state) {
        byte stateByte = state? LCD_BACKLIGHT : LCD_NOBACKLIGHT;
        try {
            device.write(stateByte);
            Thread.sleep(0, 100000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void clear() {
        writeCommand(LCD_CLEARDISPLAY);
        writeCommand(LCD_RETURNHOME);
    }

    public void clearLine(int line) {
        writeString("                    ", line, 0);
    }

    public void loadCustomChars(byte[][] customChars) {
        writeCommand((byte) 0x40);
        for (byte[] customChar : customChars) {
            for (byte b : customChar) {
                writeCharacter(b);
            }
        }
    }

    public void setCursor(int line, int column) {
        byte linePosition = 0;
        switch (line) {
            case 1:
                linePosition = 0x40;
                break;
            case 2:
                linePosition = 0x14;
                break;
            case 3:
                linePosition = 0x54;
                break;
        }
        writeCommand((byte) (0x80 + linePosition + column));
    }

    private void writeCommand(byte cmd) {
        lcdWriteFourBits((byte) ((cmd & 0xF0)));
        lcdWriteFourBits((byte) (((cmd << 4) & 0xF0)));
    }

    public void writeCharacter(byte cmd) {
        lcdWriteFourBits((byte) (1 | (cmd & 0xF0)));
        lcdWriteFourBits((byte) (1 | ((cmd << 4) & 0xF0)));
    }

    private void lcdWriteFourBits(byte data) {
        try {
            device.write((byte) (data | LCD_BACKLIGHT));
            lcdStrobe(data);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void lcdStrobe(byte data) {
        try {
            device.write((byte) (data | En | LCD_BACKLIGHT));
            Thread.sleep(0, 500000);
            device.write((byte) ((data & ~En) | LCD_BACKLIGHT));
            Thread.sleep(0, 100000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
