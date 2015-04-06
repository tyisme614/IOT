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
            id: 'service-infoGrid',
            pageNo: ${page.pageNo},
            pageSize: ${page.pageSize},
            totalCount: ${page.totalCount},
            resultSize: ${page.resultSize},
            pageCount: ${page.pageCount},
            orderBy: '${page.orderBy == null ? "" : page.orderBy}',
            asc: ${page.asc},
            params: {
                'filter_LIKES_name': '${param.filter_LIKES_name}'
            },
            selectedItemClass: 'selectedItem',
            gridFormId: 'service-infoGridForm',
            exportUrl: 'service-info-export.do'
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

                <form name="service-infoForm" method="post" action="service-info-apply-list.do" class="form-inline">
                    <label for="service-info_name">名称:</label>
                    <input type="text" id="service-info_name" name="filter_LIKES_name" value="${param.filter_LIKES_name}">
                    <button class="btn btn-small a-search" onclick="document.service-infoForm.submit()">查询</button>&nbsp;
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
                <h4 class="title">待审核服务申请列表</h4>
            </header>
            <div class="content">
                <form id="service-infoGridForm" name="service-infoGridForm" method='post' action="service-info-remove.do" class="m-form-blank">
                    <table id="service-infoGrid" class="m-table table-hover">
                        <thead>
                        <tr>
                            <th class="sorting" name="serviceInfo.name">服务名</th>
                            <th class="sorting" name="registerUser.username">注册人</th>
                            <th class="sorting" name="serviceInfo.serviceType">服务类型</th>
                            <th class="sorting" name="applyUser.username">申请人</th>
                            <th class="sorting" name="applyDate">申请时间</th>
                            <th class="sorting" name="applyMemo">备注信息</th>
                            <th width="115">审核操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${page.result}" var="item">
                            <tr>
                                <td>${item.servicename}</td>
                                <td>${item.registername}</td>
                                <td>${item.servicetype}</td>
                                <td>${item.applyname}</td>
                                <td>${item.applyday}</td>
                                <td>${item.applymemo}</td>
                                <td>
                                    <a href="service-info-verify.do?id=${item.id}" class="btn btn-small btn-primary">通过</a>
                                    <a href="service-info-withdraw.do?id=${item.id}" class="btn btn-small btn-primary">撤销</a>
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
                共${page.totalCount}条记录 显示${(page.pageNo-1)*page.pageSize +1}到${(page.pageNo+1)*page.pageSize}条记录
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
