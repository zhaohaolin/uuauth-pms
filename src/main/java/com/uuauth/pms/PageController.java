package com.uuauth.pms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uuauth.pms.model.Power;
import com.uuauth.pms.model.Role;

public class PageController {
	
	public static boolean	debug	= false;
	
	public final static void power(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		if (debug) {
			req.getSession().getServletContext()
					.getRequestDispatcher("/power.jsp").forward(req, resp);
			return;
		}
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		ServletContext application = session.getServletContext();
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		
		String opt = req.getParameter("opt");
		String title = null;
		String form_action = null;
		String but_value = null;
		if ("add".equals(opt)) {
			title = "添加权限";
			form_action = req.getRequestURL().toString()
					+ "?action=addPower&opt=add";
			but_value = " 添 加 ";
		} else if ("edit".equals(opt)) {
			title = "编辑权限";
			form_action = req.getRequestURL().toString()
					+ "?action=editPower&opt=edit";
			but_value = " 保 存 ";
		}
		
		out.write("\r\n");
		out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
		out.write("<html>\r\n");
		out.write("\t<head>\r\n");
		out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
		out.write("\t\t<title>");
		out.print(title);
		out.write("</title>\r\n");
		out.write("\t\t<link rel=\"stylesheet\" type=\"text/css\"\r\n");
		out.write("\t\t\thref=\"");
		out.print(req.getRequestURL().toString());
		out.write("?action=css\">\r\n");
		out.write("\t</head>\r\n");
		out.write("\t<body>\r\n");
		out.write("\t\t<DIV class=version>\r\n");
		out.write("\t\t\t<br>\r\n");
		out.write("\t\t\t 通用权限管理\r\n");
		out.write("\t\t\t");
		out.print(PmsServlet.version);
		out.write("\r\n");
		out.write("\t\t</DIV>\r\n");
		out.write("\t\t<div align=\"right\" style=\"width: 80%\">\r\n");
		out.write("\t\t\t当前系统：\r\n");
		out.write("\t\t\t<font color=\"#006600\">");
		out.print(application.getServletContextName());
		out.write("</font>\r\n");
		out.write("\t\t</div>\r\n");
		out.write("\t\t<div>\r\n");
		out.write("\t\t<UL>\r\n");
		out.write("\r\n");
		out.write("\t\t\t<LI class=inactive>\r\n");
		out.write("\t\t\t\t<A class=quiet href=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=roleList\">角色列表</A>\r\n");
		out.write("\t\t\t</LI>\r\n");
		out.write("\r\n");
		out.write("\t\t\t<LI class=active>\r\n");
		out.write("\t\t\t\t<A class=quiet href=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=powerList\">权限列表</A>\r\n");
		out.write("\t\t\t</LI>\r\n");
		out.write("\t\t</UL>\r\n");
		out.write("\t\t</div>\r\n");
		out.write("\t\t");
		
		Power power = (Power) req.getAttribute("power");
		
		out.write("\r\n");
		out.write("\t\t<div>\r\n");
		out.write("\t\t<form action=\"");
		out.print(form_action);
		out.write("\" method=\"post\">\r\n");
		out.write("\t\t\t<table class=\"data\" width=\"100%\">\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th width=\"150\">\r\n");
		out.write("\t\t\t\t\t\t权限编号:\r\n");
		out.write("\t\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t<input name=\"id\" value=\"");
		out.print(power.getId());
		out.write("\">\r\n");
		out.write("\t\t\t\t\t\t");
		
		if ("edit".equals(opt)) {
			
			out.write("\r\n");
			out.write("\t\t\t\t\t\t");
			
			Object oldId = req.getAttribute("oldId");
			if (oldId != null) {
				
				out.write("\r\n");
				out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"oldId\" value=\"");
				out.print(oldId);
				out.write("\">\r\n");
				out.write("\t\t\t\t\t\t");
				
			}
		}
		
		out.write("\r\n");
		out.write("\r\n");
		out.write("\t\t\t\t\t\t<label class=\"error\">\r\n");
		out.write("\t\t\t\t\t\t\t");
		
		Object idErr = req.getAttribute("idErr");
		if (idErr != null) {
			out.write(idErr.toString());
		}
		
		out.write("\r\n");
		out.write("\t\t\t\t\t\t</label>\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t\t权限名称:\r\n");
		out.write("\t\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t<input name=\"name\" value=\"");
		out.print(power.getName());
		out.write("\">\r\n");
		out.write("\t\t\t\t\t\t<label class=\"error\">\r\n");
		out.write("\t\t\t\t\t\t\t");
		
		Object nameErr = req.getAttribute("nameErr");
		if (nameErr != null) {
			out.write(nameErr.toString());
		}
		
