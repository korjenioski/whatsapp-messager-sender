using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AndroidCaller : MonoBehaviour
{
    private const float FontSizeMult = 0.04f;
    private string mDebugText = "";
    public GUISkin menuSkin;

    private void debugLog(String log)
    {
        mDebugText += "\n" + log;
        Debug.Log(log);
    }

    void OnGUI()
    {
        GUI.skin = menuSkin;
        GUI.skin.button.fontSize = (int)(FontSizeMult * Screen.height);
        GUI.skin.label.fontSize = (int)(FontSizeMult * Screen.height);

        Rect buttonRect = new Rect(20, 60,
            Screen.width - 40, 120);

        float line = buttonRect.y + buttonRect.height + 20;

        Rect buttonRect2 = new Rect(20, line,
            Screen.width - 40, 120);

        float line2 = buttonRect2.y + buttonRect2.height + 20;

        GUI.TextArea(new Rect(20, line2, Screen.width - 40, Screen.height - line - 20),
            mDebugText);

        string buttonLabel = "Send Messenger";
        string buttonLabel2 = "Send Whatsapp";

        if (GUI.Button(buttonRect, buttonLabel))
        {
            CallNativePlugin(0);
        }

        if (GUI.Button(buttonRect2, buttonLabel2))
        {
            CallNativePlugin(1);
        }
    }

    //method that calls our native plugin.
    public void CallNativePlugin(int type)
    {
        try
        {
            debugLog("CallNativePlugin");

            // Retrieve the UnityPlayer class.
            AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");

            if (unityPlayerClass != null)
            {
                debugLog("unityPlayerClass: load");
            }

            // Retrieve the UnityPlayerActivity object ( a.k.a. the current context )
            AndroidJavaObject unityActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");

            if (unityActivity != null)
            {
                debugLog("unityActivity: load");
            }

            // Retrieve the "Bridge" from our native plugin.
            // Define the complete package name.              
            AndroidJavaObject bridge = new AndroidJavaObject("com.phaneronsoft.messagelib.MessageUtil");

            if (bridge != null)
            {
                debugLog("bridge: load");
            }

            if (type == 0) { 
                // Setup the parameters we want to send to our native plugin.              
                object[] parameters = new object[2];
                parameters[0] = unityActivity;
                parameters[1] = "4";

                // Call PrintString in bridge, with our parameters.
                bridge.CallStatic("sendMessenger", parameters);
            } else if (type == 1)
            {
                // Setup the parameters we want to send to our native plugin.              
                object[] parameters = new object[3];
                parameters[0] = unityActivity;
                parameters[1] = "447700900000";
                parameters[2] = "Hello World!";

                // Call PrintString in bridge, with our parameters.
                bridge.CallStatic("sendWhatsapp", parameters);
            }
        } catch (Exception e)
        {
            debugLog("Exception: " + e);
        }
    }
}
