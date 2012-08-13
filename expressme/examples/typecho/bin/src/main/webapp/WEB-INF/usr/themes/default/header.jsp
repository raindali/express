<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://gmpg.org/xfn/11">
<meta http-equiv="content-type" content="text/html; charset=${options:value("charset")}" />
<title><?php $this->archiveTitle(' &raquo; ', '', ' - '); ?>${options:value("title")}</title>

<!-- 使用url函数转换相关路径 -->
<link rel="stylesheet" type="text/css" media="all" href="${options:themeUrl("style.css")}" />

<!-- 通过自有函数输出HTML头部信息 -->
<?php $this->header(); ?>
</head>

<body>
<div id="header" class="container_16 clearfix">
	<form id="search" method="post" action="/">
		<div><input type="text" name="s" class="text" size="20" /> <input type="submit" class="submit" value="${typecho:_e("搜索")}" /></div>
    </form>
	<div id="logo">
	    <h1><a href="${options:value("siteUrl")}">
	    <c:if test="${options:value("logoUrl") not null}">
        <img height="60" src="${options:value("logoUrl")" alt="${options:value("title")" />
        </c:if>
        ${options:value("title")}
        </a></h1>
	    <p class="description">${options:value("description")}</p>
    </div>
</div><!-- end #header -->

<div id="nav_box" class="clearfix">
<ul class="container_16 clearfix" id="nav_menu">
    <li<?php if($this->is('index')): ?> class="current"<?php endif; ?>><a href="${options:value("siteUrl")}">${typecho:_e("搜索")}</a></li>
    <?php $this->widget('Widget_Contents_Page_List')->to($pages); ?>
    <?php while($pages->next()): ?>
    <li<?php if($this->is('page', $pages->slug)): ?> class="current"<?php endif; ?>><a href="<?php $pages->permalink(); ?>" title="<?php $pages->title(); ?>"><?php $pages->title(); ?></a></li>
    <?php endwhile; ?>
</ul>
</div>

<div class="container_16 clearfix">