		out.write("\r\n");
		out.write("\t\t\t\t\t\t</label>\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t\t权限正则式:\r\n");
		out.write("\t\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t<textarea rows=\"5\" name=\"power\">");
		out.print(power.getPower());
		out.write("</textarea>\r\n");
		out.write("\t\t\t\t\t\t<label class=\"error\">\r\n");
		out.write("\t\t\t\t\t\t\t");
		
		Object powerErr = req.getAttribute("powerErr");
		if (powerErr != null) {
			out.write(powerErr.toString());
		}
		
		out.write("\r\n");
		out.write("\t\t\t\t\t\t</label>\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t\t菜单链接:\r\n");
		out.write("\t\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t<input name=\"url\" value=\"");
		out.print(power.getUrl());
		out.write("\">\r\n");
		out.write("\t\t\t\t\t\t<label class=\"error\">\r\n");
		out.write("\t\t\t\t\t\t\t");
		
		Object urlErr = req.getAttribute("urlErr");
		if (urlErr != null) {
			out.write(urlErr.toString());
		}
		
		out.write("\r\n");
		out.write("\t\t\t\t\t\t</label>\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t\t权限描述:\r\n");
		out.write("\t\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t<textarea rows=\"5\" name=\"description\">");
		out.print(power.getDescription());
		out.write("</textarea>\r\n");
		out.write("\t\t\t\t\t\t<label class=\"error\">\r\n");
		out.write("\t\t\t\t\t\t\t");
		
		Object descriptionErr = req.getAttribute("descriptionErr");
		if (descriptionErr != null) {
			out.write(descriptionErr.toString());
		}
		
