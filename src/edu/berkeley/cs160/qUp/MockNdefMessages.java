/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.berkeley.cs160.qUp;

import org.ndeftools.wellknown.TextRecord;

import java.util.Locale;

/**
 * This class provides a list of fake NFC Ndef format Tags.
 */
public class MockNdefMessages {


    /**
     * A plain text tag in english.
     */
    public static final TextRecord CHEESEBOARD = new TextRecord("Cheeseboard", Locale.ENGLISH);
    public static final TextRecord CHEESEBOARD_TIME = new TextRecord("00:20", Locale.ENGLISH);
    public static final TextRecord SLIVER_TIME = new TextRecord("00:14", Locale.ENGLISH);
    public static final TextRecord PURPLE_KOW = new TextRecord("Purple Kow", Locale.ENGLISH);
    public static final TextRecord PURPLE_KOW_TIME = new TextRecord("00:08", Locale.ENGLISH);
    public static final TextRecord SLIVER = new TextRecord("Sliver", Locale.ENGLISH);


}
