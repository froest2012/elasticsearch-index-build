package net.xiuc.util;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.Strings;

/**
 * Created by 秀川 on 16/4/27.
 */
public enum Tools {
    INSTANCE;

    public Tools getInstance() {
        return INSTANCE;
    }

    public String toUnderScoreCase(String field, String fieldName) {
        return StringUtils.isEmpty(field) ? Strings.toUnderscoreCase(fieldName) : field;
    }
}
