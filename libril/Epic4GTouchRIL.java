/*
 * Copyright (C) 2013 Dorian Snyder
 * Copyright (C) 2013 The CyanogenMod Project <http://www.cyanogenmod.org>
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

package com.android.internal.telephony;

import static com.android.internal.telephony.RILConstants.*;

import android.content.Context;
import android.media.AudioManager;
import android.os.Message;
import android.os.Parcel;
import android.os.SystemProperties;
import android.telephony.SignalStrength;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

public class Epic4GTouchRIL extends SamsungExynos4RIL implements CommandsInterface {

    private AudioManager audioManager;

    public Epic4GTouchRIL(Context context, int networkMode, int cdmaSubscription) {
        super(context, networkMode, cdmaSubscription);
        audioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
        mQANElements = 5;
    }

    @Override
    protected Object
    responseSignalStrength(Parcel p) {
        int numInts = 12;
        int response[];

        response = new int[numInts];
        for (int i = 0 ; i < 7 ; i++) {
            response[i] = p.readInt();
        }

        // Take just the least significant byte as the signal strength
        response[2] %= 256;
        response[4] %= 256;

        SignalStrength signalStrength = new SignalStrength(
            response[0], response[1], response[2], response[3], response[4],
            response[5], response[6], false);
        return signalStrength;
    }
}
