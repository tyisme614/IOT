<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentHeader", "service");%>
<!doctype html>
<html>

<head>
    <%@include file="/common/meta.jsp"%>
    <title>服务申请</title>
    <%@include file="/common/s.jsp"%>
</head>

<body>
<%@include file="/common/layout/header.jsp"%>

<div class="row-fluid">
    <%@include file="/common/layout/menu.jsp"%>

    <!-- start of main -->
    <section id="m-main" class="span10">

        <article class="m-widget">
            <header class="header">
                <h4 class="title">服务申请</h4>
            </header>
            <div class="content content-inner">

                <form id="serviceApplyForm" method="post" action="save-service-info-apply.do" class="form-horizontal">
                    <c:if test="${model != null}">
                        <input id="service_id" type="hidden" name="sid" value="${model.id}">
                    </c:if>

                    <div class="control-group">
                        <label class="control-label" for="serviceName">服务名称</label>
                        <div class="controls">
                            <input id="serviceName" type="text" name="name" value="${model.name}" size="40" class="text required" readonly="true">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="serviceUrl">服务地址</label>
                        <div class="controls">
                            <input id="serviceUrl" type="text" name="url" value="${model.url}" class="text required" readonly="true">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="serviceDesc">服务描述</label>
                        <div class="controls">
                            <input id="serviceDesc" type="text" name="desc" value="${model.desc}" class="text required" readonly="true">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="applyMemo">申请原因</label>
                        <div class="controls">
                            <textarea id="applyMemo" name="applyMemo" class="form-control" rows="3"></textarea>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <button id="submitButton" type="submit" class="btn a-submit">提交</button>
                            <button type="button" onclick="history.back();" class="btn a-cancel">返回</button>
                        </div>
                    </div>
                </form>
            </div>
        </article>
    </section>
    <!-- end of main -->
</div>

</body>

</html>
