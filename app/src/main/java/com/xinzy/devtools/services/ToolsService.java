package com.xinzy.devtools.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.xinzy.devtools.model.Tools;
import com.xinzy.devtools.utils.Logger;

import java.util.List;

public class ToolsService extends AccessibilityService
{
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent)
    {
        Logger.v("onAccessibilityEvent");

        String title = Tools.currentSelector().getSettingName();
        if (accessibilityEvent != null && !TextUtils.isEmpty(title))
        {
            AccessibilityNodeInfo info = accessibilityEvent.getSource();
            int eventType = accessibilityEvent.getEventType();
            if (info != null && accessibilityEvent.getPackageName().equals("com.android.settings")
                    && (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED)) {

                List<AccessibilityNodeInfo> nodeInfos = info.findAccessibilityNodeInfosByText("指针位置");
                if (nodeInfos != null && nodeInfos.size() > 0)
                {
                    AccessibilityNodeInfo node = nodeInfos.get(0);
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    performAction(node, AccessibilityNodeInfo.ACTION_CLICK);
                    Tools.currentSelector().clear();
                } else
                {
                    performAction(info, AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                }
            }
        }
    }

    private boolean performAction(AccessibilityNodeInfo nodeInfo, int action)
    {
        if (nodeInfo.performAction(action))
        {
            return true;
        }
        AccessibilityNodeInfo parent = nodeInfo.getParent();
        while (parent != null)
        {
            if (parent.performAction(action))
            {
                return true;
            }
            parent = parent.getParent();
        }
        final int childrenCount = nodeInfo.getChildCount();
        for (int i = 0; i < childrenCount; i++)
        {
            if (nodeInfo.getChild(i).performAction(action))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onInterrupt()
    {
        Logger.v("onInterrupt");
    }

    public static boolean isServiceStart(Context context)
    {
        AccessibilityManager manager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> services = manager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        String name = context.getPackageName() + "/.services.ToolsService";
        for (AccessibilityServiceInfo info : services)
        {
            String id = info.getId();
            Logger.d(id);
            if (id.contains(name))
            {
                return true;
            }
        }
        return false;
    }
}
