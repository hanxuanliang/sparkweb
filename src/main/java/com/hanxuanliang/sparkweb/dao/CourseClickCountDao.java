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
public class CourseClickCountDao {

    public List<CourseClickCount> queryMap(String dayCourse) throws Exception{
        List<CourseClickCount> list = new ArrayList<>();
        Map<String, Long> map = HBaseUtils.getInstance().queryByCondition("logcourse_clickcount", dayCourse);

        return HBaseUtils.getCourseClickCounts(list, map);
    }
}
