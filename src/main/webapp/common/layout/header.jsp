<%@ page language="java" pageEncoding="UTF-8" %>

<c:if test="${not empty flashMessages}">
    <div id="m-success-message" style="display:none;">
        <ul>
            <c:forEach items="${flashMessages}" var="item">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<!-- start of header bar -->
<div class="navbar navbar-inverse">
    <div class="navbar-inner">
        <div class="container">
            <a data-target=".navbar-responsive-collapse" data-toggle="collapse" class="btn btn-navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a href="${ctx}/" class="brand">信息服务管理分系统</a>
            <div class="nav-collapse collapse navbar-responsive-collapse">

                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                           ${_user_info.username}
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="${ctx}/user/user-info-edit.do">用户信息修改</a></li>
                            <li class="divider"></li>
                            <li><a href="${ctx}/user/user-log-out.do">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.nav-collapse -->
        </div>
    </div><!-- /navbar-inner -->
</div>
<!-- end of header bar -->
