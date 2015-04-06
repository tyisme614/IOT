<%@page contentType="text/html;charset=UTF-8"%>
<%pageContext.setAttribute("currentMenu", pageContext.getAttribute("currentMenu"));%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <%@include file="/common/meta.jsp"%>
    <title>个人信息修改</title>
    <%@include file="/common/s.jsp"%>

    <script type="text/javascript">
        $(function() {
            $("#userForm").validate({
                submitHandler: function(form) {
                    bootbox.animate(false);
                    var box = bootbox.dialog('<div class="progress progress-striped active" style="margin:0px;"><div class="bar" style="width: 100%;"></div></div>');
                    form.submit();
                },
                errorClass: 'validate-error',
                rules: {
                    username: {
                        remote: {
                            url: 'user-checkUsername.do',
                            data: {
                                <c:if test="${model != null}">
                                    username: function() {
                                        return $('#username').val();
                                    },
                                    id: function(){
                                        return $('#user-base_id').val();
                                    }
                                 </c:if>
                            }
                        }
                    }
                },
                messages: {
                    username: {
                        remote: "该用户名已被注册"
                    }
                }
            });
        })
    </script>
</head>

<body>

<!-- start of header bar -->
<%@include file="/common/layout/header.jsp"%>
<!-- end of header bar -->

<div class="row-fluid">
    <%@include file="/common/layout/menu.jsp"%>
    <!-- start of main -->
    <section class="span6">

        <article class="m-widget">
            <header class="header">
                <h4 class="title">个人信息修改</h4>
            </header>

            <div class="content content-inner">

                <form id="userForm" name="f" method="post" action="user-info-save.do" class="form-horizontal">

                    <c:if test="${model != null}">
                        <input id="user-base_id" type="hidden" name="id" value="${model.id}">
                    </c:if>

                    <div class="control-group">
                        <label class="control-label" for="username">用户名</label>
                        <div class="controls">
                            <input type='text' id="username" name='username'  value="${model.username}" size="40" class="text required" minlength="2" maxlength="50">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="password">密码</label>
                        <div class="controls">
                            <input type='password' id="password" name='password'  value="${model.password}" size="40" class="text required" maxlength="10">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="passwordConfirm">验证密码</label>
                        <div class="controls">
                            <input type='password' id="passwordConfirm" name='passwordConfirm' value="${model.password}" size="40" class="text required" maxlength="10" equalTo="#password">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="desc">备注</label>
                        <div class="controls">
                            <input type='text' id="desc" name='desc' class="text" value="${model.desc}">
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <button id="submitButton" type="submit" class="btn">保存</button>
                            &nbsp;
                            &nbsp;
                            <button type="button" onclick="history.back();" class="btn">取消</button>
                        </div>
                    </div>
                </form>
            </div>
        </article>

        <div class="m-spacer"></div>
    </section>
    <!-- end of main -->

    <div class="span3"></div>
</div>

</body>
</html>
