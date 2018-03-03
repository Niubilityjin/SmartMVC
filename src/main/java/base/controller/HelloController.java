package base.controller;

import base.common.RequestMapping;

public class HelloController {
	public HelloController(){
		System.out.println("����hellocontroller�Ĺ�����");
	}
	/*
	 * DispatcherServlet�յ����������Controller��������������.(����,����hello����)
	 * �÷����ķ���ֵ����ͼ��(��ͼ������ViewResolver�������ɶ�Ӧ��jsp)
	 * 
	 * @RequestMapping������:
	 * ��������·���봦���������Ķ�Ӧ��ϵ
	 */
	@RequestMapping("/hello.do")
	public String hello(){
		System.out.println("����hello����");
		return "hello";
		
	}
	
	@RequestMapping("/hello2.do")
	public String hello2(){
		System.out.println("����HelloController��hello2����");
		return "redirect:balabala.do";
	}
	@RequestMapping("/balabala.do")
	public String balabala(){
		System.out.println("�����������hellocontrol��balabala");
		return "hello2";
	}
}
