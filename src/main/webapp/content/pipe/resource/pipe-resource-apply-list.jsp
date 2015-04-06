<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentMenu", "verify");%>
<!doctype html>
<html lang="en">

<head>
    <%@include file="/common/meta.jsp"%>
    <title>列表</title>
    <%@include file="/common/s.jsp"%>
    <script type="text/javascript">
        var config = {
            id: 'pipe-relate-resourceGrid',
            pageNo: ${page.pageNo},
            pageSize: ${page.pageSize},
            totalCount: ${page.totalCount},
            resultSize: ${page.resultSize},
            pageCount: ${page.pageCount},
            orderBy: '${page.orderBy == null ? "" : page.orderBy}',
            asc: ${page.asc},
            params: {
                'filter_LIKES_pipeRelateResourceInfo.fileName': '${param.filter_LIKES_pipeRelateResourceInfo.fileName}'
            },
            selectedItemClass: 'selectedItem'
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
            <div id="service-infoSearch" class="content content-inner">

                <form name="pipe-relate-resourceForm" method="post" action="pipe-resource-apply-list.do" class="form-inline">
                    <label for="service-info_name">名称:</label>
                    <input type="text" id="service-info_name" name="filter_LIKES_pipeRelateResourceInfo.fileName" value="${param.filter_LIKES_pipeRelateResourceInfo.fileName}">
                    <button class="btn btn-small a-search" onclick="document.pipe-relate-resourceForm.submit()">查询</button>&nbsp;
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
                <h4 class="title">待审核管线相关资源下载申请列表</h4>
            </header>
            <div class="content">
                    <table id="pipe-relate-resourceGrid" class="m-table table-hover">
                        <thead>
                        <tr>
                            <th class="sorting" name="pipeRelateResourceInfo.fileName">资源名称</th>
                            <th class="sorting" name="pipeRelateResourceInfo.pipeType">资源类型</th>
                            <th class="sorting" name="pipeRelateResourceInfo.fileSize">资源大小</th>
                            <th class="sorting" name="applyUser.username">申请人</th>
                            <th class="sorting" name=applyDate>申请日期</th>
                            <th class="sorting" name="applyMemo">申请描述</th>
                            <th width="115">审核操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${page.result}" var="item">
                            <tr>
                                <td>${item.fileName}</td>
                                <td>${item.pipeType}</td>
                                <td>${item.fileSize}M</td>
                                <td>${item.applyUser}</td>
                                <td>${item.applyDate}</td>
                                <td>${item.applyMemo}</td>
                                <td>
                                    <a href="pipe-resource-verify.do?id=${item.id}" class="btn btn-small btn-primary">通过</a>
                                    <a href="pipe-resource-withdraw.do?id=${item.id}" class="btn btn-small btn-primary">撤销</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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
