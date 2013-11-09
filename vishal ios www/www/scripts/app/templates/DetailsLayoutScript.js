Aria.tplScriptDefinition({
    $classpath : "app.templates.DetailsLayoutScript",
	$constructor: function(){
	},    
	$prototype : {
		$dataReady: function() {
			this.data.events = [1,2,3];
			this.data.packages = [1,2,3,4,5];
		},
		
		$displayReady: function(){
			
			var detTpl = this;
			
			// create iscroll
			detTpl.data.myScroll = new iScroll('wrapper');
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			
			// reset scroller on orientation change
			$(window).bind('orientationchange', function() {
				detTpl.data.myScroll.refresh();
			});
			
			// reset UI
			this.hidePopup();
			this.packageClicked();
		},
		
		packageClicked: function(event, args) {
			$("#eventslist").hide();
			$("#packageList").show();
			$("#packages").addClass("selected");
			$("#events").removeClass("selected");
			this.data.myScroll.refresh()
		},
	
		eventClicked: function(event, args) {
			$("#eventslist").show();
			$("#packageList").hide();
			$("#packages").removeClass("selected");
			$("#events").addClass("selected");
			this.data.myScroll.refresh()
		},
		
		showPopup: function(event, args) {
			$(".popup2, .mask").show();
		},
		
		hidePopup: function(event, args) {
			$(".popup2, .mask").hide();
		}
 	}
})