		out.write("\r\n");
		out.write("\t\t\t\t\t\t</label>\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th></th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t<div style=\"padding-left: 160px;\">\r\n");
		out.write("\t\t\t\t\t\t\t<input id=\"act\" type=\"submit\" value=\"");
		out.print(but_value);
		out.write("\"\r\n");
		out.write("\t\t\t\t\t\t\t\tstyle=\"width: 70px;\">\r\n");
		out.write("\t\t\t\t\t\t\t<input id=\"back\" type=\"button\" value=\" 返 回 \" style=\"width: 70px;\"\r\n");
		out.write("\t\t\t\t\t\t\t\tonclick=\"location.href='");
		out.print(req.getRequestURL().toString());
		out.write("?action=powerList'\">\r\n");
		out.write("\t\t\t\t\t\t</div>\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t</table>\r\n");
		out.write("\t\t</form>\r\n");
		out.write("\t\t</div>\r\n");
		out.write("\t</body>\r\n");
		out.write("</html>");
	}
	
	@SuppressWarnings("unchecked")
	public final static void powerList(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		if (debug) {
			req.getSession().getServletContext()
					.getRequestDispatcher("/powerList.jsp").forward(req, resp);
			return;
		}
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		ServletContext application = session.getServletContext();
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		
		out.write("\r\n");
		out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
		out.write("<html>\r\n");
		out.write("\t<head>\r\n");
		out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
		out.write("\t\t<title>权限列表</title>\r\n");
		out.write("\t\t<link rel=\"stylesheet\" type=\"text/css\"\r\n");
		out.write("\t\t\thref=\"");
		out.print(req.getRequestURL().toString());
		out.write("?action=css\">\r\n");
		out.write("\t</head>\r\n");
		out.write("\t<body>\r\n");
		out.write("\t\t<DIV class=version>\r\n");
		out.write("\t\t\t<br>\r\n");
		out.write("\t\t\t 通用权限管理\r\n");
		out.write("\t\t\t");
		out.print(PmsServlet.version);
		out.write("\r\n");
		out.write("\t\t</DIV>\r\n");
		out.write("\t\t<div align=\"right\" style=\"width: 80%\">\r\n");
		out.write("\t\t\t当前系统：\r\n");
		out.write("\t\t\t<font color=\"#006600\">");
		out.print(application.getServletContextName());
		out.write("</font>\r\n");
		out.write("\t\t</div>\r\n");
		out.write("\t\t");
		
		Object optMsg = session.getAttribute("optMsg");
		if (optMsg != null) {
			session.removeAttribute("optMsg");
			
			out.write("\r\n");
			out.write("\t\t<div class='msg_success'>\r\n");
			out.write("\t\t\t");
			out.print(optMsg.toString());
			out.write("\r\n");
			out.write("\t\t</div>\r\n");
			out.write("\t\t<br />\r\n");
			out.write("\t\t");
			
		}
		
		out.write("\r\n");
		out.write("\t\t<UL>\r\n");
		out.write("\r\n");
		out.write("\t\t\t<LI class=inactive>\r\n");
		out.write("\t\t\t\t<A class=quiet href=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=roleList\">角色列表</A>\r\n");
		out.write("\t\t\t</LI>\r\n");
		out.write("\r\n");
		out.write("\t\t\t<LI class=active>\r\n");
		out.write("\t\t\t\t<A class=quiet href=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=powerList\">权限列表</A>\r\n");
		out.write("\t\t\t</LI>\r\n");
		out.write("\r\n");
		out.write("\t\t</UL>\r\n");
		out.write("\r\n");
		out.write("\t\t<table class=\"tree\" width=\"100%\" border=\"0\" cellpadding=\"0\"\r\n");
		out.write("\t\t\tcellspacing=\"0\">\r\n");
		out.write("\t\t\t<tr height=\"20\">\r\n");
		out.write("\t\t\t\t<th width=\"60\">\r\n");
		out.write("\t\t\t\t\t权限编号\r\n");
		out.write("\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t<th width=\"170\">\r\n");
		out.write("\t\t\t\t\t权限名称\r\n");
		out.write("\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t<th width=\"150\">\r\n");
		out.write("\t\t\t\t\t操作\r\n");
		out.write("\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t</th>\r\n");
		out.write("\t\t\t</tr>\r\n");
		out.write("\t\t\t<tr>\r\n");
		out.write("\t\t\t\t<td colspan=\"4\"></td>\r\n");
		out.write("\t\t\t</tr>\r\n");
		out.write("\t\t\t<tr>\r\n");
		out.write("\t\t\t\t<td></td>\r\n");
		out.write("\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t<img\r\n");
		out.write("\t\t\t\t\t\tsrc=\"");
		out.print(req.getRequestURL().toString());
		out.write("?action=image&name=tree_root.gif\">\r\n");
		out.write("\t\t\t\t\t系统权限\r\n");
		out.write("\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t<a\r\n");
		out.write("\t\t\t\t\t\thref=\"");
		out.print(req.getRequestURL().toString());
		out.write("?action=showAddPower&opt=add&parentId=\">添加子权限</a>\r\n");
		out.write("\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t<td></td>\r\n");
		out.write("\t\t\t</tr>\r\n");
		out.write("\t\t\t");
		
		List<Power> list = (List<Power>) req.getAttribute("list");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Power power = list.get(i);
				
				out.write("\r\n");
				out.write("\t\t\t<tr  onMouseOver=\"this.className='td-list-2'\" onMouseOut=\"this.className=''\">\r\n");
				out.write("\t\t\t\t<td>");
				out.print(power.getId());
				out.write("</td>\r\n");
				out.write("\t\t\t\t<td>\r\n");
				out.write("\t\t\t\t\t");
				
				if (i != list.size() - 1) {
					
					out.write("\r\n");
					out.write("\t\t\t\t\t<img\r\n");
					out.write("\t\t\t\t\t\tsrc=\"");
					out.print(req.getRequestURL().toString());
					out.write("?action=image&name=tree_linemiddle.gif\">\r\n");
					out.write("\t\t\t\t\t");
					
				} else {
					
					out.write("\r\n");
					out.write("\t\t\t\t\t<img\r\n");
					out.write("\t\t\t\t\t\tsrc=\"");
					out.print(req.getRequestURL().toString());
					out.write("?action=image&name=tree_linebottom.gif\">\r\n");
					out.write("\t\t\t\t\t");
					
				}
				
				out.write("\r\n");
				out.write("\t\t\t\t\t");
				
				if (power.getList() == null || power.getList().size() == 0) {
					
					out.write("\r\n");
					out.write("\t\t\t\t\t<img\r\n");
					out.write("\t\t\t\t\t\tsrc=\"");
					out.print(req.getRequestURL().toString());
					out.write("?action=image&name=tree_folder.gif\">\r\n");
					out.write("\t\t\t\t\t");
					
				} else {
					
					out.write("\r\n");
					out.write("\t\t\t\t\t<img\r\n");
					out.write("\t\t\t\t\t\tsrc=\"");
					out.print(req.getRequestURL().toString());
					out.write("?action=image&name=tree_folderopen.gif\">\r\n");
					out.write("\t\t\t\t\t");
					
				}
				
				out.write("\r\n");
				out.write("\t\t\t\t\t<a\r\n");
				out.write("\t\t\t\t\t\thref=\"");
				out.print(req.getRequestURL().toString());
				out.write("?action=showEditPower&opt=edit&id=");
				out.print(power.getId());
				out.write('"');
				out.write('>');
				out.print(power.getName());
				out.write("</a>\r\n");
				out.write("\t\t\t\t</td>\r\n");
				out.write("\t\t\t\t<td>\r\n");
				out.write("\t\t\t\t\t<a\r\n");
				out.write("\t\t\t\t\t\thref=\"javascript:if(confirm('确定要删除此权限及其下所有权限！'))location.href='");
				out.print(req.getRequestURL().toString());
				out.write("?action=delPower&id=");
				out.print(power.getId());
				out.write("';\">删除</a>\r\n");
				out.write("\t\t\t\t\t<a\r\n");
				out.write("\t\t\t\t\t\thref=\"");
				out.print(req.getRequestURL().toString());
				out.write("?action=showAddPower&opt=add&parentId=");
				out.print(power.getId());
				out.write("\">添加子权限</a>\r\n");
				out.write("\t\t\t\t</td>\r\n");
				out.write("\t\t\t\t<td></td>\r\n");
				out.write("\t\t\t</tr>\r\n");
				out.write("\t\t\t");
				
				List<Power> list1 = power.getList();
				if (list1 != null && list1.size() > 0) {
					for (int j = 0; j < list1.size(); j++) {
						Power power1 = list1.get(j);
						
						out.write("\r\n");
						out.write("\t\t\t<tr onMouseOver=\"this.className='td-list-2'\" onMouseOut=\"this.className=''\">\r\n");
						out.write("\t\t\t\t<td>");
						out.print(power1.getId());
						out.write("</td>\r\n");
						out.write("\t\t\t\t<td>\r\n");
						out.write("\t\t\t\t\t");
						
						if (i != list.size() - 1) {
							
							out.write("\r\n");
							out.write("\t\t\t\t\t<img\r\n");
							out.write("\t\t\t\t\t\tsrc=\"");
							out.print(req.getRequestURL().toString());
							out.write("?action=image&name=tree_line.gif\">\r\n");
							out.write("\t\t\t\t\t");
							
						} else {
							
							out.write("\r\n");
							out.write("\t\t\t\t\t<img\r\n");
							out.write("\t\t\t\t\t\tsrc=\"");
							out.print(req.getRequestURL().toString());
							out.write("?action=image&name=tree_empty.gif\">\r\n");
							out.write("\t\t\t\t\t");
							
						}
						
						out.write("\r\n");
						out.write("\t\t\t\t\t");
						
						if (j != list1.size() - 1) {
							
							out.write("\r\n");
							out.write("\t\t\t\t\t<img\r\n");
							out.write("\t\t\t\t\t\tsrc=\"");
							out.print(req.getRequestURL().toString());
							out.write("?action=image&name=tree_linemiddle.gif\">\r\n");
							out.write("\t\t\t\t\t");
							
						} else {
							
							out.write("\r\n");
							out.write("\t\t\t\t\t<img\r\n");
							out.write("\t\t\t\t\t\tsrc=\"");
							out.print(req.getRequestURL().toString());
							out.write("?action=image&name=tree_linebottom.gif\">\r\n");
							out.write("\t\t\t\t\t");
							
						}
						
						out.write("\r\n");
						out.write("\t\t\t\t\t");
						
						if (power1.getList() == null
								|| power1.getList().size() == 0) {
							
							out.write("\r\n");
							out.write("\t\t\t\t\t<img\r\n");
							out.write("\t\t\t\t\t\tsrc=\"");
							out.print(req.getRequestURL().toString());
							out.write("?action=image&name=tree_folder.gif\">\r\n");
							out.write("\t\t\t\t\t");
							
						} else {
							
							out.write("\r\n");
							out.write("\t\t\t\t\t<img\r\n");
							out.write("\t\t\t\t\t\tsrc=\"");
							out.print(req.getRequestURL().toString());
							out.write("?action=image&name=tree_folderopen.gif\">\r\n");
							out.write("\t\t\t\t\t");
							
						}
						
						out.write("\r\n");
						out.write("\t\t\t\t\t<a\r\n");
						out.write("\t\t\t\t\t\thref=\"");
						out.print(req.getRequestURL().toString());
						out.write("?action=showEditPower&opt=edit&id=");
						out.print(power1.getId());
						out.write('"');
						out.write('>');
						out.print(power1.getName());
						out.write("</a>\r\n");
						out.write("\t\t\t\t</td>\r\n");
						out.write("\t\t\t\t<td>\r\n");
						out.write("\t\t\t\t\t<a\r\n");
						out.write("\t\t\t\t\t\thref=\"javascript:if(confirm('确定要删除此权限及其下所有权限！'))location.href='");
						out.print(req.getRequestURL().toString());
						out.write("?action=delPower&id=");
						out.print(power1.getId());
						out.write("';\">删除</a>\r\n");
						out.write("\t\t\t\t\t<a\r\n");
						out.write("\t\t\t\t\t\thref=\"");
						out.print(req.getRequestURL().toString());
						out.write("?action=showAddPower&opt=add&parentId=");
						out.print(power1.getId());
						out.write("\">添加子权限</a>\r\n");
						out.write("\t\t\t\t</td>\r\n");
						out.write("\t\t\t\t<td></td>\r\n");
						out.write("\t\t\t</tr>\r\n");
						out.write("\t\t\t");
						
						List<Power> list2 = power1.getList();
						if (list2 != null && list2.size() > 0) {
							for (int k = 0; k < list2.size(); k++) {
								Power power2 = list2.get(k);
								
								out.write("\r\n");
								out.write("\t\t\t<tr onMouseOver=\"this.className='td-list-2'\" onMouseOut=\"this.className=''\">\r\n");
								out.write("\t\t\t\t<td>");
								out.print(power2.getId());
								out.write("</td>\r\n");
								out.write("\t\t\t\t<td>\r\n");
								out.write("\t\t\t\t\t");
								
								if (i != list.size() - 1) {
									
									out.write("<img\r\n");
									out.write("\t\t\t\t\t\tsrc=\"");
									out.print(req.getRequestURL().toString());
									out.write("?action=image&name=tree_line.gif\">\r\n");
									out.write("\t\t\t\t\t");
									
								} else {
									
									out.write("\r\n");
									out.write("\t\t\t\t\t<img\r\n");
									out.write("\t\t\t\t\t\tsrc=\"");
									out.print(req.getRequestURL().toString());
									out.write("?action=image&name=tree_empty.gif\">\r\n");
									out.write("\t\t\t\t\t");
									
								}
								
								out.write("\r\n");
								out.write("\t\t\t\t\t");
								
								if (j != list1.size() - 1) {
									
									out.write("<img\r\n");
									out.write("\t\t\t\t\t\tsrc=\"");
									out.print(req.getRequestURL().toString());
									out.write("?action=image&name=tree_line.gif\">\r\n");
									out.write("\t\t\t\t\t");
									
								} else {
									
									out.write("\r\n");
									out.write("\t\t\t\t\t<img\r\n");
									out.write("\t\t\t\t\t\tsrc=\"");
									out.print(req.getRequestURL().toString());
									out.write("?action=image&name=tree_empty.gif\">\r\n");
									out.write("\t\t\t\t\t");
									
								}
								
								out.write("\r\n");
								out.write("\r\n");
								out.write("\t\t\t\t\t");
								
								if (k != list2.size() - 1) {
									
									out.write("\r\n");
									out.write("\t\t\t\t\t<img\r\n");
									out.write("\t\t\t\t\t\tsrc=\"");
									out.print(req.getRequestURL().toString());
									out.write("?action=image&name=tree_linemiddle.gif\">\r\n");
									out.write("\t\t\t\t\t");
									
								} else {
									
									out.write("\r\n");
									out.write("\t\t\t\t\t<img\r\n");
									out.write("\t\t\t\t\t\tsrc=\"");
									out.print(req.getRequestURL().toString());
									out.write("?action=image&name=tree_linebottom.gif\">\r\n");
									out.write("\t\t\t\t\t");
									
								}
								
								out.write("\r\n");
								out.write("\t\t\t\t\t<img\r\n");
								out.write("\t\t\t\t\t\tsrc=\"");
								out.print(req.getRequestURL().toString());
								out.write("?action=image&name=tree_file.gif\">\r\n");
								out.write("\t\t\t\t\t<a\r\n");
								out.write("\t\t\t\t\t\thref=\"");
								out.print(req.getRequestURL().toString());
								out.write("?action=showEditPower&opt=edit&id=");
								out.print(power2.getId());
								out.write('"');
								out.write('>');
								out.print(power2.getName());
								out.write("</a>\r\n");
								out.write("\t\t\t\t</td>\r\n");
								out.write("\t\t\t\t<td>\r\n");
								out.write("\t\t\t\t\t<a\r\n");
								out.write("\t\t\t\t\t\thref=\"javascript:if(confirm('确定要删除此权限及其下所有权限！'))location.href='");
								out.print(req.getRequestURL().toString());
								out.write("?action=delPower&id=");
								out.print(power2.getId());
								out.write("';\">删除</a>\r\n");
								out.write("\t\t\t\t</td>\r\n");
								out.write("\t\t\t\t<td></td>\r\n");
								out.write("\t\t\t</tr>\r\n");
								out.write("\t\t\t");
								
							}
						}
					}
				}
			}
		}
		
		out.write("\r\n");
		out.write("\t\t\t<tr>\r\n");
		out.write("\t\t\t\t<td colspan=\"4\"></td>\r\n");
		out.write("\t\t\t</tr>\r\n");
		out.write("\t\t</table>\r\n");
		out.write("\t</body>\r\n");
		out.write("</html>");
	}
	
	@SuppressWarnings("unchecked")
	public final static void role(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		if (debug) {
			req.getSession().getServletContext()
					.getRequestDispatcher("/role.jsp").forward(req, resp);
			return;
		}
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		ServletContext application = session.getServletContext();
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		
		out.write("\r\n");
		out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
		out.write("<html>\r\n");
		out.write("\t<head>\r\n");
		out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
		out.write("\t\t<title>编辑角色</title>\r\n");
		out.write("\t\t<link rel=\"stylesheet\" type=\"text/css\"\r\n");
		out.write("\t\t\thref=\"");
		out.print(req.getRequestURL().toString());
		out.write("?action=css\">\r\n");
		out.write("\t</head>\r\n");
		out.write("\t<body>\r\n");
		out.write("\t\t<DIV class=version>\r\n");
		out.write("\t\t\t<br>\r\n");
		out.write("\t\t\t 通用权限管理\r\n");
		out.write("\t\t\t");
		out.print(PmsServlet.version);
		out.write("\r\n");
		out.write("\t\t</DIV>\r\n");
		out.write("\t\t<div align=\"right\" style=\"width: 80%\">\r\n");
		out.write("\t\t\t当前系统：\r\n");
		out.write("\t\t\t<font color=\"#006600\">");
		out.print(application.getServletContextName());
		out.write("</font>\r\n");
		out.write("\t\t</div>\r\n");
		out.write("\t\t<div>\r\n");
		out.write("\t\t<UL>\r\n");
		out.write("\r\n");
		out.write("\t\t\t<LI class=active>\r\n");
		out.write("\t\t\t\t<A class=quiet href=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=roleList\">角色列表</A>\r\n");
		out.write("\t\t\t</LI>\r\n");
		out.write("\r\n");
		out.write("\t\t\t<LI class=inactive>\r\n");
		out.write("\t\t\t\t<A class=quiet href=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=powerList\">权限列表</A>\r\n");
		out.write("\t\t\t</LI>\r\n");
		out.write("\t\t</UL>\r\n");
		out.write("\t\t</div>\r\n");
		out.write("\t\t");
		
		Role role = (Role) req.getAttribute("role");
		
		out.write("\r\n");
		out.write("\t\t<form action=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=editRole\" method=\"post\">\r\n");
		out.write("\t\t\t<table class=\"data\" width=\"100%\">\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th width=\"150\">\r\n");
		out.write("\t\t\t\t\t\t角色名称:\r\n");
		out.write("\t\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t");
		out.print(role.getName());
		out.write("\r\n");
		out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"id\" value=\"");
		out.print(role.getId());
		out.write("\">\r\n");
		out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"name\" value=\"");
		out.print(role.getName());
		out.write("\">\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t\t角色描述:\r\n");
		out.write("\t\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t<input name=\"description\" readonly=\"readonly\" value=\"");
		out.print(role.getDescription());
		out.write("\">\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t\t权限分配:\r\n");
		out.write("\t\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t");
		
		List<Power> list1 = (List<Power>) req.getAttribute("list");
		if (list1 != null && list1.size() > 0) {
			for (Power power1 : list1) {
				
				out.write("\r\n");
				out.write("\t\t\t\t\t\t<div id=\"pkg");
				out.print(power1.getId());
				out.write("\" style=\"padding-left: 20px\">\r\n");
				out.write("\t\t\t\t\t\t\t<input type=\"checkbox\" style=\"width: 15px;\"\r\n");
				out.write("\t\t\t\t\t\t\t\tonclick=\"selectedSub(this.name, this.checked);selectedParent(this, this.checked);\"\r\n");
				out.write("\t\t\t\t\t\t\t\tid=\"power");
				out.print(power1.getId());
				out.write("\" name=\"");
				out.print(power1.getId());
				out.write("\"\r\n");
				out.write("\t\t\t\t\t\t\t\t");
				if (power1.isSelect()) {
					out.write(" checked=\"checked\" ");
				}
				out.write(">\r\n");
				out.write("\t\t\t\t\t\t\t<label for=\"power");
				out.print(power1.getId());
				out.write('"');
				out.write('>');
				out.print(power1.getId());
				out.write("\t\t\t\t\t\t\t\t");
				out.print(power1.getName());
				out.write("</label>\r\n");
				out.write("\t\t\t\t\t\t\t<br />\r\n");
				out.write("\t\t\t\t\t\t\t<div id=\"pkg");
				out.print(power1.getId());
				out.write("_body\">\r\n");
				out.write("\t\t\t\t\t\t\t\t");
				
				List<Power> list2 = power1.getList();
				if (list2 != null && list2.size() > 0) {
					for (Power power2 : list2) {
						
						out.write("\r\n");
						out.write("\t\t\t\t\t\t\t\t<div id=\"pkg");
						out.print(power2.getId());
						out.write("\" style=\"padding-left: 20px\">\r\n");
						out.write("\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" style=\"width: 15px;\"\r\n");
						out.write("\t\t\t\t\t\t\t\t\t\tonclick=\"selectedSub(this.name, this.checked);selectedParent(this, this.checked);\"\r\n");
						out.write("\t\t\t\t\t\t\t\t\t\tid=\"power");
						out.print(power2.getId());
						out.write("\" name=\"");
						out.print(power2.getId());
						out.write("\"\r\n");
						out.write("\t\t\t\t\t\t\t\t\t\t");
						if (power2.isSelect()) {
							out.write(" checked=\"checked\" ");
						}
						out.write(">\r\n");
						out.write("\t\t\t\t\t\t\t\t\t<label for=\"power");
						out.print(power2.getId());
						out.write('"');
						out.write('>');
						out.print(power2.getName());
						out.write("</label>\r\n");
						out.write("\t\t\t\t\t\t\t\t\t<br />\r\n");
						out.write("\t\t\t\t\t\t\t\t\t<div id=\"pkg");
						out.print(power2.getId());
						out.write("_body\">\r\n");
						out.write("\t\t\t\t\t\t\t\t\t\t");
						
						List<Power> list3 = power2.getList();
						if (list3 != null && list3.size() > 0) {
							for (Power power3 : list3) {
								
								out.write("<div id=\"pkg");
								out.print(power3.getId());
								out.write("\" style=\"padding-left: 20px\">\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" style=\"width: 15px;\"\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t\t\tonclick=\"selectedSub(this.name, this.checked);selectedParent(this, this.checked);\"\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t\t\tid=\"power");
								out.print(power3.getId());
								out.write("\" name=\"");
								out.print(power3.getId());
								out.write("\"\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
								if (power3.isSelect()) {
									out.write(" checked=\"checked\" ");
								}
								out.write(">\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t\t<label for=\"power");
								out.print(power3.getId());
								out.write('"');
								out.write('>');
								out.print(power3.getName());
								out.write("</label>\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t\t<br />\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t\t<div id=\"pkg");
								out.print(power3.getId());
								out.write("_body\">\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
								out.write("\t\t\t\t\t\t\t\t\t\t");
								
							}
						}
						
						out.write("\r\n");
						out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
						out.write("\t\t\t\t\t\t\t\t</div>\r\n");
						out.write("\t\t\t\t\t\t\t\t");
						
					}
				}
				
				out.write("\r\n");
				out.write("\t\t\t\t\t\t\t</div>\r\n");
				out.write("\t\t\t\t\t\t</div>\r\n");
				out.write("\t\t\t\t\t\t");
				
			}
		}
		
		out.write("\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t\t<tr>\r\n");
		out.write("\t\t\t\t\t<th></th>\r\n");
		out.write("\t\t\t\t\t<td>\r\n");
		out.write("\t\t\t\t\t\t<div style=\"padding-left: 160px;\">\r\n");
		out.write("\t\t\t\t\t\t\t<input id=\"act\" type=\"submit\" value=\" 保 存 \" style=\"width: 70px;\">\r\n");
		out.write("\t\t\t\t\t\t\t<input id=\"back\" type=\"button\" value=\" 返 回 \" style=\"width: 70px;\"\r\n");
		out.write("\t\t\t\t\t\t\t\tonclick=\"location.href='");
		out.print(req.getRequestURL().toString());
		out.write("?action=roleList'\">\r\n");
		out.write("\t\t\t\t\t\t</div>\r\n");
		out.write("\t\t\t\t\t</td>\r\n");
		out.write("\t\t\t\t</tr>\r\n");
		out.write("\t\t\t</table>\r\n");
		out.write("\t\t</form>\r\n");
		out.write("\t\t<script type=\"text/javascript\">\r\n");
		out.write("\t\tfunction selectedSub(id, checked){\r\n");
		out.write("\t\t\tvar div = document.getElementById(\"pkg\"+id+\"_body\");\r\n");
		out.write("\t\t\tif(div){\r\n");
		out.write("\t\t\t\tvar els = div.children;\r\n");
		out.write("\t\t\t\tfor(var i = 0; i < els.length; i ++){\r\n");
		out.write("\t\t\t\t\tvar elsels = els[i].children;\r\n");
		out.write("\t\t\t\t\tfor(var j = 0; j < elsels.length; j ++){\r\n");
		out.write("\t\t\t\t\t\tif(elsels[j].nodeName == \"INPUT\"){\r\n");
		out.write("\t\t\t\t\t\t\tif(checked){\r\n");
		out.write("\t\t\t\t\t\t\t\telsels[j].checked = true;\r\n");
		out.write("\t\t\t\t\t\t\t}else{\r\n");
		out.write("\t\t\t\t\t\t\t\telsels[j].checked = false;\r\n");
		out.write("\t\t\t\t\t\t\t}\r\n");
		out.write("\t\t\t\t\t\t\tselectedSub(elsels[j].name, checked);\r\n");
		out.write("\t\t\t\t\t\t}\r\n");
		out.write("\t\t\t\t\t}\r\n");
		out.write("\t\t\t\t}\r\n");
		out.write("\t\t\t}\r\n");
		out.write("\t\t}\r\n");
		out.write("\t\t\r\n");
		out.write("\t\tfunction selectedParent(el, checked){\r\n");
		out.write("\t\t\tif(checked){\r\n");
		out.write("\t\t\t\tel = el.parentElement.parentElement.parentElement.children[0];\r\n");
		out.write("\t\t\t\tif(el && el.nodeName == \"INPUT\"){\r\n");
		out.write("\t\t\t\t\tel.checked = checked;\r\n");
		out.write("\t\t\t\t\tselectedParent(el, checked);\r\n");
		out.write("\t\t\t\t}\r\n");
		out.write("\t\t\t}\r\n");
		out.write("\t\t}\r\n");
		out.write("\t\t</script>\r\n");
		out.write("\t</body>\r\n");
		out.write("</html>");
	}
	
	@SuppressWarnings("unchecked")
	public final static void roleList(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		if (debug) {
			req.getSession().getServletContext()
					.getRequestDispatcher("/roleList.jsp").forward(req, resp);
			return;
		}
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		ServletContext application = session.getServletContext();
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		
		out.write("\r\n");
		out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
		out.write("<html>\r\n");
		out.write("\t<head>\r\n");
		out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
		out.write("\t\t<title>角色列表</title>\r\n");
		out.write("\t\t<link rel=\"stylesheet\" type=\"text/css\"\r\n");
		out.write("\t\t\thref=\"");
		out.print(req.getRequestURL().toString());
		out.write("?action=css\">\r\n");
		out.write("\t</head>\r\n");
		out.write("\t<body>\r\n");
		out.write("\t\t<DIV class=version>\r\n");
		out.write("\t\t\t<br>\r\n");
		out.write("\t\t\t 通用权限管理 ");
		out.print(PmsServlet.version);
		out.write("\r\n");
		out.write("\t\t</DIV>\r\n");
		out.write("\t\t<div align=\"right\" style=\"width: 80%\">\r\n");
		out.write("\t\t\t当前系统：\r\n");
		out.write("\t\t\t<font color=\"#006600\">");
		out.print(application.getServletContextName());
		out.write("</font>\r\n");
		out.write("\t\t</div>\r\n");
		out.write("\t\t");
		
		Object optMsg = session.getAttribute("optMsg");
		if (optMsg != null) {
			session.removeAttribute("optMsg");
			
			out.write("\r\n");
			out.write("\t\t<div class='msg_success'>\r\n");
			out.write("\t\t\t");
			out.print(optMsg.toString());
			out.write("\r\n");
			out.write("\t\t</div>\r\n");
			out.write("\t\t<br />\r\n");
			out.write("\t\t");
			
		}
		
		out.write("\r\n");
		out.write("\t\t<UL>\r\n");
		out.write("\r\n");
		out.write("\t\t\t<LI class=active>\r\n");
		out.write("\t\t\t\t<A class=quiet href=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=roleList\">角色列表</A>\r\n");
		out.write("\t\t\t</LI>\r\n");
		out.write("\r\n");
		out.write("\t\t\t<LI class=inactive>\r\n");
		out.write("\t\t\t\t<A class=quiet href=\"");
		out.print(req.getRequestURL().toString());
		out.print("?action=powerList\">权限列表</A>\r\n");
		out.write("\t\t\t</LI>\r\n");
		out.write("\r\n");
		out.write("\t\t</UL>\r\n");
		out.write("\t\t<table class=\"tree\" width=\"100%\">\r\n");
		out.write("\t\t\t<tr>\r\n");
		out.write("\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t角色名称\r\n");
		out.write("\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t操作\r\n");
		out.write("\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t<th>\r\n");
		out.write("\t\t\t\t\t角色描述\r\n");
		out.write("\t\t\t\t</th>\r\n");
		out.write("\t\t\t\t");
		out.write("\r\n");
		out.write("\t\t\t</tr>\r\n");
		out.write("\t\t\t");
		
		List<Role> list = (List<Role>) req.getAttribute("list");
		if (list != null && list.size() > 0) {
			for (Role role : list) {
				
				out.write("\r\n");
				out.write("\t\t\t<tr>\r\n");
				out.write("\t\t\t\t<td>");
				out.print(role.getName());
				out.write("</td>\r\n");
				out.write("\t\t\t\t<td>\r\n");
				out.write("\t\t\t\t\t<a\r\n");
				out.write("\t\t\t\t\t\thref=\"");
				out.print(req.getRequestURL().toString());
				out.write("?action=showEditRole&name=");
				out.print(role.getName());
				out.write("\">分配权限</a>\r\n");
				out.write("\r\n");
				out.write("\t\t\t\t</td>\r\n");
				out.write("\t\t\t\t<td>");
				out.print(role.getDescription());
				out.write("</td>\r\n");
				out.write("\t\t\t\t");
				out.write("\r\n");
				out.write("\t\t\t</tr>\r\n");
				out.write("\t\t\t");
			}
		}
		out.write("\r\n");
		out.write("\t\t\t<tr>\r\n");
		out.write("\t\t\t\t<td colspan=\"3\" style=\"padding-left: 100px\">\r\n");
		out.write("\t\t\t\t\t<a\r\n");
		out.write("\t\t\t\t\t\thref=\"");
		out.print(req.getRequestURL().toString());
		out.write("?action=roleList&reload=true\"></a>\r\n");
		out.write("\t\t\t\t</td>\r\n");
		out.write("\t\t\t</tr>\r\n");
		out.write("\t\t</table>\r\n");
		out.write("\t</body>\r\n");
		out.write("</html>");
	}
}
