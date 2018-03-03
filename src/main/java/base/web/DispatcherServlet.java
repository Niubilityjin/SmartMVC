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
		//��ȡ�����ļ���λ�ú��ļ���
		String filename=getServletConfig().getInitParameter("config");//web.xml�������ļ����õ�name ָ�������ļ�context��ַ
		System.out.println("filename:"+filename);
		/*
		 * ���������ļ�,�������ļ�������Controllerʵ����,���ҽ���Щʵ����ŵ�һ����������(List)
		 */
		SAXReader sax=new SAXReader();
		InputStream in=getClass().getClassLoader().getResourceAsStream(filename);
		try {
			//��ȡ�����ļ�
			Document doc=sax.read(in);
			//��ø��ڵ�
			Element root=doc.getRootElement();
			//��ø��ڵ��������е��ӽڵ�
			List<Element> elements=root.elements();
			for(Element ele:elements){
				//���beanԪ�ص�class����ֵ
				String className=ele.attributeValue("class");
				System.out.println("className:"+className);
				//����java���佫��ʵ����,���ҽ���Щʵ����ŵ�list����
				Object obj=Class.forName(className).newInstance();
				beans.add(obj);
				
			}
			System.out.println("beans:"+beans);
			//������·���봦������������Ӧ��ϵ����HandlerMapping������
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
		//��ȡ������Դ·��
		String uri=request.getRequestURI();
		
		
		System.out.println("������Դ·��Ϊ:::"+uri);
		//��ȡ������Դ·����һ����
		String contextPath=request.getContextPath();
		System.out.println("contextpath:"+contextPath);
		String path=uri.substring(contextPath.length());
		System.out.println("��ȡ���·��Ϊ:::"+path);
		//��������·��(path),�ҵ���Ӧ�Ĵ�����������
		Handler handler=hm.getHandler(path);
		
		System.out.println("handler:"+handler);
		
		Object obj=handler.getObj();
		Method mh=handler.getMh();
		//returnVal:��ͼ��
		Object returnVal=null;
		try {
			returnVal=mh.invoke(obj);
			//��ViewResolver������ͼ�� �ڴ˽��շ���returnֵ
			vr.process(returnVal,contextPath,request,response);
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
