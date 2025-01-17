package com.jeve.gestures.action;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.jeve.gestures.content.AppContent;
import com.jeve.gestures.content.ContentManager;
import com.jeve.gestures.tool.ActionTool;
import com.jeve.gestures.tool.LocalLogTool;
import com.jeve.gestures.tool.Logger;
import com.jeve.gestures.tool.Utils;

import java.util.List;

/**
 * 包名:com.xiangzi.jukandian
 * <p>
 * com.xiangzi.jukandian.activity.V2WelcomeActivity欢迎页
 * com.xiangzi.jukandian.activity.MainActivity 首页
 * com.xiangzi.jukandian.widget.dialog.UserIsRegDialog弹框
 * com.xiangzi.jukandian.widget.dialog.QuitNewUserDialog弹框
 * <p>
 * com.xiangzi.jukandian.activity.NativeArticalDetailActivity新闻
 * com.xiangzi.jukandian.activity.WebViewActivity新闻
 * <p>
 * com.xiangzinet.jkd.JKDActivity广告
 */
public class KanAction extends BaseAction {

    public KanAction() {
        super();
    }

    public KanAction(AppContent appContent) {
        super(appContent);
    }

    @Override
    public void checkAction(String className, AccessibilityNodeInfo nodeInfo, AccessibilityService service) throws Exception {
        LocalLogTool.writeTxtToFile("进入聚看点 checkAction: " + className);
        super.checkAction(className, nodeInfo, service);
        switch (className) {
            case "com.xiangzi.jukandian.activity.MainActivity":
                Logger.d("聚看点主界面操作");
                LocalLogTool.writeTxtToFile("聚看点主界面操作");
                huiMainAction(nodeInfo, service);
                break;
            case "com.xiangzi.jukandian.activity.NativeArticalDetailActivity":
            case "com.xiangzi.jukandian.activity.WebViewActivity":
                Logger.d("聚看点新闻界面操作");
                LocalLogTool.writeTxtToFile("聚看点新闻界面操作");
                newsAction(nodeInfo, service);
                break;
//            case "com.cashtoutiao.alivideodetail.AliVideoDetailActivity":
//                Logger.d("惠头条视频界面操作");
//                videoAction(service);
//                break;
            case "com.xiangzi.jukandian.activity.V2WelcomeActivity":
                Logger.d("聚看点欢迎界面");
                LocalLogTool.writeTxtToFile("聚看点欢迎界面");
                setActionTime(0);
                ContentManager.getInstance().changeContent(getAppContent());
                break;
            case "com.xiangzinet.jkd.JKDActivity":
                Logger.d("聚看点网页广告界面");
                LocalLogTool.writeTxtToFile("聚看点网页广告界面");
                clickLeftTopToClose(nodeInfo, service);
                break;
            case "com.xiangzi.jukandian.widget.dialog.SignDialog2":
                Logger.d("聚看点签到界面");
                LocalLogTool.writeTxtToFile("聚看点签到界面");
                signAction(nodeInfo, service);
                break;
            default:
                Logger.d("聚看点其他界面操作");
                LocalLogTool.writeTxtToFile("聚看点其他界面操作");
                otherAction(nodeInfo, service);
                break;
        }
    }

    //主界面  滑动屏幕三分之一，点击
    @Override
    public void huiMainAction(AccessibilityNodeInfo nodeInfo, AccessibilityService service) throws Exception {
        super.huiMainAction(nodeInfo, service);

//        List<AccessibilityNodeInfo> moreView = nodeInfo.findAccessibilityNodeInfosByText("视频");
//        if (moreView != null && !moreView.isEmpty()) {
//            Logger.d(getAppContent().getAppName() + "切换视频");
//            LocalLogTool.writeTxtToFile(getAppContent().getAppName() + "切换视频");
//            moreView.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            Thread.sleep(1000);
//        }
        Thread.sleep(2000);
        ActionTool.scroll(nodeInfo, service, 540, 1280, 540, 640);
        Thread.sleep(1000);
        ActionTool.clickScreen(nodeInfo, service, 540, 960);
        LocalLogTool.writeTxtToFile("聚看点主界面单次操作完毕");
        recordTime(3000);
    }

    //新闻界面
    @Override
    public void newsAction(AccessibilityNodeInfo nodeInfo, AccessibilityService service) throws Exception {
        super.newsAction(nodeInfo, service);
        Thread.sleep(2000);
        int time = 0;
        while (time < 90) {
            Thread.sleep(3000);
            ActionTool.scroll(nodeInfo, service, 540, 1280, 540, 880);
            Thread.sleep(3000);
            ActionTool.scroll(nodeInfo, service, 540, 880, 540, 1280);
            time += 6;
            LocalLogTool.writeTxtToFile("聚看点新闻单次操作完毕");
            if (recordTime(6000)) {
                return;
            }
        }
        Thread.sleep(2000);
        ActionTool.clickBack(service);
    }

}

