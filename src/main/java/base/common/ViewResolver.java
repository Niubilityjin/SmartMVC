package base.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 视图解析器类
 * 处理试图名
 */
public class ViewResolver {
	public void process(Object returnVal,String contextPath,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		/*
		 * 看视图名是不是以"redirect:"开头.如果是,则重定向;否则转发
		 */
	String viewName=returnVal.toString();
	
	if(viewName.startsWith("redirect")){
		//是以redirect开头,则重定向
		//截取重定向地址
		String path2=contextPath+"/"+viewName.substring("redirect:".length());
		response.sendRedirect(path2);
	}else{
		//不以"redirect:"开头则转发
		//jsp与试图名的对应关系,定义为:"/WEB-INF/"+试图名+".jsp"
		String path1="/WEB-INF/"+returnVal.toString()+".jsp";
		System.out.println(path1);
		request.getRequestDispatcher(path1).forward(request, response);
	}
	}
}
