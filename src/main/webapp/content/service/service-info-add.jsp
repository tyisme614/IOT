<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%pageContext.setAttribute("currentMenu", "service");%>
<!doctype html>
<html lang="en">
<head>
    <%@include file="/common/meta.jsp"%>
    <title>服务注册</title>
    <%@include file="/common/s.jsp"%>

    <script type="text/javascript">
        $(function() {
            $("#serviceForm").validate({
                submitHandler: function(form) {
                    bootbox.animate(false);
                    var box = bootbox.dialog('<div class="progress progress-striped active" style="margin:0px;"><div class="bar" style="width: 100%;"></div></div>');
                    form.submit();
                },
                errorClass: 'validate-error'
            });
        })
    </script>
</head>

<body>

<!-- start of header bar -->
<div class="navbar navbar-inverse">
    <div class="navbar-inner">
        <div class="container">
            <a href="${ctx}/" class="brand">信息服务管理分系统</a>
        </div>
    </div><!-- /navbar-inner -->
</div>
<!-- end of header bar -->

<div class="row-fluid">
    <%@include file="/common/layout/menu.jsp"%>
    <!-- start of main -->
    <section class="span6">

        <article class="m-widget">
            <header class="header">
                <h4 class="title">服务注册</h4>
            </header>

            <div class="content content-inner">

                <form id="serviceForm" name="f" method="post" action="${ctx}/service/service-info-add.do" class="form-horizontal">
                    <div class="control-group">
                        <label class="control-label" for="name">服务名</label>
                        <div class="controls">
                            <input type='text' id="name" name='name'  value="" size="40" class="text required" minlength="2" maxlength="50">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="serviceType">类型</label>
                        <div class="controls">
                            <select name="serviceType" id="serviceType">
                                <option value ="0">内部系统服务</option>
                                <option value ="1">外部系统服务</option>
                            </select>

                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="url">URL</label>
                        <div class="controls">
                            <input type='text' id="url" name='url'  value="" size="40" class="text required" minlength="2" maxlength="200">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="desc">说明</label>
                        <div class="controls">
                            <textarea id="desc" name="desc" rows="3" class="form-control"></textarea>
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
