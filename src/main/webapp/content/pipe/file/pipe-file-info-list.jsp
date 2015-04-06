<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentMenu", "pipeFile");%>
<!doctype html>
<html lang="en">

<head>
    <%@include file="/common/meta.jsp"%>
    <title>列表</title>
    <%@include file="/common/s.jsp"%>
    <script type="text/javascript">
        var config = {
            id: 'pipe-resource-infoGrid',
            pageNo: ${page.pageNo},
            pageSize: ${page.pageSize},
            totalCount: ${page.totalCount},
            resultSize: ${page.resultSize},
            pageCount: ${page.pageCount},
            orderBy: '${page.orderBy == null ? "" : page.orderBy}',
            asc: ${page.asc},
            params: {
                'filter_LIKES_fileName': '${param.filter_LIKES_fileName}'
            },
            selectedItemClass: 'selectedItem',
            gridFormId: 'pipe-resource-infoGridForm',
            exportUrl: 'pipe-file-info-export.do'
        };

        var table;

        $(function() {
            table = new Table(config);
            table.configPagination('.m-pagination');
            table.configPageInfo('.m-page-info');
            table.configPageSize('.m-page-size');
        });
    </script>
</head>

<body>
<%@include file="/common/layout/header.jsp"%>

<div class="row-fluid">
    <%@include file="/common/layout/menu.jsp"%>

    <!-- start of main -->
    <section id="m-main" class="span10">

        <article class="m-widget">
            <header class="header">
                <h4 class="title">查询</h4>
                <div class="ctrl">
                    <a class="btn"><i id="service-infoSearchIcon" class="icon-chevron-up"></i></a>
                </div>
            </header>
            <div id="pipe-resource-infoSearch" class="content content-inner">

                <form name="pipe-file-infoForm" method="post" action="pipe-file-info-list.do" class="form-inline">
                    <label for="pipe-file-info_name">名称:</label>
                    <input type="text" id="pipe-file-info_name" name="filter_LIKES_fileName" value="${param.filter_LIKES_fileName}">
                    <button class="btn btn-small a-search" onclick="document.pipe-file-infoForm.submit()">查询</button>&nbsp;
                    <button class="btn btn-small a-export" onclick="table.exportExcel()">导出</button>
                </form>


            </div>
        </article>

        <article class="m-blank">
            <div class="pull-right">
                每页显示
                <select class="m-page-size">
                    <option value="10">10</option>
                    <option value="20">20</option>
                    <option value="50">50</option>
                </select>
                条
            </div>

            <div class="m-clear"></div>
        </article>

        <article class="m-widget">
            <header class="header">
                <h4 class="title">在线管线业务文件列表</h4>
            </header>
            <div class="content">
                <form id="pipe-resource-infoGridForm" name="pipe-resource-infoGridForm" method='post' action="pipe-file-info-remove.do" class="m-form-blank">
                    <table id="pipe-resource-infoGrid" class="m-table table-hover">
                        <thead>
                        <tr>
                            <th class="sorting" name="fileName">文件名称</th>
                            <th class="sorting" name="desc">文件描述</th>
                            <th class="sorting" name="pipeType">文件类型</th>
                            <th class="sorting" name="fileSize">文件大小</th>
                            <th class="sorting" name="uploader">上传人</th>
                            <th class="sorting" name="uploadTime">上传时间</th>
                            <th width="80">状态</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${page.result}" var="item">
                            <tr>
                                <td>${item.fileName}</td>
                                <td>${item.desc}</td>
                                <td>${item.pipeType}</td>
                                <td>${item.fileSize}M</td>
                                <td>${item.uploader}</td>
                                <td>${item.uploadTime}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.state == 0}">
                                            <a href="pipe-file-info-apply.do?id=${item.id}" class="btn btn-small btn-primary">申请</a>
                                        </c:when>
                                        <c:when test="${item.state == 1}">
                                           <a role="button" class="btn btn-small disabled">待审核</a>
                                        </c:when>
                                        <c:when test="${item.state == 2}">
                                            <a href="pipe-file-download.do?id=${item.id}" class="btn btn-small btn-primary">下载</a>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>
        </article>

        <article>
            <div class="m-page-info pull-left">
                共 ${page.totalCount} 条记录 显示 ${(page.pageNo-1)*page.pageSize+1} 到 ${(page.pageNo+1)*page.pageSize} 条记录
            </div>

            <div class="btn-group m-pagination pull-right">
                <button class="btn btn-small">&lt;</button>
                <button class="btn btn-small">1</button>
                <button class="btn btn-small">&gt;</button>
            </div>

            <div class="m-clear"></div>
        </article>

        <div class="m-spacer"></div>

    </section>
    <!-- end of main -->
</div>

</body>

</html>
