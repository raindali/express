<?php $this->need('header.php'); ?>
	

	
<div class="columns">
        <ul class="posts" id="posts">
			<li class="posts-li">
				<div class="posts-box">
					<div class="posts-box-side">
						<div class="posts-box-side-type"></div>
					</div>
					<div class="posts-box-content">
					<h2>404 - <?php _e('页面没找到'); ?></h2>
					非常抱歉该页面已经无法找到。原本在此的页面已经被删除。
					</div>

				</div>
				<div class="posts-break"></div>
			</li>

        </ul>
	
	<div class="sidebar">
		<a class="sidebar-box portrait" href="/"><img alt="PAGES" src="http://assets.tumblr.com/images/default_avatar_128.gif" /></a>
<form action="/search" class="sidebar-box search" method="get">
	<input type="text" id="search-input" name="q" value="Lost something in the post?*" onfocus="clearText('Lost something in the post?*', this.value, this.id);" onblur="fillText('Lost something in the post?*', this.value, this.id);" />
</form>
<ul class="sidebar-box sidebar-buttons">
	<li class="FOLLOW">
		<a href="http://tumblr.com/follow/tgcho" target="_blank">
			<em></em>
				<span>
					<strong class="FOLLOW-text">Follow On</strong>
					<strong class="FOLLOW-logo"></strong>
				</span>
		</a>
	</li>	
</ul>
	</div>
</div><!-- end .c0lumns-->
<?php $this->need('footer.php'); ?>
