package com.xaeport.crossborder.convert411.parser;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/18.
 */
@Component
public class ParserHolder {
    private Map<String, BaseParser> map;

    @PostConstruct
    public void initParser() {
        map = new HashMap<>();
        map.put("exp302", new Exp302Parser());
        map.put("exp310", new Exp310Parser());
        map.put("exp312", new Exp312Parser());
    }

    public BaseParser getParser(String parserType) {
        BaseParser baseParser = null;
        if (!CollectionUtils.isEmpty(map)) {
            baseParser = map.get(parserType);
        }
        return baseParser;
    }

    public void setParser(String psrserType, BaseParser parser) {
        if (!CollectionUtils.isEmpty(map)) {
            map.put(psrserType, parser);
        }
    }

}
