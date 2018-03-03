package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 映射处理器类:
 *  建立请求路径与处理器及处理器方法的对应关系.
 *  比如 请求路径是"/hello.do",则HelloController的hello方法
 *  
 * @author UID
 *
 */
public class HandlerMapping {
	private Map<String,Handler> handlerMap=new HashMap<String,Handler>();
	/**
	 * objs:存放了所有处理器的实例
	 * @param objs
	 */
	
	/*
	 * 依据请求路径返回Handler
	 * 注:Handler封装了处理器和对应的方法
	 */
	public Handler getHandler(String path){
		return handlerMap.get(path);
	}
	public void process(List<Object> objs){
		
		for(Object obj:objs){
			//获得class对象
			Class clazz=obj.getClass();
			Method[] methods=clazz.getDeclaredMethods();
			/*
			 * 检查方法前面右没有@RequestMapping,如果有,再建立请求路径与处理器方法的对应关系
			 */
			for(Method mh:methods){
				RequestMapping rm=mh.getAnnotation(RequestMapping.class);
				if(rm==null){
					//该方法不是用来处理请求的,跳过
					continue;
				}
				//读取注解上的值(即请求路径)
				String path=rm.value();
				//把请求路径作为key,把Handler作为value,添加进map
				handlerMap.put(path, new Handler(obj,mh));
				
			}
		}
		System.out.println("handlerMap:  "+handlerMap);
	}
}
