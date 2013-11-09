Aria.tplScriptDefinition({
    $classpath : "app.templates.DetailsLayoutScript",
	$constructor: function(){
	
	},    
	$prototype : {
		$dataReady: function() {
			console.log('Details Layout');
		},
		
		$displayReady: function() {
			
			var detTpl = this;
			
			// create iscroll
			detTpl.data.myScroll = new iScroll('wrapper');
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			
			// refresh iscroll whenever change in orientation
			$(window).bind('orientationchange', function() {
				detTpl.data.myScroll.refresh();
			});
			
			// reset UI
			detTpl.hidePopup();
			detTpl.packageClicked();
		},
		
		showPopup: function(events, args) {
			$(".popup2, .mask").show();
		},
		
		hidePopup: function(events, args) {
			$(".popup2, .mask").hide();
		},
		
		eventClicked: function(events, args) {
			$("#eventslist").show();
			$("#packageList").hide();
			$("#packages").removeClass("selected");
			$("#events").addClass("selected");
			this.data.myScroll.refresh()
		},
		
		packageClicked: function(events, args) {
			$("#eventslist").hide();
			$("#packageList").show();
			$("#packages").addClass("selected");
			$("#events").removeClass("selected");
			this.data.myScroll.refresh();
		}
 	}
})