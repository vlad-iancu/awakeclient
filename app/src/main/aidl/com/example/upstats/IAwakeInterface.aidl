// IAwakeInterface.aidl
package com.example.upstats;

// Declare any non-default types here with import statements

interface IAwakeInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    long getUptime();

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}