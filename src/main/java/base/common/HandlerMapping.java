package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ӳ�䴦������:
 *  ��������·���봦�����������������Ķ�Ӧ��ϵ.
 *  ���� ����·����"/hello.do",��HelloController��hello����
 *  
 * @author UID
 *
 */
public class HandlerMapping {
	private Map<String,Handler> handlerMap=new HashMap<String,Handler>();
	/**
	 * objs:��������д�������ʵ��
	 * @param objs
	 */
	
	/*
	 * ��������·������Handler
	 * ע:Handler��װ�˴������Ͷ�Ӧ�ķ���
	 */
	public Handler getHandler(String path){
		return handlerMap.get(path);
	}
	public void process(List<Object> objs){
		
		for(Object obj:objs){
			//���class����
			Class clazz=obj.getClass();
			Method[] methods=clazz.getDeclaredMethods();
			/*
			 * ��鷽��ǰ����û��@RequestMapping,�����,�ٽ�������·���봦���������Ķ�Ӧ��ϵ
			 */
			for(Method mh:methods){
				RequestMapping rm=mh.getAnnotation(RequestMapping.class);
				if(rm==null){
					//�÷��������������������,����
					continue;
				}
				//��ȡע���ϵ�ֵ(������·��)
				String path=rm.value();
				//������·����Ϊkey,��Handler��Ϊvalue,��ӽ�map
				handlerMap.put(path, new Handler(obj,mh));
				
			}
		}
		System.out.println("handlerMap:  "+handlerMap);
	}
}
