<%@ page language="java" pageEncoding="UTF-8" %>
<!-- start of sidebar -->
<aside id="m-sidebar" class="accordion span2" data-spy="affix" data-offset-top="100">

    <!--服务管理-->
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#m-sidebar" href="#collapse-service">
                <i class="icon-user"></i>
                <span class="title">服务管理</span>
            </a>
        </div>
        <div id="collapse-service" class="accordion-body collapse ${currentMenu == 'service' ? 'in' : ''}">
            <ul class="accordion-inner nav nav-list">
                <li><a href="${ctx}/service/service-info-list.do"><i class="icon-user"></i>服务查找</a></li>
                <li><a href="${ctx}/service/my-service-info-list.do"><i class="icon-user"></i>服务注册管理</a></li>
                <li><a href="${ctx}/service/chart-service.do"><i class="icon-user"></i>服务统计</a></li>
                <li><a href="${ctx}/service/service-info-apply-history.do"><i class="icon-user"></i>历史服务查找</a></li>
               </ul>
        </div>
    </div>

    <!--管线相关资源管理-->
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#m-sidebar" href="#collapse-pipeResource">
                <i class="icon-user"></i>
                <span class="title">管线相关资源管理</span>
            </a>
        </div>
        <div id="collapse-pipeResource" class="accordion-body collapse ${currentMenu == 'pipeResource' ? 'in' : ''}">
            <ul class="accordion-inner nav nav-list">
                <li class="m-icn-view-users"><a href="${ctx}/pipe/resource/pipe-resource-info-list.do">管线相关资源查询</a></li>
                <li class="m-icn-view-users"><a href="${ctx}/pipe/resource/pipe-resource-apply-history.do">历史下载申请查询</a></li>
                <li class="m-icn-view-users"><a href="${ctx}/pipe/resource/my-pipe-resource-info-list.do">管线相关资源管理</a></li>
            </ul>
        </div>
    </div>

    <!--管线业务文件管理-->
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#m-sidebar" href="#collapse-pipeFile">
                <i class="icon-user"></i>
                <span class="title">管线业务文件管理</span>
            </a>
        </div>
        <div id="collapse-pipeFile" class="accordion-body collapse ${currentMenu == 'pipeFile' ? 'in' : ''}">
            <ul class="accordion-inner nav nav-list">
                <li class="m-icn-view-users"><a href="${ctx}/pipe/file/pipe-file-info-list.do">管线业务文件查询</a></li>
                <li class="m-icn-view-users"><a href="${ctx}/pipe/file/pipe-file-apply-history.do">历史下载申请查询</a></li>
                <li class="m-icn-view-users"><a href="${ctx}/pipe/file/my-pipe-file-info-list.do">管线业务文件管理</a></li>
            </ul>
        </div>
    </div>

    <!--用户权限管理-->
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#m-sidebar" href="#collapse-auth">
                <i class="icon-user"></i>
                <span class="title">用户权限管理</span>
            </a>
        </div>
        <div id="collapse-auth" class="accordion-body collapse ${currentMenu == 'auth' ? 'in' : ''}">
            <ul class="accordion-inner nav nav-list">
                <!--TODO LIST-->
                <!--连接到运维管理服务分系统-->
                <li class="m-icn-view-users"><a href="${ctx}/workcal/workcal-type-list.do">用户权限管理</a></li>
            </ul>
        </div>
    </div>


    <!--数据备份与还原-->
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#m-sidebar" href="#collapse-backup">
                <i class="icon-user"></i>
                <span class="title">系统数据维护</span>
            </a>
        </div>
        <div id="collapse-backup" class="accordion-body collapse ${currentMenu == 'backup' ? 'in' : ''}">
            <ul class="accordion-inner nav nav-list">
                <li class="m-icn-view-users"><a href="${ctx}/backup/backup-info-list.do">数据备份与还原</a></li>
            </ul>
        </div>
    </div>

    <!--审核模块-->
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#m-sidebar" href="#collapse-verify">
                <i class="icon-user"></i>
                <span class="title">审核管理</span>
            </a>
        </div>
        <div id="collapse-verify" class="accordion-body collapse ${currentMenu == 'verify' ? 'in' : ''}">
            <ul class="accordion-inner nav nav-list">
                <li class="m-icn-view-users"><a href="${ctx}/service/service-info-apply-list.do">服务申请审核</a></li>
                <li class="m-icn-view-users"><a href="${ctx}/pipe/resource/pipe-resource-apply-list.do">管线相关资源下载审核</a></li>
                <li class="m-icn-view-users"><a href="${ctx}/pipe/file/pipe-file-apply-list.do">管线业务文件下载审核</a></li>
            </ul>
        </div>
    </div>

    <!--日志管理-->
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#m-sidebar" href="#collapse-log">
                <i class="icon-user"></i>
                <span class="title">日志管理</span>
            </a>
        </div>
        <div id="collapse-log" class="accordion-body collapse ${currentMenu == 'log' ? 'in' : ''}">
            <ul class="accordion-inner nav nav-list">
                <li class="m-icn-view-users"><a href="${ctx}/syslog/log-info-list.do">日志记录</a></li>
            </ul>
        </div>
    </div>

    <footer id="m-footer" class="text-center">
        <hr>
        &copy;CASIC203
    </footer>
</aside>
<!-- end of sidebar -->
