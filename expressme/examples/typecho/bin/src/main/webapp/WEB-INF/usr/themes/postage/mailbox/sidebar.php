<a class="sidebar-box portrait" href="/"><img alt="PAGES" src="http://assets.tumblr.com/images/default_avatar_128.gif" /></a>
<form action="/search" class="sidebar-box search" method="get">
    <input id="search-input" type="text" name="s" class="text" size="20"  value="Search" onfocus="if (this.value == 'Search') this.value = ''" onblur="if (this.value == '') this.value = 'Search'"/>
</form>
<ul class="sidebar-box sidebar-buttons">
	<li class="FOLLOW">
		<a href="http://page.sinaapp.com/feed" target="_blank">
			<em></em>
				<span>
					<strong class="FOLLOW-text">文章 FEED 订阅</strong>
				</span>
		</a>
	</li>	
</ul>

<div class="sidebar-box">
	<h3><?php _e('最新文章'); ?></h3>
	<div class="sidecontent"> 
		<ul>
                <?php $this->widget('Widget_Contents_Post_Recent')
                ->parse('<li><a href="{permalink}">{title}</a></li>'); ?>
        </ul>
	</div>
</div>
<div class="sidebar-box">
	<h3><?php _e('最近回复'); ?></h3>
	<div class="sidecontent"> 
		<ul class="recentcomment">
            <?php $this->widget('Widget_Comments_Recent','ignoreAuthor=true')->to($comments); ?>
            <?php while($comments->next()): ?>
                <li><a href="<?php $comments->permalink(); ?>"><?php $comments->author(false); ?></a>: <?php $comments->excerpt(8, '...'); ?></li>
            <?php endwhile; ?>
         </ul>
	</div>
</div>
<?php if ($this->is('index')): ?>
<div class="sidebar-box">
	<h3><?php _e('友情链接'); ?></h3>
	<div class="sidecontent"> 
		<ul class="friendlink">
			<li><a href="http://blog.summerfly.cn/">SUMMERFLY</a></li>
			<li><a href="http://www.webcoeus.com/">WEBCOEUS</a></li>
			<li><a href="http://yun.be/">AFIL'S</a></li>
			<li><a href="http://gryu.net/">GRYU</a></li>
        </ul>
	</div>
</div>
<?php endif; ?>
<div class="sidebar-box">
	<h3><?php _e('文章归档'); ?></h3>
	<div class="sidecontent"> 
		<ul>
             <?php $this->widget('Widget_Contents_Post_Date', 'type=month&format=F Y')
                ->parse('<li><a href="{permalink}">{date}</a></li>'); ?>
         </ul>
	</div>
</div>

