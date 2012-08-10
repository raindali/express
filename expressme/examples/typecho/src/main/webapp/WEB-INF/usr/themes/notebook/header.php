<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://gmpg.org/xfn/11">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<?php if ($this->is('index')): ?>
<title><?php $this->options->title(); ?></title>
<?php else: ?>
<title><?php $this->archiveTitle('','',' - '); ?><?php $this->options->title(); ?></title>
<?php endif; ?>

<link rel="stylesheet" type="text/css" media="all" href="<?php $this->options->themeUrl('style.css'); ?>" />
<?php $this->header(); ?>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js" type="text/javascript"></script>

</head>
<body>

 <div id='header'><div class='container'>
      <div id='nav'>
        <ul>
          <li id='nav_home'><a href="http://www.pagecho.com">HOME</a></li>
          <li id='nav_archives'><a href="http://www.pagecho.com/about">ABOUT</a></li>
          <li id='nav_subscribe'><a href="http://www.pagecho.com/feed">Subscribe</a></li>
        </ul>
      </div>
</div></div><!-- #header -->


	
	
