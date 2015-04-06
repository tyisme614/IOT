<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/3/16
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentMenu", "backup");%>
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
                'filter_GED_backupDay': '${param.filter_GED_backupDay}',
                'filter_LED_backupDay': '${param.filter_LED_backupDay}'
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

                <form name="pipe-file-infoForm" method="post" action="backup-info-list.do" class="form-inline">
                    <label for="begin-day">备份开始日期:</label>
                    <input id="begin-day" name="filter_GED_backupDay" type="text" class="datepicker" data-options="yyyy-MM-dd" value="${param.filter_GED_backupDay}">
                    <label for="end-day">备份结束日期:</label>
                    <input id="end-day" name="filter_LED_backupDay" type="text" class="datepicker" data-options="yyyy-MM-dd" value="${param.filter_LED_backupDay}">
                    <button class="btn btn-small a-search" onclick="document.pipe-file-infoForm.submit()">查询</button>&nbsp;
                </form>
            </div>
        </article>

        <article class="m-blank">
            <div class="pull-left">
                <button class="btn btn-small a-remove" onclick="javascript:window.location='exp-data.do'">备份</button>
                <button class="btn btn-small a-remove" onclick="table.removeAll()">删除</button>
            </div>
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
                <h4 class="title">数据库备份历史表</h4>
            </header>
            <div class="content">
                <form id="pipe-resource-infoGridForm" name="pipe-resource-infoGridForm" method='post' action="backup-file-info-remove.do" class="m-form-blank">
                    <table id="service-infoGrid" class="m-table table-hover">
                        <thead>
                        <tr>
                            <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
                            <th class="sorting" name="fileName">备份文件</th>
                            <th class="sorting" name="fileSize">文件大小</th>
                            <th class="sorting" name="backupDay">备份日期</th>
                            <th width="80">操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${page.result}" var="item">
                            <tr>
                                <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
                                <td>${item.fileName}</td>
                                <td>${item.fileSize}MB</td>
                                <td>${item.backupDay}</td>
                                <td><a href="imp-data.do?id=${item.id}" class="btn btn-small btn-primary">还原</a></td>
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

