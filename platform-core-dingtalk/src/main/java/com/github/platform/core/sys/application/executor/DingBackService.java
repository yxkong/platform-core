package com.github.platform.core.sys.application.executor;

import com.alibaba.fastjson.JSONObject;

public interface DingBackService {
	
	void eventDeal(JSONObject eventJson);

}
