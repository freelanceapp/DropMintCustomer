package com.apporio.johnlaundry;

import android.content.Context;
import android.support.multidex.MultiDex;
;import com.orm.SugarContext;

/**
 * Created by apporio5 on 04-07-2016.
 */
public class MyApplication extends android.support.multidex.MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

        // register with Active Android
        //ActiveAndroid.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
