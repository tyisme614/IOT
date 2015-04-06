<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentMenu", "pipeResource");%>
<!doctype html>
<html>

<head>
    <%@include file="/common/meta.jsp"%>
    <title>管线相关资源下载申请</title>
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
                <h4 class="title">管线相关资源下载申请</h4>
            </header>
            <div class="content content-inner">

                <form id="serviceApplyForm" method="post" action="save-pipe-resource-info-apply.do" class="form-horizontal">
                    <c:if test="${model != null}">
                        <input id="service_id" type="hidden" name="sid" value="${model.id}">
                    </c:if>

                    <div class="control-group">
                        <label class="control-label" for="pipeName">资源名称</label>
                        <div class="controls">
                            <input id="pipeName" type="text" name="fileName" value="${model.fileName}" size="40" class="text required" readonly="true">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="pipeType">资源类型</label>
                        <div class="controls">
                            <input id="pipeType" type="text" name="url" value="${model.pipeType}" class="text required" readonly="true">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="pipeFileSize">资源大小</label>
                        <div class="controls">
                            <input id="pipeFileSize" type="text" name="fileSize" value="${model.fileSize}" class="text required" readonly="true">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="pipeDesc">资源描述</label>
                        <div class="controls">
                            <input id="pipeDesc" type="text" name="desc" value="${model.desc}" class="text required" readonly="true">
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
