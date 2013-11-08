Aria.tplScriptDefinition({
    $classpath : "app.templates.DetailsLayoutScript",
	$constructor: function(){
		var myScroll;
	},    
	$prototype : {
		$displayReady: function(){
			myScroll = new iScroll('wrapper');
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
		},
		goHome: function(e){
			e.preventDefault();
			pageEngine.navigate({"pageId": "HOME"});
		},
		bookme: function(){
			alert("coming soon");
		},
		dummyData: function(){
			var myData = {};
			myData
		}
	}
})