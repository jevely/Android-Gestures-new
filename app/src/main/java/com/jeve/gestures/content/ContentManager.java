package com.jeve.gestures.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jeve.gestures.AppContentDao;
import com.jeve.gestures.DaoMaster;
import com.jeve.gestures.DaoSession;
import com.jeve.gestures.MyApplication;
import com.jeve.gestures.action.BaseAction;
import com.jeve.gestures.action.DianAction;
import com.jeve.gestures.action.DongAction;
import com.jeve.gestures.action.HuaAction;
import com.jeve.gestures.action.HuiAction;
import com.jeve.gestures.action.KanAction;
import com.jeve.gestures.action.NiuAction;
import com.jeve.gestures.action.ShuaAction;
import com.jeve.gestures.action.ZhuanAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentManager {

    private final String DB_NAME = "appcontent.db";

    private Context context;

    private Map<String, BaseAction> actionMap;

    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * dao
     */
    private AppContentDao appContentDao;

    private ContentManager() {
        actionMap = new HashMap<>();
        context = MyApplication.getContext();
    }

    private static final class Buidler {
        private static final ContentManager manager = new ContentManager();
    }

    public static ContentManager getInstance() {
        return Buidler.manager;
    }

    public void initDB() {
        mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        appContentDao = mDaoSession.getAppContentDao();
        initData();
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        return mHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        return mHelper.getWritableDatabase();
    }

    /**
     * 自动添加或替换
     */
    public void autoAddContent(AppContent appContent) {
        appContentDao.insertOrReplace(appContent);
    }

    /**
     * 添加
     */
    public void addContent(AppContent appContent) {
        if (getContent(appContent.getPackageName()) == null) {
            appContentDao.insert(appContent);
        }
    }

    /**
     * 修改
     */
    public void changeContent(AppContent appContent) {
        appContentDao.update(appContent);
    }

    /**
     * 删除
     */
    public void deleteContent(AppContent appContent) {
        appContentDao.queryBuilder().where(AppContentDao.Properties.PackageName.eq(appContent.getPackageName())).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 根据包名查询
     */
    public AppContent getContent(String packageName) {
        return appContentDao.queryBuilder().where(AppContentDao.Properties.PackageName.eq(packageName)).build().unique();
    }

    /**
     * 获取全部数据
     */
    public List<AppContent> getAllContent() {
        return appContentDao.queryBuilder().list();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //刷宝
        AppContent shuaContent = new AppContent();
        shuaContent.setPackageName("com.jm.video");
        shuaContent.setOpenSelfPackageName("com.jm.video");
        shuaContent.setChangePackageName("com.cashtoutiao");
        shuaContent.setChangeTime(1000 * 60 * 60);
        shuaContent.setAppName("刷宝");
        //惠头条
        AppContent huiContent = new AppContent();
        huiContent.setPackageName("com.cashtoutiao,com.bytedance");
        huiContent.setOpenSelfPackageName("com.cashtoutiao");
        huiContent.setChangeTime(0);
        huiContent.setAppName("惠头条");
        //聚看点
        AppContent juContent = new AppContent();
        juContent.setPackageName("com.xiangzi.jukandian");
        juContent.setOpenSelfPackageName("com.xiangzi.jukandian");
        juContent.setChangeTime(0);
        juContent.setAppName("聚看点");
        //点点新闻
        AppContent dianContent = new AppContent();
        dianContent.setPackageName("com.yingliang.clicknews");
        dianContent.setOpenSelfPackageName("com.yingliang.clicknews");
        dianContent.setChangeTime(0);
        dianContent.setAppName("点点新闻");
        //赚钱阅有钱
        AppContent zhuanContent = new AppContent();
        zhuanContent.setPackageName("com.yj.yueyouqian");
        zhuanContent.setOpenSelfPackageName("com.yj.yueyouqian");
        zhuanContent.setChangeTime(0);
        zhuanContent.setAppName("赚钱阅有钱");
        //东方头条
        AppContent dongContent = new AppContent();
        dongContent.setPackageName("com.songheng.eastnews");
        dongContent.setOpenSelfPackageName("com.songheng.eastnews");
        dongContent.setChangeTime(0);
        dongContent.setAppName("东方头条");
        //牛牛头条
        AppContent niuContent = new AppContent();
        niuContent.setPackageName("com.huolea.bull");
        niuContent.setOpenSelfPackageName("com.huolea.bull");
        niuContent.setChangeTime(0);
        niuContent.setAppName("牛牛头条");
        //花生头条
        AppContent huaContent = new AppContent();
        huaContent.setPackageName("com.xcm.huasheng");
        huaContent.setOpenSelfPackageName("com.xcm.huasheng");
        huaContent.setChangeTime(0);
        huaContent.setAppName("花生头条");

        addContent(shuaContent);
        addContent(huiContent);
        addContent(juContent);
        addContent(dianContent);
        addContent(zhuanContent);
        addContent(dongContent);
        addContent(niuContent);
        addContent(huaContent);

        actionMap.put(shuaContent.getPackageName(), new ShuaAction(shuaContent));
        actionMap.put(huiContent.getPackageName(), new HuiAction(huiContent));
        actionMap.put(juContent.getPackageName(), new KanAction(juContent));
        actionMap.put(dianContent.getPackageName(), new DianAction(dianContent));
        actionMap.put(zhuanContent.getPackageName(), new ZhuanAction(zhuanContent));
        actionMap.put(dongContent.getPackageName(), new DongAction(dongContent));
        actionMap.put(niuContent.getPackageName(), new NiuAction(niuContent));
        actionMap.put(huaContent.getPackageName(), new HuaAction(huaContent));
    }

    public BaseAction getAction(AppContent content) {
        return actionMap.get(content.getPackageName());
    }

}