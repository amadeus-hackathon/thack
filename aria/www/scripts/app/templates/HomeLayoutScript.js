Aria.tplScriptDefinition({
    $classpath : "app.templates.HomeLayoutScript",
	$constructor: function(){
		var myScroll;
		this.data = {};
	},    
	$prototype : {
		$displayReady: function(){
			myScroll = new iScroll('wrapper');
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
			$(".mask, .popUp, #loading").hide();
					
			$(".close").click(function(){
				$(".mask, .popUp").hide();
			})
		},
		facebookData: function(){
			$("#loading").show();
			var myData = {};
			myData.title = "Inspired by friends";
			myData.desc = "The top three locations where several of your Facebook friends have recently enjoyed visiting,"
				+"and we're offering you great deals and offers to try it yourself";
			myData.list = new Array();
			myData.list[0] = {};
			myData.list[0].title = "Paris, France";
			myData.list[0].desc = "Visited by 5 of your friends";
			
			myData.list[1] = {};
			myData.list[1].title = "Nice, France";
			myData.list[1].desc = "Visited by 3 of your friends";
			
			myData.list[2] = {};
			myData.list[2].title = "New Delhi, India";
			myData.list[2].desc = "Visited by 1 of your friends";
			
			this.$json.setValue(this.data, "result", myData);
			this.$refresh({
			    outputSection : "popuplist"
			});			
			$(".mask, .popUp").show();
		},
		sessData: function(){
			$("#loading").show();
			var myData = {};
			myData.title = "Seasonal inspirations";
			myData.desc = "We know it is the time of the year, when you have an opportunity to take a break from your hectic life"
				+" and escape to an exotic or tranquil places. Here is a great list of offers for such places to visit.";
			myData.list = new Array();
			myData.list[0] = {};
			myData.list[0].title = "Paris";
			myData.list[0].desc = "France";
			
			myData.list[1] = {};
			myData.list[1].title = "Nice";
			myData.list[1].desc = "France";
			
			myData.list[2] = {};
			myData.list[2].title = "New Delhi";
			myData.list[2].desc = "India";
			
			this.$json.setValue(this.data, "result", myData);
			this.$refresh({
			    outputSection : "popuplist"
			});			
			$(".mask, .popUp").show();
		},
		wellData: function(){
			$("#loading").show();
			var myData = {};
			myData.title = "Well being inspirations";
			myData.desc = "It seems you are likely to seek holidays with a specific focus, for example, wellbeing/medical tourism,"
				+ "  learning/cultural holidays and ethical voyages."
				+ " Here is a list of such great occasions which you would like to consider.";
			myData.list = new Array();
			myData.list[0] = {};
			myData.list[0].title = "Well Paris";
			myData.list[0].desc = "France";
			
			myData.list[1] = {};
			myData.list[1].title = "Nice Well";
			myData.list[1].desc = "France";
			
			myData.list[2] = {};
			myData.list[2].title = "New Delhi";
			myData.list[2].desc = "India";
			
			this.$json.setValue(this.data, "result", myData);
			this.$refresh({
			    outputSection : "popuplist"
			});			
			$(".mask, .popUp").show();
		}
	}
})