 <div id='sidebar'>
      <div class="tit"><h2>标题</h2></div>
      <div class="info">
        <div class="avatar" style='background: url(http://127.0.0.1/usr/themes/noteplus/images/p.jpg) no-repeat;'></div>
        <p class="first">（请在sidebar.php里面修改这里的一段文字和上面的图片，以及最上面的h2红底的文字）</p>
      </div>

      <div class="social">
        <ul>
          <li id="douban" target="_blank"><a rel="nofollow" href="http://www.douban.com/people/pagecho/">豆瓣主页</a></li>
          <li id="mail"><a rel="nofollow">邮件:CHO#MSN.CN</a></li>
        </ul>
      </div>
        <div class="recentpost">
			<h4>POSTS</h4>
	  <ul class="list">
		<?php $this->widget('Widget_Contents_Post_Recent')->to($recentPost); ?>
		<?php while($recentPost->next()): ?>
			<li><a href="<?php $recentPost->permalink() ?>"><?php $recentPost->date("F.d"); ?> <?php $recentPost->title() ?></a></li>
		<?php endwhile; ?>
		</ul>
	  </div>
	  <div class="recentcomment">
		<h4>COMMENTS</h4>
           <ul class="list">
            <?php $this->widget('Widget_Comments_Recent','ignoreAuthor=true')->to($comments); ?>
            <?php while($comments->next()): ?>
                <li><a href="<?php $comments->permalink(); ?>"><?php $comments->author(false); ?></a>: <?php $comments->excerpt(15, '...'); ?></li>
            <?php endwhile; ?>
            </ul>
	    </div>	  

<?php if ($this->is('index')): ?>
       <h4>FRIENDS</h4>
    <div class="pages">
      <ul class="list">
<li><a href="http://page.sinaapp.com/">PAGECHO</a></li>
        </ul>
      </div><!-- .pages -->
<?php endif; ?>
</div>