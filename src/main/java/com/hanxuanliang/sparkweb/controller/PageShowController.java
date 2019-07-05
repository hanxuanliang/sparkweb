package com.hanxuanliang.sparkweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/charts")
public class PageShowController {

    @RequestMapping("/clickcount")
    public ModelAndView echarts() {
        return new ModelAndView("click");
    }

    @RequestMapping("/searchcount")
    public ModelAndView search() {
        return new ModelAndView("search");
    }
}
