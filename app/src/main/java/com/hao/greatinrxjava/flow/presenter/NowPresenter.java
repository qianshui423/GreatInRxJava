package com.hao.greatinrxjava.flow.presenter;

import com.hao.greatinrxjava.flow.api.SK;
import com.lucky.baselib.base.BasePresenter;
import com.hao.greatinrxjava.flow.model.Engine;
import com.hao.greatinrxjava.flow.model.Flow;
import com.hao.greatinrxjava.flow.model.bean.Now;

import java.util.ArrayList;

/**
 * Created by liuxuehao on 16/12/26.
 */

public class NowPresenter extends BasePresenter {

    @Override
    protected void bindApiLifecycle() {
        super.bindApiLifecycle();
        addApiKeys(new ArrayList<String>() {{
            add(SK.NOW);
        }});
    }

    public void dispatchNow(String city, String key, String lang) {
        super.requestData();
        Engine.getInstance().engineNow(city, key, lang, new Flow<Now>() {
            @Override
            public void flowData(Now data) {
                getView().display(data, 0);
            }
        });
    }
}
