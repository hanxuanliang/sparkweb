package com.hanxuanliang.sparkweb.controller;

import com.hanxuanliang.sparkweb.dao.CourseClickCountDao;
import com.hanxuanliang.sparkweb.dao.CourseSearchCountDao;
import com.hanxuanliang.sparkweb.domain.CourseClickCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 22545
 */
@RestController
@RequestMapping("/course")
public class CourseClickCountController {

    private static Map<String, String> courses = new HashMap<>();
    private static Map<String, String> searchSource = new HashMap<>();
    private static final String DAY ="20190705";


    static {
        courses.put("112", "以慕课网日志分析为例 进入大数据Spark SQL的世界");
        courses.put("153", "Spark Streaming实时流处理项目实战");
        courses.put("249", "Python3实战Spark大数据分析及调度");
        courses.put("215", "学习Scala进击\r\n" +
                "大数据Spark生态圈");
        courses.put("172", "基于Storm构建实时热力分布项目实战");
        courses.put("146", "Node.js入门到企业Web开发中的应用");
        courses.put("145", "深度学习之神经网络核心原理与算法");
        courses.put("131", "Python前后端分离开发\r\n" +
                "Vue+Django REST framework实战");
        courses.put("128", "10小时入门大数据");
        courses.put("217", "Spring Boot仿抖音短视频\r\n" +
                "小程序开发 全栈式实战项目");
        courses.put("361", "Zookeeper源码分析");
        courses.put("368", "强力Django + 杀手级xadmin开发在线教育网站");
        courses.put("358", "Spring Cloud Alibaba微服务从入门到进阶");
        courses.put("355", "编程必备基础知识\r\n" +
                "计算机组成原理+操作系统+计算机网络");
        courses.put("353", "阿里新零售数据库设计与实战");
        courses.put("359", "Java Web自动化测试\r\n" +
                "Selenium基础到企业实际应用");
        courses.put("354", "Node.js开发仿知乎服务端 深入理解RESTful API");
        courses.put("310", "基于Spring Cloud微服务架构 广告系统设计与实现");
        courses.put("336", "Angular8开发拼多多webapp 从基础到项目实战");
        courses.put("341", "精讲Elastic-job + Quartz实现企业级定时任务");
        courses.put("347", "全流程开发 GO实战电商网站高并发秒杀系统");
        courses.put("344", "Google老师亲授\r\n" +
                "TensorFlow2.0 入门到进阶");
        courses.put("293", "商业级支付宝小程序入门与实战");

        searchSource.put("cn.bing.com", "微软Bing");
        searchSource.put("www.google.com", "谷歌");
        searchSource.put("search.yahoo.com", "雅虎");
        searchSource.put("www.baidu.com", "百度");
        searchSource.put("www.sogou.com", "搜狗");
        searchSource.put("www.so.com", "搜狗");
        searchSource.put("so.m.sm.cn", "UC引擎");
    }

    @Autowired
    CourseClickCountDao courseClickCountDao;

    @Autowired
    CourseSearchCountDao courseSearchCountDao;

    @RequestMapping("/getcourseCount")
    @ResponseBody
    public List<CourseClickCount> queryCourse() throws Exception {
        List<CourseClickCount> list = courseClickCountDao.queryMap(DAY);
        for (CourseClickCount course : list) {
            course.setName(courses.get(course.getName().substring(9)));
        }
        return list;
    }

    @RequestMapping("/getcourseSearch")
    @ResponseBody
    public List<CourseClickCount> querySearch() throws Exception {
        List<CourseClickCount> list = courseSearchCountDao.query(DAY);
        for (CourseClickCount course : list) {
            course.setName(searchSource.get(course.getName()));
        }
        return list;
    }

}
