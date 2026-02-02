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

/**
 * The main representation of any char internally in the library.
 * This tracks if a given char uses RTL directionality. If the char is in the Arabic script, this tracks its "type" for
 * the purposes of connecting to other characters, and potentially a modified char that can change during filtering.
 * <br>
 * Created by Crowni on 10/5/2017.
 **/
public class Glyph {

    protected final char originalChar;
    protected final int type;
    protected char modifiedChar;
    protected final boolean rtl;

    public Glyph(char c) {
        this.originalChar = c;
        this.rtl = RtlUtils.isRightToLeft(c);
        type = RtlUtils.getCharType(c);
    }

    public char getOriginalChar() {
        return originalChar;
    }

    public char getChar() {
        return modifiedChar;
    }

    public boolean isRTL() {
        return rtl;
    }

    public void setChar(char c) {
        this.modifiedChar = c;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.valueOf(rtl && modifiedChar != 0 ? modifiedChar : originalChar);
    }
}
