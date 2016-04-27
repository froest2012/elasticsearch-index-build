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

    public <T> void build(String indexAliasName, Class<T> clazz){
        //解析clazz, 分析出主从关系以及数据库关系

        //从数据库获取数据并建成doc
        //创建索引并设置别名
        //索引管理
    }
}
