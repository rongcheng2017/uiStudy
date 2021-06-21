// IWebViewProcessToMainProcessInterface.aidl
package com.rong.cheng.webview;

// Declare any non-default types here with import statements

interface IWebViewProcessToMainProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void handleWebCommand(String commandName,String jsonParams);
}