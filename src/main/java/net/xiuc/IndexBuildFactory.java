package net.xiuc;

import com.tqmall.search.dal.dao.SearchDao;
import net.xiuc.build.DbTranslate;
import net.xiuc.build.MapConvert;
import net.xiuc.build.Translate;
import net.xiuc.test.Customer;
import net.xiuc.test.Order;

import java.util.Map;

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

    public <T> void build(String indexAliasName, Class<T> clazz, SearchDao... daos){
        //解析clazz, 分析出主从关系以及数据库关系
        Translate translate = new DbTranslate();
        translate.translate(Order.class);
        //从数据库获取数据并建成doc
        MapConvert convert = new MapConvert();
        Map<String, Object> docs = convert.convert(translate.getTableMap(), translate.getSqlMap(), daos);
        //setting设置
        //创建索引并设置别名

        //索引管理
    }
}
