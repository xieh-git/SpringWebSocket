package com.xieh.websocket.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 测试的controller
 * @author admin
 *
 */
@Controller
@RequestMapping("demo")
public class DemoController {	
	@RequestMapping("/{path}")
	public String toPage(@PathVariable("path")String path) {
		return "demo/" + path;
	}

}
