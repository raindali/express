<?php $this->need('header.php'); ?>

  <div class="columns">
        <ul class="posts" id="posts">
			<li class="posts-li">
				<div class="posts-box">
					<div class="posts-box-side">
					</div>
					<div class="posts-box-content">
					<h2><h2>正在查看包含 “ <?php $this->archiveTitle(' &raquo; ','',''); ?> ” 的内容</h2></h2>
					</div>
				</div>
				<div class="posts-break"></div>
			</li>
			<?php while($this->next()): ?>
			<li class="posts-li">
				<div class="posts-box">
					<div class="posts-box-side">
						<div class="posts-box-side-type"></div>
							<a class="posts-box-side-perma"></a>
					</div>
					<div class="posts-box-content">
					<h2><a href="<?php $this->permalink() ?>"><?php $this->title() ?></a></h2>
					<?php $this->content('阅读剩余部分...'); ?>
										<div class="posts-box-content-details">
						<div class="posts-box-content-details-upper">
							<div class="posts-box-content-timestamp">
								<span class="posts-box-content-details-icon"></span>
									<p><?php $this->date('F j'); ?></p>
							</div>
						</div>
						<div class="posts-box-content-tags">
							<span class="posts-box-content-details-icon"></span>
							<p>Tags: <?php $this->tags(',', true, 'none'); ?></p>
						</div>
						<div class="posts-box-content-comments">
							<span class="posts-box-content-details-icon"></span>
							<p>With <?php $this->views(); ?> Views and <?php $this->commentsNum('No Comments', '1 Comment', '%d Comments'); ?></p>
						</div>
					</div>
					</div>

				</div>
				<div class="posts-break"></div>
			</li>
			<?php endwhile; ?>
			<li class="nav">
				<span class="prev"><?php $this->pageLink('','prev'); ?></span>
				<span class="next"><?php $this->pageLink('','next'); ?></span>
			</li>
        </ul>
	
	<div class="sidebar">
		<?php $this->need('sidebar.php'); ?>
	</div>
</div><!-- end .c0lumns-->
<?php $this->need('footer.php'); ?>
