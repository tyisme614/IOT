<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/taglibs.jsp" %>
<%pageContext.setAttribute("currentHeader", "report");%>
<%pageContext.setAttribute("currentMenu", "chart");%>
<!doctype html>
<html lang="en">

<head>
    <%@include file="/common/meta.jsp" %>
    <title>热门服务</title>
    <%@include file="/common/s.jsp" %>

    <link rel="stylesheet" href="${ctx}/s/jqplot/jquery.jqplot.min.css" type="text/css" media="screen"/>
    <!--[if lte IE 8]>
    <script language="javascript" type="text/javascript" src="../s/jqplot/excanvas.min.js"></script><![endif]-->
    <script type="text/javascript" src="${ctx}/s/jqplot/jquery.jqplot.min.js"></script>
    <script type="text/javascript" src="${ctx}/s/jqplot/plugins/jqplot.barRenderer.min.js"></script>
    <script type="text/javascript" src="${ctx}/s/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
    <script type="text/javascript" src="${ctx}/s/jqplot/plugins/jqplot.pointLabels.min.js"></script>
    <script type="text/javascript" src="${ctx}/s/jqplot/plugins/jqplot.pieRenderer.min.js"></script>

    <script type="text/javascript">

        function drawPie() {
            var data = [
                <c:forEach items="${list}" var="item">
                ["${item.name}", ${item.c}],
                </c:forEach>
                []
            ];
            var plot1 = jQuery.jqplot('pie', [data],
                    {
                        seriesDefaults: {
                            // Make this a pie chart.
                            renderer: jQuery.jqplot.PieRenderer,
                            rendererOptions: {
                                // Put data labels on the pie slices.
                                // By default, labels show the percentage of the slice.
                                showDataLabels: true
                            }
                        },
                        legend: { show: true, location: 'e' }
                    }
            );

            $('#pie').bind('jqplotDataClick', function (ev, seriesIndex, pointIndex, data) {
                alert(data[0]);
            });
        }

        $(function () {
            drawPie();
        });


    </script>
</head>

<body>
<%@include file="/common/layout/header.jsp" %>

<div class="row-fluid">
    <%@include file="/common/layout/menu.jsp" %>

    <!-- start of main -->
    <section id="m-main" class="span10">

        <article class="m-widget">
            <header class="header">
                <h4 class="title">热门服务排行</h4>
            </header>

            <div class="content content-inner">
                <div id="pie" style="height:300px;"></div>
            </div>
        </article>

        <article class="m-widget">
            <header class="header">
                <h4 class="title"><i class="icon-user"></i>详细信息</h4>
                <div class="ctrl">
                    <a class="btn"><i class="icon-chevron-up"></i></a>
                </div>
            </header>
            <div class="content">
                <table class="m-table table-hover">
                    <thead>
                    <tr>
                        <th>服务名称</th>
                        <th>注册人</th>
                        <th>创建时间</th>
                        <th>申请次数</th>
                        <th>服务描述</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="item">
                        <tr>
                            <td>${item.name}</td>
                            <td>${item.registerUser}</td>
                            <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                            <td>${item.c}</td>
                            <td>${item.desc}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </article>

    </section>
    <!-- end of main -->
</div>


</iframe>
</body>

</html>
