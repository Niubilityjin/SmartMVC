package base.controller;

import base.common.RequestMapping;

public class HelloController {
	public HelloController(){
		System.out.println("这是hellocontroller的构造器");
	}
	/*
	 * DispatcherServlet收到请求后会调用Controller方法来处理请求.(比如,调用hello方法)
	 * 该方法的返回值是视图名(视图名会由ViewResolver来解析成对应的jsp)
	 * 
	 * @RequestMapping的作用:
	 * 配置请求路径与处理器方法的对应关系
	 */
	@RequestMapping("/hello.do")
	public String hello(){
		System.out.println("这是hello方法");
		return "hello";
		
	}
	
	@RequestMapping("/hello2.do")
	public String hello2(){
		System.out.println("这是HelloController的hello2方法");
		return "redirect:balabala.do";
	}
	@RequestMapping("/balabala.do")
	public String balabala(){
		System.out.println("这里他娘的是hellocontrol的balabala");
		return "hello2";
	}
}
