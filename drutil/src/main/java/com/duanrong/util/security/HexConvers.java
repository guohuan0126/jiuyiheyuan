package com.duanrong.util.security;

/**
 * 转换工具类
 */
public final class HexConvers {

    private static final char hex_digits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e', 'f'};
    private static final char HEX_DIGITS[]={'0', '1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    /**
     * byte[] 转 16 进制串
     * @param bytes
     * @return
     */
    public static String hexDigits(byte[] bytes) {
        return hexDigits(bytes, HEX_DIGITS);
    }

    /**
     * byte[] 转 16 进制串
     * @param bytes
     * @return
     */
    public static String hexDigits(byte[] bytes, char[] hex_digits) {
        if (bytes == null)
            return "";
        int j = bytes.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = bytes[i];
            str[k++] = hex_digits[byte0 >>> 4 & 0xf];
            str[k++] = hex_digits[byte0 & 0xf];
        }
        return new String(str);
    }

    /**
     * 16 进制串转 byte[]
     * @param data
     * @return
     */
    public static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] out = new byte[len >> 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }


}
