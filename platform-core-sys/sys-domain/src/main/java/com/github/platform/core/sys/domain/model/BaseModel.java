package com.github.platform.core.sys.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yxkong
 * @date 2021-06-10 16:44
 */
public class BaseModel {

    /**
     * 轻量级事件
     */
    private List<Object> lightEvents = new ArrayList<>(2);

    private Map<String, Object> weightEvents = new HashMap<>(2);

    public void addLightEvent(Object event) {
        lightEvents.add(event);
    }

    public List<Object> getLightEvents() {
        return lightEvents;
    }

    public void clearLight() {
        lightEvents.clear();
    }

    public void addWeightEvent(String topic, Object event) {
        weightEvents.put(topic, event);
    }

    public Map<String, Object> getWeightEvents() {
        return weightEvents;
    }

    public void clear() {
        weightEvents.clear();
    }
}
