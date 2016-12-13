<%@ page language="java" import=" com.duanrong.newadmin.controllhelper.UserCookieHelper,com.duanrong.newadmin.model.UserCookie"
 pageEncoding="UTF-8"%>

 <div class="headerbar">

      <a class="menutoggle"><i class="fa fa-bars"></i></a>    
      <div class="header-right" style="margin-top: 20px; margin-right: 8px;">
        <ul class="headermenu">
          <li>
            <div class="btn-group"> 
            	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                <img src="${pageContext.request.contextPath}/images/photos/loggeduser.png" alt="" />
                <%UserCookie cookie=UserCookieHelper.GetUserCookie(request, response); %>
                	登录用户：<%=cookie.getUserId() %>
                <span class="caret"></span>
              </button>
              <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
              	<li><a href="${ctx }/toRepass">修改密码</a></li> 
                <li><a href="${ctx }/logout">退出</a></li>                            
              </ul>                                     
            </div>
          </li>         
        </ul>
      </div><!-- header-right -->

    </div><!-- headerbar -->