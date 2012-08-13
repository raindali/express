<?php $this->need('header.php'); ?>
<div class='container'>
	<div id='main' role='main'>
	<div class='wrapper'>
		<div class='posts'>	  
		<div class='wrapper wrapperabc'>
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
						<h3><?php $this->title() ?></h3>
					</div>
					<div class='post-content'>
						<?php $this->content(); ?>
					</div><?php $this->need('comments.php'); ?>
				</div><!-- .post-regular -->			
		</div>
		</div><!-- .posts -->
	</div><!-- .wapper -->
	</div><!-- #main -->
<?php $this->need('sidebar.php'); ?> 
</div><!-- #container -->
<?php $this->need('footer.php'); ?> 