/*
 * SPDX-FileCopyrightText: 2026 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.ims;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;

public class ImsFactory {
    private static final String LOG_TAG = "ImsFactory";
    private static final String VENDOR_IMS_FACTORY = "com.xiaomi.ims.XmImsFactory";
    private static volatile ImsFactory sInstance;

    protected ImsFactory() {}

    public static ImsFactory get() {
        if (sInstance == null) {
            synchronized (ImsFactory.class) {
                if (sInstance == null) {
                    sInstance = injectComponent(VENDOR_IMS_FACTORY);
                }
            }
        }
        return sInstance;
    }

    private static ImsFactory injectComponent(String className) {
        try {
            Class<?> cls = Class.forName(className);
            Constructor<?> constructor = cls.getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
            sInstance = (ImsFactory) constructor.newInstance(new Object[0]);
        } catch (Exception e) {
            Log.e(LOG_TAG, "failed to load ims factory, " + e.getMessage());
            sInstance = new ImsFactory();
        }
        return sInstance;
    }
}
