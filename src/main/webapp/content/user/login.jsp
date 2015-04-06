<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <%@include file="/common/meta.jsp"%>
    <title>智慧太湖新城管线状态传感系统</title>
    <%@include file="/common/s.jsp"%>
</head>

<c:if test="${not empty flashMessages}">
    <div id="m-success-message" style="display:none;">
        <ul>
            <c:forEach items="${flashMessages}" var="item">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<body class="Login">

<form action="user-info-login.do" method="post">
    <div class="login_bg">
        <img src="${ctx}/s/XXFW/images/login_bg.jpg" />
        <div class="login_dw"></div>
    </div>
    <div class="login_fot">
        <img src="${ctx}/s/XXFW/images/login_fotBg.jpg" />
        <div class="login_BT">
            智慧太湖新城信息服务管理分系统
        </div>
        <div class="login_div">
            <input type="hidden" name="filter_EQI_status" value="1">
            <input type="text" name="filter_EQS_username" class="userName" class="text required"/>
            <input type="password" name="filter_EQS_password" class="password" class="text required"/>
            <input type="submit" class="LoginBtn" name="submit"/>
            <input type="button" class="resetBtn" name="register" onclick="window.location.href='user-info-register.do'"/>
        </div>
    </div>
</form>

<script type="text/javascript">
    $(function(){
        $("#msg").appendTo(".login_div");
    });
</script>
</body>
</html>
<script type="text/javascript">
$(".login_bg").css('height',$('.Login').height() - $('.login_fot').height() + 'px');
window.onresize=function(){
$(".login_bg").css('height',$('.Login').height() - $('.login_fot').height() + 'px');
}
</script>