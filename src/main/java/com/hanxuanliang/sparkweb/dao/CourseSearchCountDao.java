package com.hanxuanliang.sparkweb.dao;

import com.hanxuanliang.sparkweb.domain.CourseClickCount;
import com.hanxuanliang.sparkweb.util.HBaseUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 22545
 */
@Repository
public class CourseSearchCountDao {
    private Map<String,Long> querySearch(String dayCourse) throws Exception{
        Map<String, Long> map = HBaseUtils.getInstance().queryByCondition("logcourse_search_clickcount", dayCourse);
        Map<String,Long> totalMap=new HashMap<>(16);

        for (Map.Entry<String, Long> entry : map.entrySet()) {
            int index = entry.getKey().lastIndexOf("_");
            String name = entry.getKey().substring(9, index);
            Long value = entry.getValue();
            if (totalMap.entrySet() != null) {
                if (totalMap.containsKey(name)) {
                    Long v = totalMap.get(name) + value;
                    totalMap.put(name, v);
                } else {
                    totalMap.put(name, value);
                }
            } else {
                totalMap.put(name, value);
            }
        }
        return totalMap;
    }

    public List<CourseClickCount> query(String dayCourse) throws Exception{
        List<CourseClickCount> list=new ArrayList<>();
        Map<String, Long> map = querySearch(dayCourse);
        return HBaseUtils.getCourseClickCounts(list, map);
    }

}
