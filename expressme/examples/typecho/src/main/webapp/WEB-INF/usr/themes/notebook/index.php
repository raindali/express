<?php
/**
 * 移植于tumblr的主题, 原作者：http://notebook-theme.tumblr.com/
 * 
 * @package noteplus
 * @author cho
 * @version 1.0
 * @link http://www.pagecho.com
 */
 
 $this->need('header.php');
 ?>
<div class='container'>
	<div id='main' role='main'>
	<div class='wrapper'>
		<div class='posts'>	  
		<div class='wrapper'>
			<?php while($this->next()): ?>
				<div class='post-regular'>
					<div class='post-header'>
						<div class='date'>
							<div class='day'>
								<?php $this->date('j'); ?>
							</div>
							<div class='month'>
								<?php $this->date('F'); ?>
							</div>
						</div>
						<h3><a href="<?php $this->permalink() ?>"><?php $this->title() ?></a></h3>
					</div>
					<div class='post-content'>
					<?php $this->content('阅读剩余部分...'); ?>
					</div>
					<div class='post-meta'>
						<div class='tags'><?php $this->views(); ?>次阅读<?php $this->commentsNum('但木有评论', '和一个评论', '和%d个评论'); ?>
						</div>
					</div><!-- .post-meta -->
				</div><!-- .post-regular --><?php endwhile; ?>
		
<div class='paging'>		
		<div class="navigation navbottom">
			<?php $this->pageNav(); ?>
		</div>
 </div>

		</div>
		</div><!-- .posts -->
	</div><!-- .wapper -->
	</div><!-- #main -->
<?php $this->need('sidebar.php'); ?> 
</div><!-- #container -->
<?php $this->need('footer.php'); ?> 