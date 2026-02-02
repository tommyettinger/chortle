/*
 * **************************************************************************
 * Copyright 2017 See AUTHORS file.
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * *************************************************************************
 *
 */

package com.github.tommyettinger.chortle;

import com.badlogic.gdx.utils.Array;

/**
 * The main way of using this library.
 * <br>
 * Create a {@code new Chortle()} and use its {@link #getText(String)} to transform
 * a String that may contain RTL scripts to use different directionality for that text only.
 * <br>
 * Created by Crowni on 10/5/2017.
 **/
public class Chortle {
    private final Array<Glyph> glyphs = new Array<>();
    private final StringBuilder workingText = new StringBuilder(128);
    private final StringBuilder subtext = new StringBuilder(16);

    public String typing(char c) {
        if (c == '\b') // backspace
            popChar();
        else
            addChar(new Glyph(c));
        workingText.setLength(0);
        return reorder(workingText).toString();
    }

    public String getText(String given) {
        String[] split = given.split("\n");
        workingText.setLength(0);
        for(int ln = 0; ln < split.length; ln++) {
            String line = split[ln];
            for (int i = 0, n = line.length(); i < n; i++) {
                char c = line.charAt(i);
                addChar(new Glyph(c));
            }
            reorder(workingText);
            if(ln + 1 < split.length) workingText.append('\n');
            this.glyphs.clear();
        }
        return workingText.toString();
    }

    private void addChar(Glyph glyph) {
        glyphs.add(glyph);
        filterLastChars(1);
        filterLastChars(2);
    }

    private void popChar() {
        if (glyphs.size > 0) {
            Glyph aChar = glyphs.pop();
            if (aChar instanceof GlyphComplex) {
                glyphs.add(((GlyphComplex) aChar).getAfterGlyph());
            }
            filterLastChars(1);
        }
    }

    private static void appendReversed(StringBuilder mainText, CharSequence reversing) {
        for (int n = reversing.length(), i = n - 1; i >= 0; i--) {
            mainText.append(reversing.charAt(i));
        }
    }

    private StringBuilder reorder(StringBuilder text) {
        boolean inserting = true;
        subtext.setLength(0);
        for (int i = glyphs.size - 1; i >= 0; i--) {
            if (glyphs.get(i).isRTL()) {
                if (!inserting) {
                    inserting = true;
                    appendReversed(text, subtext);
                    subtext.setLength(0);
                }

                text.append(glyphs.get(i).getChar());
            } else {
                inserting = false;
                subtext.append(glyphs.get(i).getOriginalChar());
            }
        }

        appendReversed(text, subtext);

        return text;
    }

    private void filterLastChars(int i) {
        if (glyphs.size - i >= 0)
            filter(glyphs.size - i);
    }


    /**
     * Attempts to modify the glyph stored at {@code index} to match the correct presentation form for its neighboring
     * Arabic glyphs. This does nothing if the char at {@code index} is not a right-to-left char. This is permitted to
     * replace two ArGlyph items with one ArGlyphComplex item if this is at the end of the text, a LAM char precedes
     * the given index, and the glyph at index is an ALF char.
     * @param index index of the glyph that will be mutated in-place
     */
    private void filter(int index) {
        Glyph glyph = glyphs.get(index);
        if (!glyph.isRTL() || Ranges.inRange(Ranges.HEBREW, glyph.originalChar)) {
            return;
        }

        Glyph before = getPositionGlyph(index - 1);
        Glyph after = getPositionGlyph(index + 1);


        /* CASE 1 */
        if (before == null && after == null)
            glyph.setChar(glyph.getOriginalChar());


        /* CASE 2 */
        if (before == null && after != null)
            glyph.setChar(RtlUtils.getStartChar(glyph.getOriginalChar()));


        /* CASE 3 */
        if (before != null && after == null)
            if (RtlUtils.isALFChar(glyph.getOriginalChar()) && RtlUtils.isLAMChar(before.getOriginalChar())) {
                addComplexChars(index, glyph);
            } else {
                if (before.getType() == ArabicData.X2)
                    glyph.setChar(RtlUtils.getIndividualChar(glyph.getOriginalChar()));
                else
                    glyph.setChar(RtlUtils.getEndChar(glyph.getOriginalChar()));
            }


        /* CASE 4 */
        if (before != null && after != null)
            if (glyph.getType() == ArabicData.X4) {
                if (before.getType() == ArabicData.X2)
                    glyph.setChar(RtlUtils.getStartChar(glyph.getOriginalChar()));
                else
                    glyph.setChar(RtlUtils.getCenterChar(glyph.getOriginalChar()));
            } else {
                if (before.getType() == ArabicData.X2)
                    glyph.setChar(RtlUtils.getIndividualChar(glyph.getOriginalChar()));
                else
                    glyph.setChar(RtlUtils.getEndChar(glyph.getOriginalChar()));
            }

    }


    private void addComplexChars(int index, Glyph arGlyph) {
        GlyphComplex glyph = new GlyphComplex(RtlUtils.getLAM_ALF(arGlyph.getOriginalChar()));
        glyph.setSimpleGlyphs(arGlyph, getPositionGlyph(index - 1));
        glyphs.pop();
        glyphs.pop();
        addChar(glyph);
    }

    /**
     * @param index index of current glyph
     * @return correct position of glyph
     */
    private Glyph getPositionGlyph(int index) {
        Glyph glyph = (index >= 0 && index < glyphs.size) ? glyphs.get(index) : null;
        return (glyph != null) ? (RtlUtils.isInvalidChar(glyph.getOriginalChar()) ? null : glyph) : null;
    }

}

