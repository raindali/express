<?php $this->need('header.php'); ?>
<div class="columns">
        <ul class="posts" id="posts">
			<li class="posts-li">
				<div class="posts-box">
					<div class="posts-box-side">
						<div class="posts-box-side-type"></div>
							<a class="posts-box-side-perma"></a>
					</div>
					<div class="posts-box-content">
					<h2><?php $this->title() ?></h2>
					<?php $this->content(''); ?>
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
			<li>
				<script>
				var linkwithin_site_id = 810491;
				</script>
				<script src="http://www.linkwithin.com/widget.js"></script>
				<a href="http://www.linkwithin.com/"><img src="http://www.linkwithin.com/pixel.png" alt="Related Posts Plugin for WordPress, Blogger..." style="border: 0" /></a>
			</li>
			<li>
				<div class="posts-box">
					<div class="posts-box-side">
						<div class="posts-box-side-type posts-box-side-comment"></div>
					</div>
					<div class="posts-box-content">
						<?php $this->need('comments.php'); ?>
					</div>
				</div>
			</li>
        </ul>
	
	<div class="sidebar">
		<?php $this->need('sidebar.php'); ?>
	</div>
</div><!-- end .c0lumns-->
<?php $this->need('footer.php'); ?>

