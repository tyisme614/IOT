<%@page contentType="text/html;charset=UTF-8"%>
<%pageContext.setAttribute("currentMenu", "pipeResource");%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <%@include file="/common/meta.jsp"%>
    <title>管线相关资源注册</title>
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
                <h4 class="title">管线相关资源注册</h4>
            </header>

            <div class="content content-inner">

                <form id="serviceForm" name="f" method="post" enctype="multipart/form-data" action="${ctx}/pipe/resource/pipe-resource-info-add.do" class="form-horizontal">
                    <div class="control-group">
                        <label class="control-label" for="serviceType">资源类型</label>
                        <div class="controls">
                            <select name="pipeType" id="serviceType">
                                <option value ="0">雨水管线</option>
                                <option value ="1">污水管线</option>
                                <option value ="2">燃气管线</option>
                                <option value ="3">电力管线</option>
                                <option value ="4">热力管线</option>
                                <option value ="5">通信管线</option>
                                <option value ="6">其他</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="resourceFile">资源文件</label>
                        <div class="controls">
                            <input type="file" id="resourceFile" name='file'  class="text required"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="desc">资源说明</label>
                        <div class="controls">
                            <textarea class="form-control" rows="10" id="desc" name="desc" ></textarea>
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
