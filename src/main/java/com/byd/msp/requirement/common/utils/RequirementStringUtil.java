package com.byd.msp.requirement.common.utils;

public class RequirementStringUtil {

    /**
     * 移除字符串最后一部分（按'-'分割）
     * 示例: "A-B-C-D" -> "A-B-C"
     */
    public static String removeLastHyphenPart(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        int hyphenCount = 0;
        for (char c : input.toCharArray()) {
            if (c == '-') {
                hyphenCount++;
            }
        }

        if (hyphenCount > 3) {
            int lastHyphenIndex = input.lastIndexOf('-');
            return input.substring(0, lastHyphenIndex);
        } else {
            return input;
        }
    }
}
