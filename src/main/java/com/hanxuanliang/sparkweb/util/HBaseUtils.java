package com.hanxuanliang.sparkweb.util;

import com.hanxuanliang.sparkweb.domain.CourseClickCount;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 22545
 */
@Slf4j
public class HBaseUtils {

    private HBaseAdmin admin = null;
    private Configuration configuration = null;

    private HBaseUtils(){
        configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "hadoop000:2181");
        configuration.set("hbase.rootdir", "hdfs://hadoop000:8020/hbase");

        try {
            admin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HBaseUtils instance = null;

    public static synchronized HBaseUtils getInstance()  {
        if(instance == null) { instance = new HBaseUtils(); }
        return instance;
    }

    public HTable getTable(String tableName) {
        HTable table = null;
        try {
            table = new HTable(configuration, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    public Map<String, Long> queryByCondition(String tableName, String condition) throws Exception {
        Map<String, Long> map = new HashMap<>(16);
        HTable table = getTable(tableName);
        String cf = "info";
        String qulifier = "click_count";
        Scan scan = new Scan();
        scan.setFilter(new PrefixFilter(Bytes.toBytes(condition)));
        ResultScanner rs = table.getScanner(scan);

        Iterator<Result> resultIterator = rs.iterator();
        while (resultIterator.hasNext()) {
            Result result = resultIterator.next();
            String row = Bytes.toString(result.getRow());
            Cell clickCount = result.getColumnLatestCell(Bytes.toBytes(cf), Bytes.toBytes(qulifier));
            Long name = Bytes.toLong(CellUtil.cloneValue(clickCount));
            map.put(row, name);
        }
        return map;
    }

    public static List<CourseClickCount> getCourseClickCounts(List<CourseClickCount> list, Map<String, Long> map) {
        for(Map.Entry<String,Long> m:map.entrySet()) {
            CourseClickCount course = new CourseClickCount();
            course.setName(m.getKey());
            course.setValue(m.getValue());
            list.add(course);
        }
        return list;
    }
}
