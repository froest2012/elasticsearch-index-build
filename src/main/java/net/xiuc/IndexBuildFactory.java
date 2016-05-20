package net.xiuc;

import com.google.common.collect.Lists;
import com.tqmall.search.dal.dao.SearchDao;
import com.tqmall.search.dal.exception.DaoException;
import net.xiuc.build.DbTranslate;
import net.xiuc.build.Table;
import net.xiuc.build.Translate;
import net.xiuc.test.Order;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * 索引构建入口
 * <p/>
 * Created by 秀川 on 16/4/24.
 */
public enum IndexBuildFactory {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(IndexBuildFactory.class);

    public IndexBuildFactory getInstance() {
        return INSTANCE;
    }

    public <T> void build(String indexAliasName, Class<T> clazz, List<Map<String, Object>> docs) {
        //解析clazz, 分析出主从关系以及数据库关系
        Translate translate = new DbTranslate();
        List<Table> tableList = translate.translate(Order.class);
        Table mainTable = translate.getMainTable();
        //从数据库获取数据并建成doc
        //setting设置
        //创建索引并设置别名

        //索引管理
    }

    private List<Map<String, Object>> queryIndexMap(List<Table> tableList, Table table, SearchDao dao) {
        List<Map<String, Object>> docList = Lists.newArrayList();

        TreeSet<Map<String, Object>> treeSet;
        int dbPageSize = 100000, start = 0;
        try {
            do {
                treeSet = dao.queryTreeSet(table.sqlBuild(null, start, dbPageSize), new Comparator<Map<String, Object>>() {
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        return 0;
                    }
                });
                for(Table t : tableList) {
                    if(t.equals(table)){
                        List<Number> primaryList = Lists.newArrayList();
                        for(Map<String, Object> map : treeSet){
                            if(!StringUtils.isEmpty(String.valueOf(map.get(t.getPrimary())))) {
                                primaryList.add(Long.valueOf(String.valueOf(map.get(t.getPrimary()))));
                            }
                        }
                        //获取字表数据
                        //把字表数据放到主表中
                    }
                }

            } while (dbPageSize == treeSet.size());
        }catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<Map<String, Object>> queryMap(Table table, List<Number> primaryList, Integer start,
                                                  Integer limit, Class<T> clazz, SearchDao dao) {
        String sql = table.sqlBuild(primaryList, start, limit);
        try {
            return dao.queryMap(sql);
        } catch (DaoException e) {
            logger.error("表:" + table.getSchema() + "." + table.getName() + "获取数据失败", e);
        }
        return null;
    }
}
