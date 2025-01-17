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

/**
 * 牛牛头条
 * com.huolea.bull包名
 * com.huolea.bull.page.other.activity.SplashActivity启动页
 * com.huolea.bull.page.other.activity.MainActivity主界面
 * <p>
 * com.huolea.bull.page.other.activity.WebActivity直接退出
 * <p>
 * com.huolea.bull.page.news.activity.NewsDetailActivity新闻界面
 */
public class NiuAction extends BaseAction {

    public NiuAction() {
        super();
    }

    public NiuAction(AppContent appContent) {
        super(appContent);
    }

    @Override
    public void checkAction(String className, AccessibilityNodeInfo nodeInfo, AccessibilityService service) throws Exception {
        LocalLogTool.writeTxtToFile("进入牛牛头条 checkAction: " + className);
        super.checkAction(className, nodeInfo, service);
        switch (className) {
            case "com.huolea.bull.page.other.activity.MainActivity":
                Logger.d("牛牛头条主界面操作");
                LocalLogTool.writeTxtToFile("牛牛头条主界面操作");
                huiMainAction(nodeInfo, service);
                break;
            case "com.huolea.bull.page.news.activity.NewsDetailActivity":
                Logger.d("牛牛头条新闻界面操作");
                LocalLogTool.writeTxtToFile("牛牛头条新闻界面操作");
                newsAction(nodeInfo, service);
                break;
//            case "com.cashtoutiao.alivideodetail.AliVideoDetailActivity":
//                Logger.d("牛牛头条视频界面操作");
//                videoAction(service);
//                break;
            case "com.huolea.bull.page.other.activity.SplashActivity":
                Logger.d("牛牛头条启动界面操作");
                LocalLogTool.writeTxtToFile("牛牛头条启动界面操作");
                setActionTime(0);
                ContentManager.getInstance().changeContent(getAppContent());
                break;
            default:
                Logger.d("牛牛头条其他界面操作");
                LocalLogTool.writeTxtToFile("牛牛头条其他界面操作");
                otherAction(nodeInfo,service);
                break;
        }
    }

    //主界面  滑动屏幕三分之一，点击
    @Override
    public void huiMainAction(AccessibilityNodeInfo nodeInfo, AccessibilityService service) throws Exception {
        super.huiMainAction(nodeInfo, service);
        Thread.sleep(2000);
        ActionTool.scroll(nodeInfo, service, 540, 1280, 540, 640);
        Thread.sleep(1000);
        ActionTool.clickScreen(nodeInfo, service, 540, 960);
        LocalLogTool.writeTxtToFile("牛牛头条主界面单次操作完毕");
        recordTime(3000);
    }

    //新闻界面
    @Override
    public void newsAction(AccessibilityNodeInfo nodeInfo, AccessibilityService service) throws Exception {
        super.newsAction(nodeInfo, service);
        Thread.sleep(2000);
        int time = 0;
//        while (time < 120) {
        ActionTool.scroll(nodeInfo, service, 540, 1280, 540, 880);
        Thread.sleep(3000);
        ActionTool.scroll(nodeInfo, service, 540, 880, 540, 1280);
        Thread.sleep(3000);
        ActionTool.scroll(nodeInfo, service, 540, 1280, 540, 880);
        Thread.sleep(3000);
        ActionTool.scroll(nodeInfo, service, 540, 880, 540, 1280);
        Thread.sleep(3000);
        ActionTool.scroll(nodeInfo, service, 540, 1280, 540, 880);
        Thread.sleep(3000);
//        }
        LocalLogTool.writeTxtToFile("牛牛头条新闻单次操作完毕");
        ActionTool.clickBack(service);
        recordTime(15000);
    }

}
