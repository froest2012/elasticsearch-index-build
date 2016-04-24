package net.xiuc;

/**
 * 索引构建入口
 *
 * Created by 秀川 on 16/4/24.
 */
public enum IndexBuildFactory {

    INSTANCE;

    public IndexBuildFactory getInstance(){
        return INSTANCE;
    }

    public static <T> void indexBuild(Class<T> clazz){

    }
}
