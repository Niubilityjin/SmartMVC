package base.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * ��ͼ��������
 * ������ͼ��
 */
public class ViewResolver {
	public void process(Object returnVal,String contextPath,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		/*
		 * ����ͼ���ǲ�����"redirect:"��ͷ.�����,���ض���;����ת��
		 */
	String viewName=returnVal.toString();
	
	if(viewName.startsWith("redirect")){
		//����redirect��ͷ,���ض���
		//��ȡ�ض����ַ
		String path2=contextPath+"/"+viewName.substring("redirect:".length());
		response.sendRedirect(path2);
	}else{
		//����"redirect:"��ͷ��ת��
		//jsp����ͼ���Ķ�Ӧ��ϵ,����Ϊ:"/WEB-INF/"+��ͼ��+".jsp"
		String path1="/WEB-INF/"+returnVal.toString()+".jsp";
		System.out.println(path1);
		request.getRequestDispatcher(path1).forward(request, response);
	}
	}
}
