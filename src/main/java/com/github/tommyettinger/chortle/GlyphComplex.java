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
 * A special case for a Glyph composed of two other Glyphs, where if one Glyph is removed, this changes to use the
 * other internal Glyph.
 * <br>
 * Created by Crowni on 10/5/2017.
 **/
public class GlyphComplex extends Glyph {

    private Glyph beforeGlyph, afterGlyph;

    // The originalChar is "complex" but modifiedChar may still be set.
    public GlyphComplex(char complexChar) {
        super(complexChar);
    }

    public Glyph getBeforeGlyph() {
        return beforeGlyph;
    }

    public void setBeforeGlyph(Glyph beforeGlyph) {
        if(this.beforeGlyph == null)
            this.beforeGlyph = beforeGlyph;
    }

    public Glyph getAfterGlyph() {
        return afterGlyph;
    }

    public void setAfterGlyph(Glyph afterGlyph) {
        if(this.afterGlyph == null)
            this.afterGlyph = afterGlyph;
    }

    public void setSimpleGlyphs(Glyph glyph0, Glyph glyph1) {
        if (beforeGlyph == null)
            beforeGlyph = glyph0;
        if(afterGlyph == null)
            afterGlyph = glyph1;
    }
}
