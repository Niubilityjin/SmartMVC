package base.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Handler;
import base.common.HandlerMapping;
import base.common.ViewResolver;


public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List<Object> beans=new ArrayList<Object>();
	private HandlerMapping hm;
	private ViewResolver vr;
	@Override
	public void init() throws ServletException {
		//读取配置文件的位置和文件名
		String filename=getServletConfig().getInitParameter("config");//web.xml中配置文件设置的name 指向配置文件context地址
		System.out.println("filename:"+filename);
		/*
		 * 解析配置文件,将配置文件中所有Controller实例化,并且将这些实例存放到一个集合里面(List)
		 */
		SAXReader sax=new SAXReader();
		InputStream in=getClass().getClassLoader().getResourceAsStream(filename);
		try {
			//读取配置文件
			Document doc=sax.read(in);
			//获得根节点
			Element root=doc.getRootElement();
			//获得根节点下面所有的子节点
			List<Element> elements=root.elements();
			for(Element ele:elements){
				//获得bean元素的class属性值
				String className=ele.attributeValue("class");
				System.out.println("className:"+className);
				//利用java反射将类实例化,并且将这些实例存放到list里面
				Object obj=Class.forName(className).newInstance();
				beans.add(obj);
				
			}
			System.out.println("beans:"+beans);
			//将请求路径与处理器及方法对应关系交给HandlerMapping来处理
			hm=new HandlerMapping();
			hm.process(beans);
			vr=new ViewResolver();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//读取请求资源路径
		String uri=request.getRequestURI();
		
		
		System.out.println("请求资源路径为:::"+uri);
		//截取请求资源路径的一部分
		String contextPath=request.getContextPath();
		System.out.println("contextpath:"+contextPath);
		String path=uri.substring(contextPath.length());
		System.out.println("截取后的路径为:::"+path);
		//依据请求路径(path),找到对应的处理器及方法
		Handler handler=hm.getHandler(path);
		
		System.out.println("handler:"+handler);
		
		Object obj=handler.getObj();
		Method mh=handler.getMh();
		//returnVal:试图名
		Object returnVal=null;
		try {
			returnVal=mh.invoke(obj);
			//由ViewResolver处理视图名 在此接收方法return值
			vr.process(returnVal,contextPath,request,response);
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
