/*
 * Copyright (c) 2014, The CyanogenMod Project. All rights reserved.
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

import android.content.Context;
import android.os.Parcel;
import android.os.SystemProperties;
import android.telephony.SignalStrength;

/**
 * RIL customization for Xperia Z3 DUAL
 * <p/>
 * {@hide}
 */
public class SonyRIL extends RIL {

    private boolean mIsGsm = false;

    public SonyRIL(Context context, int networkMode, int cdmaSubscription) {
        super(context, networkMode, cdmaSubscription, null);

        mQANElements = SystemProperties.getInt("ro.ril.telephony.mqanelements", 5);
    }

    public SonyRIL(Context context, int preferredNetworkType,
                   int cdmaSubscription, Integer instanceId) {
        super(context, preferredNetworkType, cdmaSubscription, instanceId);

        mQANElements = SystemProperties.getInt("ro.ril.telephony.mqanelements", 5);
    }

    @Override
    public void setPhoneType(int phoneType) {
        super.setPhoneType(phoneType);
        mIsGsm = (phoneType != RILConstants.CDMA_PHONE);
    }

    @Override
    protected Object
    responseSignalStrength(Parcel p) {
        int gsmSignalStrength = p.readInt();
        int gsmBitErrorRate = p.readInt();
        int umtsRscp = p.readInt(); // not used by stock SignalStrength
        int cdmaDbm = p.readInt();
        int cdmaEcio = p.readInt();
        int evdoDbm = p.readInt();
        int evdoEcio = p.readInt();
        int evdoSnr = p.readInt();
        int lteSignalStrength = p.readInt();
        int lteRsrp = p.readInt();
        int lteRsrq = p.readInt();
        int lteRssnr = p.readInt();
        int lteCqi = p.readInt();
        int tdScdmaRscp = p.readInt();
        boolean isGsm = p.readInt() != 0;

        // constructor sets default true, makeSignalStrengthFromRilParcel does not set it
        return new SignalStrength(gsmSignalStrength, gsmBitErrorRate, cdmaDbm, cdmaEcio, evdoDbm,
                evdoEcio, evdoSnr, lteSignalStrength, lteRsrp, lteRsrq, lteRssnr, lteCqi,
                tdScdmaRscp, mIsGsm);
    }
}
