<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <%@include file="/common/meta.jsp"%>
    <title>用户注册</title>
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
                            url: '${ctx}/user/user-checkUsername.do',
                            data: {
                                <c:if test="${model != null}">
                                id: function() {
                                    return $('#username').val();
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
    <div class="span3"></div>

    <!-- start of main -->
    <section class="span6">

        <article class="m-widget">
            <header class="header">
                <h4 class="title">注册</h4>
            </header>

            <div class="content content-inner">

                <form id="userForm" name="f" method="post" action="${ctx}/user/add-user-info.do" class="form-horizontal">
                    <div class="control-group">
                        <label class="control-label" for="username">用户名</label>
                        <div class="controls">
                            <input type='text' id="username" name='username'  value="" size="40" class="text required" minlength="2" maxlength="50">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="password">密码</label>
                        <div class="controls">
                            <input type='password' id="password" name='password'  value='' size="40" class="text required" maxlength="10">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="passwordConfirm">验证密码</label>
                        <div class="controls">
                            <input type='password' id="passwordConfirm" name='passwordConfirm' value='' size="40" class="text required" maxlength="10" equalTo="#password">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="desc">备注</label>
                        <div class="controls">
                            <input type='text' id="desc" name='desc' class="text" value=''>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <button id="submitButton" type="submit" class="btn">注册</button>
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
