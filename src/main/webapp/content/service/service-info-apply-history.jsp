<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentMenu", "service");%>
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
                'filter_LIKES_serviceInfo.name': '${param.filter_LIKES_serviceInfo.name}',
                'filter_EQI_state' : '${param.filter_EQI_state}'
            },
            selectedItemClass: 'selectedItem',
            gridFormId: 'service-infoGridForm'
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

                <form name="service-infoForm" method="post" action="service-info-apply-history.do" class="form-inline">
                    <label for="service-info_name">名称:</label>
                    <input type="text" id="service-info_name" name="filter_LIKES_serviceInfo.name" value="${param.filter_LIKES_serviceInfo.name}">
                    <label for="apply_result">审核结果:</label>
                    <select id="apply_result" name="filter_EQI_state" style="width: 80px;">
                        <option value ="" selected>-请选择-</option>
                        <option value ="0">待审核</option>
                        <option value ="1">已通过</option>
                        <option value ="2">未通过</option>
                    </select>
                    <button class="btn btn-small a-search" onclick="document.service-infoForm.submit()">查询</button>&nbsp;
                </form>


            </div>
        </article>

        <article class="m-blank">
            <%--<div class="pull-left">--%>
            <%--</div>--%>

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
                <h4 class="title">我申请过的服务</h4>
            </header>
            <div class="content">
                    <table id="service-infoGrid" class="m-table table-hover">
                        <thead>
                        <tr>
                            <th class="sorting" name="serviceInfo.name">名称</th>
                            <th class="sorting" name="serviceInfo.url">URL</th>
                            <th class="sorting" name="serviceInfo.desc">描述</th>
                            <th width="90" class="sorting" name="serviceInfo.registerUser">注册人</th>
                            <th width="100" class="sorting" name="serviceInfo.registerDate">注册时间</th>
                            <th width="100" class="sorting" name="state">申请状态</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${page.result}" var="item">
                            <tr>
                                <td>${item.name}</td>
                                <td>
                                    <c:if test="${item.state == 1}">
                                        ${item.url}
                                    </c:if>
                               </td>
                                <td>${item.desc}</td>
                                <td>${item.registerUser}</td>
                                <td>${item.registerDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.state == 0}">
                                            <a class="btn btn-small disabled">待审核</a>
                                        </c:when>
                                        <c:when test="${item.state == 1}">
                                            <a class="btn btn-small disabled">已通过</a>
                                        </c:when>
                                        <c:when test="${item.state == 2}">
                                            <a class="btn btn-small disabled">未通过</a>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
            </div>
        </article>

        <article>
            <div class="m-page-info pull-left">
                共${page.totalCount}条记录 显示${(page.pageNo-1)*page.pageSize+1}到${page.pageNo*page.pageSize}条记录
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
