Aria.tplScriptDefinition({
    $classpath : "app.templates.DetailsLayoutScript",
	$constructor: function(){
	},    
	$prototype : {
		$dataReady: function() {
			var myScroll;
			console.log(this.data);
		},
		
		$displayReady: function(){			
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			document.addEventListener('DOMContentLoaded', function () { setTimeout(function(){myScroll = new iScroll('wrapper');}, 200); }, false);		
		}

 	}
})