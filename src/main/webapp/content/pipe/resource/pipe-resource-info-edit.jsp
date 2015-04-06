<%@page contentType="text/html;charset=UTF-8"%>
<%pageContext.setAttribute("currentMenu", "pipeResource");%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <%@include file="/common/meta.jsp"%>
    <title>管线相关资源编辑</title>
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
                <h4 class="title">管线相关资源编辑</h4>
            </header>

            <div class="content content-inner">

                <form id="serviceForm" name="f" method="post" enctype="multipart/form-data" action="/xxfw/pipe/resource/pipe-resource-info-add.do" class="form-horizontal">
                    <input type="hidden" name="id" value="${model.id}">
                    <div class="control-group">
                        <label class="control-label" for="access_perm">资源类型</label>
                        <div class="controls">
                            <select id="access_perm" name="pipeType">
                                <c:forEach items="${resourceTypes}" var="item">
                                    <option value="${item.id}" ${model.pipeType==item.id ? 'selected' : ''}>${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="resourceFile">资源文件</label>
                        <div class="controls">
                            <input type="file" id="resourceFile" name='file'/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="desc">资源说明</label>
                        <div class="controls">
                            <textarea id="desc" name="desc" class="form-control" rows="3">${model.desc}</textarea>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button id="submitButton" type="submit" class="btn">确定</button>
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
