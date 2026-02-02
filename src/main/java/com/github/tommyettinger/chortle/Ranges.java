package com.github.tommyettinger.chortle;

/**
 * Tracks ranges of Unicode that are used for different languages. This only actually knows about Hebrew and non-digit
 * parts of Arabic scripts, which are both written right-to-left.
 * <br>
 * Created by Crowni on 10/11/2017.
 **/
public enum Ranges {
    HEBREW(0x0590, 0x05FF),
    ARABIC_A(0x0600, 0x065F),
//	ARABIC_NUMERIC(0x0660, 0x0669), // LTR
    ARABIC_B(0x066A, 0x06EF),
//    EXTENDED_NUMERIC(0x06F0, 0x06F9), // LTR
    ARABIC_C(0x06FA, 0x06FF),
    ARABIC_SUPPLEMENT(0x0750, 0x077F),
    ARABIC_EXTENDED(0x0870, 0x08FF),
    ARABIC_PRESENTATION_A(0xFB50, 0xFDFF),
    ARABIC_PRESENTATION_B(0xFE70, 0xFEFF),;

    private final int from;
    private final int to;

    Ranges(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public static boolean inRange(Ranges languages, char c) {
        return languages.from <= c && c <= languages.to;
    }
}
